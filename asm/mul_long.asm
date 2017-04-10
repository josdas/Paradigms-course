%include "io64.inc"


section .text
    global CMAIN
CMAIN:
                mov             rbp, rsp; for correct debugging
                
                sub             rsp, 3 * 128 * 8
                lea             rdi, [rsp + 128 * 8]
                mov             rcx, 128
                call            read_long
                mov             rdi, rsp
                call            read_long
                lea             rsi, [rsp + 128 * 8]
                lea             rbx, [rsp + 128 * 8 * 2]
                
                call            mul_long_long

                mov             rdi, rbx
                call            write_long

                mov             al, 0x0a ; '\n'
                call            write_char
                
                add             rsp, 3 * 128 * 8
                xor             rax, rax
                ret

; mul two long number
;    rdi -- address of #1 (long number)
;    rsi -- address of #2 (long number)
;    rbx -- result
;    rcx -- length of long numbers in qwords
; result:
;    mul is written to rbx
mul_long_long:
                push            rdi
                mov             rdi, rbx
                call            set_zero
                pop             rdi
                
                push            r10
                push            r11
                mov             r10, 0
.loop1:
                mov             r11, 0
                
                sub             rsp, 2 * 128 * 8
                lea             r14, [rsp]
                lea             r15, [rsp + 128 * 8] 
                
                push            rdi
                mov             rdi, r14
                call            set_zero
                pop             rdi
                
                push            rdi
                mov             rdi, r15
                call            set_zero
                pop             rdi
                .loop2:
                                lea             r12, [r11 + r10]
                                cmp             r12, 126
                                je             .break
                                
                                push            rdi
                                push            rsi
                                mov             rdi, [rdi + r10 * 8]
                                mov             rsi, [rsi + r11 * 8]
                                call            mul_64_64
                                pop             rsi
                                pop             rdi
                                
                                mov             [r14 + 8 * r12], r8
                                mov             [r15 + 8 * r12 + 8], r9
                                
                                inc             r11
                                cmp             r11, 126
                                jne             .loop2        
                .break:
                push            rdi
                push            rsi
                mov             rdi, rbx
                mov             rsi, r14
                call            add_long_long
                pop             rsi
                pop             rdi
                
                push            rdi
                push            rsi
                mov             rdi, rbx
                mov             rsi, r15
                call            add_long_long
                pop             rsi
                pop             rdi
                
                add             rsp, 2 * 128 * 8
                inc             r10
                cmp             r10, 126
                jne              .loop1
                
                pop             r11
                pop             r10
                ret             
                
             
; mul two long number
;    rdi -- #1 (long number)
;    rsi -- #2 (long number)
; result:
;    mul is written to r8, r9
mul_64_64:
                push            r10
                push            r11
                push            rax
                
                xor             r8, r8
                xor             r9, r9
                
                
                mov             rax, rdi
                shr             rax, 32
                mov             r10, rsi
                shr             r10, 32
                mul             r10
                add             r9, rax
                
                
                mov             rax, rdi
                shl             rax, 32
                shr             rax, 32
                mov             r10, rsi
                shr             r10, 32
                mul             r10
                mov             r11, rax;
                shr             r11, 32
                add             r9, r11
                mov             r11, rax;
                shl             r11, 32
                add             r8, r11
                adc             r9, 0
                
                
                mov             rax, rdi
                shr             rax, 32
                mov             r10, rsi
                shl             r10, 32
                shr             r10, 32
                mul             r10
                mov             r11, rax;
                shr             r11, 32
                add             r9, r11
                mov             r11, rax;
                shl             r11, 32
                add             r8, r11
                adc             r9, 0
                
                
                mov             rax, rdi
                shl             rax, 32
                shr             rax, 32
                mov             r10, rsi
                shl             r10, 32
                shr             r10, 32
                mul             r10
                add             r8, rax
                adc             r9, 0
                
                
                pop             rax
                pop             r11
                pop             r10
                ret             
                
                
                
                
; subs two long number
;    rdi -- address of #1 (long number)
;    rsi -- address of #2 (long number)
;    rcx -- length of long numbers in qwords
; result:
;    sub is written to rdi
sub_long_long:
                push            rdi
                push            rsi
                push            rcx

                clc
.loop:
                mov             rax, [rsi]
                lea             rsi, [rsi + 8]
                sbb             [rdi], rax
                lea             rdi, [rdi + 8]
                dec             rcx
                jnz             .loop

                pop             rcx
                pop             rsi
                pop             rdi
                ret
                
; adds two long number
;    rdi -- address of summand #1 (long number)
;    rsi -- address of summand #2 (long number)
;    rcx -- length of long numbers in qwords
; result:
;    sum is written to rdi
add_long_long:
                push            rdi
                push            rsi
                push            rcx

                clc
.loop:
                mov             rax, [rsi]
                lea             rsi, [rsi + 8]
                adc             [rdi], rax
                lea             rdi, [rdi + 8]
                dec             rcx
                jnz             .loop

                pop             rcx
                pop             rsi
                pop             rdi
                ret
                
; adds 64-bit number to long number
;    rdi -- address of summand #1 (long number)
;    rax -- summand #2 (64-bit unsigned)
;    rcx -- length of long number in qwords
; result:
;    sum is written to rdi
add_long_short:
                push            rdi
                push            rcx
                push            rdx

                xor             rdx, rdx
