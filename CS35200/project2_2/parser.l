%{
    #include "node.cc"
    #include "y.tab.h"
    #include <stdio.h>
    #include <stdlib.h>
    extern "C" int yylex();
    void    yyerror(char *);
%}

%option yylineno

%%

boolean                                             { return BOOLEAN;               }
class                                               { return CLASS;                 }
else                                                { return ELSE;                  }
extends                                             { return EXTENDS;               }
false                                               { return FALSE;                 }
if                                                  { return IF;                    }
int                                                 { return INT;                   }
length                                              { return LENGTH;                }
main                                                { return MAIN;                  }
new                                                 { return NEW;                   }
public                                              { return PUBLIC;                }
return                                              { return RETURN;                }
static                                              { return STATIC;                }
String                                              { return STRING;                }
System\.out\.print                                  { return SYSTEM_OUT_PRINT;      }
System\.out\.println                                { return SYSTEM_OUT_PRINTLN;    }
this                                                { return THIS;                  }
true                                                { return TRUE;                  }
void                                                { return VOID;                  }
while                                               { return WHILE;                 }
\&\&                                                { return AND;                   }
\]                                                  { return CLOSED_BRACKET;        }
\}                                                  { return CLOSED_CURLY;          }
\)                                                  { return CLOSED_PARENTHESES;    }
\,                                                  { return COMMA;                 }
\/                                                  { return DIVIDE;                }
\.                                                  { return DOT;                   }
\=                                                  { return EQUALS;                }
\=\=                                                { return EQUAL_EQUAL;           }
\!                                                  { return EXCLAMATION;           }
\>                                                  { return GREATER;               }
\>\=                                                { return GREATER_EQUAL;         }
\<                                                  { return LESS;                  }
\<\=                                                { return LESS_EQUAL;            }
\-                                                  { return MINUS;                 }
\-\-                                                { return MINUS_MINUS;           }
\*                                                  { return MULTIPLY;              }
\!\=                                                { return NOT_EQUAL;             }
\[                                                  { return OPEN_BRACKET;          }
\{                                                  { return OPEN_CURLY;            } 
\(                                                  { return OPEN_PARENTHESES;      }
\|\|                                                { return OR;                    }
\+                                                  { return PLUS;                  }
\+\+                                                { return PLUS_PLUS;             }
\;                                                  { return SEMICOLON;             }

[0-9]+                                              {
                                                        yylval.integer = atoi(strdup(yytext));
                                                        return INTEGER_LITERAL;
                                                    }
[a-zA-Z][a-zA-Z0-9_]*                               {
                                                        yylval.string = strdup(yytext);
                                                        return ID;
                                                    }
\"([^"\\]|\\.)*\"                                   {
                                                        yylval.string = strdup(yytext);
                                                        return STRING_LITERAL;
                                                    }

"//"[^\n]*                                          { } /* Single line comments */
"/*"([^*]|\*+[^/*])*\*+"/"                          { } /* Multiline comments */

[ \t\n]                                             { } /* skip whitespace */

%%


int yywrap(void) {
    return 1;
}