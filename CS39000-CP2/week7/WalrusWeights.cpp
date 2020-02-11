#include <algorithm>
#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;

int main() {
    int n;
    scanf("%d", &n);

    int weights[n];
    for (int i = 0; i < n; i++) {
        cin >> weights[i];
    }

    int opt[n + 1][2001];
    for (int w = 0; w <= 2000; w++) {
        opt[0][w] = 0;
    }

    for (int i = 1; i <= n; i++) {
        for (int w = 1; w <= 2000; w++) {
            if (weights[i] > w) {
                opt[i][w] = opt[i-1][w];
            } else {
                opt[i][2] = max(opt[i-1][w], opt[][w - weights[i]]);
            }
        }
    }

    cout << opt[n][2000];

    return 0;
}