#include <cstdio>
#include <cstring>
#include <iostream>
#include <stdio.h>

using namespace std;

int main() {
    int t, w, h, n, shotx, shoty;
    char c;
    scanf("%i", &t);

    for (int test = 0; test < t; test++) {
        scanf("%i %i %i", &w, &h, &n);

        int map1[h][w]; int map2[h][w];
        int ships1 = 1; int ships2 = 1;

        for (int ship = 0; ship < 2; ship++) {
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    scanf("%c", &c);
                    if (ship == 0) {
                        if (c == '#') {
                            map1[i][j] = ships1++;
                        } else {
                            map1[i][j] = 0;
                        }
                    } else {
                        if (c == '#') {
                            map2[i][j] = ships2++;
                        } else {
                            map2[i][j] = 0;
                        }
                    }
                }
            }
        }

        int shots[n][2];
        for (int i = 0; i < n; i++) {
            scanf("%i %i", &shotx, &shoty);
            shots[i][0] = shotx;
            shots[i][0] = shoty;
        }

        int turn = 1;
        bool redemption = false;

        int i;
        for (i = 0 ; i < n; i++) {
            int x = shots[i][0];
            int y = h - shots[i][1] - 1;

            if (turn == 1) {
                if (map2[y][x] == 0) {
                    turn = 2;
                } else {
                    map2[y][x] = 0;
                    ships2--;
                    if (ships2 == 1) {
                        redemption = true;
                        break;
                    }
                }
            } else {
                if (map1[y][x] == 0) {
                    turn = 1;
                } else {
                    map1[y][x] = 0;
                    ships1--;
                    if (ships1 == 1) {
                        break;
                    }
                }
            }
        }

        if (redemption) {
            for (i = i; i < n; i++) {
                int x = shots[i][0];
                int y = h - shots[i][1] - 1;

                if (map1[y][x] == 0) {
                    turn = 1;
                } else {
                    map1[y][x] = 0;
                    ships1--;
                    if (ships1 == 1) {
                        break;
                    }
                }
            }
        }

        if (ships1 == ships2) {
            cout << "draw" << endl;
        } else if (ships1 == 1) {
            cout << "player two wins" << endl;
        } else if (ships2 == 1) {
            cout << "player one wins" << endl;
        } else {
            cout << "draw" << endl;
        }
    }
            
    return 0;
}