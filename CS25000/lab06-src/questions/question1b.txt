	.arch armv6
	.eabi_attribute 28, 1
	.eabi_attribute 20, 1
	.eabi_attribute 21, 1
	.eabi_attribute 23, 3
	.eabi_attribute 24, 1
	.eabi_attribute 25, 1
	.eabi_attribute 26, 2
	.eabi_attribute 30, 6
	.eabi_attribute 34, 1
	.eabi_attribute 18, 4
	.file	"recursive.c"
	.section	.rodata
	.align	2
.LC0:
	.ascii	"%d\012\000"
	.text
	.align	2
	.global	main
	.syntax unified
	.arm
	.fpu vfp
	.type	main, %function
main:
	@ args = 0, pretend = 0, frame = 0
	@ frame_needed = 1, uses_anonymous_args = 0
	push	{fp, lr}
	add	fp, sp, #4
	mov	r1, #13
	mov	r0, #11
	bl	multiply
	mov	r3, r0
	mov	r1, r3
	ldr	r0, .L3
	bl	printf
	mov	r3, #0
	mov	r0, r3
	pop	{fp, pc}
.L4:
	.align	2
.L3:
	.word	.LC0
	.size	main, .-main
	.align	2
	.global	multiply
	.syntax unified
	.arm
	.fpu vfp
	.type	multiply, %function
multiply:
	@ args = 0, pretend = 0, frame = 8
	@ frame_needed = 1, uses_anonymous_args = 0
	push	{fp, lr}
	add	fp, sp, #4
	sub	sp, sp, #8
	str	r0, [fp, #-8]
	str	r1, [fp, #-12]
	ldr	r3, [fp, #-12]
	cmp	r3, #2
	ble	.L6
	ldr	r3, [fp, #-12]
	sub	r3, r3, #1
	mov	r1, r3
	ldr	r0, [fp, #-8]
	bl	multiply
	mov	r2, r0
	ldr	r3, [fp, #-8]
	add	r3, r2, r3
	b	.L7
.L6:
	ldr	r2, [fp, #-8]
	ldr	r3, [fp, #-8]
	add	r3, r2, r3
.L7:
	mov	r0, r3
	sub	sp, fp, #4
	@ sp needed
	pop	{fp, pc}
	.size	multiply, .-multiply
	.ident	"GCC: (Raspbian 6.3.0-18+rpi1+deb9u1) 6.3.0 20170516"
	.section	.note.GNU-stack,"",%progbits
