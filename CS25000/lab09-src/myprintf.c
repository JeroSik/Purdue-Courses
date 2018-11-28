// Header files for stdio.h and stdlib.h
#include <stdio.h>
#include <stdlib.h>

// Function declaration for printx and printd
void printx(int, int);
void printd(int);

/**
 *  int myprintf(const char *format, ...);
 *  @parameters
 *    const char *format - string literal in ASCII
 *    ...                - list of arguments
 *  @function
 *    Prints format strings
 *    Allows these conversion specifiers and specifier escapes
 *    %c, single character
 *    %s, character string
 *    %x, hexadecimal using lower case
 *    %X, hexadecimal using upper case
 *    %d, signed decimal integer
 *    %%, escape from conversion and prints '%' character
 */
int myprintf(const char *format, ...) {
  // Initialize pointer to stack to last argument
  int *args = (int *)&format;

  // Loop through format string
  while (*format != '\000') {
    // Check if string is '%' otherwise putchar
    if (*format == '%') {
      // Move to next character and pointer in stack
      format++;
      args++;

      // Check if next character is conversion or escape specifier
      // putchar if character
      if (*format == 'c') {
        putchar((char) *args);
      // putchar every character in the string
      } else if (*format == 's') {
        char *string = (char *) *args;
        while (*string != '\000') {
          putchar(*string);
          string++;
        }
      // call printx with flag 0
      } else if (*format == 'x') {
        printx((int) *args, 0);
      // call printx with flag 1
      } else if (*format == 'X') {
        printx((int) *args, 1);
      // call printd
      } else if (*format == 'd') {
        printd((int) *args);
      // print '%' and decrement pointer
      } else if (*format == '%') {
        putchar('%');
        args--;
      // putchar nonexisting specifier and decrement pointer
      } else {
        putchar(*format);
        args--;
      }
    } else {
      putchar(*format);
    }
    format++;
  }
}
