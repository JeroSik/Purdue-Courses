/* A Bison parser, made by GNU Bison 3.0.4.  */

/* Bison implementation for Yacc-like parsers in C

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

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "3.0.4"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1




/* Copy the first part of user declarations.  */
#line 1 "parser.y" /* yacc.c:339  */

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

#line 82 "y.tab.c" /* yacc.c:339  */

# ifndef YY_NULLPTR
#  if defined __cplusplus && 201103L <= __cplusplus
#   define YY_NULLPTR nullptr
#  else
#   define YY_NULLPTR 0
#  endif
# endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* In a future release of Bison, this section will be replaced
   by #include "y.tab.h".  */
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
#line 17 "parser.y" /* yacc.c:355  */

    node * node;
    int integer;
    char * string;

#line 228 "y.tab.c" /* yacc.c:355  */
};

typedef union YYSTYPE YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void);

#endif /* !YY_YY_Y_TAB_H_INCLUDED  */

/* Copy the second part of user declarations.  */

#line 245 "y.tab.c" /* yacc.c:358  */

#ifdef short
# undef short
#endif

#ifdef YYTYPE_UINT8
typedef YYTYPE_UINT8 yytype_uint8;
#else
typedef unsigned char yytype_uint8;
#endif

#ifdef YYTYPE_INT8
typedef YYTYPE_INT8 yytype_int8;
#else
typedef signed char yytype_int8;
#endif

#ifdef YYTYPE_UINT16
typedef YYTYPE_UINT16 yytype_uint16;
#else
typedef unsigned short int yytype_uint16;
#endif

#ifdef YYTYPE_INT16
typedef YYTYPE_INT16 yytype_int16;
#else
typedef short int yytype_int16;
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif ! defined YYSIZE_T
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned int
# endif
#endif

#define YYSIZE_MAXIMUM ((YYSIZE_T) -1)

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(Msgid) dgettext ("bison-runtime", Msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(Msgid) Msgid
# endif
#endif

#ifndef YY_ATTRIBUTE
# if (defined __GNUC__                                               \
      && (2 < __GNUC__ || (__GNUC__ == 2 && 96 <= __GNUC_MINOR__)))  \
     || defined __SUNPRO_C && 0x5110 <= __SUNPRO_C
#  define YY_ATTRIBUTE(Spec) __attribute__(Spec)
# else
#  define YY_ATTRIBUTE(Spec) /* empty */
# endif
#endif

#ifndef YY_ATTRIBUTE_PURE
# define YY_ATTRIBUTE_PURE   YY_ATTRIBUTE ((__pure__))
#endif

#ifndef YY_ATTRIBUTE_UNUSED
# define YY_ATTRIBUTE_UNUSED YY_ATTRIBUTE ((__unused__))
#endif

#if !defined _Noreturn \
     && (!defined __STDC_VERSION__ || __STDC_VERSION__ < 201112)
# if defined _MSC_VER && 1200 <= _MSC_VER
#  define _Noreturn __declspec (noreturn)
# else
#  define _Noreturn YY_ATTRIBUTE ((__noreturn__))
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(E) ((void) (E))
#else
# define YYUSE(E) /* empty */
#endif

#if defined __GNUC__ && 407 <= __GNUC__ * 100 + __GNUC_MINOR__
/* Suppress an incorrect diagnostic about yylval being uninitialized.  */
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN \
    _Pragma ("GCC diagnostic push") \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")\
    _Pragma ("GCC diagnostic ignored \"-Wmaybe-uninitialized\"")
# define YY_IGNORE_MAYBE_UNINITIALIZED_END \
    _Pragma ("GCC diagnostic pop")
#else
# define YY_INITIAL_VALUE(Value) Value
#endif
#ifndef YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_END
#endif
#ifndef YY_INITIAL_VALUE
# define YY_INITIAL_VALUE(Value) /* Nothing. */
#endif


#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined EXIT_SUCCESS
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
      /* Use EXIT_SUCCESS as a witness for stdlib.h.  */
#     ifndef EXIT_SUCCESS
#      define EXIT_SUCCESS 0
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's 'empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (0)
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined EXIT_SUCCESS \
       && ! ((defined YYMALLOC || defined malloc) \
             && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef EXIT_SUCCESS
#    define EXIT_SUCCESS 0
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined EXIT_SUCCESS
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined EXIT_SUCCESS
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
         || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yytype_int16 yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (yytype_int16) + sizeof (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

# define YYCOPY_NEEDED 1

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)                           \
    do                                                                  \
      {                                                                 \
        YYSIZE_T yynewbytes;                                            \
        YYCOPY (&yyptr->Stack_alloc, Stack, yysize);                    \
        Stack = &yyptr->Stack_alloc;                                    \
        yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
        yyptr += yynewbytes / sizeof (*yyptr);                          \
      }                                                                 \
    while (0)

#endif

#if defined YYCOPY_NEEDED && YYCOPY_NEEDED
/* Copy COUNT objects from SRC to DST.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(Dst, Src, Count) \
      __builtin_memcpy (Dst, Src, (Count) * sizeof (*(Src)))
#  else
#   define YYCOPY(Dst, Src, Count)              \
      do                                        \
        {                                       \
          YYSIZE_T yyi;                         \
          for (yyi = 0; yyi < (Count); yyi++)   \
            (Dst)[yyi] = (Src)[yyi];            \
        }                                       \
      while (0)
#  endif
# endif
#endif /* !YYCOPY_NEEDED */

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  5
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   126

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  51
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  21
/* YYNRULES -- Number of rules.  */
#define YYNRULES  58
/* YYNSTATES -- Number of states.  */
#define YYNSTATES  130

/* YYTRANSLATE[YYX] -- Symbol number corresponding to YYX as returned
   by yylex, with out-of-bounds checking.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   305

#define YYTRANSLATE(YYX)                                                \
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[TOKEN-NUM] -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex, without out-of-bounds checking.  */
static const yytype_uint8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48,    49,    50
};

