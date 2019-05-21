# Lab 4 - Introduction to Threads
Youngsik Yoon
yoon161@purdue.edu
0029846135

## Part 2: Thread creation
### Step 3
The program thr1 constantly outputs the characters 'A', 'B', and 'C' because the 'printC()' method is an infinite loop.

### Step 5
The program thr2 will only output the character 'C' because printC("C") is called before the threads for the other two characters start.

## Part 4: Spin locks
### Step 4
+-----------------------------------------+----------------------+-----------+-----------+
|                                         | System (Kernel) Time | User Time | Real Time |
+-----------------------------------------+----------------------+-----------+-----------+
| pthread_mutex(count)                    | 0m1.357s             | 0m1.784s  | 0m1.654s  |
+-----------------------------------------+----------------------+-----------+-----------+
| spin_lock(count_spin with thr_yield)    | 0m0.276s             | 0m0.576s  | 0m0.430s  |
+-----------------------------------------+----------------------+-----------+-----------+
| spin_lock(count_spin without thr_yield) | 0m0.000s             | 0m2.790s  | 0m1.553s  |
+-----------------------------------------+----------------------+-----------+-----------+

### Question 1
The program count_spin with 'thr_yield()' will yield the exeuction of the CPU, so the overhead in spin locks decrease. This causes the thread to run faster than without 'thr_yield()'. 

### Question 2
Since the program count uses mutex locks, the program is only available in Kernel mode as well as only used in kernel programming. On the other hand, the program count_spin with thr_yield use spin locks which are implemented in the user space, but by using thr_yield the overhead in spinlocks decreases to give the CPU.

## Part 6: Buffer overflow
### Correct little endian bytes
0xefbeadde

### Code to execute the exploit
./buffer $(printf "012345678901234\xEF\xBE\xAD\xDE")

### How/Why the code works
The 'strcpy()' implementation in C does not protect against overflows, so if the argument for program buffer is greater than 15 characters, something else in the memory will be overwritten. In this case, the 'set_me' integer is overwritten. 'set_me' is only overwritten because the machine is a 32-bit little endian computer. 'set_me' is declared as an integer, which is 4 bytes, before 'buf', so byte 16, 17, 18, and 19 will override the bytes in set_me.
