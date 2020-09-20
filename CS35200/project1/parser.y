%{
    #include <stdio.h>
    
    int yylex(void);
    void yyerror(char *);

    extern int yylineno;
    extern FILE *yyin;
%}

%token BOOLEAN
%token CLASS
%token CLOSED_BRACKET
%token CLOSED_CURLY
%token CLOSED_PARENTHESES
%token COMMA
%token DOT
%token ELSE
%token EQUALS
%token EXCLAMATION
%token EXTENDS
%token FALSE
%token ID
%token IF
%token INT
%token INTEGER_LITERAL
%token LENGTH
%token MAIN
%token MINUS
%token NEW
%token OP
%token OPEN_BRACKET
%token OPEN_CURLY
%token OPEN_PARENTHESES
%token PLUS
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

Program:            MainClass ClassDeclList
                ;
MainClass:          CLASS ID OPEN_CURLY PUBLIC STATIC VOID MAIN OPEN_PARENTHESES STRING OPEN_BRACKET CLOSED_BRACKET ID CLOSED_PARENTHESES OPEN_CURLY StatementList CLOSED_CURLY CLOSED_CURLY
                ;
ClassDeclList:
                |   ClassDecl ClassDeclList
                ;
ClassDecl:          CLASS ID OptionalExtends OPEN_CURLY VarDeclList MethodDeclList CLOSED_CURLY
                ;
VarDeclList:        
                |   VarDecl VarDeclList
                ;
MethodDeclList:
                |   MethodDecl MethodDeclList
                ;
OptionalExtends:
                |   EXTENDS ID
                ;
VarDecl:            Type ID VarInit VarDeclTail SEMICOLON
                ;
VarInit:
                |   EQUALS Exp
                ;
VarDeclTail:
                |   COMMA ID VarInit VarDeclTail
                ;
MethodDecl:         OptionalPublic Type ID OPEN_PARENTHESES OptionalFormatList CLOSED_PARENTHESES OPEN_CURLY StatementList CLOSED_CURLY
                ;
OptionalFormatList:
                |   FormalList
                ;
OptionalPublic:
                |   PUBLIC
                ;
FormalList:         Type ID FormalsTail
                ;
FormalsTail:
                |   COMMA Type ID FormalsTail
                ;
PrimeType:          INT
                |   BOOLEAN
                |   ID
                |   STRING
                ;
Type:               PrimeType
                |   Type OPEN_BRACKET CLOSED_BRACKET
                ;
StatementList:
                |   Statement StatementList
                ;
Statement:          VarDecl
                |   OPEN_CURLY StatementList CLOSED_CURLY
                |   IF OPEN_PARENTHESES Exp CLOSED_PARENTHESES Statement ELSE Statement
                |   WHILE OPEN_PARENTHESES Exp CLOSED_PARENTHESES Statement
                |   SYSTEM_OUT_PRINTLN OPEN_PARENTHESES Exp CLOSED_PARENTHESES SEMICOLON
                |   SYSTEM_OUT_PRINT OPEN_PARENTHESES Exp CLOSED_PARENTHESES SEMICOLON
                |   LeftValue EQUALS Exp SEMICOLON
                |   RETURN Exp SEMICOLON
                |   MethodCall SEMICOLON
                ;
MethodCall:         LeftValue OPEN_PARENTHESES OptionalExpList CLOSED_PARENTHESES
                ;
OptionalExpList:    
                |   ExpList
                ;
Exp:                Exp OP Exp
                |   EXCLAMATION Exp
                |   PLUS Exp
                |   MINUS Exp
                |   OPEN_PARENTHESES Exp CLOSED_PARENTHESES
                |   LeftValue
                |   LeftValue DOT LENGTH
                |   INTEGER_LITERAL
                |   STRING_LITERAL
                |   TRUE
                |   FALSE
                |   MethodCall
                |   NEW ID OPEN_PARENTHESES CLOSED_PARENTHESES
                |   NEW PrimeType Index
                ;
Index:              OPEN_BRACKET Exp CLOSED_BRACKET
                |   Index OPEN_BRACKET Exp CLOSED_BRACKET
                ;
ExpList:            Exp ExpTail
                ;
ExpTail:
                |   COMMA Exp
                ;
LeftValue:          ID
                |   LeftValue Index
                |   LeftValue DOT ID
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

