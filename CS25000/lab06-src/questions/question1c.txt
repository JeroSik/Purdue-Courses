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
	.file	"explicit.c"
	.section	.rodata
	.align	2
.LC0:
	.ascii	"%d\012%d\012%d\012%d\012\000"
	.text
	.align	2
	.global	main
	.syntax unified
	.arm
	.fpu vfp
	.type	main, %function
main:
	@ args = 0, pretend = 0, frame = 24
	@ frame_needed = 1, uses_anonymous_args = 0
	push	{fp, lr}
	add	fp, sp, #4
	sub	sp, sp, #32
	mov	r3, #11
	str	r3, [fp, #-8]
	mov	r3, #13
	str	r3, [fp, #-12]
	ldr	r3, [fp, #-8]
	ldr	r2, [fp, #-12]
	mul	r3, r2, r3
	str	r3, [fp, #-16]
	ldr	r2, [fp, #-8]
	mov	r3, r2
	lsl	r3, r3, #1
	add	r3, r3, r2
	lsl	r3, r3, #2
	add	r3, r3, r2
	str	r3, [fp, #-20]
	ldr	r2, [fp, #-12]
	mov	r3, r2
	lsl	r3, r3, #2
	add	r3, r3, r2
	lsl	r3, r3, #1
	add	r3, r3, r2
	str	r3, [fp, #-24]
	mov	r3, #143
	str	r3, [fp, #-28]
	ldr	r3, [fp, #-28]
	str	r3, [sp]
	ldr	r3, [fp, #-24]
	ldr	r2, [fp, #-20]
	ldr	r1, [fp, #-16]
	ldr	r0, .L3
	bl	printf
	mov	r3, #0
	mov	r0, r3
	sub	sp, fp, #4
	@ sp needed
	pop	{fp, pc}
.L4:
	.align	2
.L3:
	.word	.LC0
	.size	main, .-main
	.ident	"GCC: (Raspbian 6.3.0-18+rpi1+deb9u1) 6.3.0 20170516"
	.section	.note.GNU-stack,"",%progbits
