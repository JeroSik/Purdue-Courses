#include "node.cc"
#include <algorithm>
#include <iostream>
#include <map>
#include <set>
#include <string>


using namespace std;

class typecheck {
    /******************************************************************************************************************************************************
     *                                                                   GLOBAL VARIABLES                                                                 *
     ******************************************************************************************************************************************************/
    /*
     *      symbol_table[i]: The scope of the symbol table at level i
     *      At each scope, map<[Variable Name], [data struct]> holds the variables.
     *          [Data struct].flag: tracks state of variable
     *              -1: Invalid
     *               0: Undeclared
     *               1: Assigned
     */
    vector<map<string,data>> symbol_table;
    map<string,c_data> class_table;
    set<int> error_set;
    string curr_class;
    int scope_level;

    /******************************************************************************************************************************************************
     *                                                                  INITIAL PROGRAM CHECK                                                             *
     ******************************************************************************************************************************************************/
    void initial_program_check(node * program) {
        // Add main class ID
        struct c_data main_info;
        string main_class_id = program->children.at(0)->data.value.stringValue;
        class_table.insert(pair<string, c_data>(main_class_id, main_info));

        // Add all the classes to table
        node * classDeclList = program->children.at(1);
        while (classDeclList->nodeType != "Empty") {
            initial_class_check(classDeclList->children.at(0));            
            classDeclList = classDeclList->children.at(1);
        }

        // Add global variables and methods
        classDeclList = program->children.at(1);
        while (classDeclList->nodeType != "Empty") {
            // Create a level for symbol table scope
            map<string, data> temp_symbol_table;
            symbol_table.push_back(temp_symbol_table);
            scope_level++;

            check_varList_and_methodList(classDeclList->children.at(0));

            // Remove level for symbol table scope
            symbol_table.pop_back();
            scope_level--;

            classDeclList = classDeclList->children.at(1);
        }

        //TODO: extends
    }

    void initial_class_check(node * classDecl) {
        string class_id = classDecl->data.value.stringValue;

        // Check if class ID is unique
        if (class_table.find(class_id) == class_table.end()) {
            struct c_data info;
            class_table.insert(pair<string, c_data>(class_id, info));
        } else {
            class_table[class_id].isValid = false;
            error_set.insert(classDecl->children.at(0)->line_no);
        }
    }

    void check_varList_and_methodList(node * classDecl) {
        string class_id = classDecl->data.value.stringValue;
        node * varDeclList = classDecl->children.at(1);
        node * methodDeclList = classDecl->children.at(2);

        if (class_table[class_id].isValid) {
            // All variable delcarations to stack
            vector<node*> varDeclStack;
            while (varDeclList->nodeType != "Empty") {
                varDeclStack.push_back(varDeclList->children.at(1));
                varDeclList = varDeclList->children.at(0);
            }

            // Check global variable declarations in reverse order
            for (int i = varDeclStack.size() - 1; i >= 0; i--) {
                bool isCorrect = checkVarDecl(varDeclStack[i]);
                if (!isCorrect) {
                    error_set.insert(varDeclStack[i]->line_no);
                }
            }

            // Insert global variables into class table
            class_table[class_id].varMap = symbol_table[scope_level];

            // Iterate through all methods
            while (methodDeclList->nodeType != "Empty") {
                node * methodDecl = methodDeclList->children.at(0);
                string method_id = methodDecl->data.value.stringValue;

                struct m_data m_info;
                vector<data> args;

                // Check if method name is unique
                if (class_table[class_id].methodMap.find(method_id) == class_table[class_id].methodMap.end()) {
                    // Get method name, return type, and method arguments
                    m_info.returnType = getLHSType(methodDecl->children.at(0));
                    node * optionalFormatList = methodDecl->children.at(1);
                    if (optionalFormatList->nodeType != "Empty") {
                        node * formalList = optionalFormatList->children.at(0);
                        args.push_back(getLHSType(formalList->children.at(0)));

                        node * formalsTail = formalList->children.at(1);
                        while (formalsTail->nodeType != "Empty") {
                            args.push_back(getLHSType(formalsTail->children.at(0)));
                            formalsTail = formalsTail->children.at(1);
                        }
                    }
                    m_info.arguments = args;

                    // Insert method into class table
                    class_table[class_id].methodMap.insert(pair<string, m_data>(method_id, m_info));
                } else {
                    error_set.insert(methodDecl->children.at(0)->line_no);
                }

                methodDeclList = methodDeclList->children.at(1);
            }
        }
    }

    /******************************************************************************************************************************************************
     *                                                                     TYPE CHECK PROGRAM                                                             *
     ******************************************************************************************************************************************************/
    void checkMain(node * mainClass) {
        curr_class = mainClass->data.value.stringValue;

        // Create a empty level for symbol table scope to include global variables
        map<string, data> fake_symbol_table;
        symbol_table.push_back(fake_symbol_table);
        scope_level++;

        // Create a level for symbol table scope
        map<string, data> temp_symbol_table;
        symbol_table.push_back(temp_symbol_table);
        scope_level++;

        // Add main method argument variable to symbol table
        string main_argument_id = mainClass->children.at(0)->data.value.stringValue;
        struct data d = {.type=data::type_t::arrayType, .flag=1, .isCorrect=true};
        struct data d_prime = {.type=data::type_t::stringType, .flag=1, .isCorrect=true};
        d.arrayValue.push_back(d_prime);
        symbol_table[scope_level].insert(pair<string, data>(main_argument_id, d));

        // Check statement list
        checkStatementList(mainClass->children.at(1), d);

        // Remove level for symbol table scope
        symbol_table.pop_back();
        scope_level--;

        // Remove level for symbol table scope for global variables
        symbol_table.pop_back();
        scope_level--;
    }

