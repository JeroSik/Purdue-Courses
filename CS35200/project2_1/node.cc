#include <stdlib.h>
#include <string>
#include <vector>
#pragma once

using namespace std;

struct data {
    enum type_t{
        str, integer, boolean
    } type;
    union value_t{
        char * stringValue;
        int intValue;
        bool booleanValue;
    } value;
};

class node{
    public:
        // Fields
        vector<node*> children;
        string nodeType;
        data data;

        // Constructor 
        node(string nodeType) {
            this->nodeType = nodeType;
        }

        // Methods 
        void addChild(node* child) {
            children.push_back(child);
        }   

        void setStringValue(char * s) {
            data.value.stringValue = s;
            data.type = data::type_t::str;
        } 

        void setIntValue(int i) {
            data.value.intValue = i;
            data.type = data::type_t::integer;
        }

        void setBooleanValue(bool b) {
            data.value.booleanValue = b;
            data.type = data::type_t::boolean;
        }
};