#if YYDEBUG
  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
static const yytype_uint16 yyrline[] =
{
       0,    44,    44,    51,    61,    70,    74,    80,    84,    92,
      97,   102,   108,   113,   118,   124,   128,   135,   141,   147,
     153,   160,   167,   175,   182,   189,   196,   202,   208,   214,
     220,   226,   232,   238,   244,   250,   256,   262,   268,   274,
     280,   286,   292,   298,   304,   309,   314,   320,   325,   330,
     335,   341,   346,   351,   356,   362,   368,   375,   380
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || 0
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "AND", "BOOLEAN", "CLASS",
  "CLOSED_BRACKET", "CLOSED_CURLY", "CLOSED_PARENTHESES", "COMMA",
  "DIVIDE", "DOT", "ELSE", "EQUALS", "EQUAL_EQUAL", "EXCLAMATION",
  "EXTENDS", "FALSE", "GREATER", "GREATER_EQUAL", "IF", "INT", "LENGTH",
  "LESS", "LESS_EQUAL", "MAIN", "MINUS", "MINUS_MINUS", "MULTIPLY", "NEW",
  "NOT_EQUAL", "OPEN_BRACKET", "OPEN_CURLY", "OPEN_PARENTHESES", "OR",
  "PLUS", "PLUS_PLUS", "PUBLIC", "RETURN", "SEMICOLON", "STATIC", "STRING",
  "SYSTEM_OUT_PRINT", "SYSTEM_OUT_PRINTLN", "THIS", "TRUE", "VOID",
  "WHILE", "INTEGER_LITERAL", "STRING_LITERAL", "ID", "$accept", "Program",
  "MainClass", "VarDecl", "VarInit", "VarDeclTail", "PrimeType", "Type",
  "StatementList", "Statement", "Exp", "LogicalOrExp", "LogicalAndExp",
  "EqualityExp", "RelationalExp", "AdditiveExp", "MultiplicativeExp",
  "UnaryExp", "Factor", "LeftValue", "Index", YY_NULLPTR
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[NUM] -- (External) token number corresponding to the
   (internal) symbol number NUM (which must be that of a token).  */
static const yytype_uint16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305
};
# endif

#define YYPACT_NINF -77

#define yypact_value_is_default(Yystate) \
  (!!((Yystate) == (-77)))

#define YYTABLE_NINF -1

#define yytable_value_is_error(Yytable_value) \
  0

  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
static const yytype_int8 yypact[] =
{
      17,   -20,    25,   -77,     7,   -77,    27,    14,    16,    35,
      33,    29,    50,    83,    40,    84,    59,    -1,   -77,    60,
     -77,    -1,   -77,    61,    62,    63,     2,   -77,   -77,   -18,
      90,    -1,     4,    26,    91,    26,    26,    26,    26,    26,
      68,    94,    88,    95,   -77,    26,    68,   -77,    34,     3,
      26,    34,   -77,   -77,   -77,    72,    96,    71,   103,    23,
      54,     1,    28,   -77,   -77,    72,   -77,    99,   100,   102,
      73,   105,    26,   -77,    26,   104,   -77,    75,   -77,    72,
     107,   -77,    -1,    26,    26,    26,    26,    26,    26,    26,
      26,    26,    26,    26,    26,    77,    78,    -1,   -77,   -77,
     112,   -77,    69,    81,   -77,    68,   -77,   109,   103,    23,
      54,    54,     1,     1,     1,     1,    28,    28,   -77,   -77,
     -77,   -77,   -77,   -77,    88,   -77,    -1,   104,   -77,   -77
};

  /* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
     Performed when YYTABLE does not specify something else to do.  Zero
     means the default is an error.  */
static const yytype_uint8 yydefact[] =
{
       0,     0,     0,     2,     0,     1,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,    15,    10,     0,
       9,    15,    11,     0,     0,     0,    13,    19,    12,     0,
       0,    15,     0,     0,     0,     0,     0,     0,     0,     0,
      56,     0,     5,     0,    16,     0,    55,    54,     0,     0,
       0,     0,    53,    52,    51,    48,     0,    25,    27,    29,
      32,    37,    40,    43,    46,    49,    24,     0,     0,     0,
       0,     0,     0,    14,     0,     7,     3,     0,    45,     0,
       0,    44,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    20,    57,
       0,     6,     0,     0,    21,    50,    47,     0,    26,    28,
      30,    31,    33,    34,    35,    36,    39,    38,    42,    41,
      18,    17,    23,    58,     5,     4,     0,     7,    22,     8
};

  /* YYPGOTO[NTERM-NUM].  */