    void checkClasses(node * classDeclList) {
        // Iterate through all classes
        while (classDeclList->nodeType != "Empty") {
            node * classDecl = classDeclList->children.at(0);
            string class_id = classDecl->data.value.stringValue;
            curr_class = class_id;

            // Add global variables as initial scope.
            if (class_table[class_id].isValid) {
                symbol_table.push_back(class_table[class_id].varMap);
            } else {
                map<string, data> temp_symbol_table;
                symbol_table.push_back(temp_symbol_table);
            }
            scope_level++;

            // Check all methods
            checkMethods(classDecl->children.at(2));

            // Remove level for symbol table scope
            symbol_table.pop_back();
            scope_level--;

            classDeclList = classDeclList->children.at(1);
        }
    }

    void checkMethods(node * methodDeclList) {
        while (methodDeclList->nodeType != "Empty") {
            node * methodDecl = methodDeclList->children.at(0);

            // Create a level for symbol table scope
            map<string, data> temp_symbol_table;
            symbol_table.push_back(temp_symbol_table);
            scope_level++;

            // Get return type
            data methodReturnType = getLHSType(methodDecl->children.at(0));

            // Add method argument variables to symbol table
            node * optionalFormatList = methodDecl->children.at(1);
            if (optionalFormatList->nodeType != "Empty") {
                node * formalList = optionalFormatList->children.at(0);

                string argument_id = formalList->data.value.stringValue;
                data d = getLHSType(formalList->children.at(0));
                d.flag = 1;
                d.isCorrect = true;
                symbol_table[scope_level].insert(pair<string, data>(argument_id, d));

                node * formalsTail = formalList->children.at(1);
                while (formalsTail->nodeType != "Empty") {
                    argument_id = formalsTail->data.value.stringValue;
                    d = getLHSType(formalsTail->children.at(0));
                    d.flag = 1;
                    d.isCorrect = true;
                    symbol_table[scope_level].insert(pair<string, data>(argument_id, d));

                    formalsTail = formalsTail->children.at(1);
                }
            }

            // Check statement list
            checkStatementList(methodDecl->children.at(2), methodReturnType);

            // Remove level for symbol table scope
            symbol_table.pop_back();
            scope_level--;

            methodDeclList = methodDeclList->children.at(1);
        }
    }

    void checkStatementList(node * statementList, data methodReturnType) {
        // Iterate through statement list and check each statement
        while (statementList->nodeType != "Empty") {
            checkStatement(statementList->children.at(0), methodReturnType);
            statementList = statementList->children.at(1);
        }
    }

    void checkStatement(node * statement, data methodReturnType) {
        // Get statement type
        string statementType = statement->nodeType;
        // cout << statementType << "\n";

        // SYSTEM_OUT_PRINT(LN) OPEN_PARENTHESES Exp CLOSED_PARENTHESES SEMICOLON
        if (statementType.find("print") != string::npos) {
            // Check expression type in print
            checkAndGetExpType(statement->children.at(0));
        // VarDecl
        } else if (statementType == "Statement - VarDecl") {
            // Check variable declaration
            bool isCorrect = checkVarDecl(statement->children.at(0));
            if (!isCorrect) {
                error_set.insert(statement->line_no);
            }
        // ID EQUALS Exp SEMICOLON
        } else if (statementType == "Statement - id_equals_exp") {
            // Check variable assignment
            bool isCorrect = checkIDAssignment(statement->data.value.stringValue, statement->children.at(0));
            if (!isCorrect) {
                error_set.insert(statement->line_no);
            }
        // LeftValue EQUALS Exp SEMICOLON
        } else if (statementType == "Statement - leftValue_equals_exp") {
            // Check array assignment
            bool isCorrect = checkLeftValueAssignment(statement->children.at(0), statement->children.at(1));
            if (!isCorrect) {
                error_set.insert(statement->line_no);
            }
        // IF OPEN_PARENTHESES Exp CLOSED_PARENTHESES Statement ELSE Statement
        } else if (statementType == "Statement - if") {
            // if condition
            data d = checkAndGetExpType(statement->children.at(0));
            if (d.type != data::type_t::booleanType) {
                error_set.insert(statement->children.at(0)->line_no);
            }

            // Create a level for symbol table scope
            map<string, data> temp_symbol_table;
            symbol_table.push_back(temp_symbol_table);
            scope_level++;

            // Check statement in true condition
            checkStatement(statement->children.at(1), methodReturnType);

            // Remove and create a level for symbol table scope
            symbol_table.pop_back();
            temp_symbol_table.clear();
            symbol_table.push_back(temp_symbol_table);

            // Check statement in false condition
            checkStatement(statement->children.at(2), methodReturnType);

            // Remove level for symbol table scope
            symbol_table.pop_back();
            scope_level--;
        // WHILE OPEN_PARENTHESES Exp CLOSED_PARENTHESES Statement
        } else if (statementType == "Statement - while") {
            // Check loop condition
            data d = checkAndGetExpType(statement->children.at(0));
            if (d.type != data::type_t::booleanType) {
                error_set.insert(statement->children.at(0)->line_no);
            }

            // Create a level for symbol table scope
            map<string, data> temp_symbol_table;
            symbol_table.push_back(temp_symbol_table);
            scope_level++;

            // Check statement in loop
            checkStatement(statement->children.at(1), methodReturnType);

            // Remove level for symbol table scope
            symbol_table.pop_back();
            scope_level--;
        // RETURN Exp SEMICOLON
        } else if (statementType == "Statement - return") {
            // Check if expression matches return type
            data d = checkAndGetExpType(statement->children.at(0));

            if (d.isCorrect) {
                if (d.type == methodReturnType.type) {
                    // Special check if object or array type
                    if (d.type == data::type_t::objType) {
                        if (d.objValue != methodReturnType.objValue) {
                            error_set.insert(statement->line_no);
                        }
                    } else if (d.type == data::type_t::arrayType || d.type == data::type_t::multiArrayType) {
                        if (d.arrayValue[0].type != methodReturnType.arrayValue[0].type) {
                            error_set.insert(statement->line_no);
                        }
                    }
                } else {
                    error_set.insert(statement->line_no);
                }
            }
        // RETURN Exp SEMICOLON
        } else if (statementType == "Statement - MethodCall") {
            data d = checkAndGetMethodCall(statement->children.at(0));
            if (!d.isCorrect) {
                error_set.insert(statement->line_no);
            }
        // OPEN_CURLY StatementList CLOSED_CURLY
        } else if (statementType == "Statement - StatementList") {
            // Check statement list
            checkStatementList(statement->children.at(0), methodReturnType);
        } else {
            cout << "   [typecheck.cc - checkStatement] Unexpected statement type\n";
        }
    }

