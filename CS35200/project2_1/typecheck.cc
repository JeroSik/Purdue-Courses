#include "node.cc"
#include <iostream>
#include <map>
#include <string>

using namespace std;

class typecheck {
    map<string, int> integer_values;
    map<string, string> string_values;
    map<string, bool> boolean_values;
    
    void checkMain(node * mainClass) {
        string nameOfClass = mainClass->data.value.stringValue;
        
        // Iterate through statement list and check each statement
        node * currStatementList = mainClass->children.at(0);
        while (currStatementList->children.size() == 2) {
            checkStatement(currStatementList->children.at(0));
            currStatementList = currStatementList->children.at(1);
        }
    }

    void checkStatement(node * statement){
        // printf("checkStatement()\n");
        string statementType = statement->nodeType;

        if (statementType == "Statement - println" || statementType == "Statement - print") {
            checkAndGetExpType(statement->children.at(0));
        } else if (statementType == "Statement - VarDecl") {
            node * varDecl = statement->children.at(0);

            data::type_t lhs_type = varDecl->children.at(0)->data.type;
            string id = varDecl->data.value.stringValue;
            data rhs = checkAndGetExpType(varDecl->children.at(1)->children.at(0));
            
            if(isUniqueVariableName(id) && isMatchingType(lhs_type, rhs.type)) {
                switch(rhs.type){
                    case 0: addToScope(id, rhs.value.stringValue); break;
                    case 1: addToScope(id, rhs.value.intValue); break;
                    case 2: addToScope(id, rhs.value.booleanValue); break;
                }
            }
        } else if (statementType == "Statement - id_equals_exp") {
        }
    }

    data checkAndGetExpType(node * exp) {
        return exp->data;
    }
            
    bool isMatchingType(data::type_t lhs_type, data::type_t rhs_type){
        if(lhs_type != rhs_type){
            numErrors++;
            string lhs, rhs;
            switch(lhs_type){
                case 0: lhs = "string"; break;
                case 1: lhs = "integer"; break;
                case 2: lhs = "boolean"; break;
            }
            switch(rhs_type){
                case 0: rhs = "string"; break;
                case 1: rhs = "integer"; break;
                case 2: rhs = "boolean"; break;
            }
        
            cout << "Can't assign " << rhs << " to a(n) " << lhs << " variable" << endl;
            return false;
        }
        else{
            return true;
        }
    }

    bool isUniqueVariableName(string id){
        if (integer_values.find(id) == integer_values.end() && string_values.find(id) == string_values.end() && boolean_values.find(id) == boolean_values.end())
        {
            return true;
        }
        else {
            numErrors++;
            cout << "Variable " << id << " already exists!" << endl;
            return false;
        }
    }

    void addToScope(string id, bool value){
        boolean_values.insert(pair<string, bool>(id, value));
    }
    void addToScope(string id, int value){
        integer_values.insert(pair<string, int>(id, value));
    }
    void addToScope(string id, string value){
        string_values.insert(pair<string, string>(id, value));
    }
    // void addToScope(string id, data rhs) {
    //     switch(rhs.type){
    //         case 0:
    //             string_values.insert(pair<string, string>(id, rhs.value.stringValue));
    //             break;
    //         case 1:
    //             integer_values.insert(pair<string, int>(id, rhs.value.intValue));
    //             break;
    //         case 2:
    //             boolean_values.insert(pair<string, bool>(id, rhs.value.booleanValue));
    //             break;
    //         default:
    //             printf("ERROR in typcheck.cc: addToScope(), invalid type found.\n");
    //     }
    // }

    public:
        int numErrors;

        void checkProgram(node * program) {
            checkMain(program->children.at(0));
        }
};