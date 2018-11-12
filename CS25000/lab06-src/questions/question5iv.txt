	.arch armv6
	.eabi_attribute 28, 1
	.eabi_attribute 20, 1
	.eabi_attribute 21, 1
	.eabi_attribute 23, 3
	.eabi_attribute 24, 1
	.eabi_attribute 25, 1
	.eabi_attribute 26, 2
	.eabi_attribute 30, 2
	.eabi_attribute 34, 1
	.eabi_attribute 18, 4
	.file	"recursive.c"
	.section	.text.startup,"ax",%progbits
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
	mov	r1, #143
	ldr	r0, .L3
	bl	printf
	mov	r0, #0
	pop	{r4, pc}
.L4:
	.align	2
.L3:
	.word	.LC0
	.size	main, .-main
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
	@ link register save eliminated.
	cmp	r1, #2
	subgt	r1, r1, #2
	movle	r1, #0
	mulgt	r1, r0, r1
	add	r0, r1, r0, lsl #1
	bx	lr
	.size	multiply, .-multiply
	.section	.rodata.str1.4,"aMS",%progbits,1
	.align	2
.LC0:
	.ascii	"%d\012\000"
	.ident	"GCC: (Raspbian 6.3.0-18+rpi1+deb9u1) 6.3.0 20170516"
	.section	.note.GNU-stack,"",%progbits
