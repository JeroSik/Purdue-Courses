#include <algorithm>
#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;
// Save which was the last menu item used to reach a number. Use a special value to remember that multiple ways are possible.

int n, m, maxS;
int costs[101];
int orders[1001];

int opt[30001];
bool multiWays[30001];
// int opt[101][1001];


int main() {
    ios_base::sync_with_stdio(false); 
    cin.tie(NULL);

    // Parse input
    cin >> n;
    for (int i = 0; i < n; i++) {
        cin >> costs[i];
    }
    
    cin >> m;
    maxS = -1;
    for (int i = 0; i < m; i++) {
        cin >> orders[i];
        maxS = orders[i] > maxS ? orders[i] : maxS;
    }

    // knapsack?
    for (int i = 0; i < maxS; i++) {
        opt[i] = -1;
        multiWays[i] = false;
    }
    opt[0] = 0;

    
    // opt[c] last menu item to reach cost c
    for (int i = 0; i < n; i++) {
        for (int w = 0; w < maxS; w++) {
            if (opt[w] != -1) {
                opt[w + costs[i]] = i;
            } else {
                multiWays[w + costs[i]] = true;
                opt[w + costs[i]] = -1;
            }

            if(v[j] >= 0) {
                if(v[j+cost] == -2) {
                    v[j+cost] = i;
                }
                else {
                    v[j+cost] = -1;
                }
            }
            if(v[j] == -1) {
                v[j+cost] = -1;
            }
        }
    }

    // Print output
    for (int i = 0; i < m; i++) {
        if (multiWays[i]) {
            cout << "Ambiguous" << endl;
        } else if (opt[i] == -1) {
            cout << "Impossible" << endl;
        } else {
            // backtrack?
        }
    }

    return 0;
}