static const yytype_int8 yypgoto[] =
{
     -77,   -77,   -77,   -77,     0,    -4,    76,   -77,    -5,   -76,
     -27,   -77,    39,    42,   -57,    -2,   -34,   -25,   -46,   -17,
     -31
};

  /* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int8 yydefgoto[] =
{
      -1,     2,     3,    27,    75,   103,    28,    29,    30,    31,
      56,    57,    58,    59,    60,    61,    62,    63,    64,    65,
      40
};

  /* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule whose
     number is the opposite.  If YYTABLE_NINF, syntax error.  */
static const yytype_uint8 yytable[] =
{
      32,    46,    78,    18,    32,    81,   107,    18,    67,    68,
      69,    70,    71,    41,    32,    38,    34,    45,    77,    19,
      20,   122,     1,    80,    20,     5,    44,    91,   110,   111,
       4,    21,    42,    39,    46,    39,    92,    85,    93,     6,
      22,    23,    24,    47,    22,   100,    25,   101,   105,    26,
     128,    47,    48,    86,     8,    49,    94,   116,   117,    50,
      10,    51,     9,    49,     7,    32,    11,    50,   118,   119,
      12,    52,    87,    88,    53,    54,    55,    89,    90,    52,
      32,    13,    53,    54,    55,   112,   113,   114,   115,    14,
      15,    17,    16,    33,    35,    36,    37,    43,    66,    72,
      73,    74,    76,    39,    82,    83,    84,    95,    96,    32,
      97,    99,    98,   102,   104,   106,   120,   121,   123,   124,
     125,   126,   108,   129,   127,    79,   109
};

static const yytype_uint8 yycheck[] =
{
      17,    32,    48,     4,    21,    51,    82,     4,    35,    36,
      37,    38,    39,    31,    31,    13,    21,    13,    45,    20,
      21,    97,     5,    50,    21,     0,    31,    26,    85,    86,
      50,    32,    50,    31,    65,    31,    35,    14,    10,    32,
      41,    42,    43,    17,    41,    72,    47,    74,    79,    50,
     126,    17,    26,    30,    40,    29,    28,    91,    92,    33,
      25,    35,    46,    29,    37,    82,    33,    33,    93,    94,
      41,    45,    18,    19,    48,    49,    50,    23,    24,    45,
      97,    31,    48,    49,    50,    87,    88,    89,    90,     6,
      50,    32,     8,    33,    33,    33,    33,     7,     7,    31,
       6,    13,     7,    31,     8,    34,     3,     8,     8,   126,
       8,     6,    39,     9,    39,     8,    39,    39,     6,    50,
      39,    12,    83,   127,   124,    49,    84
};

  /* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
static const yytype_uint8 yystos[] =
{
       0,     5,    52,    53,    50,     0,    32,    37,    40,    46,
      25,    33,    41,    31,     6,    50,     8,    32,     4,    20,
      21,    32,    41,    42,    43,    47,    50,    54,    57,    58,
      59,    60,    70,    33,    59,    33,    33,    33,    13,    31,
      71,    31,    50,     7,    59,    13,    71,    17,    26,    29,
      33,    35,    45,    48,    49,    50,    61,    62,    63,    64,
      65,    66,    67,    68,    69,    70,     7,    61,    61,    61,
      61,    61,    31,     6,    13,    55,     7,    61,    69,    57,
      61,    69,     8,    34,     3,    14,    30,    18,    19,    23,
      24,    26,    35,    10,    28,     8,     8,     8,    39,     6,
      61,    61,     9,    56,    39,    71,     8,    60,    63,    64,
      65,    65,    66,    66,    66,    66,    67,    67,    68,    68,
      39,    39,    60,     6,    50,    39,    12,    55,    60,    56
};

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_uint8 yyr1[] =
{
       0,    51,    52,    53,    54,    55,    55,    56,    56,    57,
      57,    57,    58,    58,    58,    59,    59,    60,    60,    60,
      60,    60,    60,    60,    60,    61,    62,    62,    63,    63,
      64,    64,    64,    65,    65,    65,    65,    65,    66,    66,
      66,    67,    67,    67,    68,    68,    68,    69,    69,    69,
      69,    69,    69,    69,    69,    70,    70,    71,    71
};

  /* YYR2[YYN] -- Number of symbols on the right hand side of rule YYN.  */
static const yytype_uint8 yyr2[] =
{
       0,     2,     1,    17,     5,     0,     2,     0,     4,     1,
       1,     1,     1,     1,     3,     0,     2,     5,     5,     1,
       4,     4,     7,     5,     3,     1,     3,     1,     3,     1,
       3,     3,     1,     3,     3,     3,     3,     1,     3,     3,
       1,     3,     3,     1,     2,     2,     1,     3,     1,     1,
       3,     1,     1,     1,     1,     2,     2,     3,     4
};


#define yyerrok         (yyerrstatus = 0)
#define yyclearin       (yychar = YYEMPTY)
#define YYEMPTY         (-2)
#define YYEOF           0

