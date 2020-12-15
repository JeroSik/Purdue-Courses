#include <map>
#include <string.h>
#include "node.cc" 
#include <iostream>

using namespace std;

class interpret{
    /******************************************************************************************************************************************************
     *                                                                   GLOBAL VARIABLES                                                                 *
     ******************************************************************************************************************************************************/
    vector<map<string,data>> symbol_table;
    map<string,c_data> class_table;
    vector<data> command_line_args;
    string curr_class;
    int scope_level;

    /******************************************************************************************************************************************************
     *                                                                INITIAL PROGRAM INTERPRET                                                           *
     ******************************************************************************************************************************************************/
    void initial_program_interpret(node * program) {
        // Add all classes to table
        node * classDeclList = program->children.at(1);
        while (classDeclList->nodeType != "Empty") {
            initial_class_interpret(classDeclList->children.at(0));            
            classDeclList = classDeclList->children.at(1);
        }

        // Add global variables and methods to table for each class
        classDeclList = program->children.at(1);
        while (classDeclList->nodeType != "Empty") {
            // Create a level for symbol table scope
            map<string, data> temp_symbol_table;
            symbol_table.push_back(temp_symbol_table);
            scope_level++;

            interpret_varList_and_methodList(classDeclList->children.at(0));

            // Remove level for symbol table scope
            symbol_table.pop_back();
            scope_level--;

            classDeclList = classDeclList->children.at(1);
        }
    }

    void initial_class_interpret(node * classDecl) {
        // Get class name
        string class_id = classDecl->data.value.stringValue;

        // Create empty class instance and add to table
        struct c_data c_info;
        class_table.insert(pair<string, c_data>(class_id, c_info));
    }

    void interpret_varList_and_methodList(node * classDecl) {
        // Get class name, global variables list and methods list
        string class_id = classDecl->data.value.stringValue;
        node * varDeclList = classDecl->children.at(1);
        node * methodDeclList = classDecl->children.at(2);

        // Add all variable delcarations to stack
        vector<node*> varDeclStack;
        while (varDeclList->nodeType != "Empty") {
            varDeclStack.push_back(varDeclList->children.at(1));
            varDeclList = varDeclList->children.at(0);
        }

        // Interpret global variable declarations in reverse order
        for (int i = varDeclStack.size() - 1; i >= 0; i--) {
            interpretVarDecl(varDeclStack[i]);
        }

        // Insert global variables into class table
        class_table[class_id].varMap = symbol_table[scope_level];

        // Iterate through all methods
        while (methodDeclList->nodeType != "Empty") {
            // Get method name and method declaration
            node * methodDecl = methodDeclList->children.at(0);
            string method_id = methodDecl->data.value.stringValue;

            // Create empty method instance
            struct m_data m_info;

            // Get method arguments and location
            m_info.argument_ids = interpretOptionalFormatList(methodDecl->children.at(1));
            m_info.statementLocation = methodDecl->children.at(2);

            // Insert method into class table
            class_table[class_id].methodMap.insert(pair<string, m_data>(method_id, m_info));

            methodDeclList = methodDeclList->children.at(1);
        }
    }

    vector<string> interpretOptionalFormatList(node * optionalFormatList) {
        vector<string> method_arg_ids;

        if (optionalFormatList->nodeType == "Empty") {
            return method_arg_ids;
        }

        node * formalList = optionalFormatList->children.at(0);

        string arg_id = formalList->data.value.stringValue;
        method_arg_ids.push_back(arg_id);

        node * formalsTail = formalList->children.at(1);
        while (formalsTail->nodeType != "Empty") {
            arg_id = formalsTail->data.value.stringValue;
            method_arg_ids.push_back(arg_id);
            formalsTail = formalsTail->children.at(1);
        }

        return method_arg_ids;
    }

