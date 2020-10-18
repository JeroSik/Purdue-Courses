#include <map>
#include <string.h>
#include "node.cc" 
#include <iostream>

using namespace std;

class interpret{
    map<string, int> integer_values;
    map<string, string> string_values;
    map<string, bool> boolean_values;
    void interpretMain(node* mainClass){
        /* Remember that this grammar only supports two statements in the MainClass */
        interpretStatement(mainClass->children.at(0));
        interpretStatement(mainClass->children.at(1));
    }

    void interpretStatement(node *statement){
        string statementType =  statement->nodeType;

        if(statementType == "STATEMENT - PRINTLN"){
            node* contentsToPrint = statement->children.at(0);
            switch(contentsToPrint->data.type){
                case 0:
                    /*String literal currently stored with the quotes, need to strip away quotes before printing */
                    contentsToPrint->data.value.stringValue++;
                    contentsToPrint->data.value.stringValue[strlen(contentsToPrint->data.value.stringValue) - 1] = 0;
                    cout<<contentsToPrint->data.value.stringValue<<endl;
                    break;
                case 1:
                    cout<<contentsToPrint->data.value.intValue<<endl;
                    break;
                case 2:
                    // Boolean variables are stored as 1 or 0 in C++
                    string convertToBoolean = (contentsToPrint->data.value.booleanValue == 1) ? "true" : "false";
                    cout<< convertToBoolean <<endl;
                    break;
            }    
        }

        else if(statementType == "STATEMENT - PRINT"){
            node* contentsToPrint = statement->children.at(0);
            switch(contentsToPrint->data.type){
                case 0:
                    /*String literal currently stored with the quotes, need to strip away quotes before printing */
                    contentsToPrint->data.value.stringValue++;
                    contentsToPrint->data.value.stringValue[strlen(contentsToPrint->data.value.stringValue) - 1] = 0;
                    cout<<contentsToPrint->data.value.stringValue;
                    break;
                case 1:
                    cout<<contentsToPrint->data.value.intValue;
                    break;
                case 2:
                    // Boolean variables are stored as 1 or 0 in C++
                    string convertToBoolean = (contentsToPrint->data.value.booleanValue == 1) ? "true" : "false";
                    cout<< convertToBoolean;
                    break;
            }       
        }
        
        /* Given our limited grammar, there isn't anything to do in terms of interpreting variables */
        else {
            node * varDecl = statement->children.at(0);
            data::type_t lhs_type = varDecl->children.at(0)->data.type;
            string id = varDecl->data.value.stringValue;
            data::type_t rhs_type = varDecl->children.at(1)->data.type;    
            
            switch(rhs_type){
                case 0: addToScope(id,varDecl->children.at(1)->data.value.stringValue); break;
                case 1: addToScope(id,varDecl->children.at(1)->data.value.intValue); break;
                case 2: addToScope(id,varDecl->children.at(1)->data.value.booleanValue); break;
            } 
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

    public:
        void interpretProgram(node* program){
            interpretMain(program->children.at(0));
        }
};