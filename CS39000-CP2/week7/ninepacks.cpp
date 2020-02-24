#include <algorithm>
#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;

int H, B, h, b;
int W, temp;

int main() {
    ios_base::sync_with_stdio(false); 
    cin.tie(NULL);

    cin >> H;
    int hotdogs[H];
    W = 0;
    for (int i = 0; i < H; i++) {
        scanf("%d", &h);
        hotdogs[i] = h;
        W += h;
    }
    
    cin >> B;
    int buns[B];
    temp = 0;
    for (int i = 0; i < B; i++) {
        cin >> b;
        buns[i] = b;
        temp += b;
    }

    W = W > temp ? temp : W;

    int optH[H + 1][W + 1];
    int optB[B + 1][W + 1];

    for(int i = 1; i < H + 1; i++) {
            for(int j = 0; j < W + 1; j++) {
                if(i == 0 || j == 0) {
                    optH[i][j] = 0;
                } else if (j - hotdogs[i - 1] >= 0) {
                    optH[i][j] = Math.max(DP[i-1][j], DP[i-1][j - w[i-1]] + v[i-1]);
                } else {
                    DP[i][j] = DP[i-1][j];
                }
            }
        }

    return 0;
}