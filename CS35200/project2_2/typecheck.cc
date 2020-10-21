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

        // SYSTEM_OUT_PRINT(LN) OPEN_PARENTHESES Exp CLOSED_PARENTHESES SEMICOLON
        if (statementType.find("print") != string::npos) {
            // Check expression type in print
            checkAndGetExpType(statement->children.at(0));
        // VarDecl
        } else if (statementType == "Statement - VarDecl") {
            // Check variable declaration
            // checkVarDecl(statement->children.at(0));
        // ID EQUALS Exp SEMICOLON
        } else if (statementType == "Statement - id_equals_exp") {
            // Check variable assignment
            // checkVarAssignment(statement->data.value.stringValue, statement->children.at(0));
        // LeftValue EQUALS Exp SEMICOLON
        } else if (statementType == "Statement - leftValue_equals_exp") {
            // TODO: Check array assignment
            // checkVarAssignment(statement->data.value.stringValue, statement->children.at(0));
        // IF OPEN_PARENTHESES Exp CLOSED_PARENTHESES Statement ELSE Statement
        } else if (statementType == "Statement - if") {
            // if condition
            data d = checkAndGetExpType(statement->children.at(0));
            if (d.type != data::type_t::booleanType) {
                error_set.insert(statement->line_no);
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
                error_set.insert(statement->line_no);
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

    // bool checkVarDecl(node * varDecl) {
    //     bool result = true;

    //     // Get LHS type ([Type] ID VarInit VarDeclTail SEMICOLON)
    //     data::type_t lhs_type = varDecl->children.at(0)->data.type;

    //     // Get first variable initialization
    //     // Get name of variable
    //     string id = varDecl->data.value.stringValue;
    //     // Check if variable is initialized
    //     node * varInit = varDecl->children.at(1);
    //     if (varInit->nodeType != "Empty") {
    //         bool isCorrect = checkAndComputeRHS(lhs_type, id, varInit->children.at(0));
    //         if (!isCorrect) {
    //             result = false;
    //         }
    //     } else {
    //         if (isUniqueVariableName(id)) {
    //             addToScope(id, lhs_type, 0);
    //         } else {
    //             result = false;
    //         }
    //     }

    //     // Iterate through varDeclTail and check potential multi-declarations
    //     node * varDeclTail = varDecl->children.at(2);
    //     while (varDeclTail->nodeType != "Empty") {
    //         // Get name of variable
    //         id = varDeclTail->data.value.stringValue;
    //         // Check if variable is initialized
    //         varInit = varDeclTail->children.at(0);
    //         if (varInit->nodeType != "Empty") {
    //             bool isCorrect = checkAndComputeRHS(lhs_type, id, varInit->children.at(0));
    //             if (!isCorrect) {
    //                 result = false;
    //             }
    //         } else {
    //             if (isUniqueVariableName(id)) {
    //                 addToScope(id, lhs_type, 0);
    //             } else {
    //                 result = false;
    //             }
    //         }

    //         varDeclTail = varDeclTail->children.at(1);
    //     }

    //     return result;
    // }

    // /*
    //  * Ensure variable is assigned correctly (ID EQUALS Exp SEMICOLON)
    //  */
    // bool checkVarAssignment(string id, node * exp) {
    //     // Check if variable has been declared
    //     if (integer_values.find(id) != integer_values.end()) {
    //         data rhs = checkAndGetExpType(exp);

    //         if (data::type_t::integer == rhs.type && rhs.isCorrect) {
    //             addToScope(id, rhs.type, 1);
    //             return true;
    //         } else {
    //             addToScope(id, rhs.type, -1);
    //             return false;
    //         }
    //     }

    //     return false;
    // }

    // bool checkAndComputeRHS(data::type_t lhs_type, string id, node * rhs_exp) {
    //     data rhs = checkAndGetExpType(rhs_exp);

    //     if (isUniqueVariableName(id) && lhs_type == rhs.type && rhs.isCorrect) {
    //         addToScope(id, lhs_type, 1);
    //         return true;
    //     }

    //     addToScope(id, lhs_type, -1);
    //     return false;
    // }

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
            // TODO: Implement "Factor - LeftValue"
        } else if (expType == "Factor - new_primetype_index") {
            // TODO: Implement "Factor - new_primetype_index"
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

    data getDataFromID(string id) {
        // Initialize return data struct
        struct data d;

        // Iterate through symbol_table at each scope
        for (int i = scope_level - 1; i >= 0; i--) {
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

    // bool isUniqueVariableName(string id) {
    //     if (integer_values.find(id) == integer_values.end() &&
    //         string_values.find(id) == string_values.end() &&
    //         boolean_values.find(id) == boolean_values.end()) {
    //         return true;
    //     }
    //     else {
    //         return false;
    //     }
    // }

    // void addToScope(string id, data::type_t type, int flag) {
    //     // Check if variable name already exists
    //     if (isUniqueVariableName(id)) {
    //         switch(type){
    //         case 0:
    //             string_values.insert(pair<string, int>(id, flag));
    //             break;
    //         case 1:
    //             integer_values.insert(pair<string, int>(id, flag));
    //             break;
    //         case 2:
    //             boolean_values.insert(pair<string, int>(id, flag));
    //             break;
    //         default:
    //             printf("ERROR in typcheck.cc: addToScope(), invalid type found.\n");
    //     }
    //     // Otherwise update element in map
    //     } else {
    //         integer_values[id] = flag;
    //     }
    // }

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