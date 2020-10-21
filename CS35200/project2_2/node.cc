#include <stdlib.h>
#include <string>
#include <vector>
#pragma once

using namespace std;

struct data {
    enum type_t{
        stringType, intType, booleanType
    } type;
    union value_t{
        char * stringValue;
        int intValue;
        bool booleanValue;
    } value;
    // vector<data*> arrayValue;
    int flag;
    bool isCorrect;
};

class node{
    public:
        // Fields
        vector<node*> children;
        string nodeType;
        data data;
        int line_no;

        // Constructor 
        node(string nodeType) {
            this->nodeType = nodeType;
            this->data.isCorrect = true;
        }

        // Methods 
        void addChild(node * child) {
            children.push_back(child);
        }

        void setLineNumber(int x) {
            line_no = x;
        }

        void setStringValue(char * s) {
            data.value.stringValue = s;
            data.type = data::type_t::stringType;
        } 

        void setIntValue(int i) {
            data.value.intValue = i;
            data.type = data::type_t::intType;
        }

        void setBooleanValue(bool b) {
            data.value.booleanValue = b;
            data.type = data::type_t::booleanType;
        }
};