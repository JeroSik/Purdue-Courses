#include <stdio.h>
#include <stdbool.h>

extern struct rtpkt {
    int sourceid;       /* id of sending router sending this pkt */
    int destid;         /* id of router to which pkt being sent 
                            (must be an immediate neighbor) */
    int mincost[4];     /* min cost to node 0 ... 3 */
};

/********************************************************************
 * This file contains helper functions
 *  - void send2neighbor(struct rtpkt packet)
 *  - int min(int a, int b)
 *********************************************************************/
// Calls tolayer2 to send packet to neighbors
void send2neighbor(struct rtpkt packet, bool isNeighbor[4]) {
    // Print recipients of packet
    printf("        Sending update to neighbors:");
    for (int i = 0; i < 4; i++) {
        if (isNeighbor[i]) {
            printf(" %d", i);
        }
    }
    printf("\n");

    // Print distance vector from packet
    printf("        Distance vector:");
    for (int i = 0; i < 4; i++) {
        printf(" %d", packet.mincost[i]);
    }
    printf("\n\n");

    // Send packet to all neighbors
    for (int i = 0; i < 4; i++) {
        if (isNeighbor[i]) {
            packet.destid = i;
            tolayer2(packet);
        }
    }
}

// Define helper function to find minimum between two integers
int min(int a, int b) {
    return a < b ? a : b;
}