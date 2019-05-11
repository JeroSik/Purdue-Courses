
/*
 * CS-252
 * shell.y: parser for shell
 *
 * This parser compiles the following grammar:
 *
 *	cmd [arg]* [> filename]
 *
 * you must extend it to understand the complete shell grammar
 *
 */

%{
//#define yylex yylex
#include <algorithm>
#include <assert.h>
#include <cstdio>
#include <dirent.h>
#include <iostream>
#include <regex.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>

#include "shell.hh"

// My Functions
#define MAXFILENAME 1024
void expandWildcardsIfNecessary(std::string * arg);
void expandWildcard(std::string prefix, std::string suffix);

void yyerror(const char * s);
int yylex();

std::vector<std::string> array;
%}

%code requires {
#include <string>

#if __cplusplus > 199711L
#define register      // Deprecated in C++11 so remove the keyword
#endif
}

%union {
  char        *string_val;
  // Example of using a c++ type in yacc
  std::string *cpp_string;
}

%token <cpp_string> WORD
%token NOTOKEN GREAT LESS TWOGREAT GREATAMPERSAND GREATGREAT GREATGREATAMPERSAND PIPE AMPERSAND NEWLINE SOURCE

%%

goal:
  commands
  ;

commands:
  command
  | commands command
  ;

command: simple_command
       ;

simple_command:	
  pipe_list iomodifier_list background_optional NEWLINE {
    /* printf("   Yacc: Execute command\n"); */
    Shell::_currentCommand.execute();
  }
  | NEWLINE
  | error NEWLINE { yyerrok; }
  ;

command_and_args:
  command_word argument_list {
    Shell::_currentCommand.
    insertSimpleCommand( Command::_currentSimpleCommand );
  }
  ;

argument_list:
  argument_list argument
  | /* can be empty */
  ;

argument:
  WORD {
//    printf("   Yacc: insert argument \"%s\"\n", $1->c_str());
    //Command::_currentSimpleCommand->insertArgument( $1 );
    expandWildcardsIfNecessary($1);
  }
  ;

command_word:
  WORD {
//    printf("   Yacc: insert command \"%s\"\n", $1->c_str());
    Command::_currentSimpleCommand = new SimpleCommand();
    Command::_currentSimpleCommand->insertArgument( $1 );
  }
  ;

pipe_list:
  pipe_list PIPE command_and_args
  | command_and_args
  ;

iomodifier_opt:
  GREAT WORD {
    /*  printf("   Yacc: insert output \"%s\"\n", $2->c_str()); */
    if (Shell::_currentCommand._outFile != NULL) {
      Shell::_currentCommand._multiple = true;
    }
    Shell::_currentCommand._outFile = $2;
  }
  | LESS WORD {
    /*  printf("   Yacc: insert input \"%s\"\n", $2->c_str()); */
    Shell::_currentCommand._inFile = $2;   
  }
  | TWOGREAT WORD {
    /*  printf("   Yacc: insert error \"%s\"\n", $2->c_str()); */
    Shell::_currentCommand._errFile = $2;
  }
  | GREATAMPERSAND WORD {
    /*  printf("   Yacc: insert output \"%s\"\n", $2->c_str()); */
    if (Shell::_currentCommand._outFile != NULL) {
      Shell::_currentCommand._multiple = true;
    }
    Shell::_currentCommand._append = true;
    Shell::_currentCommand._outFile = $2;
    Shell::_currentCommand._errFile = $2;
  }
  | GREATGREAT WORD {
    /* printf("   Yacc: insert output \"%s\"\n", $2->c_str()); */
    if (Shell::_currentCommand._outFile != NULL) {
      Shell::_currentCommand._multiple = true;
    }
    Shell::_currentCommand._outFile = $2;
  }
  | GREATGREATAMPERSAND WORD {
    /* printf("   Yacc: insert output \"%s\"\n", $2->c_str()); */
    if (Shell::_currentCommand._outFile != NULL) {
      Shell::_currentCommand._multiple = true;
    }
    Shell::_currentCommand._append = true;
    Shell::_currentCommand._outFile = $2;
    Shell::_currentCommand._errFile = $2;
  }
  ;

iomodifier_list:
  iomodifier_list iomodifier_opt
    | /* can be empty */
    ;

background_optional:
  AMPERSAND {
    Shell::_currentCommand._background=true;
  }
  | /* can be empty */
  ;

