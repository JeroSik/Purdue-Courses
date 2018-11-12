.section .text			// Assemble statements into text section

.global fibonacci		// Make "fibonacci" visible to linker
fibonacci:			// Label for the start of program
	push {r4, fp, lr}	// Push registers r4, fp, and lr
	
	cmp r0,	#1		// Check if input <= 1
	ble .end		// Return input if true

	mov r1, #1		// Initialize fibonacci number
	mov r2, #0	 	// Initialize previous number
	mov r3, #2		// Initialize loop counter

	b .loop			// Branch to loop

.loop:				// Label for iterative
	cmp r3, r0		// Check if loop counter >= input
	bge .end 		// Return fibonacci number if true
	
	mov r4, r1		// Initialize temp variable
	add r1, r1, r2		// Increment fibonacci number
	mov r2, r4		// Update previous number

	add r3, r3, #1		// Increment loop counter

	b .loop			// Branch back to the loop

.end:				// Label for end of program
	mov r0, r1		// Moves final number back to r0
	pop {r1, fp, pc}	// Restore r4, fp, and pc for graceful return
