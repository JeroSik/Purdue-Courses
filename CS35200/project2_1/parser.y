%{
    #include "typecheck.cc"
    #include "interpret.cc"
    #include <stdio.h>
    #include <string.h>
    
    extern "C" int yylex();
    extern int yylineno;
    extern char* yytext;
    extern FILE *yyin;

    void yyerror(char *);

    node * root;

// AdditiveExp:            AdditiveExp PLUS MultiplicativeExp
//                     |   AdditiveExp MINUS MultiplicativeExp
//                     |   MultiplicativeExp
//                     ;
// MultiplicativeExp:      MultiplicativeExp MULTIPLY FactorExp
//                     |   MultiplicativeExp DIVIDE FactorExp
//                     |   FactorExp
//                     ;
// FactorExp:              OPEN_PARENTHESES AdditiveExp CLOSED_PARENTHESES
//                     |   INTEGER_LITERAL
//                     ;
%}

%union {
    node * node;
    int integer;
    char * string;
}

// Left hand non-terminals
%type <node> Program MainClass VarDecl VarInit VarDeclTail Statement StatementList Exp Type

// Terminal symbols
%token <node> AND BOOLEAN CLASS CLOSED_BRACKET CLOSED_CURLY CLOSED_PARENTHESES COMMA
%token <node> DIVIDE DOT ELSE EQUALS EQUAL_EQUAL EXCLAMATION EXTENDS FALSE GREATER GREATER_EQUAL
%token <node> IF INT LENGTH LESS LESS_EQUAL MAIN MINUS MINUS_MINUS MULTIPLY
%token <node> NEW NOT_EQUAL OPEN_BRACKET OPEN_CURLY OPEN_PARENTHESES OR PLUS PLUS_PLUS PUBLIC
%token <node> RETURN SEMICOLON STATIC STRING SYSTEM_OUT_PRINT SYSTEM_OUT_PRINTLN
%token <node> THIS TRUE VOID WHILE

%token <integer> INTEGER_LITERAL
%token <string> STRING_LITERAL ID

%start Program

%%

Program:                MainClass
                        {
                            $$ = new node("Program");
                            $$->addChild($1);
                            root = $$;
                        }
                    ;
MainClass:              CLASS ID OPEN_CURLY PUBLIC STATIC VOID MAIN OPEN_PARENTHESES STRING OPEN_BRACKET CLOSED_BRACKET ID CLOSED_PARENTHESES OPEN_CURLY StatementList CLOSED_CURLY CLOSED_CURLY
                        {
                            $$ = new node("MainClass");
                            $$->setStringValue($2);
                            $$->addChild($15);
                        }
                    ;
VarDecl:                Type ID VarInit VarDeclTail SEMICOLON
                        {
                            $$ = new node("VarDecl");
                            $$->addChild($1);
                            $$->setStringValue($2);
                            $$->addChild($3);
                            $$->addChild($4);
                        }
                    ;
VarInit:                %empty
                        {
                            $$ = new node("Empty");
                        }
                    |   EQUALS Exp
                        {
                            $$ = new node("VarInit");
                            $$->addChild($2);
                        }
                    ;
VarDeclTail:            %empty
                        {
                            $$ = new node("Empty");
                        }
                    |   COMMA ID VarInit VarDeclTail
                        {
                            $$ = new node("VarDeclTail");
                            $$->setStringValue($2);
                            $$->addChild($3);
                            $$->addChild($4);
                        }
                    ;
Type:                   INT
                        {
                            $$ = new node("INT_TYPE");
                            $$->data.type = data::type_t::integer;
                        }
                    |   BOOLEAN
                        {
                            $$ = new node("BOOLEAN_TYPE");
                            $$->data.type = data::type_t::boolean;
                        }
                    |   STRING
                        {
                            $$ = new node("STRING_TYPE");
                            $$->data.type = data::type_t::str;
                        }
                    ;
StatementList:          %empty
                        {
                            $$ = new node("Empty");
                        }
                    |   Statement StatementList
                        {
                            $$ = new node("StatementList");
                            $$ -> addChild($1);
                            $$ -> addChild($2);
                        }
                    ;
Statement:              VarDecl
                        {
                            $$ = new node("Statement - VarDecl");
                            $$ -> addChild($1);
                        }
                    |   SYSTEM_OUT_PRINTLN OPEN_PARENTHESES Exp CLOSED_PARENTHESES SEMICOLON
                        {
                            $$ = new node("Statement - println");
                            $$ -> addChild($3);
                        }
                    |   SYSTEM_OUT_PRINT OPEN_PARENTHESES Exp CLOSED_PARENTHESES SEMICOLON
                        {
                            $$ = new node("Statement - print");
                            $$ -> addChild($3);
                        }
                    |   ID EQUALS Exp SEMICOLON
                        {
                            $$ = new node("Statement - id_equals_exp");
                            $$->setStringValue($1);
                            $$->addChild($3);
                        }
                    ;
Exp:                    Exp Op Exp
                    |   EXCLAMATION Exp
                    |   PLUS Exp
                    |   MINUS Exp
                    |   ID
                    |   INTEGER_LITERAL
                        {
                            $$ = new node("INTEGER_LITERAL");
                            $$->setIntValue($1);
                        }
                    |   STRING_LITERAL
                        {
                            $$ = new node("STRING_LITERAL");
                            $$->setStringValue($1);
                        }
                    |   TRUE
                        {
                            $$ = new node("BOOLEAN_LITERAL");
                            $$->setBooleanValue(true);
                        }
                    |   FALSE
                        {
                            $$ = new node("BOOLEAN_LITERAL");
                            $$->setBooleanValue(false);
                        }
                    ;
AdditiveExp:            AdditiveExp PLUS MultiplicativeExp
                    |   AdditiveExp MINUS MultiplicativeExp
                    |   MultiplicativeExp
                    ;
MultiplicativeExp:      MultiplicativeExp MULTIPLY FactorExp
                    |   MultiplicativeExp DIVIDE FactorExp
                    |   FactorExp
                    ;
FactorExp:              OPEN_PARENTHESES AdditiveExp CLOSED_PARENTHESES
                    |   INTEGER_LITERAL
                    ;
Op:                     AND
                    |   OR
                    |   LESS
                    |   GREATER
                    |   LESS_EQUAL
                    |   GREATER_EQUAL
                    |   EQUAL_EQUAL
                    |   NOT_EQUAL
                    ;

%%

void yyerror(char * s) {
    fprintf(stderr, "%s on line %d\n", s, yylineno);
}

int main(int argc, char ** argv) {
    ++argv, --argc;	/* skip over program name */
    if ( argc > 0 )
        yyin = fopen( argv[0], "r" );
    else
        yyin = stdin;

    // Check for syntax errors
    if (yyparse() != 0) {
        return 1;
    }

    // Traverses AST to check for semantic errors if no syntax errors
    typecheck * tc = new typecheck();
    tc->checkProgram(root);

    // Traverse the AST again to interpret the program if no semantic errors
    if(tc->numErrors == 0){
        printf("No type check errors\n");
        // interpret * ic = new interpret();
        // ic->interpretProgram(root);
    }
    return 0;
}