#define YYACCEPT        goto yyacceptlab
#define YYABORT         goto yyabortlab
#define YYERROR         goto yyerrorlab


#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)                                  \
do                                                              \
  if (yychar == YYEMPTY)                                        \
    {                                                           \
      yychar = (Token);                                         \
      yylval = (Value);                                         \
      YYPOPSTACK (yylen);                                       \
      yystate = *yyssp;                                         \
      goto yybackup;                                            \
    }                                                           \
  else                                                          \
    {                                                           \
      yyerror (YY_("syntax error: cannot back up")); \
      YYERROR;                                                  \
    }                                                           \
while (0)

/* Error token number */
#define YYTERROR        1
#define YYERRCODE       256



/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)                        \
do {                                            \
  if (yydebug)                                  \
    YYFPRINTF Args;                             \
} while (0)

/* This macro is provided for backward compatibility. */
#ifndef YY_LOCATION_PRINT
# define YY_LOCATION_PRINT(File, Loc) ((void) 0)
#endif


# define YY_SYMBOL_PRINT(Title, Type, Value, Location)                    \
do {                                                                      \
  if (yydebug)                                                            \
    {                                                                     \
      YYFPRINTF (stderr, "%s ", Title);                                   \
      yy_symbol_print (stderr,                                            \
                  Type, Value); \
      YYFPRINTF (stderr, "\n");                                           \
    }                                                                     \
} while (0)


/*----------------------------------------.
| Print this symbol's value on YYOUTPUT.  |
`----------------------------------------*/

static void
yy_symbol_value_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
{
  FILE *yyo = yyoutput;
  YYUSE (yyo);
  if (!yyvaluep)
    return;
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# endif
  YYUSE (yytype);
}


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

static void
yy_symbol_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
{
  YYFPRINTF (yyoutput, "%s %s (",
             yytype < YYNTOKENS ? "token" : "nterm", yytname[yytype]);

  yy_symbol_value_print (yyoutput, yytype, yyvaluep);
  YYFPRINTF (yyoutput, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

static void
yy_stack_print (yytype_int16 *yybottom, yytype_int16 *yytop)
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)                            \
do {                                                            \
  if (yydebug)                                                  \
    yy_stack_print ((Bottom), (Top));                           \
} while (0)


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

static void
yy_reduce_print (yytype_int16 *yyssp, YYSTYPE *yyvsp, int yyrule)
{
  unsigned long int yylno = yyrline[yyrule];
  int yynrhs = yyr2[yyrule];
  int yyi;
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %lu):\n",
             yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr,
                       yystos[yyssp[yyi + 1 - yynrhs]],
                       &(yyvsp[(yyi + 1) - (yynrhs)])
                                              );
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)          \
do {                                    \
  if (yydebug)                          \
    yy_reduce_print (yyssp, yyvsp, Rule); \
} while (0)

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif


