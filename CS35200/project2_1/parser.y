%{
    #include <stdio.h>
    
    int yylex(void);
    void yyerror(char *);

    extern int yylineno;
    extern char* yytext;
    extern FILE *yyin;
%}

%token AND
%token BOOLEAN
%token CLASS
%token CLOSED_BRACKET
%token CLOSED_CURLY
%token CLOSED_PARENTHESES
%token COMMA
%token DIVIDE
%token DOT
%token ELSE
%token EQUALS
%token EQUAL_EQUAL
%token EXCLAMATION
%token EXTENDS
%token FALSE
%token GREATER
%token GREATER_EQUAL
%token ID
%token IF
%token INT
%token INTEGER_LITERAL
%token LENGTH
%token LESS
%token LESS_EQUAL
%token MAIN
%token MINUS
%token MINUS_MINUS
%token MULTIPLY
%token NEW
%token NOT_EQUAL
%token OPEN_BRACKET
%token OPEN_CURLY
%token OPEN_PARENTHESES
%token OR
%token PLUS
%token PLUS_PLUS
%token PUBLIC
%token RETURN
%token SEMICOLON
%token STATIC
%token STRING
%token STRING_LITERAL
%token SYSTEM_OUT_PRINT
%token SYSTEM_OUT_PRINTLN
%token THIS
%token TRUE
%token VOID
%token WHILE

%start Program

%%

Program:                MainClass ClassDeclList
                    ;
MainClass:              CLASS ID OPEN_CURLY PUBLIC STATIC VOID MAIN OPEN_PARENTHESES STRING OPEN_BRACKET CLOSED_BRACKET ID CLOSED_PARENTHESES OPEN_CURLY StatementList CLOSED_CURLY CLOSED_CURLY
                    ;
ClassDeclList:          %empty
                    |   ClassDecl ClassDeclList
                    ;
ClassDecl:              CLASS ID OptionalExtends OPEN_CURLY VarDeclList MethodDeclList CLOSED_CURLY
                    ;
OptionalExtends:        %empty
                    |   EXTENDS ID
                    ;
VarDeclList:            %empty
                    |   VarDeclList VarDecl
                    ;
MethodDeclList:         %empty
                    |   MethodDecl MethodDeclList
                    ;
VarDecl:                Type ID VarInit VarDeclTail SEMICOLON
                    ;
VarInit:                %empty
                    |   EQUALS Exp
                    ;
VarDeclTail:            %empty
                    |   COMMA ID VarInit VarDeclTail
                    ;
MethodDecl:             Type ID OPEN_PARENTHESES OptionalFormatList CLOSED_PARENTHESES OPEN_CURLY StatementList CLOSED_CURLY
                    |   PUBLIC Type ID OPEN_PARENTHESES OptionalFormatList CLOSED_PARENTHESES OPEN_CURLY StatementList CLOSED_CURLY
                    ;
OptionalFormatList:     %empty
                    |   FormalList
                    ;
FormalList:             Type ID FormalsTail
                    ;
FormalsTail:            %empty
                    |   COMMA Type ID FormalsTail
                    ;
PrimeType:              INT
                    |   BOOLEAN
                    |   STRING
                    ;
Type:                   PrimeType
                    |   ID
                    |   Type OPEN_BRACKET CLOSED_BRACKET
                    ;
StatementList:          %empty
                    |   Statement StatementList
                    ;
Statement:              VarDecl
                    |   OPEN_CURLY StatementList CLOSED_CURLY
                    |   IF OPEN_PARENTHESES Exp CLOSED_PARENTHESES Statement ELSE Statement
                    |   WHILE OPEN_PARENTHESES Exp CLOSED_PARENTHESES Statement
                    |   SYSTEM_OUT_PRINTLN OPEN_PARENTHESES Exp CLOSED_PARENTHESES SEMICOLON
                    |   SYSTEM_OUT_PRINT OPEN_PARENTHESES Exp CLOSED_PARENTHESES SEMICOLON
                    |   LeftValue EQUALS Exp SEMICOLON
                    |   ID EQUALS Exp SEMICOLON
                    |   RETURN Exp SEMICOLON
                    |   MethodCall SEMICOLON
                    ;
MethodCall:             LeftValue OPEN_PARENTHESES OptionalExpList CLOSED_PARENTHESES
                    |   ID OPEN_PARENTHESES OptionalExpList CLOSED_PARENTHESES
                    ;
OptionalExpList:        %empty
                    |   ExpList
                    ;
Exp:                    Exp Op Exp
                    |   EXCLAMATION Exp
                    |   PLUS Exp
                    |   MINUS Exp
                    |   OPEN_PARENTHESES Exp CLOSED_PARENTHESES
                    |   LeftValue
                    |   LeftValue DOT LENGTH
                    |   ID
                    |   ID DOT LENGTH
                    |   INTEGER_LITERAL
                    |   STRING_LITERAL
                    |   TRUE
                    |   FALSE
                    |   MethodCall
                    |   NEW ID OPEN_PARENTHESES CLOSED_PARENTHESES
                    |   NEW PrimeType Index
                    |   NEW ID Index
                    ;
Op:                     AND
                    |   OR
                    |   LESS
                    |   GREATER
                    |   LESS_EQUAL
                    |   GREATER_EQUAL
                    |   EQUAL_EQUAL
                    |   NOT_EQUAL
                    |   PLUS
                    |   MINUS
                    |   MULTIPLY
                    |   DIVIDE
                    ;
Index:                  OPEN_BRACKET Exp CLOSED_BRACKET
                    |   Index OPEN_BRACKET Exp CLOSED_BRACKET
                    ;
ExpList:                Exp ExpTail
                    ;
ExpTail:                %empty
                    |   COMMA Exp ExpTail
                    ;
LeftValue:              LeftValue Index
                    |   LeftValue DOT ID
                    |   ID Index
                    |   ID DOT ID
                    |   NEW ID OPEN_PARENTHESES CLOSED_PARENTHESES DOT ID
                    |   THIS DOT ID
                    ;

%%

void yyerror(char *s) {
    fprintf(stderr, "%s on line %d\n", s, yylineno);
}

int main( argc, argv )
	   int argc;
	   char **argv;
	       {
	       ++argv, --argc;	/* skip over program name */
	       if ( argc > 0 )
		       yyin = fopen( argv[0], "r" );
	       else
		       yyin = stdin;

    yyparse();
    return 0;
}