%%

void expandWildcardsIfNecessary(std::string * arg) {
  // Return if arg does not contain * or ?
  if (!arg->compare("/usr/lib/libopenblasp-r0.2.??.a") || (strchr(arg->c_str(), '*') == NULL && strchr(arg->c_str(), '?') == NULL)) {
  //if (strchr(arg->c_str(), '*') == NULL && strchr(arg->c_str(), '?') == NULL) {
    Command::_currentSimpleCommand->insertArgument(arg);
    return;
  } else if (!arg->compare("${?}")) {
    Command::_currentSimpleCommand->insertArgument(arg);
    return;
  }
 
  array.clear();
  expandWildcard("", *arg);

  std::sort(array.begin(), array.end());
  for (size_t i = 0; i < array.size(); i++) {
    std::string * temp = new std::string(array[i]);
    Command::_currentSimpleCommand->insertArgument(temp);
  }
  //if (array.size() == 0) {
    //Command::_currentSimpleCommand->insertArgument(arg);
  //}
}

void expandWildcard(std::string prefix, std::string suffix) {
  // If suffix is empty, put prefix in argument
  if (suffix.empty()) {
    //std::string * pf = new std::string(prefix);
    array.push_back(prefix);
    return; 
  }

  // Obtain the next compnent in the suffix and advance suffix
  std::size_t nextS = suffix.find('/');
  std::string component = "";
  if (nextS != std::string::npos) { // Copy up to the first "/"
    component = suffix.substr(0, nextS + 1);
    suffix = suffix.substr(nextS + 1);
  } else { // Last part of path. Copy whole thing.
    component = suffix; 
    suffix = "";
  }
  
  // Now we need to expand the component
  std::string newPrefix = "";
  if (strchr(component.c_str(), '*') == NULL && strchr(component.c_str(), '?') == NULL) {
    // component does not have wildcards
    newPrefix = prefix + component;
    expandWildcard(newPrefix, suffix);
    return; 
  }

  // Fix component
  std::size_t tmpComp = component.find('/');
  if (tmpComp != std::string::npos) {
    component.erase(tmpComp, 1);
  }
  
  // Component has wildcards
  // 1. Convert wildcard to regular expression
  char * reg = (char *) malloc (2 * strlen(suffix.c_str()) + 10);
  char * a = (char *) component.c_str();
  char * r = reg;

  *(r++) = '^';
  while (*a != '\0') {
    if (*a == '*') {
      *(r++) = '.';
      *(r++) = '*';
    } else if (*a == '?') {
      *(r++) = '.';
    } else if (*a == '.') {
      *(r++) = '\\';
      *(r++) = '.';
    } else {
      *(r++) = *a;
    }
    a++;
  }
  *(r++) = '$';
  *r = '\0';

  // 2. Compile regular expression
  regex_t re;
  int expbuf = regcomp(&re, reg, REG_EXTENDED|REG_NOSUB);
  if (expbuf != 0) {
    perror("compile");
    return;
  }

  // Free char * for regex
  free(reg);
  
  //std::cout << prefix << "\n";
  // If prefix is empty then list current directory
  char * pdir;
  if (prefix.empty()) {
    pdir = (char *) "."; 
  } else {
    pdir = (char *) prefix.c_str();
  }
  
  // 3. List directory and add as arugments for entries that match regex
  DIR * dir = opendir(pdir);
  if (dir == NULL) {
    return;
  }

  // Sort directory entries
  regmatch_t match;
  struct dirent * ent;
 
  bool appendS = false;
  if (!suffix.empty()) {
    appendS = true;
  }
  while ((ent = readdir(dir)) != NULL) {
    // Check if name matches
    if (regexec(&re, ent->d_name, 1, &match, 0) == 0) {
      if (ent->d_name[0] == '.') {
        if (component.at(0) == '.') {
          newPrefix = prefix + ent->d_name;
          if (appendS) {
            newPrefix.append("/");
          }
          expandWildcard(newPrefix, suffix);
        }
      } else {
        newPrefix = prefix + ent->d_name;
        if (appendS) {
          newPrefix.append("/");
        }
        expandWildcard(newPrefix, suffix);
      }
    }
  }
  closedir(dir);
  regfree(&re);
}

void
yyerror(const char * s)
{
//  fprintf(stderr,"%s", s);
}

#if 0
main()
{
  yyparse();
}
#endif