#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
static YYSIZE_T
yystrlen (const char *yystr)
{
  YYSIZE_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
static char *
yystpcpy (char *yydest, const char *yysrc)
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYSIZE_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYSIZE_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
        switch (*++yyp)
          {
          case '\'':
          case ',':
            goto do_not_strip_quotes;

          case '\\':
            if (*++yyp != '\\')
              goto do_not_strip_quotes;
            /* Fall through.  */
          default:
            if (yyres)
              yyres[yyn] = *yyp;
            yyn++;
            break;

          case '"':
            if (yyres)
              yyres[yyn] = '\0';
            return yyn;
          }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return yystrlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

/* Copy into *YYMSG, which is of size *YYMSG_ALLOC, an error message
   about the unexpected token YYTOKEN for the state stack whose top is
   YYSSP.

   Return 0 if *YYMSG was successfully written.  Return 1 if *YYMSG is
   not large enough to hold the message.  In that case, also set
   *YYMSG_ALLOC to the required number of bytes.  Return 2 if the
   required number of bytes is too large to store.  */
static int
yysyntax_error (YYSIZE_T *yymsg_alloc, char **yymsg,
                yytype_int16 *yyssp, int yytoken)
{
  YYSIZE_T yysize0 = yytnamerr (YY_NULLPTR, yytname[yytoken]);
  YYSIZE_T yysize = yysize0;
  enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
  /* Internationalized format string. */
  const char *yyformat = YY_NULLPTR;
  /* Arguments of yyformat. */
  char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
  /* Number of reported tokens (one for the "unexpected", one per
     "expected"). */
  int yycount = 0;

  /* There are many possibilities here to consider:
     - If this state is a consistent state with a default action, then
       the only way this function was invoked is if the default action
       is an error action.  In that case, don't check for expected
       tokens because there are none.
     - The only way there can be no lookahead present (in yychar) is if
       this state is a consistent state with a default action.  Thus,
       detecting the absence of a lookahead is sufficient to determine
       that there is no unexpected or expected token to report.  In that
       case, just report a simple "syntax error".
     - Don't assume there isn't a lookahead just because this state is a
       consistent state with a default action.  There might have been a
       previous inconsistent state, consistent state with a non-default
       action, or user semantic action that manipulated yychar.
     - Of course, the expected token list depends on states to have
       correct lookahead information, and it depends on the parser not
       to perform extra reductions after fetching a lookahead from the
       scanner and before detecting a syntax error.  Thus, state merging
       (from LALR or IELR) and default reductions corrupt the expected
       token list.  However, the list is correct for canonical LR with
       one exception: it will still contain any token that will not be
       accepted due to an error action in a later state.
  */
  if (yytoken != YYEMPTY)
    {
      int yyn = yypact[*yyssp];
      yyarg[yycount++] = yytname[yytoken];
      if (!yypact_value_is_default (yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative indexes in
             YYCHECK.  In other words, skip the first -YYN actions for
             this state because they are default actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST - yyn + 1;
          int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
          int yyx;

          for (yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR
                && !yytable_value_is_error (yytable[yyx + yyn]))
              {
                if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
                  {
                    yycount = 1;
                    yysize = yysize0;
                    break;
                  }
                yyarg[yycount++] = yytname[yyx];
                {
                  YYSIZE_T yysize1 = yysize + yytnamerr (YY_NULLPTR, yytname[yyx]);
                  if (! (yysize <= yysize1
                         && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
                    return 2;
                  yysize = yysize1;
                }
              }
        }
    }

  switch (yycount)
    {
# define YYCASE_(N, S)                      \
      case N:                               \
        yyformat = S;                       \
      break
      YYCASE_(0, YY_("syntax error"));
      YYCASE_(1, YY_("syntax error, unexpected %s"));
      YYCASE_(2, YY_("syntax error, unexpected %s, expecting %s"));
      YYCASE_(3, YY_("syntax error, unexpected %s, expecting %s or %s"));
      YYCASE_(4, YY_("syntax error, unexpected %s, expecting %s or %s or %s"));
      YYCASE_(5, YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s"));
# undef YYCASE_
    }

  {
    YYSIZE_T yysize1 = yysize + yystrlen (yyformat);
    if (! (yysize <= yysize1 && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
      return 2;
    yysize = yysize1;
  }

  if (*yymsg_alloc < yysize)
    {
      *yymsg_alloc = 2 * yysize;
      if (! (yysize <= *yymsg_alloc
             && *yymsg_alloc <= YYSTACK_ALLOC_MAXIMUM))
        *yymsg_alloc = YYSTACK_ALLOC_MAXIMUM;
      return 1;
    }

  /* Avoid sprintf, as that infringes on the user's name space.
     Don't have undefined behavior even if the translation
     produced a string with the wrong number of "%s"s.  */
  {
    char *yyp = *yymsg;
    int yyi = 0;
    while ((*yyp = *yyformat) != '\0')
      if (*yyp == '%' && yyformat[1] == 's' && yyi < yycount)
        {
          yyp += yytnamerr (yyp, yyarg[yyi++]);
          yyformat += 2;
        }
      else
        {
          yyp++;
          yyformat++;
        }
  }
  return 0;
}
#endif /* YYERROR_VERBOSE */

/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep)
{
  YYUSE (yyvaluep);
  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YYUSE (yytype);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}




/* The lookahead symbol.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;
/* Number of syntax errors so far.  */
int yynerrs;


/*----------.
| yyparse.  |
`----------*/

int
yyparse (void)
{
    int yystate;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus;

    /* The stacks and their tools:
       'yyss': related to states.
       'yyvs': related to semantic values.

       Refer to the stacks through separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* The state stack.  */
    yytype_int16 yyssa[YYINITDEPTH];
    yytype_int16 *yyss;
    yytype_int16 *yyssp;

    /* The semantic value stack.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs;
    YYSTYPE *yyvsp;

    YYSIZE_T yystacksize;

  int yyn;
  int yyresult;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken = 0;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;

#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYSIZE_T yymsg_alloc = sizeof yymsgbuf;
#endif

#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  yyssp = yyss = yyssa;
  yyvsp = yyvs = yyvsa;
  yystacksize = YYINITDEPTH;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY; /* Cause a token to be read.  */
  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
        /* Give user a chance to reallocate the stack.  Use copies of
           these so that the &'s don't force the real ones into
           memory.  */
        YYSTYPE *yyvs1 = yyvs;
        yytype_int16 *yyss1 = yyss;

        /* Each stack pointer address is followed by the size of the
           data in use in that stack, in bytes.  This used to be a
           conditional around just the two extra args, but that might
           be undefined if yyoverflow is a macro.  */
        yyoverflow (YY_("memory exhausted"),
                    &yyss1, yysize * sizeof (*yyssp),
                    &yyvs1, yysize * sizeof (*yyvsp),
                    &yystacksize);

        yyss = yyss1;
        yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyexhaustedlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
        goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
        yystacksize = YYMAXDEPTH;

      {
        yytype_int16 *yyss1 = yyss;
        union yyalloc *yyptr =
          (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
        if (! yyptr)
          goto yyexhaustedlab;
        YYSTACK_RELOCATE (yyss_alloc, yyss);
        YYSTACK_RELOCATE (yyvs_alloc, yyvs);
#  undef YYSTACK_RELOCATE
        if (yyss1 != yyssa)
          YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
                  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
        YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yypact_value_is_default (yyn))
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = yylex ();
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yytable_value_is_error (yyn))
        goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);

  /* Discard the shifted token.  */
  yychar = YYEMPTY;

  yystate = yyn;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END

  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     '$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
        case 2:
#line 45 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Program");
                            (yyval.node)->addChild((yyvsp[0].node));
                            root = (yyval.node);
                        }
#line 1414 "y.tab.c" /* yacc.c:1646  */
    break;

  case 3:
#line 52 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("MainClass");
                            (yyval.node)->setStringValue((yyvsp[-15].string));
                            node * main_argument = new node("MainArgument");
                            main_argument->setStringValue((yyvsp[-5].string));
                            (yyval.node)->addChild(main_argument);
                            (yyval.node)->addChild((yyvsp[-2].node));
                        }
#line 1427 "y.tab.c" /* yacc.c:1646  */
    break;

  case 4:
#line 62 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("VarDecl");
                            (yyval.node)->addChild((yyvsp[-4].node));
                            (yyval.node)->setStringValue((yyvsp[-3].string));
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[-1].node));
                        }
#line 1439 "y.tab.c" /* yacc.c:1646  */
    break;

  case 5:
#line 71 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Empty");
                        }
