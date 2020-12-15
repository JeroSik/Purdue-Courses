.section .data
text0: .asciz "%s"
text1: .asciz "%d"
text2: .asciz "\n"
text3: .asciz "true"
text4: .asciz "false"
text5: .asciz "Hello"
text6: .asciz "True"
text7: .asciz "hello"
text8: .asciz "cs352"

.section .text
.global main
.balign 4

main:
   push {lr}
   sub sp, sp, #140
   mov r2, #1
   sub r0, r0, r2
   str r0, [sp, #36]
   mov r0, #4
   add r1, r1, r0
   str r1, [sp, #40]
   ldr r0, word3
   str r0, [sp, #44]
   ldr r0, [sp, #44]
   mov r1, #4
   mul r1, r1, r0
   ldr r0, [sp, #40]
   add r0, r0, r1
   ldr r2, [r0, #0]
   str r2, [sp, #48]
   ldr r0, =text0
   ldr r1, [sp, #48]
   bl printf
   ldr r0, =text2
   bl printf
   ldr r0, word4
   str r0, [sp, #52]
   ldr r0, [sp, #52]
   mov r1, #4
   mul r1, r1, r0
   ldr r0, [sp, #40]
   add r0, r0, r1
   ldr r2, [r0, #0]
   str r2, [sp, #56]
   ldr r0, [sp, #56]
   bl atoi
   str r0, [sp, #60]
   ldr r0, =text1
   ldr r1, [sp, #60]
   bl printf
   ldr r0, =text2
   bl printf
   ldr r0, word5
   str r0, [sp, #64]
   ldr r0, [sp, #64]
   mov r1, #4
   mul r1, r1, r0
   ldr r0, [sp, #40]
   add r0, r0, r1
   ldr r2, [r0, #0]
   str r2, [sp, #68]
   ldr r0, [sp, #68]
   bl atoi
   str r0, [sp, #72]
   ldr r0, word7
   bl malloc
   str r0, [sp, #84]
   ldr r0, word6
   str r0, [sp, #76]
   ldr r0, [sp, #72]
   ldr r1, [sp, #76]
   add r0, r0, r1
   str r0, [sp, #80]
   ldr r1, [sp, #80]
   bl .haha
   str r0, [sp, #88]
   ldr r0, [sp, #88]
   ldr r1, [sp, #72]
   cmp r0, r1
   movle r0, #1
   movgt r0, #0
   str r0, [sp, #92]
   ldr r0, [sp, #88]
   ldr r1, [sp, #72]
   cmp r0, r1
   moveq r0, #1
   movne r0, #0
   str r0, [sp, #96]
   ldr r0, [sp, #92]
   ldr r1, [sp, #96]
   orr r0, r0, r1
   str r0, [sp, #100]
   ldr r0, [sp, #100]
   cmp r0, #1
   bne ._else1
   ldr r0, =text6
   str r0, [sp, #104]
   ldr r0, =text0
   ldr r1, [sp, #104]
   bl printf
   b ._endloop0
._loop0:
   ldr r0, =text7
   str r0, [sp, #108]
   ldr r0, =text0
   ldr r1, [sp, #108]
   bl printf
   ldr r0, =text2
   bl printf
   ldr r0, word8
   str r0, [sp, #112]
   ldr r0, [sp, #88]
   ldr r1, [sp, #112]
   add r0, r0, r1
   str r0, [sp, #116]
._endloop0:
   ldr r0, word9
   str r0, [sp, #120]
   ldr r0, [sp, #116]
   ldr r1, [sp, #120]
   cmp r0, r1
   movlt r0, #1
   movge r0, #0
   str r0, [sp, #124]
   ldr r0, [sp, #124]
   cmp r0, #1
   beq ._loop0
   b ._endif1
._else1:
   b ._endloop1
._loop1:
   ldr r0, =text8
   str r0, [sp, #128]
   ldr r0, =text0
   ldr r1, [sp, #128]
   bl printf
._endloop1:
   ldr r0, word10
   str r0, [sp, #132]
   ldr r0, [sp, #88]
   ldr r1, [sp, #132]
   cmp r0, r1
   movlt r0, #1
   movge r0, #0
   str r0, [sp, #136]
   ldr r0, [sp, #136]
   cmp r0, #1
   beq ._loop1
._endif1:
   add sp, sp, #140
   pop {pc}

word0: .word 10
word1: .word 100
word2: .word 1
word3: .word 0
word4: .word 1
word5: .word 2
word6: .word 1
word7: .word 0
word8: .word 2
word9: .word 12
word10: .word 13

.haha:
   push {lr}
   sub sp, sp, #32
   str r1, [sp, #0]
   ldr r0, word0
   str r0, [sp, #4]
   ldr r0, [sp, #0]
   ldr r1, [sp, #4]
   cmp r0, r1
   movgt r0, #1
   movle r0, #0
   str r0, [sp, #8]
   ldr r0, [sp, #8]
   cmp r0, #1
   bne ._else0
   ldr r0, word1
   str r0, [sp, #12]
   ldr r0, [sp, #12]
   b ._endif0
._else0:
   ldr r0, =text5
   str r0, [sp, #16]
   ldr r0, =text0
   ldr r1, [sp, #16]
   bl printf
   ldr r0, =text2
   bl printf
._endif0:
   ldr r0, word2
   str r0, [sp, #20]
   ldr r0, [sp, #0]
   ldr r1, [sp, #20]
   add r0, r0, r1
   str r0, [sp, #24]
   ldr r1, [sp, #24]
   bl .haha
   str r0, [sp, #28]
   ldr r0, [sp, #28]
   add sp, sp, #32
   pop {lr}
   bx lr
