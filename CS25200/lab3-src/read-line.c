/*
 * CS252: Systems Programming
 * Purdue University
 * Example that shows how to read one line with simple editing
 * using raw terminal.
 */

#include <dirent.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

#define MAX_BUFFER_LINE 2048

extern void tty_raw_mode(void);

// Buffer where line is stored
int line_length;
char line_buffer[MAX_BUFFER_LINE];

// Cursor position
int cur_pos = 0;

// Simple history array
// This history does not change. 
// Yours have to be updated.
int history_index = 0;
char * history[25];
int history_length = 0;
int max_history = 25;

void read_line_print_usage()
{
  char * usage = "\n"
    " ctrl-?       Print usage\n"
    " Backspace    Deletes last character\n"
    " up arrow     See last command in the history\n";

  write(1, usage, strlen(usage));
}

/* 
 * Input a line with some basic editing.
 */
char * read_line() {

  // Set terminal in raw mode
  tty_raw_mode();

  line_length = 0;
  cur_pos = 0;
  history_index = 0;

  // Read one line until enter is typed
  while (1) {

    // Read one character in raw mode.
    char ch;
    read(0, &ch, 1);

    // It is a printable character. 
    if (ch>=32) {
      // Check if printing at end
      if (cur_pos == line_length) {
        // Do echo
        write(1,&ch,1);

        // If max number of character reached return.
        if (line_length==MAX_BUFFER_LINE-2) break; 

        // add char to buffer.
        line_buffer[line_length]=ch;
        line_length++;

        // Update cursor position
        cur_pos++;
      } else {
        // If max number of character reached return.
        if (line_length == MAX_BUFFER_LINE - 1) break;
        line_length++;

        // Move to end of line
        char ch1;
        for (int i = cur_pos; i < line_length - 1; i++) {
          ch1 = line_buffer[i];
          write(1, &ch1, 1);
        }

        // Create room for new character
        ch1 = ' ';
        write(1, &ch1, 1);

        // Shift line buffer right one
        ch1 = 8;
        for (int i = line_length - 1; i >= cur_pos; i--) {
          line_buffer[i] = line_buffer[i - 1];
          write(1, &ch1, 1);
        }

        // Enter new character
        line_buffer[cur_pos] = ch;

        // Update buffer with new character
        for (int i = cur_pos; i < line_length; i++) {
          ch = line_buffer[i];
          write(1, &ch, 1);
        }

        // Move cursor back
        ch = 8;
        for (int i = 0; i < (line_length - cur_pos - 1); i++) {
          write(1, &ch1, 1);
        }

        // Update cursor position
        cur_pos++;
      }
    } else if (ch==10) {
      // <Enter> was typed. Return line
      
      // Print newline
      write(1,&ch,1);

      break;
    } else if (ch == 31) {
      // ctrl-?
      read_line_print_usage();
      line_buffer[0]=0;
      break;
    }
    // <backspace> was typed. Remove previous character read.
    else if (ch == 8 || ch == 127) {
      // Check to see characters to delete
      if (line_length > 0 && cur_pos > 0) {
        // Go back one character
        ch = 8;
        write(1,&ch,1);
        cur_pos--;

        // Shift line_buffer left one
        for (int i = cur_pos; i < line_length - 1; i++) {
          line_buffer[i] = line_buffer[i + 1];
          ch = line_buffer[i];
          write(1, &ch, 1);
        }

        // Write a space to erase the last character read
        ch = ' ';
        write(1,&ch,1);

        // Move cursor back in place
        ch = 8;
        for (int i = 0; i < (line_length - cur_pos); i++) {
          write(1,&ch,1);
        }

        // Remove one character from buffer
        line_length--;
      }
    // <escape> was typed. Remove next character read.
    } else if (ch == 4) {
      // Check to see characters to delete 
      if (line_length > 0 && cur_pos < line_length) {
        // Shift line_buffer left one
        for (int i = cur_pos; i < line_length - 1; i++) {
          line_buffer[i] = line_buffer[i + 1];
          ch = line_buffer[i];
          write(1, &ch, 1);
        }

        // Write a space to erase the last character read
        ch = ' ';
        write(1,&ch,1);
        line_buffer[line_length - 1] = ' ';

        // Move cursor back in place
        ch = 8;
        for (int i = 0; i < (line_length - cur_pos); i++) {
          write(1,&ch,1);
        }

        // Remove one character from buffer
        line_length--;
      }
    // <home> was typed. Move cursor to start.
    } else if (ch == 1) {
      // Check if cursor can move left
      if (cur_pos > 0) {
        // Move left to start
        ch = 27; char ch1 = 91; char ch2 = 68;
        for (int i = cur_pos; i > 0; i--) {
          write(1, &ch, 1);
          write(1, &ch1, 1);
          write(1, &ch2, 1);
        }

        // Update cursor position
        cur_pos = 0;
      }
    // <end> was typed. Move cursor to end.
    } else if (ch == 5) {
      // Check if cursor can move right
      if (cur_pos < line_length) {
        // Move left to end
        ch = 27; char ch1 = 91; char ch2 = 67;
        for (int i = cur_pos; i < line_length; i++) {
          write(1, &ch, 1);
          write(1, &ch1, 1);
          write(1, &ch2, 1);
        }

        // Update cursor position
        cur_pos = line_length;
      }
    // <tab> was typed. Tab completion.
    } else if (ch == 9) {
      if (line_length != 0) {
        // Convert current buffer to regex

        // Open current directory
        DIR * dir = opendir(".");
        
        closedir(dir);
      }
    } else if (ch==27) {
      // Escape sequence. Read two chars more
      char ch1; 
      char ch2;
      read(0, &ch1, 1);
      read(0, &ch2, 1);
      // Up arrow. Print next line in history.
      if (ch1==91 && ch2==65) {
        if (history_index < history_length) {
	        // Print backspaces and erase line
	        for (int i = 0; i < line_length; i++) {
	          ch = 8;
	          write(1,&ch,1);
  	      }

	        // Print spaces on top
	        for (int i = 0; i < line_length; i++) {
	          ch = ' ';
	          write(1,&ch,1);
  	      }

	        // Print backspaces
	        for (int i = 0; i < line_length; i++) {
	          ch = 8;
	          write(1,&ch,1);
  	      }	

	        // Copy line from history
	        strcpy(line_buffer, history[history_index]);
	        line_length = strlen(line_buffer);
          cur_pos = line_length;
          history_index++;
  	      //history_index=(history_index+1)%history_length;

	        // echo line
	        write(1, line_buffer, line_length);
        }
      // Down arrow. Print previous line in history.
      } else if (ch1 == 91 && ch2 == 66) {
        if (history_index > 0) {
	        // Print backspaces and erase line
	        for (int i = 0; i < line_length; i++) {
	          ch = 8;
	          write(1,&ch,1);
  	      }

	        // Print spaces on top
	        for (int i = 0; i < line_length; i++) {
	          ch = ' ';
	          write(1,&ch,1);
  	      }

	        // Print backspaces
	        for (int i = 0; i < line_length; i++) {
	          ch = 8;
	          write(1,&ch,1);
  	      }	

	        // Copy line from history
	        strcpy(line_buffer, history[history_index]);
	        line_length = strlen(line_buffer);
          cur_pos = line_length;
          history_index--;
  	      //history_index=(history_index+1)%history_length;

	        // echo line
	        write(1, line_buffer, line_length);
        }
      
      // Right arrow. Move cursor to the right.
      } else if (ch1 == 91 && ch2 == 67) {
        // Check if cursor can move right
        if (cur_pos < line_length) {
          // Move right
          write(1, &ch, 1);
          write(1, &ch1, 1);
          write(1, &ch2, 1);

          // Update cursor position
          cur_pos++;
        }
      // Left arrow. Move cursor to the left.
      } else if (ch1 == 91 && ch2 == 68) {
        // Check if cursor can move left
        if (cur_pos > 0) {
          // Move left
          write(1, &ch, 1);
          write(1, &ch1, 1);
          write(1, &ch2, 1);

          // Update cursor position
          cur_pos--;
        }
      }
    }
  }

  // Add line to history
  if (history_length < max_history) {
    history[history_length] = (char *) malloc(sizeof(line_buffer) + 1);
    line_buffer[line_length] = '\0';
    strcpy(history[history_length], line_buffer);
    history_length++;
  }

  // Add eol and null char at the end of string
  line_buffer[line_length]=10;
  line_length++;
  line_buffer[line_length]=0;

  return line_buffer;
}
