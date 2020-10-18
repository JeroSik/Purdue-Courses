#include <map>
#include <string.h>
#include "node.cc" 
#include <iostream>

using namespace std;

class interpret{
    /*
     * map <string, pair<string, int>> variable_map;
     *      Key: Variable name
     *      Value: Pair <[Variable type], [Flag]>
     *          Flags:
     *             -1 - Invalid
     *              0 - Undeclared
     *              1 - Declared
     */
    map<string, data> variable_map;
    
    /*
     * Iterate through statement list of main class
     */
    void interpretMain(node * mainClass) {        
        // Iterate through statement list and check each statement
        node * currStatementList = mainClass->children.at(0);
        while (currStatementList->children.size() == 2) {
            interpretStatement(currStatementList->children.at(0));
            currStatementList = currStatementList->children.at(1);
        }
    }

    /*
     * Ensure each statement has correct type
     */
    void interpretStatement(node * statement){
        // Get statement type
        string statementType = statement->nodeType;

        // SYSTEM_OUT_PRINTLN OPEN_PARENTHESES Exp CLOSED_PARENTHESES SEMICOLON
        if (statementType == "Statement - println") {
            data exp = interpretExp(statement->children.at(0));
            if (exp.type == data::type_t::str) {
                cout << interpretExp(statement->children.at(0)).value.stringValue << endl;
            } else if (exp.type == data::type_t::integer) {
                cout << interpretExp(statement->children.at(0)).value.intValue << endl;
            } else {
                cout << interpretExp(statement->children.at(0)).value.booleanValue << endl;
            }
        
        // SYSTEM_OUT_PRINT OPEN_PARENTHESES Exp CLOSED_PARENTHESES SEMICOLON
        } else if (statementType == "Statement - print") {
            data exp = interpretExp(statement->children.at(0));
            if (exp.type == data::type_t::str) {
                cout << interpretExp(statement->children.at(0)).value.stringValue;
            } else if (exp.type == data::type_t::integer) {
                cout << interpretExp(statement->children.at(0)).value.intValue;
            } else {
                cout << interpretExp(statement->children.at(0)).value.booleanValue;
            }

        // VarDecl
        } else if (statementType == "Statement - VarDecl") {
            // interpretVarDecl(statement->children.at(0));
            
        // ID EQUALS Exp SEMICOLON
        } else if (statementType == "Statement - id_equals_exp") {
            // // cout << "Assignment\n";
            // bool isCorrect = checkVarAssignment(statement->data.value.stringValue, statement->children.at(0));
            // if (!isCorrect) {
            //     numErrors++;
            //     cout << "Type Violation in Line " << statement->line_no << endl;
            // }

        } else {
            cout << "[interpret.cc - interpretStatement] Unexpected statement type\n";
        }
    }

    // /*
    //  * Ensure variable is declared correctly (Type ID VarInit VarDeclTail SEMICOLON)
    //  */
    // bool interpretVarDecl(node * varDecl) {
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
    //         addToScope(id, lhs_type, 0);
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
    //             addToScope(id, lhs_type, 0);
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

    //     // cout << "   checkAndComputeRHS\n";
    //     // cout << "       lhs_type: " << lhs_type << "\n";
    //     // cout << "       rhs.type: " << rhs.type << "\n";

    //     if (isUniqueVariableName(id) && lhs_type == rhs.type && rhs.isCorrect) {
    //         addToScope(id, lhs_type, 1);
    //         return true;
    //     }

    //     addToScope(id, lhs_type, -1);
    //     return false;
    // }

    data interpretExp(node * exp) {
        node * equalityExp = exp->children.at(0);
        // data exp_data = _interpretExp(equalityExp);

        // return exp_data;
        return exp->data;
    }
    // data _interpretExp(node * exp) {
    //     // Base case reaching FACTOR
    //     if (exp->nodeType == "INTEGER_LITERAL") {
    //         return exp->data;
    //     } else if (exp->nodeType == "STRING_LITERAL") {
    //         return exp->data;
    //     } else if (exp->nodeType == "ID") {
    //         string id = exp->data.value.stringValue;
    //         return variable_map[id];
    //     } else if (exp->nodeType == "Factor - PARENTHESES") {
    //         return _interpretExp(exp->children.at(0));
    //     }

