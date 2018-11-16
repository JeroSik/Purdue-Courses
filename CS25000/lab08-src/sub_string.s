.data                       // Declare data section
.balign 4                   // Byte alignment for 32-bit machine

out_string: .space 100      // Declare out_string variable

.section .text              // Declare section and text section

/*
 * Starts sub_string function
 * Initialize counters and out_string variable
 */
.global sub_string          // Make "sub_string" visible to linker
sub_string:                 // Label for the start of program
	push {r4-r6, fp, lr}      // Push registers r4-r4, fp, and lr
	ldr r3, =out_string       // Load r3 with out_string variable
	
	mov r4, r1			          // Initialize loop counter
	add r4, r4, #-1			      // Correct counter offset
	add r0,r0, r4			        // Pointer on start index for input string
	bl .loop                  // Start loop

/*
 * Loops through each character of input string
 * Places character into output string
 */
.loop:                      // Label for loop
	cmp r4, r2                // Check if loop counter >= end index
	bge .end                  // End loop if true

	ldrb r6, [r0]			        // Load r6 with char byte from input input
	strb r6, [r3]			        // Store out_string r6

	add r0, r0, #1			      // Increment pointer for input
	add r3, r3, #1			      // Increment pointer for output
	add r4, r4, #1			      // Increment loop counter

	bl .loop                  // Repeat loop

/*
 * Loads correct register with out_string
 */
.end:                       // Label for end
	ldr r0, =out_string       // Load r0 with out_string
	pop {r4-r6, fp, pc}       // Pop r4-r6, fp and pc
