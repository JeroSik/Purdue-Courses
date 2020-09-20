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
    BOOLEAN = 258,
    CLASS = 259,
    CLOSED_BRACKET = 260,
    CLOSED_CURLY = 261,
    CLOSED_PARENTHESES = 262,
    COMMA = 263,
    DOT = 264,
    ELSE = 265,
    EQUALS = 266,
    EXCLAMATION = 267,
    EXTENDS = 268,
    FALSE = 269,
    ID = 270,
    IF = 271,
    INT = 272,
    INTEGER_LITERAL = 273,
    LENGTH = 274,
    MAIN = 275,
    MINUS = 276,
    NEW = 277,
    OP = 278,
    OPEN_BRACKET = 279,
    OPEN_CURLY = 280,
    OPEN_PARENTHESES = 281,
    PLUS = 282,
    PUBLIC = 283,
    RETURN = 284,
    SEMICOLON = 285,
    STATIC = 286,
    STRING = 287,
    STRING_LITERAL = 288,
    SYSTEM_OUT_PRINT = 289,
    SYSTEM_OUT_PRINTLN = 290,
    THIS = 291,
    TRUE = 292,
    VOID = 293,
    WHILE = 294
  };
#endif
/* Tokens.  */
#define BOOLEAN 258
#define CLASS 259
#define CLOSED_BRACKET 260
#define CLOSED_CURLY 261
#define CLOSED_PARENTHESES 262
#define COMMA 263
#define DOT 264
#define ELSE 265
#define EQUALS 266
#define EXCLAMATION 267
#define EXTENDS 268
#define FALSE 269
#define ID 270
#define IF 271
#define INT 272
#define INTEGER_LITERAL 273
#define LENGTH 274
#define MAIN 275
#define MINUS 276
#define NEW 277
#define OP 278
#define OPEN_BRACKET 279
#define OPEN_CURLY 280
#define OPEN_PARENTHESES 281
#define PLUS 282
#define PUBLIC 283
#define RETURN 284
#define SEMICOLON 285
#define STATIC 286
#define STRING 287
#define STRING_LITERAL 288
#define SYSTEM_OUT_PRINT 289
#define SYSTEM_OUT_PRINTLN 290
#define THIS 291
#define TRUE 292
#define VOID 293
#define WHILE 294

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void);

#endif /* !YY_YY_Y_TAB_H_INCLUDED  */