#line 1447 "y.tab.c" /* yacc.c:1646  */
    break;

  case 6:
#line 75 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("VarInit");
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1456 "y.tab.c" /* yacc.c:1646  */
    break;

  case 7:
#line 81 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Empty");
                        }
#line 1464 "y.tab.c" /* yacc.c:1646  */
    break;

  case 8:
#line 85 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("VarDeclTail");
                            (yyval.node)->setStringValue((yyvsp[-2].string));
                            (yyval.node)->addChild((yyvsp[-1].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1475 "y.tab.c" /* yacc.c:1646  */
    break;

  case 9:
#line 93 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("PrimeType - INT");
                            (yyval.node)->data.type = data::type_t::intType;
                        }
#line 1484 "y.tab.c" /* yacc.c:1646  */
    break;

  case 10:
#line 98 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("PrimeType - BOOLEAN");
                            (yyval.node)->data.type = data::type_t::booleanType;
                        }
#line 1493 "y.tab.c" /* yacc.c:1646  */
    break;

  case 11:
#line 103 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("PrimeType - STRING");
                            (yyval.node)->data.type = data::type_t::stringType;
                        }
#line 1502 "y.tab.c" /* yacc.c:1646  */
    break;

  case 12:
#line 109 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Type - PrimeType");
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1511 "y.tab.c" /* yacc.c:1646  */
    break;

  case 13:
#line 114 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Type - ID");
                            (yyval.node)->setStringValue((yyvsp[0].string));
                        }
#line 1520 "y.tab.c" /* yacc.c:1646  */
    break;

  case 14:
#line 119 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Type - BRACKET");
                            (yyval.node)->addChild((yyvsp[-2].node));
                        }
#line 1529 "y.tab.c" /* yacc.c:1646  */
    break;

  case 15:
#line 125 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Empty");
                        }
#line 1537 "y.tab.c" /* yacc.c:1646  */
    break;

  case 16:
#line 129 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("StatementList");
                            (yyval.node)->addChild((yyvsp[-1].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1547 "y.tab.c" /* yacc.c:1646  */
    break;

  case 17:
#line 136 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Statement - println");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->setLineNumber(yylineno);
                        }
#line 1557 "y.tab.c" /* yacc.c:1646  */
    break;

  case 18:
#line 142 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Statement - print");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->setLineNumber(yylineno);
                        }
#line 1567 "y.tab.c" /* yacc.c:1646  */
    break;

  case 19:
#line 148 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Statement - VarDecl");
                            (yyval.node)->addChild((yyvsp[0].node));
                            (yyval.node)->setLineNumber(yylineno);
                        }
#line 1577 "y.tab.c" /* yacc.c:1646  */
    break;

  case 20:
#line 154 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Statement - id_equals_exp");
                            (yyval.node)->setStringValue((yyvsp[-3].string));
                            (yyval.node)->addChild((yyvsp[-1].node));
                            (yyval.node)->setLineNumber(yylineno);
                        }
#line 1588 "y.tab.c" /* yacc.c:1646  */
    break;

  case 21:
#line 161 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Statement - leftValue_equals_exp");
                            (yyval.node)->addChild((yyvsp[-3].node));
                            (yyval.node)->addChild((yyvsp[-1].node));
                            (yyval.node)->setLineNumber(yylineno);
                        }
#line 1599 "y.tab.c" /* yacc.c:1646  */
    break;

  case 22:
#line 168 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Statement - if");
                            (yyval.node)->addChild((yyvsp[-4].node));
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                            (yyval.node)->setLineNumber(yylineno);
                        }
#line 1611 "y.tab.c" /* yacc.c:1646  */
    break;

  case 23:
#line 176 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Statement - while");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                            (yyval.node)->setLineNumber(yylineno);
                        }
#line 1622 "y.tab.c" /* yacc.c:1646  */
    break;

  case 24:
#line 183 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Statement - StatementList");
                            (yyval.node)->addChild((yyvsp[-1].node));
                            (yyval.node)->setLineNumber(yylineno);
                        }
