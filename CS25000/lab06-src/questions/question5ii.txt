	.arch armv6
	.eabi_attribute 28, 1
	.eabi_attribute 20, 1
	.eabi_attribute 21, 1
	.eabi_attribute 23, 3
	.eabi_attribute 24, 1
	.eabi_attribute 25, 1
	.eabi_attribute 26, 2
	.eabi_attribute 30, 1
	.eabi_attribute 34, 1
	.eabi_attribute 18, 4
	.file	"recursive.c"
	.text
	.align	2
	.global	multiply
	.syntax unified
	.arm
	.fpu vfp
	.type	multiply, %function
multiply:
	@ args = 0, pretend = 0, frame = 0
	@ frame_needed = 0, uses_anonymous_args = 0
	cmp	r1, #2
	bgt	.L8
	lsl	r0, r0, #1
	bx	lr
.L8:
	push	{r4, lr}
	mov	r4, r0
	sub	r1, r1, #1
	bl	multiply
	add	r0, r4, r0
	pop	{r4, pc}
	.size	multiply, .-multiply
	.align	2
	.global	main
	.syntax unified
	.arm
	.fpu vfp
	.type	main, %function
main:
	@ args = 0, pretend = 0, frame = 0
	@ frame_needed = 0, uses_anonymous_args = 0
	push	{r4, lr}
	mov	r1, #13
	mov	r0, #11
	bl	multiply
	mov	r1, r0
	ldr	r0, .L11
	bl	printf
	mov	r0, #0
	pop	{r4, pc}
.L12:
	.align	2
.L11:
	.word	.LC0
	.size	main, .-main
	.section	.rodata.str1.4,"aMS",%progbits,1
	.align	2
.LC0:
	.ascii	"%d\012\000"
	.ident	"GCC: (Raspbian 6.3.0-18+rpi1+deb9u1) 6.3.0 20170516"
	.section	.note.GNU-stack,"",%progbits
