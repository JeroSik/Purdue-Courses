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
} dt1;


/* students to write the following two routines, and maybe some others */

// Store cost from Node 1 to directly attached neighbors
int link_costs1[4] = {1, 0, 1, 999};
// Store whether or not Node i is a direct neighbor of Node 1
bool isNeighbor1[4] = {true, false, true, false};

void rtinit1() {
    // Initialize distance table
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            // Check if node i is a direct neighbor
            if (isNeighbor1[i] && i == j) {
                dt1.costs[i][j] = link_costs1[i];
            // Otherwise, set initial distance as infinity
            } else {
                dt1.costs[i][j] = 999;
            }
        }
    }

    // Print initial node 1 distance table
    printf("    [rtinit1] t=%.3f: Initializing Node 1\n", clocktime);
    printdt1(&dt1);
    printf("\n");

    // Initialize packet containing minimum cost distance vector
    struct rtpkt packet;
    packet.sourceid = 1;
    for (int i = 0; i < 4; i++) {
        packet.mincost[i] = link_costs1[i];
    }

    // Send packet to neighbors
    send2neighbor(packet, isNeighbor1);
}

void rtupdate1(struct rtpkt *rcvdpkt) {
    // Print message that packet has been received
    printf("    [rtupdate1] t=%.3f: Node 1 received update packet from Node %d\n", clocktime, rcvdpkt->sourceid);

    // Initialize flag to track if changes were made
    bool hasChanged = false;

    // Check new path cost for each node
    for (int i = 0; i < 4; i++) {
        // Skip current node
        if (i == 1) {
            continue;
        }

        // Calculate new path cost for node i [c(x,v) + Dv(y)]
        int new_path_cost = link_costs1[rcvdpkt->sourceid] + rcvdpkt->mincost[i];

        // Check if the new path cost is smaller than the current path cost
        if (new_path_cost < dt1.costs[i][rcvdpkt->sourceid]) {
            dt1.costs[i][rcvdpkt->sourceid] = new_path_cost;
            hasChanged = true;
        }
    }

    // Check if distance vector for node 1 has changed
    if (hasChanged) {
        // Print node 1 distance table
        printf("        Table Updated: True\n");
        printdt1(&dt1);
        printf("\n");

        // Initialize packet containing minimum cost distance vector
        struct rtpkt packet;
        packet.sourceid = 1;
        for (int i = 0; i < 4; i++) {
            packet.mincost[i] = 999;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                packet.mincost[i] = min(packet.mincost[i], dt1.costs[i][j]);
            }
        }
        packet.mincost[1] = 0;

        // Send packet to neighbors
        send2neighbor(packet, isNeighbor1);
    } else {
        // Print that table has not updated
        printf("        Table Updated: False\n\n");
    }
}


printdt1(dtptr)
  struct distance_table *dtptr;
  
{
  printf("             via   \n");
  printf("   D1 |    0     2 \n");
  printf("  ----|-----------\n");
  printf("     0|  %3d   %3d\n",dtptr->costs[0][0], dtptr->costs[0][2]);
  printf("dest 2|  %3d   %3d\n",dtptr->costs[2][0], dtptr->costs[2][2]);
  printf("     3|  %3d   %3d\n",dtptr->costs[3][0], dtptr->costs[3][2]);

}

void linkhandler1(int linkid, int newcost) {
    // Print message that link is changing
    printf("    [linkhandler1] t=%.3f: Node 1 detects link cost to Node %d being changed to %d\n", clocktime, linkid, newcost);

    // Update distance table with new link cost
    for (int i = 0; i < 4; i++) {
        dt1.costs[i][linkid] += newcost - link_costs1[linkid];
    }

    // Update routing info
    link_costs1[linkid] = newcost;

    // Print node 1 distance table
    printf("        Table Updated with new link cost\n");
    printdt1(&dt1);
    printf("\n");

    // Initialize packet containing minimum cost distance vector
    struct rtpkt packet;
    packet.sourceid = 1;
    for (int i = 0; i < 4; i++) {
        packet.mincost[i] = 999;
    }
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            packet.mincost[i] = min(packet.mincost[i], dt1.costs[i][j]);
        }
    }
    packet.mincost[1] = 0;

    // Send packet to neighbors
    send2neighbor(packet, isNeighbor1);
}