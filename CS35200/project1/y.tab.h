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
    ID = 275,
    IF = 276,
    INT = 277,
    INTEGER_LITERAL = 278,
    LENGTH = 279,
    LESS = 280,
    LESS_EQUAL = 281,
    MAIN = 282,
    MINUS = 283,
    MULTIPLY = 284,
    NEW = 285,
    NOT_EQUAL = 286,
    OPEN_BRACKET = 287,
    OPEN_CURLY = 288,
    OPEN_PARENTHESES = 289,
    OR = 290,
    PLUS = 291,
    PLUS_PLUS = 292,
    PUBLIC = 293,
    RETURN = 294,
    SEMICOLON = 295,
    STATIC = 296,
    STRING = 297,
    STRING_LITERAL = 298,
    SYSTEM_OUT_PRINT = 299,
    SYSTEM_OUT_PRINTLN = 300,
    THIS = 301,
    TRUE = 302,
    VOID = 303,
    WHILE = 304
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
#define ID 275
#define IF 276
#define INT 277
#define INTEGER_LITERAL 278
#define LENGTH 279
#define LESS 280
#define LESS_EQUAL 281
#define MAIN 282
#define MINUS 283
#define MULTIPLY 284
#define NEW 285
#define NOT_EQUAL 286
#define OPEN_BRACKET 287
#define OPEN_CURLY 288
#define OPEN_PARENTHESES 289
#define OR 290
#define PLUS 291
#define PLUS_PLUS 292
#define PUBLIC 293
#define RETURN 294
#define SEMICOLON 295
#define STATIC 296
#define STRING 297
#define STRING_LITERAL 298
#define SYSTEM_OUT_PRINT 299
#define SYSTEM_OUT_PRINTLN 300
#define THIS 301
#define TRUE 302
#define VOID 303
#define WHILE 304

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void);

#endif /* !YY_YY_Y_TAB_H_INCLUDED  */
