#include <algorithm>
#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;
// Save which was the last menu item used to reach a number. Use a special value to remember that multiple ways are possible.

int n, m, c, o;
int costs[101];
int opt[30001];

int main() {
    ios_base::sync_with_stdio(false); 
    cin.tie(NULL);
    cout.tie(0);

    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        scanf("%d", &c);
        costs[i] = c;
    }

    opt[0] = -3;
    for (int i = 1; i < 30001; i++) {
        opt[i] = -1;
    }
    
    // opt[c] last menu item to reach cost c, -1 if impossible, -2 if ambiguous
    for (int i = 0; i < n; i++) {
        for (int w = 0; w < 30001; w++) {
            if (costs[i] + w < 30001) {
                if (opt[w] != -1) {
                    if (opt[w] == -2) {
                        opt[w + costs[i]] = -2;
                    } else if (opt[w + costs[i]] >= 0) {
                        opt[w + costs[i]] = -2;
                    } else {
                        opt[w + costs[i]] = i;
                    }
                }
            }
        }

        // printf("****** FINISHED %d ******\n", i);
        // for (int a = 0; a < 15; a++) {
        //     printf("%d: %d\n",a, opt[a]);
        // }
    }
    // for (int a = 0; a < 15; a++) {
    //     printf("%d: %d\n",a, opt[a]);
    // }

    // Print output
    scanf("%d", &m);
    for (int i = 0; i < m; i++) {
        scanf("%d", &o);

        if (opt[o] == -2) {
            printf("Ambiguous\n");
        } else if (opt[o] == -1) {
            printf("Impossible\n");
        } else {
            vector<int> ans(n, 0);
            int x = o;
            while (opt[x] != -3) {
                ans[opt[x]]++;
                x -= costs[opt[x]];
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < ans[i]; j++) {
                    printf("%d ", i + 1);
                }
            }
            printf("\n");
        }
    }

    return 0;
}