    bool checkVarDecl(node * varDecl) {
        node * varInit = varDecl->children.at(1);
        node * varDeclTail = varDecl->children.at(2);
        bool isCorrect = true;

        // Get LHS type and variable name
        struct data lhs_type = getLHSType(varDecl->children.at(0));
        string id = varDecl->data.value.stringValue;

        // Check if variable does not already exist
        if (isUniqueVariableName(id)) {
            // Check if variable is initialized
            if (varInit->nodeType != "Empty") {
                // Get RHS type
                data rhs_type = checkAndGetExpType(varInit->children.at(0));

                // Check if LHS and RHS type are the same
                if (lhs_type.type == rhs_type.type) {
                    // Special check if object or array type
                    if (lhs_type.type == data::type_t::objType) {
                        if (lhs_type.objValue != rhs_type.objValue) {
                            addToScope(id, lhs_type, -1, true);
                            isCorrect = false;
                        } else {
                            addToScope(id, lhs_type, 1, true);
                        }
                    } else if (lhs_type.type == data::type_t::arrayType || lhs_type.type == data::type_t::multiArrayType) {
                        if (lhs_type.arrayValue[0].type != rhs_type.arrayValue[0].type) {
                            addToScope(id, lhs_type, -1, true);
                            isCorrect = false;
                        } else {
                            addToScope(id, lhs_type, 1, true);
                        }
                    } else {
                        addToScope(id, lhs_type, 1, true);
                    }
                } else {
                    addToScope(id, lhs_type, -1, true);
                    isCorrect = false;
                }
            } else {
                addToScope(id, lhs_type, 0, true);
            }
        } else {
            isCorrect = false;
        }

        // Iterate through varDeclTail and check potential multi-declarations
        while (varDeclTail->nodeType != "Empty") {
            varInit = varDeclTail->children.at(0);

            // Get name of variable
            id = varDeclTail->data.value.stringValue;

            // Check if variable does not already exist
            if (isUniqueVariableName(id)) {
                // Check if variable is initialized
                if (varInit->nodeType != "Empty") {
                    // Get RHS type
                    data rhs_type = checkAndGetExpType(varInit->children.at(0));

                    // Check if LHS and RHS type are the same
                    if (lhs_type.type == rhs_type.type) {
                        // Special check if object or array type
                        if (lhs_type.type == data::type_t::objType) {
                            if (lhs_type.objValue != rhs_type.objValue) {
                                addToScope(id, lhs_type, -1, true);
                                isCorrect = false;
                            } else {
                                addToScope(id, lhs_type, 1, true);
                            }
                        } else if (lhs_type.type == data::type_t::arrayType || lhs_type.type == data::type_t::multiArrayType) {
                            if (lhs_type.arrayValue[0].type != rhs_type.arrayValue[0].type) {
                                addToScope(id, lhs_type, -1, true);
                                isCorrect = false;
                            } else {
                                addToScope(id, lhs_type, 1, true);
                            }
                        } else {
                            addToScope(id, lhs_type, 1, true);
                        }
                    } else {
                        addToScope(id, lhs_type, -1, true);
                        isCorrect = false;
                    }
                } else {
                    addToScope(id, lhs_type, 0, true);
                }
            } else {
                isCorrect = false;
            }

            varDeclTail = varDeclTail->children.at(1);
        }

        return isCorrect;
    }

