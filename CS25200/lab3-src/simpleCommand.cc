#include <cstdio>
#include <cstdlib>

#include <iostream>
#include <string>
#include <regex>

#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <pwd.h>

#include <limits.h>
#include "command.hh"
#include "simpleCommand.hh"

SimpleCommand::SimpleCommand() {
  _arguments = std::vector<std::string *>();
}

SimpleCommand::~SimpleCommand() {
  // iterate over all the arguments and delete them
  for (auto & arg : _arguments) {
    delete arg;
  }
}

int SimpleCommand::bg_pid = 0;
int SimpleCommand::es = 0;
std::string SimpleCommand::last_arg("");

void SimpleCommand::insertArgument( std::string * argument ) {
  // Environment Variable Expansion
  std::regex regEVE ("[^ \t\n]*\\$\\{[^}][^}]*\\}[^ \t\n]*");
  while (std::regex_match(*argument, regEVE)) {
    std::size_t start = argument->find("{");
    std::size_t end = argument->find("}");
    std::string ev = argument->substr(start + 1, end - start - 1);
    // Special cases
    if (!ev.compare("$")) {    
      char * pid = (char *) malloc(sizeof(char) * 128);
      sprintf(pid, "%ld", (long) getpid());
      argument->replace(start + 1, end - start - 1, pid);
    } else if (!ev.compare("?")) {
      argument->replace(start + 1, end - start - 1, std::to_string(SimpleCommand::es));
    } else if (!ev.compare("!")) {
      argument->replace(start + 1, end - start - 1, std::to_string(SimpleCommand::bg_pid));
    } else if (!ev.compare("_")) {
      argument->replace(start + 1, end - start - 1, SimpleCommand::last_arg.c_str());
    } else if (!ev.compare("SHELL")) {
      argument->replace(start + 1, end - start - 1, getenv("REAL_SHELL"));
    } else {
      argument->replace(start + 1, end - start - 1, getenv(ev.c_str()));
    }
 
    //remove additional characters
    std::size_t escD = argument->find("$");
    argument->erase(escD, 2);
    std::size_t escP = argument->find("}");
    argument->erase(escP, 1);
  }

  // Tilde
  std::regex regT ("\\~[^ \t\n]*");
  while (std::regex_match(*argument, regT)) {
    if (argument->length() == 1) {
      *argument = getenv("HOME");
    } else if (argument->at(1) == '/') {
      std::string home = getenv("HOME");
      argument->erase(0, 1);
      *argument = home.append(*argument);
    } else {
      std::size_t index = argument->find("/");
      std::string name;
      if (index != std::string::npos) {
        name = argument->substr(1, index - 1);
        argument->erase(0, index - 1);
      } else {
        name = argument->substr(1);
        argument->erase(0);
      }
      std::string user = getpwnam(name.c_str())->pw_dir;
      argument->erase(0, 1);
      *argument = user.append(*argument);
    }
  }

  // simply add the argument to the vector
  _arguments.push_back(argument);
}

// Print out the simple command
void SimpleCommand::print() {
  for (auto & arg : _arguments) {
    std::cout << "\"" << *arg << "\" \t";
  }
  // effectively the same as printf("\n\n");
  std::cout << std::endl;
}