.loop:
                add             [rdi], rax
                adc             rdx, 0
                mov             rax, rdx
                xor             rdx, rdx
                add             rdi, 8
                dec             rcx
                jnz             .loop

                pop             rdx
                pop             rcx
                pop             rdi
                ret

; inverse long number
;    rdi -- address of #1 (long number)
;    rcx -- length of long number in qwords
; result:
;    inv is written to rdi
inv_long:
                push            rdi
                push            rcx
                push            r8

.loop:
                mov             r8, [rdi]
                not             r8
                mov             [rdi], r8
                add             rdi, 8
                dec             rcx
                jnz             .loop

                pop             r8
                pop             rcx
                pop             rdi
                ret

; multiplies long number by a short
;    rdi -- address of multiplier #1 (long number)
;     -- multiplier #2 (64-bit unsigned)
;    rcx -- length of long number in qwords
; result:
;    product is written to rdi
mul_long_short:
                push            rax
                push            rdi
                push            rcx

                xor             rsi, rsi
.loop:
                mov             rax, [rdi]
                mul             rbx
                add             rax, rsi
                adc             rdx, 0
                mov             [rdi], rax
                add             rdi, 8
                mov             rsi, rdx
                dec             rcx
                jnz             .loop

                pop             rcx
                pop             rdi
                pop             rax
                ret

; divides long number by a short
;    rdi -- address of dividend (long number)
;    r -- divisor (64-bit unsigned)
;    rcx -- length of long number in qwords
; result:
;    quotient is written to rdi
;    rdx -- remainder
div_long_short:
                push            rdi
                push            rax
                push            rcx

                lea             rdi, [rdi + 8 * rcx - 8]
                xor             rdx, rdx

.loop:
                mov             rax, [rdi]
                div             rbx
                mov             [rdi], rax
                sub             rdi, 8
                dec             rcx
                jnz             .loop

                pop             rcx
                pop             rax
                pop             rdi
                ret

; assigns a zero to long number
;    rdi -- argument (long number)
;    rcx -- length of long number in qwords
set_zero:
                push            rax
                push            rdi
                push            rcx

                xor             rax, rax
                rep stosq

                pop             rcx
                pop             rdi
                pop             rax
                ret

; checks if a long number is a zero
;    rdi -- argument (long number)
;    rcx -- length of long number in qwords
; result:
;    ZF=1 if zero
is_zero:
                push            rax
                push            rdi
                push            rcx

                xor             rax, rax
                rep scasq

                pop             rcx
                pop             rdi
                pop             rax
                ret

; read long number from stdin
;    rdi -- location for output (long number)
;    rcx -- length of long number in qwords
read_long:
                push            rcx
                push            rdi

                call            set_zero
.loop:
                call            read_char
                or              rax, rax
                js              exit
                cmp             rax, 0x0a
                je              .done
                cmp             rax, '0'
                jb              .invalid_char
                cmp             rax, '9'
                ja              .invalid_char

                sub             rax, '0'
                mov             rbx, 10
                call            mul_long_short
                call            add_long_short
                jmp             .loop

.done:
                pop             rdi
                pop             rcx
                ret

.invalid_char:
                mov             rsi, invalid_char_msg
                mov             rdx, invalid_char_msg_size
                call            print_string
                call            write_char
                mov             al, 0x0a
                call            write_char

.skip_loop:
                call            read_char
                or              rax, rax
                js              exit
                cmp             rax, 0x0a
                je              exit
                jmp             .skip_loop
                
                

; write long number to stdout
;    rdi -- argument (long number)
;    rcx -- length of long number in qwords
write_long:
                push            rax
                push            r8
                
                mov             r8, [rdi + rcx * 8 - 8]
                cmp             r8, 0xffffffffffffffff ;flag = -1
                mov             r8, 1
                
                jnz             .minus
                mov             r8, 0
                mov             al, '-'
                call            write_char
                
                mov             rax, 1
                call            inv_long
                call            add_long_short
.minus:         
                call            write_unslong
                
                pop             r8
                pop             rax
                ret

; write unlong number to stdout
;    rdi -- argument (unlong number)
;    rcx -- length of unlong number in qwords
write_unslong:
                push            rax
                push            rcx

                mov             rax, 20
                mul             rcx
                mov             rbp, rsp
                sub             rsp, rax

                mov             rsi, rbp

.loop:
                mov             rbx, 10
                call            div_long_short
                add             rdx, '0'
                dec             rsi
                mov             [rsi], dl
                call            is_zero
                jnz             .loop

                mov             rdx, rbp
                sub             rdx, rsi
                call            print_string

                mov             rsp, rbp
                pop             rcx
                pop             rax
                ret
                
; read one char from stdin
; result:
;    rax == -1 if error occurs
;    rax \in [0; 255] if OK
read_char:
                GET_CHAR        rax
                ret

; write one char to stdout, errors are ignored
;    al -- char
write_char:
                PRINT_CHAR      al
                ret
                
exit:
                mov             rax, 60
                xor             rdi, rdi
                syscall
; print string to stdout
;    rsi -- string
;    rdx -- size
print_string:
                push             r8
                push             rax
                push             rsi
                
                mov              r8, rdx
.loop1:
                
                mov              al, [rsi]
                call             write_char
                dec              r8
                inc              rsi
                cmp              r8, 0
                JNE              .loop1
                
                pop              rsi
                pop              rax
                pop              r8
                ret


                section         .rodata
invalid_char_msg:
                db              "Invalid character: "
invalid_char_msg_size: equ             $ - invalid_char_msg
