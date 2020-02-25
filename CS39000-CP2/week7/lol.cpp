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
bool optH[101][100001];
bool optB[101][100001];

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
    
    maxS = min(maxH, maxB);
    
    for (int i = 0; i <= H; i++) {
        optH[i][0] = true;
    }
    for (int i = 1; i <= maxS; i++) {
        optH[0][i] = false;
    }
    for (int i = 1; i <= H; i++) {
        for (int j = 1; j < maxS; j++) {
            if (j < hotdogs[i - 1]) {
                optH[i][j] = optH[i-1][j];
            } else {
                optH[i][j] = optH[i-1][j] || optH[i-1][j-hotdogs[i-1]];
            }
        }
    }

    for (int i = 0; i <= B; i++) {
        optB[i][0] = true;
    }
    for (int i = 1; i <= maxS; i++) {
        optB[0][i] = false;
    }
    for (int i = 1; i <= B; i++) {
        for (int j = 1; j < maxS; j++) {
            if (j < buns[i - 1]) {
                optB[i][j] = optB[i-1][j];
            } else {
                optB[i][j] = optB[i-1][j] || optB[i-1][j-buns[i-1]];
            }
        }
    }

    ans = 1000001;
    for (int i = 1; i <= maxS; i++) {
        if (optH[H][i] && optB[B][i]) {
            int num = 0;
            
            int x = H;
            int y = i;
            while (x > 0 && y > 0) {
                if (optH[x - 1][y] == false) {
                    num++;
                    y -= hotdogs[x - 1];
                } else {
                    x--;
                }
            }

            x = B;
            y = i;
            while (x > 0 && y > 0) {
                if (optB[x - 1][y] == false) {
                    num++;
                    y -= buns[x - 1];
                } else {
                    x--;
                }
            }
            ans = min(num, ans);
        }
    }

    if (ans == 1000001) {
        cout << "impossible";
    } else {
        cout << ans;
    }

    return 0;
}