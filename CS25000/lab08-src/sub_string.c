#include <stdio.h>                                                      // Header file for standard input and output
#include <stdlib.h>                                                     // Header file for general performance

char* sub_string(char *in_string, int start_index, int end_index) {     // Start of sub_string method 
	int n = end_index - start_index + 2;                                  // Initialize size of substring
	char *out_string = (char *) malloc(sizeof(char) * (n));               // Allocate memory for out_string

	for (int i = start_index - 1; i < end_index; i++) {                   // Initialize loop for copying characters
		*out_string = *(in_string + i);                                     // Copy character from in_string to out_string
		out_string++;                                                       // Increment out_string pointer
	}                                                                     // End loop

	*out_string = '\000';                                                 // Add terminating character
	return out_string - n + 1;                                            // Return out_string pointing to start of string
}                                                                       // End sub_string method
