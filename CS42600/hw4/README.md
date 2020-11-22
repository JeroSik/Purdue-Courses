# Homework 4: Packet Sniffing and Spoofing
CS 426 - Fall 2020
Instructor: Professor Aniket Kate
Due: Thursday, November 19
Two late days used

## Youngsik Yoon - yoon161@purdue.edu - 0029846135

### 1.b
- Capture the ICMP packets between two specific hosts.
VM1:
`gcc -Wall -o task_1_b_i task_1_b_i.c -lpcap`
`sudo ./task_1_b_i`
VM2:
`ping 192.168.15.4`

- Capture the TCP packets that have a destination port range from to port 20 - 80.
VM1:
`gcc -Wall -o task_1_b_ii task_1_b_ii.c -lpcap`
`sudo ./task_1_b_ii`
VM2:
`nmap -p 40 google.com`

### 1.c
Set up telnet on VM2:
`telnet 192.168.15.4`

Compile and execute sniffer on VM1:
`gcc -Wall -o task_1_c task_1_c.c -lpcap`
`sudo ./task_1_b_c`

Input username and password on VM2.

### 2.a
Packet spoofing program. Program will always send packet to google.com (8.8.8.8).

Compile:
`gcc -Wall -o task_2 task_2.c -lpcap`
Usage:
`sudo ./task_2 [mode] [source_ip]`
- `[mode]`: 0 represents program will spoof an ICMP echo request packet, and 1 represents program will spoof an ethernet frame.
- `[source_ip]`: IP address of source. Destination will always be google.com (8.8.8.8).

### 2.b
Set up tcpdump on VM2:
`sudo tcpdump -XX icmp`
Compile and execute spoofer on VM1:
`gcc -Wall -o task_2 task_2.c -lpcap`
`sudo ./task_2 0 192.168.5.4`

### 2.c
Set up tcpdump on VM2:
`sudo tcpdump -eXX ether host aa:bb:cc:dd:ee:ff`
Compile and execute spoofer on VM1:
`gcc -Wall -o task_2 task_2.c -lpcap`
`sudo ./task_2 1 192.168.5.4`

### 3
`gcc -Wall -o task_3 task_3.c -lpcap`