    /******************************************************************************************************************************************************
     *                                                                     INTERPRET PROGRAM                                                              *
     ******************************************************************************************************************************************************/
    void interpretMain(node * mainClass) {
        // Set current class as main class
        curr_class = mainClass->data.value.stringValue;

        // Create a empty level for symbol table scope to include global variables
        map<string, data> fake_symbol_table;
        symbol_table.push_back(fake_symbol_table);
        scope_level++;

        // Create a level for symbol table scope for methods
        map<string, data> temp_symbol_table;
        symbol_table.push_back(temp_symbol_table);
        scope_level++;

        // Add main method argument variable to symbol table
        string main_argument_id = mainClass->children.at(0)->data.value.stringValue;
        struct data main_argument_data = {.type=data::type_t::arrayType, .arrayValue=command_line_args};
        symbol_table[scope_level].insert(pair<string, data>(main_argument_id, main_argument_data));

        // Check statement list
        interpretStatementList(mainClass->children.at(1));

        // Remove boths levels for symbol table
        symbol_table.pop_back();
        scope_level--;
        symbol_table.pop_back();
        scope_level--;
    }

    data interpretStatementList(node * statementList) {
        struct data return_data;

        // Iterate through statement list and check each statement
        while (statementList->nodeType != "Empty") {
            return_data = interpretStatement(statementList->children.at(0));
            statementList = statementList->children.at(1);
        }

        return return_data;
    }

    data interpretStatement(node * statement) {
        // Get statement type
        string statementType = statement->nodeType;

        if (statementType.find("print") != string::npos) {
            // Evalulate expression within print statement
            data exp = interpretExp(statement->children.at(0));

            // Print to standard out according to message
            if (exp.type == data::type_t::stringType) {
                string output = string(exp.value.stringValue);
                cout << output;
            } else if (exp.type == data::type_t::intType) {
                cout << exp.value.intValue;
            } else if (exp.type == data::type_t::booleanType) {
                if (exp.value.booleanValue) {
                    cout << "true";
                } else {
                    cout << "false";
                }
            }

            // Print new line if needed
            if (statementType.find("println") != string::npos) {
                cout << endl;
            }
        } else if (statementType == "Statement - VarDecl") {
            // Interpret variable declaration
            interpretVarDecl(statement->children.at(0));
        } else if (statementType == "Statement - id_equals_exp") {
            // Interpret basic variable assignment
            interpretIDAssignment(statement->data.value.stringValue, statement->children.at(0));
        } else if (statementType == "Statement - leftValue_equals_exp") {
            // Interpret complex variable assignment
            interpretLeftValueAssignment(statement->children.at(0), statement->children.at(1));
        } else if (statementType == "Statement - if") {
            // if condition
            data d = interpretExp(statement->children.at(0));

            // Create a level for symbol table scope
            map<string, data> temp_symbol_table;
            symbol_table.push_back(temp_symbol_table);
            scope_level++;

            if (d.value.booleanValue == true) {
                interpretStatement(statement->children.at(1));
            } else {
                interpretStatement(statement->children.at(2));
            }

            // Remove level for symbol table scope
            symbol_table.pop_back();
            scope_level--;
        } else if (statementType == "Statement - while") {
            // Check loop condition
            data d = interpretExp(statement->children.at(0));

            while (d.value.booleanValue == true) {
                // Create a level for symbol table scope
                map<string, data> temp_symbol_table;
                symbol_table.push_back(temp_symbol_table);
                scope_level++;

                interpretStatement(statement->children.at(1));
                d = interpretExp(statement->children.at(0));

                // Remove level for symbol table scope
                symbol_table.pop_back();
                scope_level--;
            }
        } else if (statementType == "Statement - return") {
            return interpretExp(statement->children.at(0));
        } else if (statementType == "Statement - MethodCall") {
            return interpretMethodCall(statement->children.at(0));
        } else if (statementType == "Statement - StatementList") {
            // Check statement list
            interpretStatementList(statement->children.at(0));
        } else {
            cout << "   [typecheck.cc - checkStatement] Unexpected statement type\n";
        }

        struct data void_data;
        return void_data;
    }

