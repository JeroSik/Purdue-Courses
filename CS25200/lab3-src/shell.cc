#include <cstdio>
#include <unistd.h>
#include "shell.hh"

// My libraries
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <limits.h>
#include <iostream>
#include <string.h>
#include <stdio.h>
#include <string>
#include <stdlib.h>

int yyparse(void);

void Shell::prompt() {
  if (isatty(0)) {
    char * newPrompt = getenv("PROMPT");
    if (newPrompt == NULL) {
      printf("myshell>");
    } else {
      printf("%s", newPrompt);
    }
  }
  fflush(stdout);
}

extern "C" void sigIntHandler(int sig) {
  fprintf(stdout, "\n"); 
  Shell::prompt();
}

extern "C" void sigChildHandler(int sig) {
  while(waitpid(-1, NULL, WNOHANG) > 0);
}

int main(int argc, char ** argv) {
  // Ctrl-C
  struct sigaction saC;
  saC.sa_handler = sigIntHandler;
  sigemptyset(&saC.sa_mask);
  saC.sa_flags = SA_RESTART;
  int errorC = sigaction(SIGINT, &saC, NULL);
  if (errorC) {
    perror("sigaction");
    exit(-1);
  }

  // Zombie Elimination
  struct sigaction saZ;
  saZ.sa_handler = sigChildHandler;
  sigemptyset(&saZ.sa_mask);
  saZ.sa_flags = SA_RESTART;
  int errorZ = sigaction(SIGCHLD, &saZ, NULL);
  if (errorZ) {
    perror("sigaction");
    exit(-1);
  }

  char buf[PATH_MAX + 1];
  setenv("REAL_SHELL", realpath(argv[0], buf), 1);

  //Shell::_currentCommand.insertSimpleCommand("source .shellrc");  

  Shell::prompt();
  yyparse();
}

Command Shell::_currentCommand;
