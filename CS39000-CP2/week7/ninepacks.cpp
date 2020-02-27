#include <algorithm>
#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;

int H, B, maxH, maxB, maxS, ans;
int hotdogs[101];
int buns[101];
int optH[100001];
int optB[100001];

int main() {
    ios_base::sync_with_stdio(false); 
    cin.tie(NULL);
    cout.tie(0);

    cin >> H;
    maxH = 0;
    for (int i = 0; i < H; i++) {
        cin >> hotdogs[i];
        maxH += hotdogs[i];
    }

    cin >> B;
    maxB = 0;
    for (int i = 0; i < B; i++) {
        cin >> buns[i];
        maxB += buns[i];
    }
    
    maxS = min(maxH, maxB) + 1;
    optH[0] = 0; optB[0] = 0;
    for (int w = 1; w < maxS; w++) {
        optH[w] = 100001;
        optB[w] = 100001;
    }

    for (int i = 0; i < H; i++) {
        for (int w = maxS; w >= 0; w--) {
            if (hotdogs[i] <= w) {
                optH[w] = min(optH[w], optH[w - hotdogs[i]] + 1);
            }
        }
    }

    for (int i = 0; i < B; i++) {
        for (int w = maxS; w >= 0; w--) {
            if (buns[i] <= w) {
                optB[w] = min(optB[w], optB[w - buns[i]] + 1);
            }
        }
    }

    ans = 100001;
    for (int i = 1; i < maxS; i++) {
        if (optH[i] != 100001 && optB[i] != 100001) {
            ans = min(ans, optH[i] + optB[i]);
        }
    }

    if (ans == 100001) {
        cout << "impossible";
    } else {
        cout << ans;
    }

    return 0;
}