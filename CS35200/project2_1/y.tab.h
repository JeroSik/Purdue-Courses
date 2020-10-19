/* A Bison parser, made by GNU Bison 3.0.4.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015 Free Software Foundation, Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

#ifndef YY_YY_Y_TAB_H_INCLUDED
# define YY_YY_Y_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token type.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    AND = 258,
    BOOLEAN = 259,
    CLASS = 260,
    CLOSED_BRACKET = 261,
    CLOSED_CURLY = 262,
    CLOSED_PARENTHESES = 263,
    COMMA = 264,
    DIVIDE = 265,
    DOT = 266,
    ELSE = 267,
    EQUALS = 268,
    EQUAL_EQUAL = 269,
    EXCLAMATION = 270,
    EXTENDS = 271,
    FALSE = 272,
    GREATER = 273,
    GREATER_EQUAL = 274,
    IF = 275,
    INT = 276,
    LENGTH = 277,
    LESS = 278,
    LESS_EQUAL = 279,
    MAIN = 280,
    MINUS = 281,
    MINUS_MINUS = 282,
    MULTIPLY = 283,
    NEW = 284,
    NOT_EQUAL = 285,
    OPEN_BRACKET = 286,
    OPEN_CURLY = 287,
    OPEN_PARENTHESES = 288,
    OR = 289,
    PLUS = 290,
    PLUS_PLUS = 291,
    PUBLIC = 292,
    RETURN = 293,
    SEMICOLON = 294,
    STATIC = 295,
    STRING = 296,
    SYSTEM_OUT_PRINT = 297,
    SYSTEM_OUT_PRINTLN = 298,
    THIS = 299,
    TRUE = 300,
    VOID = 301,
    WHILE = 302,
    INTEGER_LITERAL = 303,
    STRING_LITERAL = 304,
    ID = 305
  };
#endif
/* Tokens.  */
#define AND 258
#define BOOLEAN 259
#define CLASS 260
#define CLOSED_BRACKET 261
#define CLOSED_CURLY 262
#define CLOSED_PARENTHESES 263
#define COMMA 264
#define DIVIDE 265
#define DOT 266
#define ELSE 267
#define EQUALS 268
#define EQUAL_EQUAL 269
#define EXCLAMATION 270
#define EXTENDS 271
#define FALSE 272
#define GREATER 273
#define GREATER_EQUAL 274
#define IF 275
#define INT 276
#define LENGTH 277
#define LESS 278
#define LESS_EQUAL 279
#define MAIN 280
#define MINUS 281
#define MINUS_MINUS 282
#define MULTIPLY 283
#define NEW 284
#define NOT_EQUAL 285
#define OPEN_BRACKET 286
#define OPEN_CURLY 287
#define OPEN_PARENTHESES 288
#define OR 289
#define PLUS 290
#define PLUS_PLUS 291
#define PUBLIC 292
#define RETURN 293
#define SEMICOLON 294
#define STATIC 295
#define STRING 296
#define SYSTEM_OUT_PRINT 297
#define SYSTEM_OUT_PRINTLN 298
#define THIS 299
#define TRUE 300
#define VOID 301
#define WHILE 302
#define INTEGER_LITERAL 303
#define STRING_LITERAL 304
#define ID 305

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED

union YYSTYPE
{
#line 29 "parser.y" /* yacc.c:1909  */

    node * node;
    int integer;
    char * string;

#line 160 "y.tab.h" /* yacc.c:1909  */
};

typedef union YYSTYPE YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void);

#endif /* !YY_YY_Y_TAB_H_INCLUDED  */
