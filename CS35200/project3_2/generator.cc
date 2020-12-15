#include <fstream>
#include <iostream>
#include <map>
#include <string.h>

#include "node.cc"

using namespace std;

class generator{
    /******************************************************************************************************************************************************
     *                                                                   GLOBAL VARIABLES                                                                 *
     ******************************************************************************************************************************************************/
    vector<map<string,data>> symbol_table;
    map<string,c_data> class_table;
    vector<data> command_line_args;
    string curr_class;
    int scope_level;

    int text_filler_counter;
    int word_filler_counter;
    int if_filler_counter;
    int while_filler_counter;
    int offset_counter;

    vector<string> text_instructions;
    vector<string> word_instructions;
    vector<string> method_instructions;
    vector<string> main_instructions;

    string string_format_text;
    string decimal_format_text;
    string newline_text;
    string true_text;
    string false_text;

    /******************************************************************************************************************************************************
     *                                                                INITIAL PROGRAM INTERPRET                                                           *
     ******************************************************************************************************************************************************/
    void initial_program_generate(node * program) {
        // Add all classes to table
        node * classDeclList = program->children.at(1);
        while (classDeclList->nodeType != "Empty") {
            initial_class_generate(classDeclList->children.at(0));            
            classDeclList = classDeclList->children.at(1);
        }

        // Add global variables and methods to table for each class
        classDeclList = program->children.at(1);
        while (classDeclList->nodeType != "Empty") {
            // Create a level for symbol table scope
            map<string, data> temp_symbol_table;
            symbol_table.push_back(temp_symbol_table);
            scope_level++;

            generate_varList_and_methodList(classDeclList->children.at(0));

            // Remove level for symbol table scope
            symbol_table.pop_back();
            scope_level--;

            classDeclList = classDeclList->children.at(1);
        }
    }

    void initial_class_generate(node * classDecl) {
        // Get class name
        string class_id = classDecl->data.value.stringValue;

        // Create empty class instance and add to table
        struct c_data c_info;
        class_table.insert(pair<string, c_data>(class_id, c_info));
    }

    void generate_varList_and_methodList(node * classDecl) {
        // Get class name, global variables list and methods list
        string class_id = classDecl->data.value.stringValue;
        node * varDeclList = classDecl->children.at(1);
        node * methodDeclList = classDecl->children.at(2);

        // Add all variable delcarations to stack
        vector<node*> varDeclStack;
        while (varDeclList->nodeType != "Empty") {
            varDeclStack.push_back(varDeclList->children.at(1));
            varDeclList = varDeclList->children.at(0);
        }

        // Interpret global variable declarations in reverse order
        for (int i = varDeclStack.size() - 1; i >= 0; i--) {
            generateVarDecl(varDeclStack[i]);
        }

        // Insert global variables into class table
        class_table[class_id].varMap = symbol_table[scope_level];

        // Iterate through all methods
        while (methodDeclList->nodeType != "Empty") {
            // Get method name and method declaration
            node * methodDecl = methodDeclList->children.at(0);
            string method_id = methodDecl->data.value.stringValue;

            // Create empty method instance
            struct m_data m_info;

            // Get method return type
            m_info.returnType = getLHSType(methodDecl->children.at(0));

            // Insert method into class table
            class_table[class_id].methodMap.insert(pair<string, m_data>(method_id, m_info));

            // Get method decl instructions
            vector<string> methodDecl_instructions = generateMethodDecl(methodDecl, class_id);

            // Declare method branch
            method_instructions.push_back("." + method_id + ":");

            // Push link register into stack
            method_instructions.push_back("push {lr}");

            // Get method stack size
            int method_stack_size = get_next_offset();

            // Reserve space in the stack
			method_instructions.push_back("sub sp, sp, #" + to_string(method_stack_size));

            // Append method decl instructions to global method list
            for (int i = 0; i < methodDecl_instructions.size(); i++) {
                method_instructions.push_back(methodDecl_instructions[i]);
            }

            // Move back up the stack and pop program counter
            method_instructions.push_back("add sp, sp, #" + to_string(method_stack_size));
            method_instructions.push_back("pop {lr}");
            method_instructions.push_back("bx lr");

            // Get next method declaration
            methodDeclList = methodDeclList->children.at(1);
        }
    }

    vector<string> generateMethodDecl(node * methodDecl, string class_id) {
        // Initialize instruction list
        vector<string> methodDecl_instructions;

        // Get optional format list and statement list
        node * optionalFormalList = methodDecl->children.at(1);
		node * statementList = methodDecl->children.at(2);

        // Get optional formal list instructions
        methodDecl_instructions = generateOptionalFormatList(optionalFormalList, class_id);

        // Get statement list instructions
        vector<string> statementList_instructions = generateStatementList(statementList).second;

        // Append statement list instructions to method decl instructions
        for (int i = 0; i < statementList_instructions.size(); i++) {
            methodDecl_instructions.push_back(statementList_instructions[i]);
        }

        // Return instructions
        return methodDecl_instructions;
    }

    vector<string> generateOptionalFormatList(node * optionalFormatList, string class_id) {
        // Initialize instruction list and list of arguments
        vector<string> optionalFormatList_instructions;
        vector<data> method_arg;

        // Check if method has a formal list
        if (optionalFormatList->nodeType == "Empty") {
            return optionalFormatList_instructions;
        }

        // Get formal list since method contains one
        node * formalList = optionalFormatList->children.at(0);

        // Get argument name and data type
        string arg_id = formalList->data.value.stringValue;
        struct data arg_data = getLHSType(formalList->children.at(0));
        arg_data.offset = get_next_offset();

        // TODO: add to scope?
        addToScope(arg_id, arg_data, true);
        // class_table[class_id].varMap.insert(pair<string, data>(arg_id, arg_data));
        
        // Store argument data
        method_arg.push_back(arg_data);

        // Iterate through formalsTail and check potential multi-args
        node * formalsTail = formalList->children.at(1);
        while (formalsTail->nodeType != "Empty") {
            // Get argument name and data type
            arg_id = formalsTail->data.value.stringValue;
            arg_data = getLHSType(formalsTail->children.at(0));
            arg_data.offset = get_next_offset();

            // TODO: add to scope?
            addToScope(arg_id, arg_data, true);
            // class_table[class_id].varMap.insert(pair<string, data>(arg_id, arg_data));
            
            // Store argument data
            method_arg.push_back(arg_data);

            // Get next formalsTail
            formalsTail = formalsTail->children.at(1);
        }

        // Store method arguments into registers
        for (int i = 0; i < method_arg.size(); i++) {
            optionalFormatList_instructions.push_back("str r" + to_string(i + 1) + ", [sp, #" + to_string(method_arg[i].offset) + "]");
        }

        // Return instructions
        return optionalFormatList_instructions;
    }

