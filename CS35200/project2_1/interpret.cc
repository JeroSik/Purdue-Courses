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
                string output = string(exp.value.stringValue);
                cout << output.substr(1, output.size() - 2) << endl;
            } else if (exp.type == data::type_t::integer) {
                cout << exp.value.intValue << endl;
            } else {
                if (exp.value.booleanValue) {
                    cout << "true" << endl;
                } else {
                    cout << "false" << endl;
                }
            }
        
        // SYSTEM_OUT_PRINT OPEN_PARENTHESES Exp CLOSED_PARENTHESES SEMICOLON
        } else if (statementType == "Statement - print") {
            data exp = interpretExp(statement->children.at(0));
            if (exp.type == data::type_t::str) {
                string output = string(exp.value.stringValue);
                cout << output.substr(1, output.size() - 2);
            } else if (exp.type == data::type_t::integer) {
                cout << exp.value.intValue;
            } else {
                if (exp.value.booleanValue) {
                    cout << "true";
                } else {
                    cout << "false";
                }
            }

        // VarDecl
        } else if (statementType == "Statement - VarDecl") {
            interpretVarDecl(statement->children.at(0));
            
        // ID EQUALS Exp SEMICOLON
        } else if (statementType == "Statement - id_equals_exp") {
            interpretVarAssignment(statement->data.value.stringValue, statement->children.at(0));

        } else {
            cout << "[interpret.cc - interpretStatement] Unexpected statement type\n";
        }
    }

    /*
     * Ensure variable is declared correctly (Type ID VarInit VarDeclTail SEMICOLON)
     */
    void interpretVarDecl(node * varDecl) {
        data::type_t lhs_type = varDecl->children.at(0)->data.type;

        string id = varDecl->data.value.stringValue;
        node * varInit = varDecl->children.at(1);
        struct data d = {.type = lhs_type};
        if (varInit->nodeType != "Empty") {
            d = interpretExp(varInit->children.at(0));
        }
        addToScope(id, d);

        // Iterate through varDeclTail and check potential multi-declarations
        node * varDeclTail = varDecl->children.at(2);
        while (varDeclTail->nodeType != "Empty") {
            id = varDeclTail->data.value.stringValue;
            varInit = varDeclTail->children.at(0);
            d = {.type = lhs_type};
            if (varInit->nodeType != "Empty") {
                d = interpretExp(varInit->children.at(0));
            }
            addToScope(id, d);

            varDeclTail = varDeclTail->children.at(1);
        }
    }

    /*
     * Ensure variable is assigned correctly (ID EQUALS Exp SEMICOLON)
     */
    void interpretVarAssignment(string id, node * exp) {
        addToScope(id, interpretExp(exp));
    }

    data interpretExp(node * exp) {
        node * equalityExp = exp->children.at(0);
        data exp_data = _interpretExp(equalityExp);

        return exp_data;
    }
    data _interpretExp(node * exp) {
        // Base case reaching FACTOR
        if (exp->nodeType == "INTEGER_LITERAL") {
            return exp->data;
        } else if (exp->nodeType == "STRING_LITERAL") {
            return exp->data;
        } else if (exp->nodeType == "ID") {
            string id = exp->data.value.stringValue;
            return variable_map[id];
        } else if (exp->nodeType == "Factor - PARENTHESES") {
            return _interpretExp(exp->children.at(0));
        }

        data val1;
        data val2;
        if (exp->nodeType == "EqualityExp - EQUAL_EQUAL") {
            val1 = _interpretExp(exp->children.at(0));
            val2 = _interpretExp(exp->children.at(1));
            bool result;

            if (val1.type == data::type_t::integer) {
                result = (val1.value.intValue == val2.value.intValue);
            } else {
                result = (val1.value.booleanValue == val2.value.booleanValue);
            }

            struct data d = {.type = data::type_t::boolean, .value.booleanValue = result};
            return d;
        } else if (exp->nodeType == "EqualityExp - NOT_EQUAL") {
            val1 = _interpretExp(exp->children.at(0));
            val2 = _interpretExp(exp->children.at(1));
            bool result;

            if (val1.type == data::type_t::integer) {
                result = (val1.value.intValue != val2.value.intValue);
            } else {
                result = (val1.value.booleanValue != val2.value.booleanValue);
            }

            struct data d = {.type = data::type_t::boolean, .value.booleanValue = result};
            return d;
        } else if (exp->nodeType == "RelationalExp - GREATER") {
            val1 = _interpretExp(exp->children.at(0));
            val2 = _interpretExp(exp->children.at(1));
            bool result = (val1.value.intValue > val2.value.intValue);

            struct data d = {.type = data::type_t::boolean, .value.booleanValue = result};
            return d;
        } else if (exp->nodeType == "RelationalExp - GREATER_EQUAL") {
            val1 = _interpretExp(exp->children.at(0));
            val2 = _interpretExp(exp->children.at(1));
            bool result = (val1.value.intValue >= val2.value.intValue);

            struct data d = {.type = data::type_t::boolean, .value.booleanValue = result};
            return d;
        } else if (exp->nodeType == "RelationalExp - LESS") {
            val1 = _interpretExp(exp->children.at(0));
            val2 = _interpretExp(exp->children.at(1));
            bool result = (val1.value.intValue < val2.value.intValue);

            struct data d = {.type = data::type_t::boolean, .value.booleanValue = result};
            return d;
        } else if (exp->nodeType == "RelationalExp - LESS_EQUAL") {
            val1 = _interpretExp(exp->children.at(0));
            val2 = _interpretExp(exp->children.at(1));
            bool result = (val1.value.intValue <= val2.value.intValue);

            struct data d = {.type = data::type_t::boolean, .value.booleanValue = result};
            return d;
        } else if (exp->nodeType == "AdditiveExp - PLUS") {
            val1 = _interpretExp(exp->children.at(0));
            val2 = _interpretExp(exp->children.at(1));
            int result = (val1.value.intValue + val2.value.intValue);

            struct data d = {.type = data::type_t::integer, .value.intValue = result};
            return d;
        } else if (exp->nodeType == "AdditiveExp - MINUS") {
            val1 = _interpretExp(exp->children.at(0));
            val2 = _interpretExp(exp->children.at(1));
            int result = (val1.value.intValue - val2.value.intValue);

            struct data d = {.type = data::type_t::integer, .value.intValue = result};
            return d;
        } else if (exp->nodeType == "MultiplicativeExp - MULTIPLY") {
            val1 = _interpretExp(exp->children.at(0));
            val2 = _interpretExp(exp->children.at(1));
            int result = (val1.value.intValue * val2.value.intValue);

            struct data d = {.type = data::type_t::integer, .value.intValue = result};
            return d;
        } else if (exp->nodeType == "MultiplicativeExp - DIVIDE") {
            val1 = _interpretExp(exp->children.at(0));
            val2 = _interpretExp(exp->children.at(1));
            int result = (val1.value.intValue / val2.value.intValue);

            struct data d = {.type = data::type_t::integer, .value.intValue = result};
            return d;
        } else if (exp->nodeType == "UnaryExp - PLUS") {
            return _interpretExp(exp->children.at(0));
        } else if (exp->nodeType == "UnaryExp - MINUS") {
            val1 = _interpretExp(exp->children.at(0));
            int result = (- val1.value.intValue);

            struct data d = {.type = data::type_t::integer, .value.intValue = result};
            return d;
        }

        // RelationalExp, AdditiveExp, MultiplicativeExp, UnaryExp, Factor
        return _interpretExp(exp->children.at(0));
    }

    void addToScope(string id, data d) {
        // Check if new variable
        if (variable_map.find(id) == variable_map.end()) {
            variable_map.insert(pair<string, data>(id, d));
        // Otherwise update element in map
        } else {
            variable_map[id] = d;
        }
    }

    public:
        /*
         * Run program
         */
        void interpretProgram(node * program) {
            interpretMain(program->children.at(0));
        }
};