#include <bits/stdc++.h>
#include <iostream>
#include <math.h>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;

vector<vector<int>> adj;
int edges[100000][2];
int color[100000];
unordered_set<int> visited;

void DFS(int curr) {
    for (auto &next : adj.at(curr)) {
        if (visited.find(next) == visited.end()) {
            visited.insert(next);
            // cout << next << endl;

            color[next] = color[curr] == 0 ? 1 : 0;
            DFS(next);
        }
    }
}

int main() {
    int N;
    scanf("%d", &N);
    
    for (int i = 0; i < N; i++) {
        vector<int> v;
        adj.push_back(v);
    }

    for (int i = 0; i < N - 1; i++) {
        int a, b;
        scanf("%d %d", &a, &b);
        a -= 1;
        b -= 1;

        edges[i][0] = a;
        edges[i][1] = b;
        adj.at(a).push_back(b);
        adj.at(b).push_back(a);
    }

    visited.insert(0);
    color[0] = 0;
    DFS(0);

    // for (int i = 0; i < N; i++) {
    //     cout << color[i] << " ";
    // }
    // cout << endl;

    for (int i = 0; i < N - 1; i++) {
        int a = edges[i][0];
        int b = edges[i][1];

        if (color[a] == 0 && color[b] == 1) {
            printf("0\n");
        } else if (color[a] == 1 && color[b] == 0) {
            printf("1\n");
        } else {
           printf("ERROR\n");
        }
    }

    return 0;
}