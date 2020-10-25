#include <map>
#include <string.h>
#include "node.cc" 
#include <iostream>

using namespace std;

class interpret{
    /*
     * Data structure to build symbol table.
     *      symbol_table[i]: The scope of the symbol table at level i
     *      At each scope, map<[Variable Name], [data struct]> holds the variables.
     *          [Data struct].flag: tracks state of variable
     *              -1: Invalid
     *               0: Undeclared
     *               1: Assigned
     */
    vector<map<string, data>> symbol_table;
    int scope_level;
    
    /*
     * Iterate through statement list of main class
     */
    void interpretMain(node * mainClass) {        
        // Create a level for symbol table scope
        map<string, data> temp_symbol_table;
        symbol_table.push_back(temp_symbol_table);
        scope_level++;

        // Check statement list
        interpretStatementList(mainClass->children.at(1));

        // Remove level for symbol table scope
        symbol_table.pop_back();
        scope_level--;
    }

    void interpretStatementList(node * statementList) {
        // Iterate through statement list and check each statement
        while (statementList->nodeType != "Empty") {
            interpretStatement(statementList->children.at(0));
            statementList = statementList->children.at(1);
        }
    }

    void interpretStatement(node * statement) {
        // Get statement type
        string statementType = statement->nodeType;

        // SYSTEM_OUT_PRINT(LN) OPEN_PARENTHESES Exp CLOSED_PARENTHESES SEMICOLON
        if (statementType.find("print") != string::npos) {
            data exp = interpretExp(statement->children.at(0));

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

            if (statementType.find("println") != string::npos) {
                cout << endl;
            }
        // VarDecl
        } else if (statementType == "Statement - VarDecl") {
            // Check variable declaration
            interpretVarDecl(statement->children.at(0));
        // ID EQUALS Exp SEMICOLON
        } else if (statementType == "Statement - id_equals_exp") {
            // Check variable assignment
            interpretVarAssignment(statement->data.value.stringValue, statement->children.at(0));
        // LeftValue EQUALS Exp SEMICOLON
        } else if (statementType == "Statement - leftValue_equals_exp") {
            // Check array assignment
            interpretArrayAssignment(statement->children.at(0), statement->children.at(1));
        // IF OPEN_PARENTHESES Exp CLOSED_PARENTHESES Statement ELSE Statement
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
        // WHILE OPEN_PARENTHESES Exp CLOSED_PARENTHESES Statement
        } else if (statementType == "Statement - while") {
            // Check loop condition
            data d = interpretExp(statement->children.at(0));

            // // Create a level for symbol table scope
            // map<string, data> temp_symbol_table;
            // symbol_table.push_back(temp_symbol_table);
            // scope_level++;

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

            // // Remove level for symbol table scope
            // symbol_table.pop_back();
            // scope_level--;
        // OPEN_CURLY StatementList CLOSED_CURLY
        } else if (statementType == "Statement - StatementList") {
            // Check statement list
            interpretStatementList(statement->children.at(0));
        } else {
            cout << "   [typecheck.cc - checkStatement] Unexpected statement type\n";
        }
    }

    void interpretVarDecl(node * varDecl) {
        // Get LHS type and variable name
        struct data lhs_type = getLHSType(varDecl->children.at(0));
        string id = varDecl->data.value.stringValue;

        // Check if variable is initialized
        node * varInit = varDecl->children.at(1);
        data d = {.type=lhs_type.type};
        if (varInit->nodeType != "Empty") {
            d = interpretExp(varInit->children.at(0));
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
            }
            addToScope(id, d, true);

            varDeclTail = varDeclTail->children.at(1);
        }
    }

    void interpretVarAssignment(string id, node * exp) {
        data exp_data = interpretExp(exp);

        addToScope(id, exp_data, false);
    }

    void interpretArrayAssignment(node * leftValue, node * exp) {
        string id = leftValue->data.value.stringValue;
        data exp_data = interpretExp(exp);

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
                        // char * result = const_cast<char *>(string("\"\"").c_str());
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
                            char * result = const_cast<char *>(string("\"\"").c_str());
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

        // Check expression on first child
        data val1 = _interpretExp(exp->children.at(0));

        // Check is expression type is unary operation
        if (expType == "UnaryExp - PLUS") {
            return val1;
        } else if (expType == "UnaryExp - MINUS") {
            int result = (- val1.value.intValue);
            struct data d = {.type=data::type_t::intType, .value.intValue=result};
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

        // PrimeType
        if (type->nodeType == "Type - PrimeType") {
            node * primetype = type->children.at(0);
            d.type = primetype->data.type;
        // Type OPEN_BRACKET CLOSED_BRACKET
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

        if (leftValueType == "LeftValue - id_index") {
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
        } else {
            cout << "   [interpret.cc - getLeftValue] Unexpected LeftValue type.\n";
        }

        struct data d;
        return d;
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
                }
            }
        }
    }

    public:

        /*
         * Run interpretation on main program
         */
        void interpretProgram(node * program) {
            // Initialize scope as -1
            scope_level = -1;

            // Interpret main class
            interpretMain(program->children.at(0));
        }
};