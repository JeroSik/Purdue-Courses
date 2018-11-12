.section .text			// Assemble statements into text section

.global insertionSort		// Make "fibonacci" visible to linker
insertionSort:			// Label for the start of program
	push {r4-r7, fp, lr}

	cmp r1, #1		// Check if array length <= 1
	ble .end		// Return input if true

	mov r2, #0		// Initialize outer loop counter
	mov r3, #0		// Initialize inner loop counter
	
	b .loopOuter		// Branch to outer loop

.loopOuter:			// Label for outer loop
	cmp r2, r1		// Check if outer loop counter >= array length
	bge .end		// Return sorted array if true

	mov r3, r2		// Set inner loop counter to outer loop counter
	b .loopInner		// Branch to inner loop

.loopInner:			// Label for inner loop
	cmp r3, #0		// Check if inner loop counter <= 0
	ble .incrementOuter	// Increment outer loop counter if true
	
	lsl r4, r3, #2		// Get index of array
	ldr r5, [r0, r4]	// Get value at index of array
	add r4, r4, #-4		// Get previous index of array
	ldr r6, [r0, r4]	// Get value at index of array

	cmp r5, r6		// Checks if current < previous
	blt .swap		// Swap values if true

	add r3, r3, #-1		// Decrement inner loop counter
	
	b .loopInner		// Branch to inner loop

.incrementOuter:		// Label for incrementing outer loop counter
	add r2, r2, #1		// Increment outer loop counter
	b .loopOuter		// Branch to outer loop

.swap:				// Label for swapping values
	str r5, [r0, r4]	// Set current value to previous value
	add r4, r4, #4		// Get index of current value
	str r6, [r0, r4]	// Set previous value to curent value
	
	add r3, r3, #-1		// Decrement inner loop counter
	
	b .loopInner		// Branch to inner loop

.end:
	pop {r4-r7, fp, lr}	// Restore r4-r7, fp, and lr for graceful return
