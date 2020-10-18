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
%type <node> Program MainClass VarDecl VarInit VarDeclTail Type StatementList Statement Exp EqualityExp RelationalExp AdditiveExp MultiplicativeExp UnaryExp Factor

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
                            $$->addChild($1);
                            $$->addChild($2);
                        }
                    ;
Statement:              SYSTEM_OUT_PRINTLN OPEN_PARENTHESES Exp CLOSED_PARENTHESES SEMICOLON
                        {
                            $$ = new node("Statement - println");
                            $$->addChild($3);
                            $$->setLineNumber(yylineno);
                        }
                    |   SYSTEM_OUT_PRINT OPEN_PARENTHESES Exp CLOSED_PARENTHESES SEMICOLON
                        {
                            $$ = new node("Statement - print");
                            $$->addChild($3);
                            $$->setLineNumber(yylineno);
                        }
                    |   VarDecl
                        {
                            $$ = new node("Statement - VarDecl");
                            $$->addChild($1);
                            $$->setLineNumber(yylineno);
                        }
                    |   ID EQUALS Exp SEMICOLON
                        {
                            $$ = new node("Statement - id_equals_exp");
                            $$->setStringValue($1);
                            $$->addChild($3);
                            $$->setLineNumber(yylineno);
                        }
                    ;
Exp:                    EqualityExp
                        {
                            $$ = new node("Exp - EqualityExp");
                            $$->addChild($1);
                        }
                    ;
EqualityExp:            EqualityExp EQUAL_EQUAL RelationalExp
                        {
                            $$ = new node("EqualityExp - EQUAL_EQUAL");
                            $$->addChild($1);
                            $$->addChild($3);
                        }
                    |   EqualityExp NOT_EQUAL RelationalExp
                        {
                            $$ = new node("EqualityExp - NOT_EQUAL");
                            $$->addChild($1);
                            $$->addChild($3);
                        }
                    |   RelationalExp
                        {
                            $$ = new node("RelationalExp");
                            $$->addChild($1);
                        }
                    ;
RelationalExp:          RelationalExp GREATER AdditiveExp
                        {
                            $$ = new node("RelationalExp - GREATER");
                            $$->addChild($1);
                            $$->addChild($3);
                        }
                    |   RelationalExp GREATER_EQUAL AdditiveExp
                        {
                            $$ = new node("RelationalExp - GREATER_EQUAL");
                            $$->addChild($1);
                            $$->addChild($3);
                        }
                    |   RelationalExp LESS AdditiveExp
                        {
                            $$ = new node("RelationalExp - LESS");
                            $$->addChild($1);
                            $$->addChild($3);
                        }
                    |   RelationalExp LESS_EQUAL AdditiveExp
                        {
                            $$ = new node("RelationalExp - LESS_EQUAL");
                            $$->addChild($1);
                            $$->addChild($3);
                        }
                    |   AdditiveExp
                        {
                            $$ = new node("AdditiveExp");
                            $$->addChild($1);
                        }
                    ;
AdditiveExp:            AdditiveExp PLUS MultiplicativeExp
                        {
                            $$ = new node("AdditiveExp - PLUS");
                            $$->addChild($1);
                            $$->addChild($3);
                        }
                    |   AdditiveExp MINUS MultiplicativeExp
                        {
                            $$ = new node("AdditiveExp - MINUS");
                            $$->addChild($1);
                            $$->addChild($3);
                        }
                    |   MultiplicativeExp
                        {
                            $$ = new node("MultiplicativeExp");
                            $$->addChild($1);
                        }
                    ;
MultiplicativeExp:      MultiplicativeExp MULTIPLY UnaryExp
                        {
                            $$ = new node("MultiplicativeExp - MULTIPLY");
                            $$->addChild($1);
                            $$->addChild($3);
                        }
                    |   MultiplicativeExp DIVIDE UnaryExp
                        {
                            $$ = new node("MultiplicativeExp - DIVIDE");
                            $$->addChild($1);
                            $$->addChild($3);
                        }
                    |   UnaryExp
                        {
                            $$ = new node("UnaryExp");
                            $$->addChild($1);
                        }
                    ;
UnaryExp:               PLUS Factor
                        {
                            $$ = new node("UnaryExp - PLUS");
                            $$->addChild($2);
                        }
                    |   MINUS Factor
                        {
                            $$ = new node("UnaryExp - MINUS");
                            $$->addChild($2);
                        }
                    |   Factor
                        {
                            $$ = new node("Factor");
                            $$->addChild($1);
                        }
                    ;
Factor:                 OPEN_PARENTHESES Exp CLOSED_PARENTHESES
                        {
                            $$ = new node("Factor - PARENTHESES");
                            $$->addChild($2);
                        }
                    |   ID
                        {
                            $$ = new node("ID");
                            $$->setStringValue($1);
                        }
                    |   STRING_LITERAL
                        {
                            $$ = new node("STRING_LITERAL");
                            $$->setStringValue($1);
                        }
                    |   INTEGER_LITERAL
                        {
                            $$ = new node("INTEGER_LITERAL");
                            $$->setIntValue($1);
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
        interpret * ic = new interpret();
        ic->interpretProgram(root);
    }
    return 0;
}