    /******************************************************************************************************************************************************
     *                                                                      GENERATE PROGRAM                                                              *
     ******************************************************************************************************************************************************/
    void generateMainClass(node * mainClass, vector<string> args) {
        // Set current class as main class
        curr_class = mainClass->data.value.stringValue;

        // Create a empty level for symbol table scope to include global variables
        map<string, data> fake_symbol_table;
        symbol_table.push_back(fake_symbol_table);
        scope_level++;

        // Create a level for symbol table scope for main method
        map<string, data> temp_symbol_table;
        symbol_table.push_back(temp_symbol_table);
        scope_level++;

        // Get ID of main argument and create argument instructions
        string main_argument_id = mainClass->children.at(0)->data.value.stringValue;
        vector<string> arg_instructions;

        // Add length of main to symbol table
        struct data arg_length_data = {.type=data::type_t::intType, .offset=get_next_offset()};
        symbol_table[scope_level].insert(pair<string, data>("_"+main_argument_id, arg_length_data));

        // Store value in stack
		arg_instructions.push_back("mov r2, #1");
		arg_instructions.push_back("sub r0, r0, r2");
		arg_instructions.push_back("str r0, [sp, #" + to_string(arg_length_data.offset) + "]");

        // Add argument name to symbol table
        struct data main_argument_data = {.type=data::type_t::stringType, .value.intValue=0, .offset=get_next_offset()};
        symbol_table[scope_level].insert(pair<string, data>(main_argument_id, main_argument_data));

        // Store base of array
		arg_instructions.push_back("mov r0, #4");
		arg_instructions.push_back("add r1, r1, r0");
		arg_instructions.push_back("str r1, [sp, #" + to_string(main_argument_data.offset) + "]");

        // Get statement list instructions
        vector<string> statementList_instructions = generateStatementList(mainClass->children.at(1)).second;

        // Push link register into stack
        main_instructions.push_back("push {lr}");

        // Get stack size
        int stack_size = get_next_offset();

        // Reserve space in the stack
        main_instructions.push_back("sub sp, sp, #" + to_string(stack_size));

        // Append main method argument instructions
        for (int i = 0; i < arg_instructions.size(); i++) {
            main_instructions.push_back(arg_instructions[i]);
        }

        // Append statement list instructions to main method
        for (int i = 0; i < statementList_instructions.size(); i++) {
            main_instructions.push_back(statementList_instructions[i]);
        }

        // Move back up the stack and pop program counter
        main_instructions.push_back("add sp, sp, #" + to_string(stack_size));
        main_instructions.push_back("pop {pc}");

        // Remove boths levels for symbol table
        symbol_table.pop_back();
        scope_level--;
        symbol_table.pop_back();
        scope_level--;
    }

    pair<data, vector<string>> generateStatementList(node * statementList) {
        // Initialize return value data and instruction list
        struct data return_data;
        vector<string> statementList_instructions;

        // Iterate through statement list and check each statement
        while (statementList->nodeType != "Empty") {
            // Generate statement
            pair<data, vector<string>> statement_pair = generateStatement(statementList->children.at(0));
            
            // Set return data and append instructions
            return_data = statement_pair.first;
            for (int i = 0; i < statement_pair.second.size(); i++) {
                statementList_instructions.push_back(statement_pair.second[i]);
            }

            // Get next statement
            statementList = statementList->children.at(1);
        }

        // Return pair of data and instructions
        pair<data, vector<string>> statementList_pair(return_data, statementList_instructions);
        return statementList_pair;
    }

