#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>
#include <queue>

using namespace std;

int main() {
    int C, P, X, L;
    scanf("%d %d %d %d", &C, &P, &X, &L);

    if (X == L) {
        printf("leave");
        return 0;
    }

    int leaveCount[C] = {0};
    int tradeCount[C] = {0};
    vector<vector<int>> tradePartners;

    queue<int> leaving;
    leaving.push(L);
    while (!leaving.empty) {
        int c = leaving.front();
        leaving.pop();

        vector<int> partners = tradePartners.at(c);
        for (int i : partners) {
            leaveCount[i] += 1;
            
            if (leaveCount)
        }
    }

    return 0;
}