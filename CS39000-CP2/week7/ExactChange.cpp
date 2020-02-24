#include <algorithm>
#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;

int T, price, n, x;
int denom[101];
int opt[10001];

int main() {
    ios_base::sync_with_stdio(false); 
    cin.tie(NULL);
    cout.tie(0);

    scanf("%d", &T);

    for (int t = 0; t < T; t++) {
        scanf("%d", &price);
        scanf("%d", &n);

        for (int i = 0; i < n; i++) {
            scanf("%d", &x);
            denom[i] = x;
        }
        
        opt[0] = 0;
        for (int i = 1; i < 10001; i++) {
            opt[i] = 10001;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 10001; j >= 0; j--) {
                if (denom[i] <= j) {
                    opt[j] = min(opt[j], opt[j - denom[i]] + 1);
                }
            }
        }

        for (int i = price; i < 10001; i++) {
            if (opt[i] != 10001) {
                printf("%d %d\n", i, opt[i]);
                break;
            }
        }
    }

    return 0;
}