    pair<data, vector<string>> generateStatement(node * statement) {
        // Initialize return data and instructions
        struct data void_data;
        vector<string> statement_instructions;
        
        // Get statement type
        string statementType = statement->nodeType;
        // cout << statementType << "\n";

        if (statementType.find("print") != string::npos) {
            // Get expression within print statement
            pair<data, vector<string>> exp_pair = generateExp(statement->children.at(0));

            // Get data and instructions;
            data exp_data = exp_pair.first;
            statement_instructions = exp_pair.second;

            // Load registers
            if (exp_data.type == data::type_t::stringType) {
                statement_instructions.push_back("ldr r0, =" + string_format_text);
                statement_instructions.push_back("ldr r1, [sp, #" + to_string(exp_data.offset) + "]");
            } else if (exp_data.type == data::type_t::intType) {
                statement_instructions.push_back("ldr r0, =" + decimal_format_text);
                statement_instructions.push_back("ldr r1, [sp, #" + to_string(exp_data.offset) + "]");
            } else if (exp_data.type == data::type_t::booleanType) {
                statement_instructions.push_back("ldr r0, [sp, #" + to_string(exp_data.offset) + "]");
                statement_instructions.push_back("cmp r0, #1");
                statement_instructions.push_back("ldreq r1, =" + true_text);
                statement_instructions.push_back("ldrne r1, =" + false_text);
                statement_instructions.push_back("ldr r0, =" + string_format_text);
            }

            // Print expression
            statement_instructions.push_back("bl printf");

            // Print new line if needed
            if (statementType.find("println") != string::npos) {
                statement_instructions.push_back("ldr r0, =" + newline_text);
                statement_instructions.push_back("bl printf");
            }
        } else if (statementType == "Statement - VarDecl") {
            // Get instructions for variable declarations
            statement_instructions = generateVarDecl(statement->children.at(0));
        }else if (statementType == "Statement - id_equals_exp") {
            // Get instructions for basic variable assignment
            statement_instructions = generateIDAssignment(statement->data.value.stringValue, statement->children.at(0));
        }
        /*
        else if (statementType == "Statement - leftValue_equals_exp") {
            // TODO:
            // // Interpret complex variable assignment
            // interpretLeftValueAssignment(statement->children.at(0), statement->children.at(1));
        }
        */
        else if (statementType == "Statement - if") {
            // Get expression within if statement
            pair<data, vector<string>> exp_pair = generateExp(statement->children.at(0));

            // Get data and instructions;
            data exp_data = exp_pair.first;
            statement_instructions = exp_pair.second;

            // Check if expression is true
            statement_instructions.push_back("ldr r0, [sp, #" + to_string(exp_data.offset) + "]");
            statement_instructions.push_back("cmp r0, #1");

            // Create else and endif branches
            string else_branch = "._else" + to_string(if_filler_counter);
            string endif_branch = "._endif" + to_string(if_filler_counter);
            if_filler_counter++;

            // Create a level for symbol table scope
            map<string, data> temp_symbol_table;
            symbol_table.push_back(temp_symbol_table);
            scope_level++;

            // Then branch
            statement_instructions.push_back("bne " + else_branch);
            pair<data, vector<string>> then_pair = generateStatement(statement->children.at(1));
            for (int i = 0; i < then_pair.second.size(); i++) {
                statement_instructions.push_back(then_pair.second[i]);
            }
            statement_instructions.push_back("b " + endif_branch);

            // Remove level for symbol table scope
            symbol_table.pop_back();
            scope_level--;

            // Create a level for symbol table scope
            map<string, data> temp_symbol_table2;
            symbol_table.push_back(temp_symbol_table2);
            scope_level++;

            // False branch
            statement_instructions.push_back(else_branch + ":");
            pair<data, vector<string>> else_pair = generateStatement(statement->children.at(2));
            for (int i = 0; i < else_pair.second.size(); i++) {
                statement_instructions.push_back(else_pair.second[i]);
            }
            statement_instructions.push_back(endif_branch + ":");

            // Remove level for symbol table scope
            symbol_table.pop_back();
            scope_level--;
        } else if (statementType == "Statement - while") {
            // Create a level for symbol table scope
            map<string, data> temp_symbol_table;
            symbol_table.push_back(temp_symbol_table);
            scope_level++;
            
            // Create loop and endloop branches
            string loop_branch = "._loop" + to_string(while_filler_counter);
            string endloop_branch = "._endloop" + to_string(while_filler_counter);
            while_filler_counter++;

            // Branch to end loop to check condition
            statement_instructions.push_back("b " + endloop_branch);

            // Loop Branch
            statement_instructions.push_back(loop_branch + ":");
            pair<data, vector<string>> loop_body_pair = generateStatement(statement->children.at(1));
            for (int i = 0; i < loop_body_pair.second.size(); i++) {
                statement_instructions.push_back(loop_body_pair.second[i]);
            }

            // End loop Branch
            statement_instructions.push_back(endloop_branch + ":");
            pair<data, vector<string>> exp_pair = generateExp(statement->children.at(0));
            for (int i = 0; i < exp_pair.second.size(); i++) {
                statement_instructions.push_back(exp_pair.second[i]);
            }

            // Evaluate loop expression
            statement_instructions.push_back("ldr r0, [sp, #" + to_string(exp_pair.first.offset) + "]");
            statement_instructions.push_back("cmp r0, #1");

            // Branch back if expression is true
            statement_instructions.push_back("beq " + loop_branch);

            // Remove level for symbol table scope
            symbol_table.pop_back();
            scope_level--;
        } else if (statementType == "Statement - return") {
            // Get return expression
            pair<data, vector<string>> exp_pair = generateExp(statement->children.at(0));

            // Get instructions and store result
            statement_instructions = exp_pair.second;
            statement_instructions.push_back("ldr r0, [sp, #" + to_string(exp_pair.first.offset) + "]");

            // Return information
            return {exp_pair.first, statement_instructions};
        } else if (statementType == "Statement - MethodCall") {
            return generateMethodCall(statement->children.at(0));
        } else if (statementType == "Statement - StatementList") {
            // Generate statement list
            return generateStatementList(statement->children.at(0));
        } else {
            cout << "   [generate.cc - checkStatement] Unexpected statement type\n";
        }

        // Return pair of data and instructions
        return {void_data, statement_instructions};
    }

    vector<string> generateVarDecl(node * varDecl) {
        // Initialize instructions
        vector<string> varDecl_instructions;

        // Get LHS type and variable name
        struct data lhs_type = getLHSType(varDecl->children.at(0));
        string id = varDecl->data.value.stringValue;

        // Initialize variable data struct
        struct data d;

        // Check if variable is initialized
        node * varInit = varDecl->children.at(1);
        if (varInit->nodeType != "Empty") {
            // Get data and instructions from expression
            pair<data, vector<string>> exp_pair = generateExp(varInit->children.at(0));

            // Set data and instructions from expression
            d = exp_pair.first;
            varDecl_instructions = exp_pair.second;

            // Add variable to scope
            addToScope(id, d, true);
        } else {
            d = lhs_type;
        }

        

        // Iterate through varDeclTail and check potential multi-declarations
        node * varDeclTail = varDecl->children.at(2);
        while (varDeclTail->nodeType != "Empty") {
            // Get name of variable
            id = varDeclTail->data.value.stringValue;

            // Check if variable is initialized
            varInit = varDeclTail->children.at(0);
            if (varInit->nodeType != "Empty") {
                // Get data and instructions from expression
                pair<data, vector<string>> exp_pair = generateExp(varInit->children.at(0));

                // Set data and instructions from expression
                d = exp_pair.first;
                for (int i = 0; i < exp_pair.second.size(); i++) {
                    varDecl_instructions.push_back(exp_pair.second[i]);
                }
            } else {
                d = lhs_type;
            }

            // Add variable to scope
            addToScope(id, d, true);

            // Get next variable declarations
            varDeclTail = varDeclTail->children.at(1);
        }

        // Return instructions
        return varDecl_instructions;
    }

