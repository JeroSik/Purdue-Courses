
/*
 *
 * CS-252 
 * shell.l: lexical analyzer for shell
 * You have to extend it.
 *
 */
%{

#include <cstring>
#include "y.tab.hh"

#include <string.h>
#include <unistd.h>

static  void yyunput (int c,char *buf_ptr  );

void myunputc(int c) {
  unput(c);
}

extern "C" char * read_line();

int mygetc(FILE * f) {
  static char *p;
  char ch;
  
  if (!isatty(0)) {
  // stdin is not a tty. Call real getc
    return getc(f);
  }

  // stdin is a tty. Call our read_line.
  if (p==NULL || *p == 0) {
    char * s = read_line();
    p = s;
  }

  ch = *p;
  p++;

  return ch;
}

#undef getc
#define getc(f) mygetc(f)

%}

%option noyywrap

%%

\n {
  return NEWLINE;
}

[ \t] {
  /* Discard spaces and tabs */
}

">" {
  return GREAT;
}

"<" {
  return LESS;
}

"2>" {
  return TWOGREAT;
}

">&" {
  return GREATAMPERSAND;
}

">>" {
  return GREATGREAT;
}

">>&" {
  return GREATGREATAMPERSAND;
}

"|" {
  return PIPE;
}

"&" {
  return AMPERSAND;
}

source[ ][^ \t\n][^ \t\n]* {
  char * src = strdup(yytext);
  src += 7;

  FILE * fd = fopen(src, "r");
  if (fd) {
    yypush_buffer_state(yy_create_buffer(fd, YY_BUF_SIZE));
    yyparse();
    yypop_buffer_state();
    fclose(fd);
  } else {
    yyparse();
  }
}

\$\(.*\) {
  char *src = strdup(yytext);
  // Get rid of extra characters
  src += 2;
  src[strlen(src) - 1] = '\0';

  // Write an extra exit command
  strcat(src, "\n");

  // Parent creates two pipes
  int pin[2];
  int pout[2];
  pipe(pin);
  pipe(pout);
  int tmpin = dup(0);
  int tmpout = dup(1);

  // Parents writes to pin[1]
  write(pin[1], src, strlen(src));
  close(pin[1]);
  
  // child redirect inout from pin[0] and redirect output to pout[1]
  dup2(pin[0], 0);
  dup2(pout[1], 1);
  close(pin[0]);
  close(pout[1]);
  
  //Parent creates child process
  int ret = fork();
  if (ret == 0) {
    char ** temp = new char*[2];
    temp[0] =  (char *) "/proc/self/exe";
    temp[1] = NULL;
    
    close(pout[0]);

    execvp(temp[0], temp);
    perror("execvp");
    _exit(1);
  } else if (ret < 0) {
    perror("fork");
    exit(1);
  }

  dup2(tmpin, 0);
  dup2(tmpout, 1);
  close(tmpin);
  close(tmpout);

  char curr;
  char * buff = (char *) malloc(sizeof(char) * 1024);
  int index = 0;
  while (read(pout[0], &curr, 1) > 0) {
    if (curr == '\n') {
      buff[index] = ' ';
    } else {
      buff[index] = curr;
    }
    index++;
  }
 
  close(pout[0]); 

  buff[index] = '\0';
  
  for (int i = strlen(buff) - 1; i >= 0; i--) {
    myunputc(buff[i]);
  }
}

\"[^"\n]*["\n] {
  // Quotes
  std::string temp = strdup(yytext);
  temp = temp.erase(0, 1);
  temp = temp.erase(temp.size() - 1, 1);
  yylval.cpp_string = new std::string(temp);
  return WORD;
}

[^ \t\n|><&]*\\[^ \t\n]*  {
  // Escape
  char *src = strdup(yytext);
  char *temp = new char[strlen(src)];
  int countS = 0;
  int countT = 0;
  int countE = 0;
  while (src[countS] != '\0') {
    if (src[countS] != (char) 92) {
      temp[countT] = src[countS];
      countT++;
      countE = 0;
    } else {
      countE++;
      if (countE == 3) {
        temp[countT] = src[countS];
        countT++;
      }
    }
    countS++;
  }
  temp[countT] = '\0';
  yylval.cpp_string = new std::string(temp);
  return WORD;
}

[^ ^\\&<>|\t\n][^ ^\\&<>|\t\n]* {
  yylval.cpp_string = new std::string(yytext);
  return WORD;
}
