/*
 * CS252: Shell project
 *
 * Template file.
 * You will need to add more code here to execute the command table.
 *
 * NOTE: You are responsible for fixing any bugs this code may have!
 *
 * DO NOT PUT THIS PROJECT IN A PUBLIC REPOSITORY LIKE GIT. IF YOU WANT 
 * TO MAKE IT PUBLICALLY AVAILABLE YOU NEED TO REMOVE ANY SKELETON CODE 
 * AND REWRITE YOUR PROJECT SO IT IMPLEMENTS FUNCTIONALITY DIFFERENT THAN
 * WHAT IS SPECIFIED IN THE HANDOUT. WE OFTEN REUSE PART OF THE PROJECTS FROM  
 * SEMESTER TO SEMESTER AND PUTTING YOUR CODE IN A PUBLIC REPOSITORY
 * MAY FACILITATE ACADEMIC DISHONESTY.
 */

#include <cstdio>
#include <cstdlib>

#include <iostream>
#include <cstring>
#include <string>

// My imported libraries
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <fstream>
#include <fcntl.h>
//#include <signal.h>
#include <sstream>
#include <FlexLexer.h>
#include <regex>

#include "command.hh"
#include "shell.hh"
#include "simpleCommand.hh"

Command::Command() {
    // Initialize a new vector of Simple Commands
    _simpleCommands = std::vector<SimpleCommand *>();

    _outFile = NULL;
    _inFile = NULL;
    _errFile = NULL;
    _background = false;
    _append = false;
    _multiple = false;
}

void Command::insertSimpleCommand( SimpleCommand * simpleCommand ) {
    // add the simple command to the vector
    _simpleCommands.push_back(simpleCommand);
}

void Command::clear() {
    // deallocate all the simple commands in the command vector
    for (auto simpleCommand : _simpleCommands) {
        delete simpleCommand;
    }

    // remove all references to the simple commands we've deallocated
    // (basically just sets the size to 0)
    _simpleCommands.clear();

    if ( _outFile ) {
        delete _outFile;
    }
    _outFile = NULL;

    if ( _inFile ) {
        delete _inFile;
    }
    _inFile = NULL;

    if ( _errFile ) {
        delete _errFile;
    }
    _errFile = NULL;

    _background = false;
    _append = false;
    _multiple = false;
}

void Command::print() {
    printf("\n\n");
    printf("              COMMAND TABLE                \n");
    printf("\n");
    printf("  #   Simple Commands\n");
    printf("  --- ----------------------------------------------------------\n");

    int i = 0;
    // iterate over the simple commands and print them nicely
    for ( auto & simpleCommand : _simpleCommands ) {
        printf("  %-3d ", i++ );
        simpleCommand->print();
    }

    printf( "\n\n" );
    printf( "  Output       Input        Error        Background\n" );
    printf( "  ------------ ------------ ------------ ------------\n" );
    printf( "  %-12s %-12s %-12s %-12s\n",
            _outFile?_outFile->c_str():"default",
            _inFile?_inFile->c_str():"default",
            _errFile?_errFile->c_str():"default",
            _background?"YES":"NO");
    printf( "\n\n" );
}

std::vector<char *> Command::strVec_charVec(std::vector<std::string *> _arguments) {
  std::vector<char *> strVec(_arguments.size() + 1);
  for (size_t i = 0; i < _arguments.size(); i++) {
    strVec[i] = (char *) _arguments[i]->c_str();
  }
  return strVec;
}

void Command::checkExit(const char * argv) {
  std::string quit ("exit");
  if (quit.compare(argv) == 0) {
    printf("\nGood bye!!\n\n");
    exit(0);
  }
}

void Command::check_printenv(std::vector<char *> argv) {
  if (!strcmp(argv[0], "printenv")) {
    char **p = environ;
    while (*p != NULL) {
      printf("%s\n", *p);
      p++;
    }
    exit(0);
  }
}

bool Command::check_setenv(std::vector<char *> argv) {
  if (!strcmp(argv[0], "setenv")) {
    int error = setenv(argv[1], argv[2], 1);
    if (error) {
      perror("setenv");
    }
    return true;
  } else {
    return false;
  }
}

bool Command::check_unsetenv(std::vector<char *> argv) {
  if (!strcmp(argv[0], "unsetenv")) {
    int error = unsetenv(argv[1]);
    if (error) {
      perror("unsetenv");
    }
    return true;
  } else {
    return false;
  }
}