    vector<string> generateIDAssignment(string id, node * exp) {
        // Get data and instructions from expression
        pair<data, vector<string>> exp_pair = generateExp(exp);

        // Initialize instructions
        vector<string> IDAssignment_instructions = exp_pair.second;
        data id_data = getDataFromID(id);

        if (id_data.isCorrect) {
            IDAssignment_instructions.push_back("ldr r0, [sp, #" + to_string(exp_pair.first.offset) + "]");
            IDAssignment_instructions.push_back("str r0, [sp, #" + to_string(id_data.offset) + "]");

            exp_pair.first.offset = id_data.offset;
            addToScope(id, exp_pair.first, false);
        } else {
            // Set data and instructions from expression
            id_data = exp_pair.first;

            // Add variable to scope
            addToScope(id, id_data, true);
        }

        

        // Return instructions
        return IDAssignment_instructions;
    }

    pair<data, vector<string>> generateMethodCall(node * methodCall) {
        // Initialize return data and instructions
        struct data methodCall_data;
        vector<string> methodCall_instructions;

        // Store current scope details
        vector<map<string,data>> old_scope = symbol_table;
        int old_scope_level = scope_level;
        string old_class = curr_class;

        // Check method call type
        string methodCallType = methodCall->nodeType;
        if (methodCallType == "MethodCall - ID") {
            // Get children node
            node * optionalExpList = methodCall->children.at(0);

            // Get relevant info
            string method_id = methodCall->data.value.stringValue;
            m_data method_data = class_table[curr_class].methodMap[method_id];

            methodCall_instructions = generateOptionalExpList(optionalExpList);

            methodCall_data = {.type=method_data.returnType.type, .offset=get_next_offset()};
            methodCall_instructions.push_back("bl ." + method_id);
            methodCall_instructions.push_back("str r0, [sp, #" + to_string(methodCall_data.offset) + "]");
        } else if (methodCallType == "MethodCall - LeftValue") {
            // Get children node
            node * leftValue = methodCall->children.at(0);
            node * optionalExpList = methodCall->children.at(1);

            // Get relevant info
            string obj_id = leftValue->data.value.stringValue;
            string method_id = leftValue->children.at(0)->data.value.stringValue;
            m_data method_data = class_table[obj_id].methodMap[method_id];

            vector<string> optionalExpList_instructions = generateOptionalExpList(optionalExpList);

            // Reset scope
            symbol_table.clear();
            scope_level = -1;
            curr_class = obj_id;

            // Add global variables to scope
            symbol_table.push_back(class_table[obj_id].varMap);
            scope_level++;

            // Create a level for symbol table scope for methods
            map<string, data> temp_symbol_table;
            symbol_table.push_back(temp_symbol_table);
            scope_level++;

            // Get leftValue data and instructions
            pair<data, vector<string>> leftValue_pair = getLeftValue(leftValue);

            // Set instructions but remove last call
            methodCall_instructions = leftValue_pair.second;
            methodCall_instructions.pop_back();


            for (int i = 0; i < optionalExpList_instructions.size(); i++) {
				methodCall_instructions.push_back(optionalExpList_instructions[i]);
			}


            // leftValue_pair.first, method_id
            methodCall_data = {.type=method_data.returnType.type, .offset=get_next_offset()};
            methodCall_instructions.push_back("bl ." + method_id);
            methodCall_instructions.push_back("str r0, [sp, #" + to_string(methodCall_data.offset) + "]");

            // Remove levels from scope
            symbol_table.pop_back();
            scope_level--;
            symbol_table.pop_back();
            scope_level--;
        }

        // Restore scope to old scope
        symbol_table = old_scope;
        scope_level = old_scope_level;
        curr_class = old_class;

        // Return pair of data and instructions
        return {methodCall_data, methodCall_instructions};
    }

    pair<data, vector<string>> generateExp(node * exp) {
        // Initialize pair of return data and instructions
        struct data exp_data;
        vector<string> exp_instructions;
        pair<data, vector<string>> exp_pair(exp_data, exp_instructions);

        // Get expression type
        string expType = exp->nodeType;

        // LogicalOrExp
        if (expType == "Exp - LogicalOrExp") {
            // Check expression type
            exp_pair = _generateExp(exp->children.at(0));
        } else {
            cout << "   [generate.cc - checkAndGetExpType] Unexpected expression type: " << expType << "\n";
        }
        
        return exp_pair;
    }

