#include <stdio.h>                                                          // Header file for standard input and ouput
#include <stdlib.h>                                                         // Header file for general performance

char* sub_string(char *in_string, int start_index, int end_index);          // Initialize sub_string method

/*
 * Main function prompts user for inputs and outputs substring
 */
int main() {                                                                // Start of main method
	int start_index, end_index;                                               // Initialize start and end index
	char *in_string = (char *) malloc(sizeof(char) * 100);                    // Allocate memory to input string
	char *out_string;                                                         // Initialize output string

	printf("Enter a string: ");                                               // Prompt for input string
	scanf("%[^\n]s", in_string);                                                   // Listen to user input

	printf("Enter the start index: ");                                        // Prompt for start index
	scanf("%d", &start_index);                                                // Listen to user input

	printf("Enter the end index: ");                                          // Prompt for end index
	scanf("%d", &end_index);                                                  // Listen to user input

	out_string = sub_string(in_string, start_index, end_index);               // Set output string as substring
	printf("The substring of the given string is '%s'\n", out_string);        // Print output

	return 0;                                                                 // Return 0
}                                                                           // End of main method
