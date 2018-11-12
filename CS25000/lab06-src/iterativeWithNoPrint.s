	.file	"iterative.c"
	.text
	.section	.text.startup,"ax",@progbits
	.globl	main
	.type	main, @function
main:
.LFB13:
	.cfi_startproc
	xorl	%eax, %eax
	ret
	.cfi_endproc
.LFE13:
	.size	main, .-main
	.ident	"GCC: (Ubuntu 7.3.0-16ubuntu3) 7.3.0"
	.section	.note.GNU-stack,"",@progbits
