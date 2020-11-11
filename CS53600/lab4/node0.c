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
} dt0;


/* students to write the following two routines, and maybe some others */

// Store cost from Node 0 to directly attached neighbors
int link_costs0[4] = {0, 1, 3, 9};
// Store whether or not Node i is a direct neighbor of Node 0
bool isNeighbor0[4] = {false, true, true, true};

void rtinit0() {
    // Initialize distance table
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            // Check if node i is a direct neighbor
            if (isNeighbor0[i] && i == j) {
                dt0.costs[i][j] = link_costs0[i];
            // Otherwise, set initial distance as infinity
            } else {
                dt0.costs[i][j] = 999;
            }
        }
    }

    // Print initial node 0 distance table
    printf("    [rtinit0] t=%.3f: Initializing Node 0\n", clocktime);
    printdt0(&dt0);
    printf("\n");

    // Initialize packet containing minimum cost distance vector
    struct rtpkt packet;
    packet.sourceid = 0;
    for (int i = 0; i < 4; i++) {
        packet.mincost[i] = link_costs0[i];
    }

    // Send packet to neighbors
    send2neighbor(packet, isNeighbor0);
}

void rtupdate0(struct rtpkt *rcvdpkt) {
    // Print message that packet has been received
    printf("    [rtupdate0] t=%.3f: Node 0 received update packet from Node %d\n", clocktime, rcvdpkt->sourceid);

    // Initialize flag to track if changes were made
    bool hasChanged = false;

    // Check new path cost for each node
    for (int i = 0; i < 4; i++) {
        // Skip current node
        if (i == 0) {
            continue;
        }

        // Calculate new path cost for node i [c(x,v) + Dv(y)]
        int new_path_cost = link_costs0[rcvdpkt->sourceid] + rcvdpkt->mincost[i];

        // Check if the new path cost is smaller than the current path cost
        if (new_path_cost < dt0.costs[i][rcvdpkt->sourceid]) {
            dt0.costs[i][rcvdpkt->sourceid] = new_path_cost;
            hasChanged = true;
        }
    }

    // Check if distance vector for node 0 has changed
    if (hasChanged) {
        // Print node 0 distance table
        printf("        Table Updated: True\n");
        printdt0(&dt0);
        printf("\n");

        // Initialize packet containing minimum cost distance vector
        struct rtpkt packet;
        packet.sourceid = 0;
        for (int i = 0; i < 4; i++) {
            packet.mincost[i] = 999;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                packet.mincost[i] = min(packet.mincost[i], dt0.costs[i][j]);
            }
        }
        packet.mincost[0] = 0;

        // Send packet to neighbors
        send2neighbor(packet, isNeighbor0);
    } else {
        // Print that table has not updated
        printf("        Table Updated: False\n\n");
    }
}

printdt0(struct distance_table *dtptr) {
  printf("                via     \n");
  printf("   D0 |    1     2    3 \n");
  printf("  ----|-----------------\n");
  printf("     1|  %3d   %3d   %3d\n",dtptr->costs[1][1],
	 dtptr->costs[1][2],dtptr->costs[1][3]);
  printf("dest 2|  %3d   %3d   %3d\n",dtptr->costs[2][1],
	 dtptr->costs[2][2],dtptr->costs[2][3]);
  printf("     3|  %3d   %3d   %3d\n",dtptr->costs[3][1],
	 dtptr->costs[3][2],dtptr->costs[3][3]);
}

void linkhandler0(int linkid, int newcost) {
    // Print message that link is changing
    printf("    [linkhandler0] t=%.3f: Node 0 detects link cost to Node %d being changed to %d\n", clocktime, linkid, newcost);

    // Update distance table with new link cost
    for (int i = 0; i < 4; i++) {
        dt0.costs[i][linkid] += newcost - link_costs0[linkid];
    }

    // Update routing info
    link_costs0[linkid] = newcost;

    // Print node 0 distance table
    printf("        Table Updated with new link cost\n");
    printdt0(&dt0);
    printf("\n");

    // Initialize packet containing minimum cost distance vector
    struct rtpkt packet;
    packet.sourceid = 0;
    for (int i = 0; i < 4; i++) {
        packet.mincost[i] = 999;
    }
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            packet.mincost[i] = min(packet.mincost[i], dt0.costs[i][j]);
        }
    }
    packet.mincost[0] = 0;

    // Send packet to neighbors
    send2neighbor(packet, isNeighbor0);
}