#line 1632 "y.tab.c" /* yacc.c:1646  */
    break;

  case 25:
#line 190 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Exp - LogicalOrExp");
                            (yyval.node)->addChild((yyvsp[0].node));
                            (yyval.node)->setLineNumber(yylineno);
                        }
#line 1642 "y.tab.c" /* yacc.c:1646  */
    break;

  case 26:
#line 197 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("LogicalOrExp - OR");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1652 "y.tab.c" /* yacc.c:1646  */
    break;

  case 27:
#line 203 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("LogicalAndExp");
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1661 "y.tab.c" /* yacc.c:1646  */
    break;

  case 28:
#line 209 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("LogicalAndExp - AND");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1671 "y.tab.c" /* yacc.c:1646  */
    break;

  case 29:
#line 215 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("EqualityExp");
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1680 "y.tab.c" /* yacc.c:1646  */
    break;

  case 30:
#line 221 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("EqualityExp - EQUAL_EQUAL");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1690 "y.tab.c" /* yacc.c:1646  */
    break;

  case 31:
#line 227 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("EqualityExp - NOT_EQUAL");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1700 "y.tab.c" /* yacc.c:1646  */
    break;

  case 32:
#line 233 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("RelationalExp");
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1709 "y.tab.c" /* yacc.c:1646  */
    break;

  case 33:
#line 239 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("RelationalExp - GREATER");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1719 "y.tab.c" /* yacc.c:1646  */
    break;

  case 34:
#line 245 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("RelationalExp - GREATER_EQUAL");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1729 "y.tab.c" /* yacc.c:1646  */
    break;

  case 35:
#line 251 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("RelationalExp - LESS");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1739 "y.tab.c" /* yacc.c:1646  */
    break;

  case 36:
#line 257 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("RelationalExp - LESS_EQUAL");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1749 "y.tab.c" /* yacc.c:1646  */
    break;

  case 37:
#line 263 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("AdditiveExp");
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1758 "y.tab.c" /* yacc.c:1646  */
    break;

  case 38:
#line 269 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("AdditiveExp - PLUS");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1768 "y.tab.c" /* yacc.c:1646  */
    break;

  case 39:
#line 275 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("AdditiveExp - MINUS");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1778 "y.tab.c" /* yacc.c:1646  */
    break;

  case 40:
#line 281 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("MultiplicativeExp");
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1787 "y.tab.c" /* yacc.c:1646  */
    break;

  case 41:
#line 287 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("MultiplicativeExp - MULTIPLY");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1797 "y.tab.c" /* yacc.c:1646  */
    break;

  case 42:
#line 293 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("MultiplicativeExp - DIVIDE");
                            (yyval.node)->addChild((yyvsp[-2].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1807 "y.tab.c" /* yacc.c:1646  */
    break;

  case 43:
#line 299 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("UnaryExp");
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1816 "y.tab.c" /* yacc.c:1646  */
    break;

  case 44:
#line 305 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("UnaryExp - PLUS");
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1825 "y.tab.c" /* yacc.c:1646  */
    break;

  case 45:
#line 310 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("UnaryExp - MINUS");
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1834 "y.tab.c" /* yacc.c:1646  */
    break;

  case 46:
#line 315 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Factor");
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1843 "y.tab.c" /* yacc.c:1646  */
    break;

  case 47:
#line 321 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Factor - PARENTHESES");
                            (yyval.node)->addChild((yyvsp[-1].node));
                        }
#line 1852 "y.tab.c" /* yacc.c:1646  */
    break;

  case 48:
#line 326 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Factor - ID");
                            (yyval.node)->setStringValue((yyvsp[0].string));
                        }
#line 1861 "y.tab.c" /* yacc.c:1646  */
    break;

  case 49:
#line 331 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Factor - LeftValue");
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1870 "y.tab.c" /* yacc.c:1646  */
    break;

  case 50:
#line 336 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Factor - new_primetype_index");
                            (yyval.node)->addChild((yyvsp[-1].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1880 "y.tab.c" /* yacc.c:1646  */
    break;

  case 51:
#line 342 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Factor - STRING_LITERAL");
                            (yyval.node)->setStringValue((yyvsp[0].string));
                        }
#line 1889 "y.tab.c" /* yacc.c:1646  */
    break;

  case 52:
#line 347 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Factor - INTEGER_LITERAL");
                            (yyval.node)->setIntValue((yyvsp[0].integer));
                        }
#line 1898 "y.tab.c" /* yacc.c:1646  */
    break;

  case 53:
#line 352 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Factor - BOOLEAN_LITERAL");
                            (yyval.node)->setBooleanValue(true);
                        }
#line 1907 "y.tab.c" /* yacc.c:1646  */
    break;

  case 54:
#line 357 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Factor - BOOLEAN_LITERAL");
                            (yyval.node)->setBooleanValue(false);
                        }
#line 1916 "y.tab.c" /* yacc.c:1646  */
    break;

  case 55:
#line 363 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("LeftValue - leftValue_index");
                            (yyval.node)->addChild((yyvsp[-1].node));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1926 "y.tab.c" /* yacc.c:1646  */
    break;

  case 56:
