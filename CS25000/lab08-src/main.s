.data                                                                   // Delcare data section
.balign 4                                                               // Byte alignment on a 32-bit machine

stringPrompt: .asciz "Enter a string: "                                 // Declare prompt for input string
startPrompt: .asciz "Enter the start index: "                           // Declare prompt for start index
endPrompt: .asciz "Enter the end index: "                               // Declare prompt for end index
outputPrompt: .asciz "The substring of the given string is '%s'\n"      // Declare output for substring

strFmt: .asciz "%[^\n]s"                                                     // Declare format for string
intFmt: .asciz "%d"                                                     // Declare format for integer

string: .skip 150                                                       // Allocate memory for input string
start: .skip 100                                                        // Allocate memory for start index
end: .skip 100                                                          // Allocate memory for end index

.text							                                                    	// Declare text section

/*
 * Main method prompts for user input and outputs substring
 */
.global main							                                            	// Make "main" visible to linker
main:								                                                  	// Label for the start of program
	push {fp, lr}							                                            // Push fp and lr

	ldr r0, =stringPrompt                                                 // Load r0 with prompt for input string
	bl printf                                                             // Print prompt for input string
	ldr r0, =strFmt                                                       // Load r0 with format for string
	ldr r1, =string                                                       // Load r1 with input string variable
	bl scanf                                                              // Listen for user input
	
	ldr r0, =startPrompt                                                  // Load r0 with prompt for start index
	bl printf                                                             // Print prompt for start index
	ldr r0, =intFmt                                                       // Load r0 with format for integer
	ldr r1, =start                                                        // Load r1 with start index variable
	bl scanf                                                              // Listen for user input

	ldr r0, =endPrompt                                                    // Load r0 with prompt for end index
	bl printf                                                             // Print prompt for end index
	ldr r0, =intFmt                                                       // Load r0 with format for integer
	ldr r1, =end                                                          // Load r1 with end index variable
	bl scanf                                                              // Listen for user input

	ldr r0, =string                                                       // Load r0 with input string variable
	ldr r1, =start                                                        // Load r1 with start index
	ldr r1, [r1]                                                          // Deference address to get value
	ldr r2, =end                                                          // Load r2 with end index
	ldr r2, [r2]                                                          // Deference address to get value
	bl sub_string                                                         // Find substring

	mov r1, r0                                                            // Move substring to r1
	ldr r0, =outputPrompt                                                 // Load r0 with output for substring
	bl printf                                                             // Print output for substring

	pop {fp, pc}                                                          // Pop fp and pc
