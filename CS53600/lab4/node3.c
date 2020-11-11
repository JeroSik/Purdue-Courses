#include <stdio.h>
#include <stdbool.h>

extern struct rtpkt {
  int sourceid;       /* id of sending router sending this pkt */
  int destid;         /* id of router to which pkt being sent 
                         (must be an immediate neighbor) */
  int mincost[4];    /* min cost to node 0 ... 3 */
  };

extern int TRACE;
extern int YES;
extern int NO;
extern float clocktime;

struct distance_table 
{
  int costs[4][4];
} dt3;

/* students to write the following two routines, and maybe some others */

// Store cost from Node 3 to directly attached neighbors
int link_costs3[4] = {9, 999, 4, 0};
// Store whether or not Node i is a direct neighbor of Node 3
bool isNeighbor3[4] = {true, false, true, false};

void rtinit3() {
    // Initialize distance table
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            // Check if node i is a direct neighbor
            if (isNeighbor3[i] && i == j) {
                dt3.costs[i][j] = link_costs3[i];
            // Otherwise, set initial distance as infinity
            } else {
                dt3.costs[i][j] = 999;
            }
        }
    }

    // Print initial node 3 distance table
    printf("    [rtinit3] t=%.3f: Initializing Node 3\n", clocktime);
    printdt3(&dt3);
    printf("\n");

    // Initialize packet containing minimum cost distance vector
    struct rtpkt packet;
    packet.sourceid = 3;
    for (int i = 0; i < 4; i++) {
        packet.mincost[i] = link_costs3[i];
    }

    // Send packet to neighbors
    send2neighbor(packet, isNeighbor3);
}

void rtupdate3(struct rtpkt *rcvdpkt) {
    // Print message that packet has been received
    printf("    [rtupdate3] t=%.3f: Node 3 received update packet from Node %d\n", clocktime, rcvdpkt->sourceid);

    // Initialize flag to track if changes were made
    bool hasChanged = false;

    // Check new path cost for each node
    for (int i = 0; i < 4; i++) {
        // Skip current node
        if (i == 3) {
            continue;
        }

        // Calculate new path cost for node i [c(x,v) + Dv(y)]
        int new_path_cost = link_costs3[rcvdpkt->sourceid] + rcvdpkt->mincost[i];

        // Check if the new path cost is smaller than the current path cost
        if (new_path_cost < dt3.costs[i][rcvdpkt->sourceid]) {
            dt3.costs[i][rcvdpkt->sourceid] = new_path_cost;
            hasChanged = true;
        }
    }

    // Check if distance vector for node 3 has changed
    if (hasChanged) {
        // Print node 3 distance table
        printf("        Table Updated: True\n");
        printdt3(&dt3);
        printf("\n");

        // Initialize packet containing minimum cost distance vector
        struct rtpkt packet;
        packet.sourceid = 3;
        for (int i = 0; i < 4; i++) {
            packet.mincost[i] = 999;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                packet.mincost[i] = min(packet.mincost[i], dt3.costs[i][j]);
            }
        }
        packet.mincost[3] = 0;

        // Send packet to neighbors
        send2neighbor(packet, isNeighbor3);
    } else {
        // Print that table has not updated
        printf("        Table Updated: False\n\n");
    }
}

printdt3(dtptr)
  struct distance_table *dtptr;
  
{
  printf("             via     \n");
  printf("   D3 |    0     2 \n");
  printf("  ----|-----------\n");
  printf("     0|  %3d   %3d\n",dtptr->costs[0][0], dtptr->costs[0][2]);
  printf("dest 1|  %3d   %3d\n",dtptr->costs[1][0], dtptr->costs[1][2]);
  printf("     2|  %3d   %3d\n",dtptr->costs[2][0], dtptr->costs[2][2]);

}