#line 369 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("LeftValue - id_index");
                            (yyval.node)->setStringValue((yyvsp[-1].string));
                            (yyval.node)->addChild((yyvsp[0].node));
                        }
#line 1936 "y.tab.c" /* yacc.c:1646  */
    break;

  case 57:
#line 376 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Index - BRACKET");
                            (yyval.node)->addChild((yyvsp[-1].node));
                        }
#line 1945 "y.tab.c" /* yacc.c:1646  */
    break;

  case 58:
#line 381 "parser.y" /* yacc.c:1646  */
    {
                            (yyval.node) = new node("Index - index_bracket");
                            (yyval.node)->addChild((yyvsp[-3].node));
                            (yyval.node)->addChild((yyvsp[-1].node));
                        }
#line 1955 "y.tab.c" /* yacc.c:1646  */
    break;


#line 1959 "y.tab.c" /* yacc.c:1646  */
      default: break;
    }
  /* User semantic actions sometimes alter yychar, and that requires
     that yytoken be updated with the new translation.  We take the
     approach of translating immediately before every use of yytoken.
     One alternative is translating here after every semantic action,
     but that translation would be missed if the semantic action invokes
     YYABORT, YYACCEPT, or YYERROR immediately after altering yychar or
     if it invokes YYBACKUP.  In the case of YYABORT or YYACCEPT, an
     incorrect destructor might then be invoked immediately.  In the
     case of YYERROR or YYBACKUP, subsequent parser actions might lead
     to an incorrect destructor call or verbose syntax error message
     before the lookahead is translated.  */
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;

  /* Now 'shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*--------------------------------------.
| yyerrlab -- here on detecting error.  |
`--------------------------------------*/
yyerrlab:
  /* Make sure we have latest lookahead translation.  See comments at
     user semantic actions for why this is necessary.  */
  yytoken = yychar == YYEMPTY ? YYEMPTY : YYTRANSLATE (yychar);

  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (YY_("syntax error"));
#else
# define YYSYNTAX_ERROR yysyntax_error (&yymsg_alloc, &yymsg, \
                                        yyssp, yytoken)
      {
        char const *yymsgp = YY_("syntax error");
        int yysyntax_error_status;
        yysyntax_error_status = YYSYNTAX_ERROR;
        if (yysyntax_error_status == 0)
          yymsgp = yymsg;
        else if (yysyntax_error_status == 1)
          {
            if (yymsg != yymsgbuf)
              YYSTACK_FREE (yymsg);
            yymsg = (char *) YYSTACK_ALLOC (yymsg_alloc);
            if (!yymsg)
              {
                yymsg = yymsgbuf;
                yymsg_alloc = sizeof yymsgbuf;
                yysyntax_error_status = 2;
              }
            else
              {
                yysyntax_error_status = YYSYNTAX_ERROR;
                yymsgp = yymsg;
              }
          }
        yyerror (yymsgp);
        if (yysyntax_error_status == 2)
          goto yyexhaustedlab;
      }
# undef YYSYNTAX_ERROR
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
         error, discard it.  */

      if (yychar <= YYEOF)
        {
          /* Return failure if at end of input.  */
          if (yychar == YYEOF)
            YYABORT;
        }
      else
        {
          yydestruct ("Error: discarding",
                      yytoken, &yylval);
          yychar = YYEMPTY;
        }
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:

  /* Pacify compilers like GCC when the user code never invokes
     YYERROR and the label yyerrorlab therefore never appears in user
     code.  */
  if (/*CONSTCOND*/ 0)
     goto yyerrorlab;

  /* Do not reclaim the symbols of the rule whose action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;      /* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (!yypact_value_is_default (yyn))
        {
          yyn += YYTERROR;
          if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
            {
              yyn = yytable[yyn];
              if (0 < yyn)
                break;
            }
        }

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
        YYABORT;


      yydestruct ("Error: popping",
                  yystos[yystate], yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#if !defined yyoverflow || YYERROR_VERBOSE
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
  if (yychar != YYEMPTY)
    {
      /* Make sure we have latest lookahead translation.  See comments at
         user semantic actions for why this is necessary.  */
      yytoken = YYTRANSLATE (yychar);
      yydestruct ("Cleanup: discarding lookahead",
                  yytoken, &yylval);
    }
  /* Do not reclaim the symbols of the rule whose action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
                  yystos[*yyssp], yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  return yyresult;
}
#line 388 "parser.y" /* yacc.c:1906  */


void yyerror(char * s) {
    fprintf(stderr, "%s on line %d\n", s, yylineno);
}

int main(int argc, char ** argv) {
    ++argv, --argc;	/* skip over program name */
    // Run program on file
    if (argc > 0) {
        yyin = fopen( argv[0], "r" );
    } else {
        yyin = stdin;
    }

    // Check for syntax errors
    if (yyparse() != 0) {
        return 1;
    }

    // Traverses AST to check for semantic errors if no syntax errors
    typecheck * tc = new typecheck();
    tc->checkProgram(root);

    // Traverse the AST again to interpret the program if no semantic errors
    if(tc->numErrors == 0){
        cout << "No errors\n";
        // interpret * ic = new interpret();
        // ic->interpretProgram(root);
    }
    return 0;
}