    //     data val1;
    //     data val2;
    //     if (exp->nodeType == "EqualityExp - EQUAL_EQUAL") {
    //         val1 = _interpretExp(exp->children.at(0));
    //         val2 = _interpretExp(exp->children.at(1));
    //         bool result;

    //         if (val1.type == data::type_t::integer) {
    //             result = (val1.value.intValue == val2.value.intValue);
    //         } else {
    //             result = (val1.value.booleanValue == val2.value.booleanValue);
    //         }

    //         struct data d = {.type = data::type_t::boolean, .value.booleanValue = result};
    //         return d;
    //     // }
    //     //     exp->nodeType == "EqualityExp - NOT_EQUAL") {
    //     //     val1 = _interpretExp(exp->children.at(0));
    //     //     val2 = _interpretExp(exp->children.at(1));
            
            

    //     // } else if (exp->nodeType == "RelationalExp - GREATER" ||
    //     //             exp->nodeType == "RelationalExp - GREATER_EQUAL" ||
    //     //             exp->nodeType == "RelationalExp - LESS" ||
    //     //             exp->nodeType == "RelationalExp - LESS_EQUAL") {
    //     //     val1 = checkAndGetExpType_recurse(exp->children.at(0));
    //     //     val2 = checkAndGetExpType_recurse(exp->children.at(1));
    //     //     if (!val1.isCorrect || !val1.isCorrect ||
    //     //         val1.type != data::type_t::integer || val2.type != data::type_t::integer) {
    //     //         struct data d = {.type = data::type_t::boolean, .isCorrect = false};
    //     //         return d;
    //     //     }
    //     //     struct data d = {.type = data::type_t::boolean, .isCorrect = true};
    //     //     return d;
    //     // } else if (exp->nodeType == "AdditiveExp - PLUS" ||
    //     //             exp->nodeType == "AdditiveExp - MINUS" ||
    //     //             exp->nodeType == "MultiplicativeExp - MULTIPLY" ||
    //     //             exp->nodeType == "MultiplicativeExp - DIVIDE") {
    //     //     val1 = checkAndGetExpType_recurse(exp->children.at(0));
    //     //     val2 = checkAndGetExpType_recurse(exp->children.at(1));
    //     //     if (!val1.isCorrect || !val1.isCorrect ||
    //     //         val1.type != data::type_t::integer || val2.type != data::type_t::integer) {
    //     //         struct data d = {.type = data::type_t::integer, .isCorrect = false};
    //     //         return d;
    //     //     }
    //     //     struct data d = {.type = data::type_t::integer, .isCorrect = true};
    //     //     return d;
    //     // } else if (exp->nodeType == "UnaryExp - PLUS" ||
    //     //             exp->nodeType == "UnaryExp - MINUS") {
    //     //     val1 = checkAndGetExpType_recurse(exp->children.at(0));
    //     //     if (!val1.isCorrect ||
    //     //         val1.type != data::type_t::integer) {
    //     //         struct data d = {.type = data::type_t::integer, .isCorrect = false};
    //     //         return d;
    //     //     }

    //     //     struct data d = {.type = data::type_t::integer, .isCorrect = true};
    //     //     return d;
    //     // }

    //     // RelationalExp, AdditiveExp, MultiplicativeExp, UnaryExp, Factor
    //     return _interpretExp(exp->children.at(0));
    // }

    // data getDataFromID(node * id) {
    //     map<string, int>::iterator it = integer_values.find(id->data.value.stringValue);
    //     if (it != integer_values.end()) {
    //         int flag = it->second;
            
    //         if (flag == -1) {
    //             struct data d = {.type = data::type_t::integer, .isCorrect = true};
    //             return d;
    //         } else if (flag == 0) {
    //             struct data d = {.type = data::type_t::integer, .isCorrect = false};
    //             return d;
    //         } else {
    //             struct data d = {.type = data::type_t::integer, .isCorrect = true};
    //             return d;
    //         }
    //     }

    //     struct data d = {.type = data::type_t::integer, .isCorrect = false};
    //     return d;
    // }

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

    public:
        /*
         * Run program
         */
        void interpretProgram(node * program) {
            interpretMain(program->children.at(0));
        }
};