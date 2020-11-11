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
} dt2;


/* students to write the following two routines, and maybe some others */

// Store cost from Node 2 to directly attached neighbors
int link_costs2[4] = {3, 1, 0, 4};
// Store whether or not Node i is a direct neighbor of Node 2
bool isNeighbor2[4] = {true, true, false, true};

void rtinit2() {
    // Initialize distance table
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            // Check if node i is a direct neighbor
            if (isNeighbor2[i] && i == j) {
                dt2.costs[i][j] = link_costs2[i];
            // Otherwise, set initial distance as infinity
            } else {
                dt2.costs[i][j] = 999;
            }
        }
    }

    // Print initial node 2 distance table
    printf("    [rtinit2] t=%.3f: Initializing Node 2\n", clocktime);
    printdt2(&dt2);
    printf("\n");

    // Initialize packet containing minimum cost distance vector
    struct rtpkt packet;
    packet.sourceid = 2;
    for (int i = 0; i < 4; i++) {
        packet.mincost[i] = link_costs2[i];
    }

    // Send packet to neighbors
    send2neighbor(packet, isNeighbor2);
}

void rtupdate2(struct rtpkt *rcvdpkt) {
    // Print message that packet has been received
    printf("    [rtupdate2] t=%.3f: Node 2 received update packet from Node %d\n", clocktime, rcvdpkt->sourceid);

    // Initialize flag to track if changes were made
    bool hasChanged = false;

    // Check new path cost for each node
    for (int i = 0; i < 4; i++) {
        // Skip current node
        if (i == 2) {
            continue;
        }

        // Calculate new path cost for node i [c(x,v) + Dv(y)]
        int new_path_cost = link_costs2[rcvdpkt->sourceid] + rcvdpkt->mincost[i];

        // Check if the new path cost is smaller than the current path cost
        if (new_path_cost < dt2.costs[i][rcvdpkt->sourceid]) {
            dt2.costs[i][rcvdpkt->sourceid] = new_path_cost;
            hasChanged = true;
        }
    }

    // Check if distance vector for node 2 has changed
    if (hasChanged) {
        // Print node 2 distance table
        printf("        Table Updated: True\n");
        printdt2(&dt2);
        printf("\n");

        // Initialize packet containing minimum cost distance vector
        struct rtpkt packet;
        packet.sourceid = 2;
        for (int i = 0; i < 4; i++) {
            packet.mincost[i] = 999;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                packet.mincost[i] = min(packet.mincost[i], dt2.costs[i][j]);
            }
        }
        packet.mincost[2] = 0;

        // Send packet to neighbors
        send2neighbor(packet, isNeighbor2);
    } else {
        // Print that table has not updated
        printf("        Table Updated: False\n\n");
    }
}

printdt2(dtptr)
  struct distance_table *dtptr;
  
{
  printf("                via     \n");
  printf("   D2 |    0     1    3 \n");
  printf("  ----|-----------------\n");
  printf("     0|  %3d   %3d   %3d\n",dtptr->costs[0][0],
	 dtptr->costs[0][1],dtptr->costs[0][3]);
  printf("dest 1|  %3d   %3d   %3d\n",dtptr->costs[1][0],
	 dtptr->costs[1][1],dtptr->costs[1][3]);
  printf("     3|  %3d   %3d   %3d\n",dtptr->costs[3][0],
	 dtptr->costs[3][1],dtptr->costs[3][3]);
}