    void interpretVarDecl(node * varDecl) {
        // Get LHS type and variable name
        struct data lhs_type = getLHSType(varDecl->children.at(0));
        string id = varDecl->data.value.stringValue;

        // Check if variable is initialized
        node * varInit = varDecl->children.at(1);
        struct data d;
        if (varInit->nodeType != "Empty") {
            d = interpretExp(varInit->children.at(0));
        } else {
            d = lhs_type;
        }
        addToScope(id, d, true);

        // Iterate through varDeclTail and check potential multi-declarations
        node * varDeclTail = varDecl->children.at(2);
        while (varDeclTail->nodeType != "Empty") {
            // Get name of variable
            id = varDeclTail->data.value.stringValue;

            // Check if variable is initialized
            varInit = varDeclTail->children.at(0);
            if (varInit->nodeType != "Empty") {
                d = interpretExp(varInit->children.at(0));
            } else {
                d = lhs_type;
            }
            addToScope(id, d, true);

            varDeclTail = varDeclTail->children.at(1);
        }
    }

    void interpretIDAssignment(string id, node * exp) {
        data exp_data = interpretExp(exp);

        addToScope(id, exp_data, false);
    }

    void interpretLeftValueAssignment(node * leftValue, node * exp) {
        // Get leftValue type
        string leftValueType = leftValue->nodeType;
        data exp_data = interpretExp(exp);

        if (leftValueType == "LeftValue - leftValue_index") {
            //TODO:
        } else if (leftValueType == "LeftValue - id_index") {
            string id = leftValue->data.value.stringValue;

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(id) != symbol_table[i].end()) {
                    vector<int> array_indices = getIndex(leftValue->children.at(0));

                    if (array_indices.size() == 1) {
                        symbol_table[i][id].arrayValue[array_indices[0]] = exp_data;
                    } else {
                        symbol_table[i][id].arrayValue[array_indices[0]].arrayValue[array_indices[1]] = exp_data;
                    }
                }
            }
        } else if (leftValueType == "LeftValue - leftValue_dot_id") {
            string obj_id = getObjFromLeftValue(leftValue->children.at(0));
            string field_id = leftValue->data.value.stringValue;

            class_table[obj_id].varMap[field_id] = exp_data;
        } else if (leftValueType == "LeftValue - id_dot_id") {
            string var_id = leftValue->data.value.stringValue;
            string field_id = leftValue->children.at(0)->data.value.stringValue;

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(var_id) != symbol_table[i].end()) {
                    data id_data = symbol_table[i].find(var_id)->second;
                    class_table[id_data.objValue].varMap[field_id] = exp_data;
                    return;
                }
            }
        } else if (leftValueType == "LeftValue - this_dot_id") {
            string id = leftValue->data.value.stringValue;
            data id_data = symbol_table[0].find(id)->second;
            class_table[curr_class].varMap[id] = exp_data;
            symbol_table[0][id] = exp_data;
        }
    }

    data interpretMethodCall(node * methodCall) {
        struct data return_data;

        // Store current scope details
        vector<map<string,data>> old_scope = symbol_table;
        int old_scope_level = scope_level;
        string old_class = curr_class;

        // Check method call type
        string methodCallType = methodCall->nodeType;

        cout << "   " << methodCallType << "\n";
        if (methodCallType == "MethodCall - ID") {
            // Get relevant info
            string method_id = methodCall->data.value.stringValue;
            vector<data> user_args = interpretOptionalExpList(methodCall->children.at(0));
            m_data method_data = class_table[curr_class].methodMap[method_id];
            
            // Reset scope
            symbol_table.clear();
            scope_level = -1;

            // Add global variabels to scope
            symbol_table.push_back(class_table[curr_class].varMap);
            scope_level++;

            // Create a level for symbol table scope for methods
            map<string, data> temp_symbol_table;
            symbol_table.push_back(temp_symbol_table);
            scope_level++;

            // Add method arguments to scope
            vector<string> arg_ids = method_data.argument_ids;
            for (int i = 0; i < user_args.size(); i++) {
                symbol_table[scope_level].insert(pair<string, data>(arg_ids[i], user_args[i]));
            }

            return_data = interpretStatementList(method_data.statementLocation);

            // Remove levels from scope
            symbol_table.pop_back();
            scope_level--;
            symbol_table.pop_back();
            scope_level--;
        } else if (methodCallType == "MethodCall - LeftValue") {
            node * leftValue = methodCall->children.at(0);
            vector<data> user_args = interpretOptionalExpList(methodCall->children.at(1));
            cout << "   " << leftValue->nodeType << "\n";

            if (leftValue->nodeType == "LeftValue - leftValue_dot_id") {
                // Get relevant info
                string obj_id = getObjFromLeftValue(leftValue->children.at(0));
                string method_id = leftValue->data.value.stringValue;
                m_data method_data = class_table[obj_id].methodMap[method_id];

                // Reset scope
                symbol_table.clear();
                scope_level = -1;
                curr_class = obj_id;

                // Add global variabels to scope
                symbol_table.push_back(class_table[obj_id].varMap);
                scope_level++;

                // Create a level for symbol table scope for methods
                map<string, data> temp_symbol_table;
                symbol_table.push_back(temp_symbol_table);
                scope_level++;

                // Add method arguments to scope
                vector<string> arg_ids = method_data.argument_ids;
                for (int i = 0; i < user_args.size(); i++) {
                    symbol_table[scope_level].insert(pair<string, data>(arg_ids[i], user_args[i]));
                }
                
                return_data = interpretStatementList(method_data.statementLocation);

                // Remove levels from scope
                symbol_table.pop_back();
                scope_level--;
                symbol_table.pop_back();
                scope_level--;
            } else if (leftValue->nodeType == "LeftValue - id_dot_id") {
                // Get relevant info
                string obj_id = "";
                string var_id = leftValue->data.value.stringValue;
                string method_id = leftValue->children.at(0)->data.value.stringValue;

                // Get object id corresponding to var_id
                for (int i = scope_level; i >= 0; i--) {
                    if (symbol_table[i].find(var_id) != symbol_table[i].end()) {
                        data id_data = symbol_table[i].find(var_id)->second;
                        obj_id = id_data.objValue;
                        break;
                    }
                }
                m_data method_data = class_table[obj_id].methodMap[method_id];

                // Reset scope
                symbol_table.clear();
                scope_level = -1;
                curr_class = obj_id;

                // Add global variabels to scope
                symbol_table.push_back(class_table[obj_id].varMap);
                scope_level++;

                // Create a level for symbol table scope for methods
                map<string, data> temp_symbol_table;
                symbol_table.push_back(temp_symbol_table);
                scope_level++;

                // Add method arguments to scope
                vector<string> arg_ids = method_data.argument_ids;
                for (int i = 0; i < user_args.size(); i++) {
                    symbol_table[scope_level].insert(pair<string, data>(arg_ids[i], user_args[i]));
                }

                return_data = interpretStatementList(method_data.statementLocation);

                // Remove levels from scope
                symbol_table.pop_back();
                scope_level--;
                symbol_table.pop_back();
                scope_level--;
            } else if (leftValue->nodeType == "LeftValue - new_id_parentheses_dot_id") {
                // Get relevant info
                string obj_id = leftValue->data.value.stringValue;
                string method_id = leftValue->children.at(0)->data.value.stringValue;
                m_data method_data = class_table[obj_id].methodMap[method_id];

                // Reset scope
                symbol_table.clear();
                scope_level = -1;
                curr_class = obj_id;

                // Add global variabels to scope
                symbol_table.push_back(class_table[obj_id].varMap);
                scope_level++;

                // Create a level for symbol table scope for methods
                map<string, data> temp_symbol_table;
                symbol_table.push_back(temp_symbol_table);
                scope_level++;

                // Add method arguments to scope
                vector<string> arg_ids = method_data.argument_ids;
                for (int i = 0; i < user_args.size(); i++) {
                    symbol_table[scope_level].insert(pair<string, data>(arg_ids[i], user_args[i]));
                }

                return_data = interpretStatementList(method_data.statementLocation);

                // Remove levels from scope
                symbol_table.pop_back();
                scope_level--;
                symbol_table.pop_back();
                scope_level--;
            }
        }

        // Restore scope and return method value
        symbol_table = old_scope;
        scope_level = old_scope_level;
        curr_class = old_class;
        return return_data;
    }

    vector<data> interpretOptionalExpList(node * optionalExpList) {
        vector<data> user_args;

        if (optionalExpList->nodeType == "Empty") {
            return user_args;
        }

        node * expList = optionalExpList->children.at(0);

        node * exp = expList->children.at(0);
        user_args.push_back(interpretExp(exp));

        node * expTail = expList->children.at(1);
        while (expTail->nodeType != "Empty") {
            exp = expTail->children.at(0);
            user_args.push_back(interpretExp(exp));
            expTail = expTail->children.at(1);
        }

        return user_args;
    }

    data interpretExp(node * exp) {
        // Get expression type and initialize return data struct
        string expType = exp->nodeType;
        struct data exp_data;

        // LogicalOrExp
        if (expType == "Exp - LogicalOrExp") {
            // Check expression type
            exp_data = _interpretExp(exp->children.at(0));
        } else {
            cout << "   [typecheck.cc - checkAndGetExpType] Unexpected expression type\n";
        }
        
        return exp_data;
    }

    data _interpretExp(node * exp) {
        // Get expression type
        string expType = exp->nodeType;
        cout << expType << "\n";

        // Base case reaching FACTOR
        if (expType == "Factor - INTEGER_LITERAL") {
            return exp->data;
        } else if (expType == "Factor - STRING_LITERAL") {
            return exp->data;
        } else if (expType == "Factor - BOOLEAN_LITERAL") {
            return exp->data;
        } else if (expType == "Factor - ID") {
            return getDataFromID(exp->data.value.stringValue);
        } else if (expType == "Factor - LeftValue") {
            return getLeftValue(exp->children.at(0));
        } else if (expType == "Factor - MethodCall") {
            return interpretMethodCall(exp->children.at(0));
        } else if (expType == "Factor - Integer_dot_parseInt") {
            data exp_data = _interpretExp(exp->children.at(0));
            int result = atoi(exp_data.value.stringValue);
            struct data d = {.type=data::type_t::intType, .value.intValue=result};
            return d;
        } else if (expType == "Factor - new_id") {
            string obj_id = exp->data.value.stringValue;
            struct data d = {.type=data::type_t::objType, .objValue=obj_id};
            return d;
        } else if (expType == "Factor - new_primetype_index") {
            data::type_t array_type = exp->children.at(0)->data.type;
            vector<int> array_indices = getIndex(exp->children.at(1));
            vector<data> array_data;
            struct data d;

            if (array_indices.size() == 1) {
                d.type = data::type_t::arrayType;
                for (int i = 0; i < array_indices[0]; i++) {
                    struct data array_element = {.type=array_type};
                    if (array_type == data::type_t::stringType) {
                        string empty = "";
                        char * result = new char[empty.length() + 1];
                        strcpy(result, empty.c_str());
                        array_element.value.stringValue = result;
                    } else if (array_type == data::type_t::intType) {
                        array_element.value.intValue = 0;
                    } else {
                        array_element.value.booleanValue = false;
                    }

                    array_data.push_back(array_element);
                }
            } else {
                d.type = data::type_t::multiArrayType;
                for (int i = 0; i < array_indices[0]; i++) {
                    struct data sub_array_data = {.type=data::type_t::arrayType};
                    for (int j = 0; j < array_indices[1]; j++) {
                        struct data array_element = {.type=array_type};
                        if (array_type == data::type_t::stringType) {
                            string empty = "";
                            char * result = new char[empty.length() + 1];
                            strcpy(result, empty.c_str());
                            array_element.value.stringValue = result;
                        } else if (array_type == data::type_t::intType) {
                            array_element.value.intValue = 0;
                        } else {
                            array_element.value.booleanValue = false;
                        }

                        sub_array_data.arrayValue.push_back(array_element);
                    }

                    array_data.push_back(sub_array_data);
                }
            }

            d.arrayValue = array_data;
            return d;
        } else if (expType == "Factor - leftValue_dot_length") {
            data leftValue = getLeftValue(exp->children.at(0));

            if (leftValue.type == data::type_t::stringType) {
                int string_len = string(leftValue.value.stringValue).size();
                struct data d = {.type=data::type_t::intType, .value.intValue=string_len};
                return d;
            } else if (leftValue.type == data::type_t::arrayType || leftValue.type == data::type_t::multiArrayType) {
                int array_len = leftValue.arrayValue.size();
                struct data d = {.type=data::type_t::intType, .value.intValue=array_len};
                return d;
            }
        } else if (expType == "Factor - id_dot_length") {
            string id = exp->data.value.stringValue;

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(id) != symbol_table[i].end()) {
                    data val1 = symbol_table[i].find(id)->second;
                    if (val1.type == data::type_t::stringType) {
                        int string_len = string(val1.value.stringValue).size();
                        struct data d = {.type=data::type_t::intType, .value.intValue=string_len};
                        return d;
                    } else if (val1.type == data::type_t::arrayType || val1.type == data::type_t::multiArrayType) {
                        int array_len = val1.arrayValue.size();
                        struct data d = {.type=data::type_t::intType, .value.intValue=array_len};
                        return d;
                    }
                }
            }
        } else if (expType == "Factor - PARENTHESES") {
            return _interpretExp(exp->children.at(0));
        }

        // Interpret expression on first child
        data val1 = _interpretExp(exp->children.at(0));

        // Check is expression type is unary operation
        if (expType == "UnaryExp - PLUS") {
            return val1;
        } else if (expType == "UnaryExp - MINUS") {
            int result = (- val1.value.intValue);
            struct data d = {.type=data::type_t::intType, .value.intValue=result};
            return d;
        } else if (expType == "UnaryExp - EXCLAMATION") {
            bool result = (!val1.value.booleanValue);
            struct data d = {.type=data::type_t::booleanType, .value.booleanValue=result};
            return d;
        }

        // Check expression on second child if expression is binary operation
        if (expType == "LogicalOrExp - OR") {
            data val2 = _interpretExp(exp->children.at(1));
            bool result = (val1.value.booleanValue || val2.value.booleanValue);
            struct data d = {.type=data::type_t::booleanType, .value.booleanValue=result};
            return d;
        } else if (expType == "LogicalAndExp - AND") {
            data val2 = _interpretExp(exp->children.at(1));
            bool result = (val1.value.booleanValue && val2.value.booleanValue);
            struct data d = {.type=data::type_t::booleanType, .value.booleanValue=result};
            return d;
        } else if (expType == "EqualityExp - EQUAL_EQUAL") {
            data val2 = _interpretExp(exp->children.at(1));
            bool result = (val1.value.intValue == val2.value.intValue);
            struct data d = {.type=data::type_t::booleanType, .value.booleanValue=result};
            return d;
        } else if (expType == "EqualityExp - NOT_EQUAL") {
            data val2 = _interpretExp(exp->children.at(1));
            bool result = (val1.value.intValue != val2.value.intValue);
            struct data d = {.type=data::type_t::booleanType, .value.booleanValue=result};
            return d;
        } else if (expType == "RelationalExp - GREATER") {
            data val2 = _interpretExp(exp->children.at(1));
            bool result = (val1.value.intValue > val2.value.intValue);
            struct data d = {.type=data::type_t::booleanType, .value.booleanValue=result};
            return d;
        } else if (expType == "RelationalExp - GREATER_EQUAL") {
            data val2 = _interpretExp(exp->children.at(1));
            bool result = (val1.value.intValue >= val2.value.intValue);
            struct data d = {.type=data::type_t::booleanType, .value.booleanValue=result};
            return d;
        } else if (expType == "RelationalExp - LESS") {
            data val2 = _interpretExp(exp->children.at(1));
            bool result = (val1.value.intValue < val2.value.intValue);
            struct data d = {.type=data::type_t::booleanType, .value.booleanValue=result};
            return d;
        } else if (expType == "RelationalExp - LESS_EQUAL") {
            data val2 = _interpretExp(exp->children.at(1));
            bool result = (val1.value.intValue <= val2.value.intValue);
            struct data d = {.type=data::type_t::booleanType, .value.booleanValue=result};
            return d;
        } else if (expType == "AdditiveExp - PLUS") {
            data val2 = _interpretExp(exp->children.at(1));
            if (val1.type == data::type_t::intType) {
                int result = (val1.value.intValue + val2.value.intValue);
                struct data d = {.type=data::type_t::intType, .value.intValue=result};
                return d;
            } else {
                string s1 = string(val1.value.stringValue);
                string s2 = string(val2.value.stringValue);
                // string s_result = s1.substr(0,s1.size()-1) + s2.substr(1);
                // char * result = const_cast<char *>(s_result.c_str());
                char * result = strcat(val1.value.stringValue, val2.value.stringValue);

                struct data d = {.type=data::type_t::stringType, .value.stringValue=result};
                return d;
            }
        } else if (expType == "AdditiveExp - MINUS") {
            data val2 = _interpretExp(exp->children.at(1));
            int result = (val1.value.intValue - val2.value.intValue);
            struct data d = {.type=data::type_t::intType, .value.intValue=result};
            return d;
        } else if (expType == "MultiplicativeExp - MULTIPLY") {
            data val2 = _interpretExp(exp->children.at(1));
            int result = (val1.value.intValue * val2.value.intValue);
            struct data d = {.type=data::type_t::intType, .value.intValue=result};
            return d;
        } else if (expType == "MultiplicativeExp - DIVIDE") {
            data val2 = _interpretExp(exp->children.at(1));
            int result = (val1.value.intValue / val2.value.intValue);
            struct data d = {.type=data::type_t::intType, .value.intValue=result};
            return d;
        }

        // "Exp - LogicalOrExp", "LogicalAndExp", "EqualityExp", "RelationalExp", "AdditiveExp", "MultiplicativeExp", "UnaryExp", "Factor"
        return val1;
    }

    data getLHSType(node * type) {
        struct data d;

        if (type->nodeType == "Type - PrimeType") {
            node * primetype = type->children.at(0);
            d.type = primetype->data.type;
        } else if (type->nodeType == "Type - ID") { 
            d.type = data::type_t::objType;
            d.objValue = type->data.value.stringValue;
        } else if (type->nodeType == "Type - BRACKET") {
            node * type_child = type->children.at(0);

            // Check if array is multidimensional
            if (type_child->nodeType == "Type - BRACKET") {
                d.type = data::type_t::multiArrayType;
                type_child = type_child->children.at(0);
            } else {
                d.type = data::type_t::arrayType;
            }

            if (type_child->nodeType == "Type - PrimeType") {
                struct data d_prime = {.type = type_child->children.at(0)->data.type};
                d.arrayValue.push_back(d_prime);
            }
        } else {
            cout << "   [typecheck.cc - getLHSType] Unexpected type.\n";
        }

        return d;
    }

    data getLeftValue(node * leftValue) {
        string leftValueType = leftValue->nodeType;
        cout << "   LMAO" << leftValueType << "\n";

        if (leftValueType == "LeftValue - leftValue_index") {
            data leftValue_data = getLeftValue(leftValue->children.at(0));
            vector<int> array_indices = getIndex(leftValue->children.at(1));

            if (array_indices.size() == 1) {
                return leftValue_data.arrayValue[array_indices[0]];
            } else {
                return leftValue_data.arrayValue[array_indices[0]].arrayValue[array_indices[1]];
            }
        } else if (leftValueType == "LeftValue - id_index") {
            string id = leftValue->data.value.stringValue;

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(id) != symbol_table[i].end()) {
                    data array_data = symbol_table[i].find(id)->second;
                    vector<int> array_indices = getIndex(leftValue->children.at(0));

                    if (array_indices.size() == 1) {
                        return array_data.arrayValue[array_indices[0]];
                    } else {
                        return array_data.arrayValue[array_indices[0]].arrayValue[array_indices[1]];
                    }
                }
            }
        } else if (leftValueType == "LeftValue - leftValue_dot_id") {
            data leftValue_data = getLeftValue(leftValue->children.at(0));

            string obj_id = leftValue_data.objValue;
            string field_id = leftValue->data.value.stringValue;

            data d = class_table[obj_id].varMap[field_id];
            return d;
        } else if (leftValueType == "LeftValue - id_dot_id") {
            // Get id names
            string var_id = leftValue->data.value.stringValue;
            string field_id = leftValue->children.at(0)->data.value.stringValue;

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(var_id) != symbol_table[i].end()) {
                    data id_data = symbol_table[i].find(var_id)->second;
                    data d = class_table[id_data.objValue].varMap[field_id];
                    return d;
                }
            }
        } else if (leftValueType == "LeftValue - new_id_parentheses_dot_id") {
            // Get id names
            string obj_id = leftValue->data.value.stringValue;
            string field_id = leftValue->children.at(0)->data.value.stringValue;

            struct data d = class_table[obj_id].varMap[field_id];
            return d;
        } else if (leftValueType == "LeftValue - this_dot_id") {
            string id = leftValue->data.value.stringValue;
            data id_data = symbol_table[0].find(id)->second;
            return id_data;
        } else {
            cout << "   [interpret.cc - getLeftValue] Unexpected LeftValue type.\n";
        }

        struct data d;
        return d;
    }

    string getObjFromLeftValue(node * leftValue) {
        string leftValueType = leftValue->nodeType;
        string obj_id;

        if (leftValueType == "LeftValue - leftValue_dot_id") {
            string obj_id = getObjFromLeftValue(leftValue->children.at(0));
            string field_id = leftValue->data.value.stringValue;

            data d = class_table[obj_id].varMap[field_id];
            return d.objValue;
        } else if (leftValueType == "LeftValue - id_dot_id") {
            // Get id names
            string var_id = leftValue->data.value.stringValue;
            string field_id = leftValue->children.at(0)->data.value.stringValue;

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(var_id) != symbol_table[i].end()) {
                    data id_data = symbol_table[i].find(var_id)->second;
                    data d = class_table[id_data.objValue].varMap[field_id];
                    return d.objValue;
                }
            }
        } else if (leftValueType == "LeftValue - new_id_parentheses_dot_id") {
            // Get id names
            string obj_id = leftValue->data.value.stringValue;
            string field_id = leftValue->children.at(0)->data.value.stringValue;

            struct data d = class_table[obj_id].varMap[field_id];
            return d.objValue;
        } else if (leftValueType == "LeftValue - this_dot_id") {
            string id = leftValue->data.value.stringValue;
            data id_data = symbol_table[0].find(id)->second;
            return id_data.objValue;
        }

        return obj_id;
    }

    vector<int> getIndex(node * index) {
        vector<int> array_indices; 

        if (index->nodeType == "Index - BRACKET") {
            data op1 = interpretExp(index->children.at(0));
            array_indices.push_back(op1.value.intValue);
        } else if (index->nodeType == "Index - index_bracket") {
            data op2 = interpretExp(index->children.at(1));
            data op1 = interpretExp(index->children.at(0)->children.at(0));

            array_indices.push_back(op1.value.intValue);
            array_indices.push_back(op2.value.intValue);
        } else {
            cout << "   [typecheck.cc - getIndex] Unexpected Index type.\n";
        }

        return array_indices;
    }

    /******************************************************************************************************************************************************
     *                                                                     HELPER FUNCTION                                                                *
     ******************************************************************************************************************************************************/
    data getDataFromID(string id) {
        // Initialize return data struct
        struct data d;

        // Iterate through symbol_table at each scope
        for (int i = scope_level; i >= 0; i--) {
            if (symbol_table[i].find(id) != symbol_table[i].end()) {
                return symbol_table[i].find(id)->second;
            }
        }

        cout << "   [interpret.cc - getDataFromID] ID not found.\n";
        return d;
    }

    void addToScope(string id, data info, bool isNewVar) {
        // Check if delcaring variable
        if (isNewVar) {
            symbol_table[scope_level].insert(pair<string, data>(id, info));
        // Otherwise, variable is being reassigned.
        } else {
            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(id) != symbol_table[i].end()) {
                    symbol_table[i][id] = info;
                    return;
                }
            }
        }
    }

    /******************************************************************************************************************************************************
     *                                                                          CLASS CODE                                                                *
     ******************************************************************************************************************************************************/
    public:
        // Constructor handles command line arguments
        interpret(int argc, char ** argv) {
            // Iterate through all arguments
            for (int i = 1; i < argc; i++) {
                // Create string object
                struct data arg;
                arg.type = data::type_t::stringType;
                arg.value.stringValue = argv[i];

                // Push object into array
                command_line_args.push_back(arg);
            }
        }

        void interpretProgram(node * program) {
            // Initialize scope as -1
            scope_level = -1;

            // Create one instance of each class
            initial_program_interpret(program);

            // Interpret main class
            interpretMain(program->children.at(0));
        }
};