    pair<data, vector<string>> _generateExp(node * exp) {
        // Initialize pair of return data and instructions
        struct data exp_data;
        vector<string> exp_instructions;

        // Get expression type
        string expType = exp->nodeType;

        // Base case reaching FACTOR
        if (expType == "Factor - INTEGER_LITERAL") {
            // Get data and offset
            exp_data = exp->data;
            exp_data.offset = get_next_offset();

            // Save value in word and stack
            string word_id = create_word_instruction(exp_data.value.intValue);
            exp_instructions.push_back("ldr r0, " + word_id);
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "Factor - STRING_LITERAL") {
            // Get data and offset
            exp_data = exp->data;
            exp_data.offset = get_next_offset();

            // Save value in word and stack
            string text_id = create_text_instruction(exp_data.value.stringValue);
            exp_instructions.push_back("ldr r0, =" + text_id);
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "Factor - BOOLEAN_LITERAL") {
            // Get data and offset
            exp_data = exp->data;
            exp_data.offset = get_next_offset();

            // Save value in word and stack
            string word_id = create_word_instruction(exp_data.value.booleanValue);
            exp_instructions.push_back("ldr r0, " + word_id);
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "Factor - ID") {
            // Get expression data from passed in ID
            exp_data = getDataFromID(exp->data.value.stringValue);

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "Factor - LeftValue") {
            return getLeftValue(exp->children.at(0));
        } else if (expType == "Factor - MethodCall") {
            return generateMethodCall(exp->children.at(0));
        } else if (expType == "Factor - Integer_dot_parseInt") {
            // Get and set data and instructions from expression
            pair<data, vector<string>> exp_pair = _generateExp(exp->children.at(0));
            exp_data = exp_pair.first;
            exp_instructions = exp_pair.second;

            // Call "atoi" with value from expression
            exp_instructions.push_back("ldr r0, [sp, #" + to_string(exp_data.offset) + "]");
            exp_instructions.push_back("bl atoi");
            
            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = data::type_t::intType;

            // Store value in stack
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        }
        /*
        else if (expType == "Factor - new_id") {
            string obj_id = exp->data.value.stringValue;
            struct data d = {.type=data::type_t::objType, .objValue=obj_id};
            return d;
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
                            string empty = "";
                            char * result = new char[empty.length() + 1];
                            strcpy(result, empty.c_str());
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
        }
        */
        else if (expType == "Factor - id_dot_length") {
            string id = exp->data.value.stringValue;

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(id) != symbol_table[i].end()) {
                    data val1 = symbol_table[i].find(id)->second;
                    if (val1.type == data::type_t::stringType) {
                        // Set type and offset
                        exp_data.type = data::type_t::intType;
                        exp_data.offset = get_next_offset();

                        // Get length of string
                        exp_instructions.push_back("ldr r0, [sp, #" + to_string(val1.offset) + "]");
                        exp_instructions.push_back("bl strlen");

                        // Store value in stack
                        exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

                        // Return data and instructions
                        return {exp_data, exp_instructions};
                    } else if (val1.type == data::type_t::arrayType || val1.type == data::type_t::multiArrayType) {
                        // TODO:
                    }
                }
            }
        } else if (expType == "Factor - PARENTHESES") {
            return _generateExp(exp->children.at(0));
        }

        // Get data and instructions on first child
        pair<data, vector<string>> val1_pair = _generateExp(exp->children.at(0));
        exp_data = val1_pair.first;
        exp_instructions = val1_pair.second;

        // Check is expression type is unary operation
        if (expType == "UnaryExp - PLUS") {
            return val1_pair;
        } else if (expType == "UnaryExp - MINUS") {
            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = data::type_t::intType;

            // Compute #0 - r0 to get MINUS expression
            exp_instructions.push_back("mov r1, #0");
			exp_instructions.push_back("sub r0, r1, r0");

            // Store value in stack
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "UnaryExp - EXCLAMATION") {
            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = data::type_t::booleanType;

            // Compute #0 - r0 to get MINUS expression
            exp_instructions.push_back("cmp r0, #1");
			exp_instructions.push_back("moveq r0, #0");
            exp_instructions.push_back("movne r0, #1");

            // Store value in stack
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        }

        // Check expression on second child if expression is binary operation
        if (expType == "LogicalOrExp - OR") {
            // Get data and instructions on second child
            pair<data, vector<string>> val2_pair = _generateExp(exp->children.at(1));
            for (int i = 0; i < val2_pair.second.size(); i++) {
                exp_instructions.push_back(val2_pair.second[i]);
            }

            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = data::type_t::booleanType;

            // Set r0 and r1 to first and second child values respectively
            exp_instructions.push_back("ldr r0, [sp, #" + to_string(val1_pair.first.offset) + "]");
            exp_instructions.push_back("ldr r1, [sp, #" + to_string(val2_pair.first.offset) + "]");

            // Compare values and store result
            exp_instructions.push_back("orr r0, r0, r1");
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "LogicalAndExp - AND") {
            // Get data and instructions on second child
            pair<data, vector<string>> val2_pair = _generateExp(exp->children.at(1));
            for (int i = 0; i < val2_pair.second.size(); i++) {
                exp_instructions.push_back(val2_pair.second[i]);
            }

            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = data::type_t::booleanType;

            // Set r0 and r1 to first and second child values respectively
            exp_instructions.push_back("ldr r0, [sp, #" + to_string(val1_pair.first.offset) + "]");
            exp_instructions.push_back("ldr r1, [sp, #" + to_string(val2_pair.first.offset) + "]");

            // Compare values and store result
            exp_instructions.push_back("and r0, r0, r1");
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "EqualityExp - EQUAL_EQUAL") {
            // Get data and instructions on second child
            pair<data, vector<string>> val2_pair = _generateExp(exp->children.at(1));
            for (int i = 0; i < val2_pair.second.size(); i++) {
                exp_instructions.push_back(val2_pair.second[i]);
            }

            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = data::type_t::booleanType;

            // Set r0 and r1 to first and second child values respectively
            exp_instructions.push_back("ldr r0, [sp, #" + to_string(val1_pair.first.offset) + "]");
            exp_instructions.push_back("ldr r1, [sp, #" + to_string(val2_pair.first.offset) + "]");

            // Compare values and store result
            exp_instructions.push_back("cmp r0, r1");
            exp_instructions.push_back("moveq r0, #1");
            exp_instructions.push_back("movne r0, #0");
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "EqualityExp - NOT_EQUAL") {
            // Get data and instructions on second child
            pair<data, vector<string>> val2_pair = _generateExp(exp->children.at(1));
            for (int i = 0; i < val2_pair.second.size(); i++) {
                exp_instructions.push_back(val2_pair.second[i]);
            }

            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = data::type_t::booleanType;

            // Set r0 and r1 to first and second child values respectively
            exp_instructions.push_back("ldr r0, [sp, #" + to_string(val1_pair.first.offset) + "]");
            exp_instructions.push_back("ldr r1, [sp, #" + to_string(val2_pair.first.offset) + "]");

            // Compare values and store result
            exp_instructions.push_back("cmp r0, r1");
            exp_instructions.push_back("movne r0, #1");
            exp_instructions.push_back("moveq r0, #0");
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "RelationalExp - GREATER") {
            // Get data and instructions on second child
            pair<data, vector<string>> val2_pair = _generateExp(exp->children.at(1));
            for (int i = 0; i < val2_pair.second.size(); i++) {
                exp_instructions.push_back(val2_pair.second[i]);
            }

            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = data::type_t::booleanType;

            // Set r0 and r1 to first and second child values respectively
            exp_instructions.push_back("ldr r0, [sp, #" + to_string(val1_pair.first.offset) + "]");
            exp_instructions.push_back("ldr r1, [sp, #" + to_string(val2_pair.first.offset) + "]");

            // Compare values and store result
            exp_instructions.push_back("cmp r0, r1");
            exp_instructions.push_back("movgt r0, #1");
            exp_instructions.push_back("movle r0, #0");
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "RelationalExp - GREATER_EQUAL") {
            // Get data and instructions on second child
            pair<data, vector<string>> val2_pair = _generateExp(exp->children.at(1));
            for (int i = 0; i < val2_pair.second.size(); i++) {
                exp_instructions.push_back(val2_pair.second[i]);
            }

            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = data::type_t::booleanType;

            // Set r0 and r1 to first and second child values respectively
            exp_instructions.push_back("ldr r0, [sp, #" + to_string(val1_pair.first.offset) + "]");
            exp_instructions.push_back("ldr r1, [sp, #" + to_string(val2_pair.first.offset) + "]");

            // Compare values and store result
            exp_instructions.push_back("cmp r0, r1");
            exp_instructions.push_back("movge r0, #1");
            exp_instructions.push_back("movlt r0, #0");
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "RelationalExp - LESS") {
            // Get data and instructions on second child
            pair<data, vector<string>> val2_pair = _generateExp(exp->children.at(1));
            for (int i = 0; i < val2_pair.second.size(); i++) {
                exp_instructions.push_back(val2_pair.second[i]);
            }

            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = data::type_t::booleanType;

            // Set r0 and r1 to first and second child values respectively
            exp_instructions.push_back("ldr r0, [sp, #" + to_string(val1_pair.first.offset) + "]");
            exp_instructions.push_back("ldr r1, [sp, #" + to_string(val2_pair.first.offset) + "]");

            // Compare values and store result
            exp_instructions.push_back("cmp r0, r1");
            exp_instructions.push_back("movlt r0, #1");
            exp_instructions.push_back("movge r0, #0");
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "RelationalExp - LESS_EQUAL") {
            // Get data and instructions on second child
            pair<data, vector<string>> val2_pair = _generateExp(exp->children.at(1));
            for (int i = 0; i < val2_pair.second.size(); i++) {
                exp_instructions.push_back(val2_pair.second[i]);
            }

            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = data::type_t::booleanType;

            // Set r0 and r1 to first and second child values respectively
            exp_instructions.push_back("ldr r0, [sp, #" + to_string(val1_pair.first.offset) + "]");
            exp_instructions.push_back("ldr r1, [sp, #" + to_string(val2_pair.first.offset) + "]");

            // Compare values and store result
            exp_instructions.push_back("cmp r0, r1");
            exp_instructions.push_back("movle r0, #1");
            exp_instructions.push_back("movgt r0, #0");
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "AdditiveExp - PLUS") {
            // Get data and instructions on second child
            pair<data, vector<string>> val2_pair = _generateExp(exp->children.at(1));
            for (int i = 0; i < val2_pair.second.size(); i++) {
                exp_instructions.push_back(val2_pair.second[i]);
            }

            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = val1_pair.first.type;

            // Set r0 and r1 to first and second child values respectively
            exp_instructions.push_back("ldr r0, [sp, #" + to_string(val1_pair.first.offset) + "]");
            exp_instructions.push_back("ldr r1, [sp, #" + to_string(val2_pair.first.offset) + "]");

            // Check if adding integers or concatenating strings
            if (val1_pair.first.type == data::type_t::intType) {
                // Add values and store result
                exp_instructions.push_back("add r0, r0, r1");
			    exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");
            } else {
                // Store lengths of both strings
                exp_instructions.push_back("mov r4, r0");
                exp_instructions.push_back("mov r5, r1");
                exp_instructions.push_back("bl strlen");
                exp_instructions.push_back("mov r6, r0");
                exp_instructions.push_back("mov r0, r5");
                exp_instructions.push_back("bl strlen");

                // Create space for new string
                exp_instructions.push_back("add r0, r0, r6");
                exp_instructions.push_back("add r0, r0, #1");
                exp_instructions.push_back("bl malloc");

                // Concatenate values and store result
                exp_instructions.push_back("mov r1, #0");
                exp_instructions.push_back("str r1, [r0]");
                exp_instructions.push_back("mov r1, r4");
                exp_instructions.push_back("bl strcat");
                exp_instructions.push_back("mov r1, r5");
                exp_instructions.push_back("bl strcat");
                exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");
            }

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "AdditiveExp - MINUS") {
            // Get data and instructions on second child
            pair<data, vector<string>> val2_pair = _generateExp(exp->children.at(1));
            for (int i = 0; i < val2_pair.second.size(); i++) {
                exp_instructions.push_back(val2_pair.second[i]);
            }

            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = val1_pair.first.type;

            // Set r0 and r1 to first and second child values respectively
            exp_instructions.push_back("ldr r0, [sp, #" + to_string(val1_pair.first.offset) + "]");
            exp_instructions.push_back("ldr r1, [sp, #" + to_string(val2_pair.first.offset) + "]");

            // Subtract values and store result
            exp_instructions.push_back("sub r0, r0, r1");
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "MultiplicativeExp - MULTIPLY") {
            // Get data and instructions on second child
            pair<data, vector<string>> val2_pair = _generateExp(exp->children.at(1));
            for (int i = 0; i < val2_pair.second.size(); i++) {
                exp_instructions.push_back(val2_pair.second[i]);
            }

            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = val1_pair.first.type;

            // Set r0 and r1 to first and second child values respectively
            exp_instructions.push_back("ldr r0, [sp, #" + to_string(val1_pair.first.offset) + "]");
            exp_instructions.push_back("ldr r1, [sp, #" + to_string(val2_pair.first.offset) + "]");

            // Multiply values and store result
            exp_instructions.push_back("mul r0, r0, r1");
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        } else if (expType == "MultiplicativeExp - DIVIDE") {
            // Get data and instructions on second child
            pair<data, vector<string>> val2_pair = _generateExp(exp->children.at(1));
            for (int i = 0; i < val2_pair.second.size(); i++) {
                exp_instructions.push_back(val2_pair.second[i]);
            }

            // Set offset and type
            exp_data.offset = get_next_offset();
            exp_data.type = val1_pair.first.type;

            // Set r0 and r1 to first and second child values respectively
            exp_instructions.push_back("ldr r0, [sp, #" + to_string(val1_pair.first.offset) + "]");
            exp_instructions.push_back("ldr r1, [sp, #" + to_string(val2_pair.first.offset) + "]");

            // Divide values and store result
            exp_instructions.push_back("bl __aeabi_idiv");
            exp_instructions.push_back("str r0, [sp, #" + to_string(exp_data.offset) + "]");

            // Return data and instructions
            return {exp_data, exp_instructions};
        }

        // "Exp - LogicalOrExp", "LogicalAndExp", "EqualityExp", "RelationalExp", "AdditiveExp", "MultiplicativeExp", "UnaryExp", "Factor"
        return val1_pair;
    }

    vector<string> generateOptionalExpList(node * optionalExpList) {
        // Initialize instruction list and list of arguments
        vector<string> optionalExpList_instructions;
        vector<data> user_args;

        if (optionalExpList->nodeType == "Empty") {
            return optionalExpList_instructions;
        }

        node * expList = optionalExpList->children.at(0);
        node * exp = expList->children.at(0);

        pair<data, vector<string>> exp_pair = generateExp(exp);
        user_args.push_back(exp_pair.first);
        optionalExpList_instructions = exp_pair.second;

        node * expTail = expList->children.at(1);
        while (expTail->nodeType != "Empty") {
            exp = expTail->children.at(0);
            
            exp_pair = generateExp(exp);
            user_args.push_back(exp_pair.first);
            for (int i = 0; i < exp_pair.second.size(); i++) {
                optionalExpList_instructions.push_back(exp_pair.second[i]);
            }

            expTail = expTail->children.at(1);
        }

        // Load user arguments into registers
        for (int i = 0; i < user_args.size(); i++) {
            optionalExpList_instructions.push_back("ldr r" + to_string(i + 1) + ", [sp, #" + to_string(user_args[i].offset) + "]");
        }

        return optionalExpList_instructions;
    }

    pair<data, vector<string>> getLeftValue(node * leftValue) {
        // Initialize pair of return data and instructions
        struct data leftValue_data;
        vector<string> leftValue_instructions;

        // Get leftValue type
        string leftValueType = leftValue->nodeType;

        if (leftValueType == "LeftValue - leftValue_index") {
            // TODO:
            cout << "Did not implement\n";
        } else if (leftValueType == "LeftValue - id_index") {
            // Get ID and exp from LeftValue
            string id = leftValue->data.value.stringValue;
            node * exp = leftValue->children.at(0)->children.at(0);

            // Get data and instructions from expression
            pair<data, vector<string>> exp_pair = generateExp(exp);
            leftValue_instructions = exp_pair.second;

            // Get data from ID
            data id_data = getDataFromID(id);
            leftValue_data = {.type=id_data.type, .value.intValue=id_data.value.intValue, .offset=get_next_offset()};

            // Get offset of the index in array
            leftValue_instructions.push_back("ldr r0, [sp, #" + to_string(exp_pair.first.offset) + "]");
            leftValue_instructions.push_back("mov r1, #4");
			leftValue_instructions.push_back("mul r1, r1, r0");
            
            // Get base value of array
            leftValue_instructions.push_back("ldr r0, [sp, #" + to_string(id_data.offset) + "]");

            // Add offset to base value of array
            leftValue_instructions.push_back("add r0, r0, r1");
			leftValue_instructions.push_back("ldr r2, [r0, #0]");
			leftValue_instructions.push_back("str r2, [sp, #" + to_string(leftValue_data.offset) + "]");
        } else if (leftValueType == "LeftValue - leftValue_dot_id") {
            // TODO:
            cout << "Did not implement\n";
        } else if (leftValueType == "LeftValue - id_dot_id") {
            // TODO:
            cout << "Did not implement\n";
        } else if (leftValueType == "LeftValue - new_id_parentheses_dot_id") {
            // Get id names
            string obj_id = leftValue->data.value.stringValue;
            string field_id = leftValue->children.at(0)->data.value.stringValue;

            leftValue_data = {.type=data::type_t::objType, .objValue=obj_id, .offset=get_next_offset()};
            int size = 0;
            string word_id = create_word_instruction(size);
            leftValue_instructions.push_back("ldr r0, " + word_id);
            leftValue_instructions.push_back("bl malloc");
            leftValue_instructions.push_back("str r0, [sp, #" + to_string(leftValue_data.offset) + "]");

            leftValue_instructions.push_back(field_id);
        } else if (leftValueType == "LeftValue - this_dot_id") {
            // TODO:
            cout << "Did not implement\n";
        } else {
            cout << "   [generator.cc - getLeftValue] Unexpected LeftValue type.\n";
        }

        // Return pair of data and instructions
        return {leftValue_data, leftValue_instructions};
    }

    data getLHSType(node * type) {
        struct data d;

        if (type->nodeType == "Type - PrimeType") {
            node * primetype = type->children.at(0);
            d.type = primetype->data.type;
        } else if (type->nodeType == "Type - ID") { 
            d.type = data::type_t::objType;
            d.objValue = type->data.value.stringValue;
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
            cout << "   [generate.cc - getLHSType] Unexpected type.\n";
        }

        return d;
    }

    /*
    void interpretLeftValueAssignment(node * leftValue, node * exp) {
        // Get leftValue type
        string leftValueType = leftValue->nodeType;
        data exp_data = interpretExp(exp);

        if (leftValueType == "LeftValue - leftValue_index") {
            //TODO:
        } else if (leftValueType == "LeftValue - id_index") {
            string id = leftValue->data.value.stringValue;

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
        } else if (leftValueType == "LeftValue - leftValue_dot_id") {
            string obj_id = getObjFromLeftValue(leftValue->children.at(0));
            string field_id = leftValue->data.value.stringValue;

            class_table[obj_id].varMap[field_id] = exp_data;
        } else if (leftValueType == "LeftValue - id_dot_id") {
            string var_id = leftValue->data.value.stringValue;
            string field_id = leftValue->children.at(0)->data.value.stringValue;

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(var_id) != symbol_table[i].end()) {
                    data id_data = symbol_table[i].find(var_id)->second;
                    class_table[id_data.objValue].varMap[field_id] = exp_data;
                    return;
                }
            }
        } else if (leftValueType == "LeftValue - this_dot_id") {
            string id = leftValue->data.value.stringValue;
            data id_data = symbol_table[0].find(id)->second;
            class_table[curr_class].varMap[id] = exp_data;
            symbol_table[0][id] = exp_data;
        }
    }

    string getObjFromLeftValue(node * leftValue) {
        string leftValueType = leftValue->nodeType;
        string obj_id;

        if (leftValueType == "LeftValue - leftValue_dot_id") {
            string obj_id = getObjFromLeftValue(leftValue->children.at(0));
            string field_id = leftValue->data.value.stringValue;

            data d = class_table[obj_id].varMap[field_id];
            return d.objValue;
        } else if (leftValueType == "LeftValue - id_dot_id") {
            // Get id names
            string var_id = leftValue->data.value.stringValue;
            string field_id = leftValue->children.at(0)->data.value.stringValue;

            for (int i = scope_level; i >= 0; i--) {
                if (symbol_table[i].find(var_id) != symbol_table[i].end()) {
                    data id_data = symbol_table[i].find(var_id)->second;
                    data d = class_table[id_data.objValue].varMap[field_id];
                    return d.objValue;
                }
            }
        } else if (leftValueType == "LeftValue - new_id_parentheses_dot_id") {
            // Get id names
            string obj_id = leftValue->data.value.stringValue;
            string field_id = leftValue->children.at(0)->data.value.stringValue;

            struct data d = class_table[obj_id].varMap[field_id];
            return d.objValue;
        } else if (leftValueType == "LeftValue - this_dot_id") {
            string id = leftValue->data.value.stringValue;
            data id_data = symbol_table[0].find(id)->second;
            return id_data.objValue;
        }

        return obj_id;
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
    */

    /******************************************************************************************************************************************************
     *                                                                     HELPER FUNCTION                                                                *
     ******************************************************************************************************************************************************/
    data getDataFromID(string id) {
        // Initialize return data struct
        struct data d = {.isCorrect=false};

        // Iterate through symbol_table at each scope
        for (int i = scope_level; i >= 0; i--) {
            if (symbol_table[i].find(id) != symbol_table[i].end()) {
                return symbol_table[i].find(id)->second;
            }
        }

        // cout << "   [generator.cc - getDataFromID] ID not found.\n";
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
                    return;
                }
            }
        }
    }

    string create_text_instruction(string str) {
        // Generate ID for text
        string text_id = "text" + to_string(text_filler_counter);
        text_filler_counter++;

        // Add string as global text
        text_instructions.push_back(text_id + ": .asciz \"" + str +"\"");

        // Return new text ID
        return text_id;
    }

    string create_word_instruction(int num) {
        // Generate ID for word
        string word_id = "word" + to_string(word_filler_counter);
        word_filler_counter++;

        // Add number as global word
        word_instructions.push_back(word_id + ": .word " + to_string(num));

        // Return new word ID
        return word_id;
    }

    int get_next_offset() {
        // Get current offset
        int offset = offset_counter;
        // Increment offset by 4 bytes
        offset_counter += 4;
        // Return old offset
        return offset;
    }

    void generate_ASM(string filename) {
        // Create new ASM file
		ofstream asm_file;
		asm_file.open(filename + ".s");

        // Declare data section 
		asm_file << ".section .data\n";

        // Add .text values
		for (int i = 0; i < text_instructions.size(); i++) {
			asm_file << text_instructions[i] << "\n";
		}
        asm_file << "\n";

        // Declare text section 
		asm_file << ".section .text\n";
        // Make main function visible to linker
		asm_file << ".global main\n";
        // Declare byte alignment for 32-bit machine
		asm_file << ".balign 4\n";
        
        // Add main function
		asm_file << "\nmain:\n";

		// Add instructions for main class
		for (int i = 0; i < main_instructions.size(); i++) {
            if (main_instructions[i].size() > 0 && main_instructions[i].at(0) == '.') {
                asm_file << main_instructions[i] << "\n";
            } else {
                asm_file << "   " << main_instructions[i] << "\n";
            }
		}

		// Append .word values
        asm_file << "\n";
		for (int i = 0; i < word_instructions.size(); i++) {
			asm_file << word_instructions[i] << "\n";
		}

        // Add instructions for methods
        asm_file << "\n";
        for (int i = 0; i < method_instructions.size(); i++) {
            if (method_instructions[i].size() > 0 && method_instructions[i].at(0) == '.') {
                asm_file << method_instructions[i] << "\n";
            } else {
                asm_file << "   " << method_instructions[i] << "\n";
            }
		}

        // Close file
		asm_file.close();
	}

    /******************************************************************************************************************************************************
     *                                                                          CLASS CODE                                                                *
     ******************************************************************************************************************************************************/
    public:
        void generateProgram(node * program, string program_name, vector<string> args) {
            // Initialize scope as -1
            scope_level = -1;

            // Initialize filler counters as 0
            text_filler_counter = 0;
            word_filler_counter = 0;
            if_filler_counter = 0;
            while_filler_counter = 0;

            // Initialize global text strings
            string_format_text = create_text_instruction("\%s");
            decimal_format_text = create_text_instruction("\%d");
            newline_text = create_text_instruction("\\n");
            true_text = create_text_instruction("true");
            false_text = create_text_instruction("false");

            // Create one instance of each class
            initial_program_generate(program);

            // Interpret main class
            generateMainClass(program->children.at(0), args);

            // Generate ASM file
            generate_ASM(program_name);
        }
};