bool Command::check_cd(std::vector<char *> argv) {
  if (!strcmp(argv[0], "cd")) {
    int error = 0;
    if (argv.size() == 2 || !strcmp(argv[1], "${HOME}")) {
      error = chdir(getenv("HOME"));
    } else {
      error = chdir(argv[1]);
    }  
    if (error) {
      fprintf(stderr, "cd: can't cd to notfound\n");
    }
    return true;
  } else {
    return false;
  }
}

void Command::execute() {
    // Don't do anything if there are no simple commands
    if ( _simpleCommands.size() == 0 ) {
        Shell::prompt();
        return;
    }
    
    // Exit
    checkExit(_simpleCommands[0]->_arguments[0]->c_str());

    std::vector<char *> argv = strVec_charVec(_simpleCommands[0]->_arguments);

    // setenv
    if (check_setenv(argv)) {
      clear();
      Shell::prompt();
      return;
    }

    // unsetenv
    if (check_unsetenv(argv)) {
      clear();
      Shell::prompt();
      return;
    }

    // cd
    if (check_cd(argv)) {
      clear();
      Shell::prompt();
      return;
    }

    if (_multiple) {
      printf("Ambiguous output redirect.\n");
    }

    // Print contents of Command data structure
    //print();

    // Add execution here
    // For every simple command fork a new process
    // Setup i/o redirection
    // and call exec
    // save in/out
    int tmpin = dup(0);
    int tmpout = dup(1);
    int tmperr = dup(2);

    // set the initial input
    int fdin;
    if (_inFile) {
      fdin = open(_inFile->c_str(), O_RDONLY);
      if (fdin < 0) {
        printf("/bin/sh: 1: cannot open files/aaa: No such file\n");
        exit(1);
      }
    } else {
      // Use default input
      fdin = dup(tmpin);
    }

    int fderr;
    if (_errFile) {
      if (_append) {
        fderr = open(_errFile->c_str(), O_WRONLY | O_APPEND | O_CREAT, 0600);
      } else {
        fderr = open(_errFile->c_str(), O_WRONLY | O_CREAT | O_TRUNC, 0600);
      }
      if (fderr < 0) {
        printf("/bin/sh: 1: cannot open files/aaa: No such file\n");
        exit(1); 
      }
    } else {
      fderr = dup(tmperr);
    }

    // redirect error
    dup2(fderr, 2);
    close(fderr);

    int ret;
    int fdout;
    for (size_t i = 0; i < _simpleCommands.size(); i++) {
      // redirect input
      dup2(fdin, 0);
      close(fdin);
      // setup output
      if (i == _simpleCommands.size() - 1) {
        // Last simple command
        if (_outFile) {
          if (_append) {
            fdout = open(_outFile->c_str(), O_WRONLY | O_APPEND | O_CREAT, 0600);
          } else {
            fdout = open(_outFile->c_str(), O_WRONLY | O_CREAT | O_TRUNC, 0600);
          }
          if (fdout < 0) {
            printf("/bin/sh: 1: cannot open files/aaa: No such file\n");
            exit(1);
          }
        } else {
          // Use default output
          fdout = dup(tmpout); 
        }
        SimpleCommand::last_arg = argv[argv.size() - 2];
      } else {
        // Not last simple command
        // create pipe
        int fdpipe[2];
        pipe(fdpipe);
        fdout = fdpipe[1];
        fdin = fdpipe[0];
      } // if/else
      // Redirect output
      dup2(fdout, 1);
      close(fdout);
      // Create child process
      ret = fork();
      if (ret == 0) { 
        argv = strVec_charVec(_simpleCommands[i]->_arguments);

        // printenv
        check_printenv(argv);
        
        // child
        execvp(argv[0], argv.data());
        perror("execvp");
        _exit(1);
      } else if (ret < 0) {
        perror("fork");
        return;
      }
      // Parent shell continue
    } // for
    dup2(tmpin, 0);
    dup2(tmpout, 1);
    dup2(tmperr, 2);
    close(tmpin);
    close(tmpout);
    close(tmperr);

    if (!_background) {
      // wait for last process
      int status;
      if (waitpid(ret, &status, 0) == -1) {
        perror("waitpid() failed");
        exit(EXIT_FAILURE);
      }
      
      if (WIFEXITED(status)) {
        SimpleCommand::es = WEXITSTATUS(status);
        if (SimpleCommand::es != 0) {
          char * onError = getenv("ON_ERROR");
          if (onError != NULL) {
            printf("%s\n", onError);
          }
        }
      }
    } else {
      SimpleCommand::bg_pid = ret;
    }

    // Clear to prepare for next command
    clear();

    // Print new prompt
    Shell::prompt();
}

SimpleCommand * Command::_currentSimpleCommand;