    bool checkIDAssignment(string id, node * exp) {
        data exp_data = checkAndGetExpType(exp);

        // Check if expression is valid
        if (exp_data.isCorrect) {
            // Check if variable exists
            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(id) != symbol_table[i].end()) {
                    data id_data = symbol_table[i].find(id)->second;

                    // Check if LHS and RHS type are the same
                    if (id_data.type == exp_data.type) {
                        // Special check if object or array type
                        if (id_data.type == data::type_t::objType) {
                            if (id_data.objValue == exp_data.objValue) {
                                addToScope(id, exp_data, 1, false);
                                return true;
                            }
                        } else if (id_data.type == data::type_t::arrayType || id_data.type == data::type_t::multiArrayType) {
                            if (id_data.arrayValue[0].type == exp_data.arrayValue[0].type) {
                                addToScope(id, exp_data, 1, false);
                                return true;
                            }
                        } else {
                            addToScope(id, exp_data, 1, false);
                            return true;
                        }
                    }
                    
                    return false;
                }
            }
        }

        return false;
    }

    bool checkLeftValueAssignment(node * leftValue, node * exp) {
        data leftValue_data = getLeftValue(leftValue);
        data exp_data = checkAndGetExpType(exp);
        if (!exp_data.isCorrect || !leftValue_data.isCorrect) {
            return false;
        }

        string leftValueType = leftValue->nodeType;
        if (leftValueType == "LeftValue - leftValue_index") {
            if (leftValue_data.type == exp_data.type) {
                return true;
            } else {
                return false;
            }
        } else if (leftValueType == "LeftValue - id_index") {
            string id = leftValue->data.value.stringValue;
            data index = getIndex(leftValue->children.at(0));

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(id) != symbol_table[i].end()) {
                    data id_data = symbol_table[i].find(id)->second;

                    if (leftValue_data.type == exp_data.type) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        // LeftValue DOT ID
        } else if (leftValueType == "LeftValue - leftValue_dot_id") {
            string field_id = leftValue->data.value.stringValue;
            leftValue_data = getLeftValue(leftValue->children.at(0));

            if (leftValue_data.type == data::type_t::objType) {
                string obj_id = leftValue_data.objValue;

                if (class_table.find(obj_id) != class_table.end()) {
                    if (class_table[obj_id].varMap.find(field_id) != class_table[obj_id].varMap.end()) {
                        if (class_table[obj_id].varMap[field_id].type == exp_data.type) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        // ID DOT ID
        } else if (leftValueType == "LeftValue - id_dot_id") {
            string var_id = leftValue->data.value.stringValue;
            string field_id = leftValue->children.at(0)->data.value.stringValue;

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(var_id) != symbol_table[i].end()) {
                    data id_data = symbol_table[i].find(var_id)->second;
                    
                    if ((id_data.type == data::type_t::objType) &&
                        id_data.flag != 0) {
                        if (class_table.find(id_data.objValue) != class_table.end()) {
                            if (class_table[id_data.objValue].varMap.find(field_id) != class_table[id_data.objValue].varMap.end()) {
                                if (class_table[id_data.objValue].varMap[field_id].type == exp_data.type) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        // THIS DOT ID
        } else if (leftValueType == "LeftValue - this_dot_id") {
            string id = leftValue->data.value.stringValue;

            if (symbol_table[0].find(id) != symbol_table[0].end()) {
                data id_data = symbol_table[0].find(id)->second;
                id_data.isCorrect = 1;
                id_data.flag = 1;

                if (id_data.type == exp_data.type) {
                    if (id_data.type == data::type_t::objType) {
                        if (id_data.objValue == exp_data.objValue) {
                            symbol_table[0][id] = id_data;
                            return true;
                        }
                    } else if (id_data.type == data::type_t::arrayType || id_data.type == data::type_t::multiArrayType) {
                        if (id_data.arrayValue[0].type == exp_data.arrayValue[0].type) {
                            symbol_table[0][id] = id_data;
                            return true;
                        }
                    } else {
                        symbol_table[0][id] = id_data;
                        return true;
                    }
                }
                
                return false;
            }
        }

        return false;
    }
    
    data checkAndGetMethodCall(node * methodCall) {
        struct data d = {.isCorrect = false};
        string methodCallType = methodCall->nodeType;

        if (methodCallType == "MethodCall - ID") {
            string method_id = methodCall->data.value.stringValue;

            if (class_table[curr_class].methodMap.find(method_id) != class_table[curr_class].methodMap.end()) {
                d = class_table[curr_class].methodMap[method_id].returnType;
                if (checkOptionalExpList(methodCall->children.at(0), class_table[curr_class].methodMap[method_id].arguments)) {
                    d.isCorrect = true;
                    return d;
                } else {
                    d.isCorrect = false;
                    return d;
                }
            } else {
                return d;
            }
        } else if (methodCallType == "MethodCall - LeftValue") {
            node * leftValue = methodCall->children.at(0);
            if (leftValue->nodeType == "LeftValue - leftValue_dot_id") {
                data var = getLeftValue(leftValue);
                string method_id = leftValue->data.value.stringValue;

                if (var.type == data::type_t::objType) {
                    string obj_id = var.objValue;

                    if (class_table.find(obj_id) != class_table.end()) {
                        if (class_table[obj_id].methodMap.find(method_id) != class_table[obj_id].methodMap.end()) {
                            d = class_table[obj_id].methodMap[method_id].returnType;
                            if (checkOptionalExpList(methodCall->children.at(1), class_table[obj_id].methodMap[method_id].arguments)) {
                                d.isCorrect = true;
                                return d;
                            } else {
                                d.isCorrect = false;
                                return d;
                            }
                        } else {
                            return d;
                        }
                    }
                }

                return d;
            } else if (leftValue->nodeType == "LeftValue - id_dot_id") {
                string var_id = leftValue->data.value.stringValue;
                string method_id = leftValue->children.at(0)->data.value.stringValue;

                for (int i = scope_level; i >= 0; i--) {
                    if (symbol_table[i].find(var_id) != symbol_table[i].end()) {
                        data id_data = symbol_table[i].find(var_id)->second;
                        if ((id_data.type == data::type_t::objType) &&
                            id_data.flag != 0) {
                            if (class_table.find(id_data.objValue) != class_table.end()) {
                                // Check if method exists
                                if (class_table[id_data.objValue].methodMap.find(method_id) != class_table[id_data.objValue].methodMap.end()) {
                                    d = class_table[id_data.objValue].methodMap[method_id].returnType;
                                    if (checkOptionalExpList(methodCall->children.at(1), class_table[id_data.objValue].methodMap[method_id].arguments)) {
                                        d.isCorrect = true;
                                        return d;
                                    } else {
                                        d.isCorrect = false;
                                        return d;
                                    }
                                } else {
                                    d.isCorrect = false;
                                    return d;
                                }
                            } else {
                                d = {.type=data::type_t::booleanType, .isCorrect=false};
                                return d;
                            }
                        } else {
                            return d;
                        }
                    }
                }

                return d;
            } else if (leftValue->nodeType == "LeftValue - new_id_parentheses_dot_id") {
                string obj_id = leftValue->data.value.stringValue;
                string method_id = leftValue->children.at(0)->data.value.stringValue;

                if (class_table.find(obj_id) != class_table.end()) {
                    if (class_table[obj_id].methodMap.find(method_id) != class_table[obj_id].methodMap.end()) {
                        d = class_table[obj_id].methodMap[method_id].returnType;
                        if (checkOptionalExpList(methodCall->children.at(1), class_table[obj_id].methodMap[method_id].arguments)) {
                            d.isCorrect = true;
                            return d;
                        } else {
                            d.isCorrect = false;
                            return d;
                        }
                    } else {
                        return d;
                    }
                }
                return d;
            } else {
                return d;
            }
        } else {
            cout << "   [typecheck.cc - checkAndGetMethodCall] Unexpected method call type\n";
        }

        return d;
    }

    bool checkOptionalExpList(node * optionalExpList, vector<data> args) {
        if (optionalExpList->nodeType == "Empty") {
            if (args.size() == 0) {
                return true;
            } else {
                return false;
            }
        }

        vector<data::type_t> user_args;
        node * expList = optionalExpList->children.at(0);
        node * exp = expList->children.at(0);
        user_args.push_back(checkAndGetExpType(exp).type);

        node * expTail = expList->children.at(1);
        while (expTail->nodeType != "Empty") {
            exp = expTail->children.at(0);
            user_args.push_back(checkAndGetExpType(exp).type);
            expTail = expTail->children.at(1);
        }

        // Check if there are correct number of arguments
        if (user_args.size() != args.size()) {
            return false;
        }

        for (int i = 0; i < args.size(); i++) {
            if (args[i].type != user_args[i]) {
                return false;
            }
        }

        return true;
    }

    data checkAndGetExpType(node * exp) {
        string expType = exp->nodeType;
        struct data exp_data;

        // LogicalOrExp
        if (expType == "Exp - LogicalOrExp") {
            exp_data = _checkAndGetExpType(exp->children.at(0));
        } else {
            cout << "   [typecheck.cc - checkAndGetExpType] Unexpected expression type\n";
        }

        if (!exp_data.isCorrect) {
            error_set.insert(exp->line_no);
        }
        
        return exp_data;
    }

    data _checkAndGetExpType(node * exp) {
        string expType = exp->nodeType;

        // Base case reaching FACTOR
        if (expType == "Factor - INTEGER_LITERAL") {
            return exp->data;
        } else if (expType == "Factor - STRING_LITERAL") {
            return exp->data;
        } else if (expType == "Factor - BOOLEAN_LITERAL") {
            return exp->data;
        } else if (expType == "Factor - ID") {
            string id = exp->data.value.stringValue;

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(id) != symbol_table[i].end()) {
                    data id_data = symbol_table[i].find(id)->second;
                    if (id_data.flag == 0) {
                        id_data.isCorrect = false;
                        return id_data;
                    } else {
                        id_data.isCorrect = true;
                        return id_data;
                    }
                }
            }

            struct data d = {.type=data::type_t::intType, .isCorrect=false};
            return d;
        } else if (expType == "Factor - LeftValue") {
            return getLeftValue(exp->children.at(0));
        } else if (expType == "Factor - MethodCall") {
            return checkAndGetMethodCall(exp->children.at(0));
        } else if (expType == "Factor - Integer_dot_parseInt") {
            struct data d = {.type=data::type_t::intType, .isCorrect=true};
            return d;
        } else if (expType == "Factor - new_id") {
            string obj_id = exp->data.value.stringValue;
            struct data d = {.type=data::type_t::objType, .objValue=obj_id};
            if (class_table.find(obj_id) != class_table.end()) {
                d.isCorrect = true;
                d.flag = 1;
            } else {
                d.isCorrect = false;
                d.flag = -1;
            }
            return d;
        } else if (expType == "Factor - new_primetype_index") {
            data index = getIndex(exp->children.at(1));
            struct data d_prime = {.type=exp->children.at(0)->data.type};
            index.arrayValue.push_back(d_prime);
            return index;
        } else if (expType == "Factor - leftValue_dot_length") {
            data leftValue = getLeftValue(exp->children.at(0));
            if (leftValue.type == data::type_t::stringType || leftValue.type == data::type_t::arrayType || leftValue.type == data::type_t::multiArrayType) {
                struct data d = {.type=data::type_t::intType, .isCorrect=true};
                return d;
            } else {
                struct data d = {.type=data::type_t::intType, .isCorrect=false};
                return d;
            }
        } else if (expType == "Factor - id_dot_length") {
            string id = exp->data.value.stringValue;

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(id) != symbol_table[i].end()) {
                    data id_data = symbol_table[i].find(id)->second;
                    if ((id_data.type == data::type_t::stringType || id_data.type == data::type_t::arrayType || id_data.type == data::type_t::multiArrayType) &&
                        id_data.flag != 0) {
                        struct data d = {.type=data::type_t::intType, .isCorrect=true};
                        return d;
                    } else {
                        struct data d = {.type=data::type_t::intType, .isCorrect=false};
                        return d;
                    }
                }
            }

            struct data d = {.type=data::type_t::intType, .isCorrect=false};
            return d;
        } else if (expType == "Factor - PARENTHESES") {
            return _checkAndGetExpType(exp->children.at(0));
        }

        // Check expression on first child
        data val1 = _checkAndGetExpType(exp->children.at(0));

        // Check is expression type is unary operation
        if (expType == "UnaryExp - PLUS" || expType == "UnaryExp - MINUS") {
            if (val1.isCorrect && val1.type == data::type_t::intType) {
                struct data d = {.type=data::type_t::intType, .isCorrect=true};
                return d;
            } else {
                struct data d = {.type=data::type_t::intType, .isCorrect=false};
                return d;
            }
        } else if (expType == "UnaryExp - EXCLAMATION") {
            if (val1.isCorrect && val1.type == data::type_t::booleanType) {
                struct data d = {.type=data::type_t::booleanType, .isCorrect=true};
                return d;
            } else {
                struct data d = {.type=data::type_t::booleanType, .isCorrect=false};
                return d;
            }
        }

        // Check expression on second child if expression is binary operation
        if (expType == "LogicalOrExp - OR" || expType == "LogicalAndExp - AND") {
            data val2 = _checkAndGetExpType(exp->children.at(1));
            if (val1.isCorrect && val2.isCorrect &&
                val1.type == data::type_t::booleanType && val2.type == data::type_t::booleanType) {
                struct data d = {.type=data::type_t::booleanType, .isCorrect=true};
                return d;
            } else {
                struct data d = {.type=data::type_t::booleanType, .isCorrect=false};
                return d;
            }
        } else if (expType == "EqualityExp - EQUAL_EQUAL" || expType == "EqualityExp - NOT_EQUAL") {
            data val2 = _checkAndGetExpType(exp->children.at(1));
            if (val1.isCorrect && val2.isCorrect && val1.type == val2.type &&
                (val1.type == data::type_t::intType || val1.type == data::type_t::booleanType)) {
                struct data d = {.type=data::type_t::booleanType, .isCorrect=true};
                return d;
            } else {
                struct data d = {.type=data::type_t::booleanType, .isCorrect=false};
                return d;
            }
        } else if (expType == "RelationalExp - GREATER" || expType == "RelationalExp - GREATER_EQUAL" ||
                    expType == "RelationalExp - LESS" || expType == "RelationalExp - LESS_EQUAL") {
            data val2 = _checkAndGetExpType(exp->children.at(1));
            if (val1.isCorrect && val2.isCorrect &&
                val1.type == data::type_t::intType && val2.type == data::type_t::intType) {
                struct data d = {.type=data::type_t::booleanType, .isCorrect=true};
                return d;
            } else {
                struct data d = {.type=data::type_t::booleanType, .isCorrect=false};
                return d;
            }
        } else if (expType == "AdditiveExp - PLUS") {
            data val2 = _checkAndGetExpType(exp->children.at(1));
            if (val1.isCorrect && val2.isCorrect && val1.type == val2.type) {
                if (val1.type == data::type_t::intType) {
                    struct data d = {.type=data::type_t::intType, .isCorrect=true};
                    return d;
                } else if (val1.type == data::type_t::stringType) {
                    struct data d = {.type=data::type_t::stringType, .isCorrect=true};
                    return d;
                } else {
                    struct data d = {.type=data::type_t::intType, .isCorrect=false};
                    return d;
                }
            } else {
                struct data d = {.type=data::type_t::intType, .isCorrect=false};
                return d;
            }
        } else if (expType == "AdditiveExp - MINUS" || expType == "MultiplicativeExp - MULTIPLY" || expType == "MultiplicativeExp - DIVIDE") {
            data val2 = _checkAndGetExpType(exp->children.at(1));
            if (val1.isCorrect && val2.isCorrect &&
                val1.type == data::type_t::intType && val2.type == data::type_t::intType) {
                struct data d = {.type=data::type_t::intType, .isCorrect=true};
                return d;
            } else {
                struct data d = {.type=data::type_t::intType, .isCorrect=false};
                return d;
            }
        }

        // "Exp - LogicalOrExp", "LogicalAndExp", "EqualityExp", "RelationalExp", "AdditiveExp", "MultiplicativeExp", "UnaryExp", "Factor"
        return val1;
    }

    data getLHSType(node * type) {
        struct data d = {.isCorrect=true};

        // PrimeType
        if (type->nodeType == "Type - PrimeType") {
            node * primetype = type->children.at(0);
            d.type = primetype->data.type;
        // ID
        } else if (type->nodeType == "Type - ID") {
            string obj_id = type->data.value.stringValue;
            d.type = data::type_t::objType;
            d.objValue = obj_id;

            // Check if class name exists
            if (class_table.find(obj_id) == class_table.end()) {
                d.isCorrect = false;
                error_set.insert(type->line_no);
            }
        // Type OPEN_BRACKET CLOSED_BRACKET
        } else if (type->nodeType == "Type - BRACKET") {
            node * type_child = type->children.at(0);

            // Check if array is multidimensional
            if (type_child->nodeType == "Type - BRACKET") {
                d.type = data::type_t::multiArrayType;
                type_child = type_child->children.at(0);
                if (type_child->nodeType == "Type - PrimeType") {
                    struct data d_prime = {.type = type_child->children.at(0)->data.type};
                    d.arrayValue.push_back(d_prime);
                }
            } else {
                d.type = data::type_t::arrayType;
                if (type_child->nodeType == "Type - PrimeType") {
                    struct data d_prime = {.type = type_child->children.at(0)->data.type};
                    d.arrayValue.push_back(d_prime);
                }
            }
        } else {
            cout << "   [typecheck.cc - getLHSType] Unexpected type.\n";
        }

        return d;
    }

    data getLeftValue(node * leftValue) {
        string leftValueType = leftValue->nodeType;
        struct data d;

        if (leftValueType == "LeftValue - leftValue_index") {
            data val1 = getLeftValue(leftValue->children.at(0));

            if (val1.type == data::type_t::arrayType || val1.type == data::type_t::multiArrayType) {
                data index = getIndex(leftValue->children.at(1));

                if (index.isCorrect) {
                    if (val1.type == data::type_t::arrayType && index.type == data::type_t::arrayType) {
                        d = {.type=val1.arrayValue[0].type, .isCorrect=true};
                        return d;
                    } else if (val1.type == data::type_t::multiArrayType) {
                        if (index.type == data::type_t::arrayType) {
                            d = {.type=data::type_t::arrayType, .isCorrect=true};
                        } else {
                            d = {.type=val1.arrayValue[0].type, .isCorrect=true};
                        }
                        return d;
                    } else {
                        d = {.type=val1.type, .isCorrect=false};
                        return d;
                    }
                }
            }
            d = {.type=val1.type, .isCorrect=false};
            return d;
        } else if (leftValueType == "LeftValue - id_index") {
            string id = leftValue->data.value.stringValue;

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(id) != symbol_table[i].end()) {
                    data val1 = symbol_table[i].find(id)->second;

                    if (val1.type == data::type_t::arrayType || val1.type == data::type_t::multiArrayType) {
                        data index = getIndex(leftValue->children.at(0));

                        if (index.isCorrect) {
                            if (val1.type == data::type_t::arrayType && index.type == data::type_t::arrayType) {
                                d = {.type=val1.arrayValue[0].type, .isCorrect=true};
                                return d;
                            } else if (val1.type == data::type_t::multiArrayType) {
                                if (index.type == data::type_t::arrayType) {
                                    d = {.type=data::type_t::arrayType, .isCorrect=true};
                                } else {
                                    d = {.type=val1.arrayValue[0].type, .isCorrect=true};
                                }
                                return d;
                            } else {
                                d = {.type=val1.type, .isCorrect=false};
                                return d;
                            }
                        } else {
                            d = {.type=val1.type, .isCorrect=false};
                            return d;
                        }
                    } else {
                        d = {.type=val1.type, .isCorrect=false};
                        return d;
                    }
                }
            }

            d = {.type=data::type_t::arrayType, .isCorrect=false};
            return d;
        // LeftValue DOT ID
        } else if (leftValueType == "LeftValue - leftValue_dot_id") {
            data var = getLeftValue(leftValue->children.at(0));
            string field_id = leftValue->data.value.stringValue;

            if (var.type == data::type_t::objType) {
                string obj_id = var.objValue;

                d = {.type=data::type_t::objType, .objValue=obj_id};
                if (class_table.find(obj_id) != class_table.end()) {
                    if (class_table[obj_id].varMap.find(field_id) != class_table[obj_id].varMap.end()) {
                        d = class_table[obj_id].varMap[field_id];
                        d.isCorrect = true;
                        d.flag = 1;
                        return d;
                    } else {
                        d.isCorrect = false;
                        d.flag = -1;
                        return d;
                    }
                } else {
                    d.isCorrect = false;
                    d.flag = -1;
                    return d;
                }
            }

            d = {.type=data::type_t::booleanType, .isCorrect=false};
            return d;
        // ID DOT ID
        } else if (leftValueType == "LeftValue - id_dot_id") {
            string var_id = leftValue->data.value.stringValue;
            string field_id = leftValue->children.at(0)->data.value.stringValue;

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(var_id) != symbol_table[i].end()) {
                    data id_data = symbol_table[i].find(var_id)->second;
                    if ((id_data.type == data::type_t::objType) &&
                        id_data.flag != 0) {
                        if (class_table.find(id_data.objValue) != class_table.end()) {
                            if (class_table[id_data.objValue].varMap.find(field_id) != class_table[id_data.objValue].varMap.end()) {
                                d = class_table[id_data.objValue].varMap[field_id];
                                d.isCorrect = true;
                                d.flag = 1;
                                return d;
                            } else {
                                d = {.type=data::type_t::booleanType, .isCorrect=false};
                                return d;
                            }
                        } else {
                            d = {.type=data::type_t::booleanType, .isCorrect=false};
                            return d;
                        }
                    } else {
                        d = {.type=data::type_t::booleanType, .isCorrect=false};
                        return d;
                    }
                }
            }

            d = {.type=data::type_t::booleanType, .isCorrect=false};
            return d;
        // NEW ID OPEN_PARENTHESES CLOSED_PARENTHESES DOT ID
        } else if (leftValueType == "LeftValue - new_id_parentheses_dot_id") {
            string obj_id = leftValue->data.value.stringValue;
            string field_id = leftValue->children.at(0)->data.value.stringValue;

            d = {.type=data::type_t::objType, .objValue=obj_id};
            if (class_table.find(obj_id) != class_table.end()) {
                if (class_table[obj_id].varMap.find(field_id) != class_table[obj_id].varMap.end()) {
                    d = class_table[obj_id].varMap[field_id];
                    d.isCorrect = true;
                    d.flag = 1;
                    return d;
                } else {
                    d.isCorrect = false;
                    d.flag = -1;
                    return d;
                }
            } else {
                d.isCorrect = false;
                d.flag = -1;
                return d;
            }
            return d;
        // THIS DOT ID
        } else if (leftValueType == "LeftValue - this_dot_id") {
            string id = leftValue->data.value.stringValue;
            if (symbol_table[0].find(id) != symbol_table[0].end()) {
                data id_data = symbol_table[0].find(id)->second;
                id_data.isCorrect = true;
                return id_data;
            } else {
                d = {.type=data::type_t::booleanType, .isCorrect=false};
                return d;
            }
        } else {
            cout << "   [typecheck.cc - checkAndGetLeftValue] Unexpected LeftValue type.\n";
        }

        return d;
    }

    data getIndex(node * index) {
        struct data d;

        if (index->nodeType == "Index - BRACKET") {
            d = checkAndGetExpType(index->children.at(0));

            if (d.type == data::type_t::intType) {
                d = {.type=data::type_t::arrayType, .isCorrect=true};
                return d;
            } else {
                d = {.type=data::type_t::arrayType, .isCorrect=false};
                return d;
            }
        } else if (index->nodeType == "Index - index_bracket") {
            d = checkAndGetExpType(index->children.at(1));
            if (d.type == data::type_t::intType) {
                d = checkAndGetExpType(index->children.at(0)->children.at(0));
                if (d.type == data::type_t::intType) {
                    d = {.type=data::type_t::multiArrayType, .isCorrect=true};
                    return d;
                } else {
                    d = {.type=data::type_t::multiArrayType, .isCorrect=false};
                    return d;
                }
            } else {
                d = {.type=data::type_t::multiArrayType, .isCorrect=false};
                return d;
            }
        } else {
            cout << "   [typecheck.cc - getIndex] Unexpected Index type.\n";
        }

        return d;
    }

    /******************************************************************************************************************************************************
     *                                                                     HELPER FUNCTION                                                                *
     ******************************************************************************************************************************************************/
    bool isUniqueVariableName(string id) {
        // Iterate through symbol_table at each scope
        // Ignore global variables
        for (int i = scope_level; i >= 1; i--) {
            if (symbol_table[i].find(id) != symbol_table[i].end()) {
                return false;
            }
        }

        return true;
    }

    void addToScope(string id, data info, int f, bool isNewVar) {
        info.flag = f;

        // Check if delcaring variable
        if (isNewVar) {
            symbol_table[scope_level].insert(pair<string, data>(id, info));
        // Otherwise, variable is being reassigned.
        } else {
            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(id) != symbol_table[i].end()) {
                    data old_info = symbol_table[i].find(id)->second;
                    old_info.flag = f;

                    symbol_table[i][id] = old_info;
                    return;
                }
            }
        }
    }

    void printErrors() {
        numErrors = error_set.size();

        for (auto it = error_set.begin(); it != error_set.end(); it++) {
            cout << "Type Violation in Line " << *it << endl;
        }
    }

    /******************************************************************************************************************************************************
     *                                                                          CLASS CODE                                                                *
     ******************************************************************************************************************************************************/
    public:
        // Keep track of number of errors
        int numErrors;

        // Check for type check errors across program
        void checkProgram(node * program) {
            scope_level = -1;

            initial_program_check(program);

            checkMain(program->children.at(0));
            checkClasses(program->children.at(1));
            
            printErrors();
        }
};