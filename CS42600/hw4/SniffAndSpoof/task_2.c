#include <pcap.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/ioctl.h>
#include <linux/if_packet.h>
#include <linux/if_ether.h>
#include <net/if.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <netinet/ip_icmp.h>
#include <arpa/inet.h>

/*
 * List of definitions
 */
#define BUFFER_SIZE 256
#define GOOGLE_DNS "8.8.8.8"
#define DEFAULT_IF "eth14"

/*
 * Computes checksum of given packet.
 * (Modified algorithm from "The Regents of the University of California")
 */
unsigned short compute_checksum(unsigned short * packet, int len) {
	unsigned short checksum = 0;

	// Sum contents of packet
	while (len > 1) {
		checksum += *packet;
		len -= sizeof(unsigned short);

		packet++;
	}

	// Check if there odd number of bytes
	if (len == 1) {
		checksum += *(unsigned char *) packet;
	}

	checksum = (checksum >> 16) + (checksum * 0xffff);
	checksum += (checksum >> 16);

	return (unsigned short) (~checksum);
}

void spoof_icmp(const char * src_ip) {
	int sd;
	struct sockaddr_in sin;
	char buffer[BUFFER_SIZE];

	/*
     * (1) Create a raw socket with IP protocol.
	 */
	sd = socket(AF_INET, SOCK_RAW, IPPROTO_RAW);
	if (sd < 0) {
		perror("socket() error\n");
		exit(-1);
	}

	/*
	 * (2) Set socket option.
	 */
	sin.sin_family = AF_INET;
	
	/*
	 * (3) Construct the packet.
	 */
	// Set buffer to be all A's
	memset(buffer, 'A', sizeof(buffer));

	// Construct IP header
	struct iphdr * ip = (struct iphdr *) buffer;
	ip->ihl = 4 << 4 | 5;
	ip->version = 4;
	ip->tot_len = sizeof(struct iphdr) + sizeof(struct icmphdr);
	ip->frag_off = 0x0;
	ip->ttl = 64;
	ip->protocol = IPPROTO_ICMP;
	ip->saddr = inet_addr(src_ip);
	ip->daddr = inet_addr(GOOGLE_DNS);

	// Construct ICMP header
	struct icmphdr * icmp = (struct icmphdr *) (buffer + sizeof(struct iphdr));
	icmp->type = ICMP_ECHO;
	icmp->code = 0;
	icmp->checksum = compute_checksum((unsigned short *) icmp, sizeof(struct icmphdr));

	/*
	 * (4) Send out the IP packet through the raw socket.
	 */
	if (sendto(sd, buffer, ip->tot_len, 0, (struct sockaddr *) &sin, sizeof(sin)) < 0) {
		perror("sendto() error\n");
		exit(-1);
	}	
}

void spoof_ethernet(const char * src_ip) {
	int sd;
	struct sockaddr_ll sll;
	char buffer[BUFFER_SIZE];

	/*
         * (1) Create a raw socket with IP protocol.
	 */
	sd = socket(AF_PACKET, SOCK_RAW, htons(ETH_P_IP));
	if (sd < 0) {
		perror("socket() error\n");
		exit(-1);
	}

	/*
	 * (2) Set socket option.
	 */
	// Get index of the interface to send on
	struct ifreq if_idx;
	memset(&if_idx, 0, sizeof(struct ifreq));
	strncpy(if_idx.ifr_name, DEFAULT_IF, IFNAMSIZ - 1);
	if (ioctl(sd, SIOCGIFINDEX, &if_idx) < 0) {
		perror("SIOCGIFINDEX\n");
	}

	// Set options
	sll.sll_family = AF_PACKET;
	sll.sll_ifindex = if_idx.ifr_ifindex;
	sll.sll_halen = ETH_ALEN; //length of ethernet header
	
	/*
	 * (3) Construct the packet.
	 */
	// Define source and destination address
	unsigned char src_addr[] = {0xaa, 0xbb, 0xcc, 0xdd, 0xee, 0xff};
	unsigned char dest_addr[] = {0xff, 0xff, 0xff, 0xff, 0xff, 0xff};

	// Construct Ethernet header
	struct ethhdr *ethernet = (struct ethhdr *) buffer;
	memcpy(ethernet->h_source, src_addr,6);
	memcpy(ethernet->h_dest, dest_addr,6);
	ethernet->h_proto = htons(ETH_P_IP);

	// Construct IP header
	struct iphdr * ip = (struct iphdr *) (buffer + sizeof(struct ethhdr));
	ip->ihl = 4 << 4 | 5;
	ip->version = 4;
	ip->tot_len = sizeof(struct iphdr) + sizeof(struct icmphdr);
	ip->frag_off = 0x0;
	ip->ttl = 64;
	ip->protocol = IPPROTO_ICMP;
	ip->saddr = inet_addr(src_ip);
	ip->daddr = inet_addr(GOOGLE_DNS);

	// Construct ICMP header
	struct icmphdr * icmp = (struct icmphdr *) (buffer + sizeof(struct ethhdr) + sizeof(struct iphdr));
	icmp->type = ICMP_ECHO;
	icmp->code = 0;
	icmp->checksum = compute_checksum((unsigned short *) icmp, sizeof(struct icmphdr));

	/*
	 * (4) Send out the IP packet through the raw socket.
	 */
	if (sendto(sd, buffer, ip->tot_len, 0, (struct sockaddr *) &sll, sizeof(sll)) < 0) {
		perror("sendto() error\n");
		exit(-1);
	}	
}

int main(int argc, char const *argv[]) {
	// Check if correct numbner of arguments
	if (argc != 3) {
		printf("Incorrect number of arguments. Expected 3, actual %d.\n", argc);
	}

	// Get source
	const char * src = argv[2];

	// Check what to spoof
	int mode = atoi(argv[1]);
	if (mode == 0) {
		printf("----- ICMP -----\n");
		spoof_icmp(src);
	} else if (mode == 1) {
		printf("----- Ethernet -----\n");
		spoof_ethernet(src);
	} else {
		printf("Invalid value for mode.\n");
	}

	printf("\nSpoofing complete.\n");
	return 0;
}
