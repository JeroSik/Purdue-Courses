// Declare data section and byte alignment for 32-bit machine
.section .data
.balign 4

// Declare text section and create branch s_divide_by_10
.section .text
u_divide_by_10:
   /* r0 contains the argument to be divided by 10 */
   ldr r1, .Lu_magic_number_10 /* r1 ← magic_number */
   umull r1, r2, r1, r0   /* r1 ← Lower32Bits(r1*r0). r2 ← Upper32Bits(r1*r0) */
   mov r0, r2, LSR #3     /* r0 ← r2 >> 3 */
   bx lr                  /* leave function */
   .align 4
   .Lu_magic_number_10: .word 0xcccccccd

// Make printx and printd visidible to linker
.global printx
.global printd

/**
 *  printx(int, int);
 *  @parameters
 *    int - integer to print
 *    int - flag to mark '%x' or '%X'
 *  @function
 *    Add the right most 4 bits to the stack
 *    Shift the bit string right by 4 bits
 *    Repeat until finished
 *    Print out stack
 */
printx:
  push {r4-r9, fp, lr}      // Push appropriate registers
  mov fp, sp                // Update fp to sp
  mov r4, #0                // Initialize stack size counter
  mov r5, #0                // Initialize negative loop counter

  cmp r0, #0                // Check if integer is negative
  blt .negativeHex          // If true, branch to .negativeHex

.positiveHex:
  sub sp, sp, #4            // Create room in stack
  add r4, r4, #1            // Increment stack size
  and r3, r0, #0x000F       // Get first 4 LSB

  cmp r3, #10               // Check if digit is greater than 10
  bge .pushLower            // If true, branch to .pushLower
  add r3, r3, #48           // Get correct ASCII value
  b .pushHexASCII           // Branch to .pushHexASCII

.negativeHex:
  cmp r5, #8                // Check if looped 8 times
  beq .printHex             // If true, branch to .printNum
  add r5, r5, #1            // Increase loop counter

  b .positiveHex            // Treat number normally

.pushLower:
  cmp r1, #1                // Check is %x or %X
  beq .pushUpper            // If %X, branch to .pushUpper

  add r3, r3, #87           // Get correct ASCII value
  b .pushHexASCII           // Branch to .pushHexASCII

.pushUpper:
  add r3, r3, #55           // Get correct ASCII value
  b .pushHexASCII           // Branch to .pushHexASCII

.pushHexASCII:
  str r3, [sp]              // Pop from stack
  lsr r0, r0, #4            // Right shift input by 4 bits

  cmp r0, #0                // Check if input is 0
  beq .printHex             // If true, branch to .printNum

  cmp r0, #0                // Check if integer is negative
  blt .negativeHex          // If true, branch to .negativeHex
  b .positiveHex            // If false, branch to .positiveHex

.printHex:
  cmp r4, #0                // Check if stack is empty
  beq .end                  // If true, branch to .end

  ldr r0, [sp]              // Pop from stack
  bl putchar                // putchar correct ASCII character
  add sp, sp, #4            // Move up the stack

  sub r4, r4, #1            // Decrement stack counter
  b .printHex               // Branch to .printNum

.end:
  pop {r4-r9, fp, pc}       // Pop r4-r9, fp, and pc

/**
 *  printd(int);
 *  @parameters
 *    int - integer to print
 *  @function
 *    Takes floor of integer and multiply by 10
 *    Subtract from integer and push to stack
 *    Floor of integer is new integer
 *    Repeat until finished
 *    Print out stack
 */
printd:
  push {r4-r9, fp, lr}    // Push appropriate registers
  mov fp, sp              // Update fp to sp
  mov r4, #0              // Initialize stack size counter
  mov r5, r0              // Move input to r5
  mov r6, #0              // Initialize multiplication counter

  cmp r0, #0              // Check if integer is negative or 0
  blt .negativeDeci       // If negative, branch to .negativeDeci
  beq .isZero             // If zero, branch to .isZero

.positiveDeci:
  cmp r5, #0              // Check if input is 0
  beq .printDeci          // If true, branch to .printDeci

  bl u_divide_by_10       // Get floor of input divided by 10

  mov r7, r0              // Move result to r7
  mov r3, #0              // Reset temp variable

  mov r6, #0              // Reset multiplication counter
  b .multiplyBy10         // Branch to .multiplyBy10
  
.plusConvert:
  sub r3, r5, r3          // Get integer value
  add r3, r3, #48         // Get correct ASCII value
  
  sub sp, sp, #4          // Create room in stack
  str r3, [sp]            // Push value to stack
  add r4, r4, #1          // Increment stack size counter

  mov r5, r0              // Move truncated integer as input
  b .positiveDeci         // Branch to .positiveDeci

.negativeDeci:
  mov r0, #45             // Get correct ASCII value
  bl putchar              // Print "-"

  neg r5, r5              // Flip input to positive
  mov r0, r5              // Move input back to r0
  
  b .positiveDeci         // Branch to .positiveDeci

.multiplyBy10:          
  cmp r6, #10             // Check if multiplication counter is at 10
  beq .plusConvert        // If true, branch to .plusConvert

  add r3, r3, r7          // Add temp variable to itself

  add r6, r6, #1          // Increment multiplication counter
  bl .multiplyBy10        // Branch to .multiplyBy10

.printDeci:
  cmp r4, #0              // Check if stack is empty
  beq .exit               // If true, branch to .exit

  ldr r0, [sp]            // Pop from stack
  bl putchar              // putchar correct ASCII character
  add sp, sp, #4          // Move up the stack

  sub r4, r4, #1          // Decrement stack size counter
  b .printDeci            // Branch to .printDeci

.isZero:
  mov r0, #48             // Get correct ASCII value
  bl putchar              // Print 0
  b .exit                 // Branch to .exit

.exit:
  pop {r4-r9, fp, pc}     // Pop r4-r9, fp, and pc
