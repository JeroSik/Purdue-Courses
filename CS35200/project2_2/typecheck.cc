#include "node.cc"
#include <algorithm>
#include <iostream>
#include <map>
#include <set>
#include <string>


using namespace std;

class typecheck {
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

    // Set to store type violation line numbers
    set<int> error_set;
    
    void checkMain(node * mainClass) {
        // Create a level for symbol table scope
        map<string, data> temp_symbol_table;
        symbol_table.push_back(temp_symbol_table);
        scope_level++;

        // Add main method argument variable to symbol table
        string main_argument_id = mainClass->children.at(0)->data.value.stringValue;
        struct data d = {.type=data::type_t::stringType, .flag=1, .isCorrect=true};
        symbol_table[scope_level].insert(pair<string, data>(main_argument_id, d));

        // Check statement list
        checkStatementList(mainClass->children.at(1));

        // Remove level for symbol table scope
        symbol_table.pop_back();
        scope_level--;
    }

    void checkStatementList(node * statementList) {
        // Iterate through statement list and check each statement
        while (statementList->nodeType != "Empty") {
            checkStatement(statementList->children.at(0));
            statementList = statementList->children.at(1);
        }
    }

    void checkStatement(node * statement) {
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
            bool isCorrect = checkVarAssignment(statement->data.value.stringValue, statement->children.at(0));
            if (!isCorrect) {
                error_set.insert(statement->line_no);
            }
        // LeftValue EQUALS Exp SEMICOLON
        } else if (statementType == "Statement - leftValue_equals_exp") {
            // Check array assignment
            bool isCorrect = checkArrayAssignment(statement->children.at(0), statement->children.at(1));
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
            checkStatement(statement->children.at(1));

            // Remove and create a level for symbol table scope
            symbol_table.pop_back();
            temp_symbol_table.clear();
            symbol_table.push_back(temp_symbol_table);

            // Check statement in false condition
            checkStatement(statement->children.at(2));

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
            checkStatement(statement->children.at(1));

            // Remove level for symbol table scope
            symbol_table.pop_back();
            scope_level--;
        // OPEN_CURLY StatementList CLOSED_CURLY
        } else if (statementType == "Statement - StatementList") {
            // Check statement list
            checkStatementList(statement->children.at(0));
        } else {
            cout << "   [typecheck.cc - checkStatement] Unexpected statement type\n";
        }
    }

    bool checkVarDecl(node * varDecl) {
        // Type ID VarInit VarDeclTail SEMICOLON
        bool isCorrect = true;

        // Get LHS type and variable name
        struct data lhs_type = getLHSType(varDecl->children.at(0));
        string id = varDecl->data.value.stringValue;

        // Check if variable is initialized
        node * varInit = varDecl->children.at(1);
        if (isUniqueVariableName(id)) {
            if (varInit->nodeType != "Empty") {
                // cout << "Before Exp Check\n";
                struct data rhs_type = checkAndGetExpType(varInit->children.at(0));

                // cout << "Before LHS and RHS type check\n";
                if (lhs_type.type == rhs_type.type) {
                    if (lhs_type.type == data::type_t::arrayType || lhs_type.type == data::type_t::multiArrayType) {
                        // cout << "Before get LHS array value\n";
                        data::type_t lhs_array_type = lhs_type.arrayValue[0].type;
                        // cout << "Before get RHS array value\n";
                        data::type_t rhs_array_type = rhs_type.arrayValue[0].type;

                        // cout << "Before array type check\n";
                        if (lhs_array_type != rhs_array_type) {
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
        node * varDeclTail = varDecl->children.at(2);
        while (varDeclTail->nodeType != "Empty") {
            // Get name of variable
            id = varDeclTail->data.value.stringValue;

            // Check if variable is initialized
            varInit = varDeclTail->children.at(0);

            if (isUniqueVariableName(id)) {
                if (varInit->nodeType != "Empty") {
                    struct data rhs_type = checkAndGetExpType(varInit->children.at(0));
                    if (lhs_type.type == rhs_type.type) {
                        if (lhs_type.type == data::type_t::arrayType || lhs_type.type == data::type_t::multiArrayType) {
                            data::type_t lhs_array_type = lhs_type.arrayValue[0].type;
                            data::type_t rhs_array_type = rhs_type.arrayValue[0].type;
                            if (lhs_array_type != rhs_array_type) {
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

    bool checkVarAssignment(string id, node * exp) {
        data exp_data = checkAndGetExpType(exp);

        if (!isUniqueVariableName(id) && exp_data.isCorrect) {
            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(id) != symbol_table[i].end()) {
                    data id_data = symbol_table[i].find(id)->second;

                    if (id_data.type == exp_data.type) {
                        addToScope(id, exp_data, 1, false);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return false;
    }

    bool checkArrayAssignment(node * leftValue, node * exp) {
        data leftValue_data = getLeftValue(leftValue);
        data exp_data = checkAndGetExpType(exp);

        if (leftValue_data.isCorrect && exp_data.isCorrect) {
            if (leftValue_data.type == exp_data.type) {
                return true;
            } else {
                // TODO: assign invalid to that index
                return false;
            }
        }

        return false;
    }

    data checkAndGetExpType(node * exp) {
        // Get expression type and initialize return data struct
        string expType = exp->nodeType;
        struct data exp_data;

        // LogicalOrExp
        if (expType == "Exp - LogicalOrExp") {
            // Check expression type
            exp_data = _checkAndGetExpType(exp->children.at(0));
        } else {
            cout << "   [typecheck.cc - checkAndGetExpType] Unexpected expression type\n";
        }

        // Add line number to set if incorrect expression
        if (!exp_data.isCorrect) {
            error_set.insert(exp->line_no);
        }
        
        return exp_data;
    }

    data _checkAndGetExpType(node * exp) {
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
            data index = getIndex(exp->children.at(1));
            if (index.isCorrect) {
                struct data d_prime = {.type=exp->children.at(0)->data.type};
                index.arrayValue.push_back(d_prime);
                return index;
            } else {
                struct data d_prime = {.type=exp->children.at(0)->data.type};
                index.arrayValue.push_back(d_prime);
                index = {.isCorrect=false};
                return index;
            }
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

            if (isUniqueVariableName(id)) {
                struct data d = {.type=data::type_t::intType, .isCorrect=false};
                return d;
            } else {
                for (int i = scope_level; i >= 0; i--) {
                    if (symbol_table[i].find(id) != symbol_table[i].end()) {
                        data val1 = symbol_table[i].find(id)->second;
                        if ((val1.type == data::type_t::stringType || val1.type == data::type_t::arrayType || val1.type == data::type_t::multiArrayType) &&
                            val1.flag != 0) {
                            struct data d = {.type=data::type_t::intType, .isCorrect=true};
                            return d;
                        } else {
                            struct data d = {.type=data::type_t::intType, .isCorrect=false};
                            return d;
                        }
                    }
                }
            }
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
        struct data d;

        // PrimeType
        if (type->nodeType == "Type - PrimeType") {
            node * primetype = type->children.at(0);
            d.type = primetype->data.type;
        // ID
        } else if (type->nodeType == "Type - ID") {
            //TODO: Implement custom objects
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
                } else if (type_child->nodeType == "Type - ID") {
                    //TODO: Implement custom objects
                }
            } else {
                d.type = data::type_t::arrayType;
                if (type_child->nodeType == "Type - PrimeType") {
                    struct data d_prime = {.type = type_child->children.at(0)->data.type};
                    d.arrayValue.push_back(d_prime);
                } else if (type_child->nodeType == "Type - ID") {
                    //TODO: Implement custom objects
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

        if (leftValueType == "LeftValue - id_index") {
            string id = leftValue->data.value.stringValue;

            if (isUniqueVariableName(id)) {
                d = {.type=data::type_t::arrayType, .isCorrect=false};
                return d;
            } else {
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

    data getDataFromID(string id) {
        // Initialize return data struct
        struct data d;

        // Iterate through symbol_table at each scope
        for (int i = scope_level; i >= 0; i--) {
            if (symbol_table[i].find(id) != symbol_table[i].end()) {
                data id_data = symbol_table[i].find(id)->second;

                if (id_data.flag == 0) {
                    d = {.type=id_data.type, .isCorrect=false};
                    return d;
                } else {
                    d = {.type=id_data.type, .isCorrect=true};
                    return d;
                }
            }
        }

        d = {.type=data::type_t::intType, .isCorrect=false};
        return d;
    }

    bool isUniqueVariableName(string id) {
        // Iterate through symbol_table at each scope
        for (int i = scope_level; i >= 0; i--) {
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

    public:
        // Keep track of number of errors
        int numErrors;

        /*
         * Check program
         */
        void checkProgram(node * program) {
            scope_level = -1;

            checkMain(program->children.at(0));
            /*
             * TODO: Check class declarations
             * currClassDeclList <- program.childAt(1);
             * while currClassDeclList's name is not EMPTY:
             *      check currClassDeclList.childAt(0)
             *      get next classDeclList
             */
            
            printErrors();
        }
};