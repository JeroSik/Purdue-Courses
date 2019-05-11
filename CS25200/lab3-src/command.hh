#ifndef command_hh
#define command_hh

#include "simpleCommand.hh"

// Command Data Structure

struct Command {
  std::vector<SimpleCommand *> _simpleCommands;
  std::string * _outFile;
  std::string * _inFile;
  std::string * _errFile;
  bool _background;

  // My helper variables
  bool _append;
  bool _multiple;

  Command();
  void insertSimpleCommand( SimpleCommand * simpleCommand );

  void clear();
  void print();
  void execute();

  // My helper functions
  std::vector<char *> strVec_charVec(std::vector<std::string *> _arguments);
  void checkExit(const char* argv);
  void check_printenv(std::vector<char *> argv);
  bool check_setenv(std::vector<char *> argv);
  bool check_unsetenv(std::vector<char *> argv);
  //bool check_cd(std::vector<std::string *> argv);
  bool check_cd(std::vector<char *> argv); 

  static SimpleCommand *_currentSimpleCommand;
};

#endif
