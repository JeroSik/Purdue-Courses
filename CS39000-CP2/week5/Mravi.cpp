#include <bits/stdc++.h>
#include <iostream>
#include <math.h>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;

double DFS(int v, int p, vector<vector<int [3]>> adj, int needs[], int t) {
    int amount = 0;
    // code for handling leaf nodes goes here, return
    if (needs[v] != -1) {
        return t = 1 ? sqrt(needs[v]) : needs[v];
    }

    for (auto &edge : adj.at(v)) {  
        if (edge[0] == p) {
            continue;
        }

        amount += DFS(edge[0], v, adj, needs, edge[2]);
    }

    return amount;
}

int main() {
    int N;
    scanf("%d", &N);
    
    int needs[N];
    vector<vector<int [3]>> adj;
    for (int i = 0; i < N; i++) {
        vector<int[3]> v;
        adj.push_back(v);
    }

    for (int i = 0; i < N - 1; i++) {
        int A, B, X, T;
        scanf("%d %d %d %d", &A, &B, &X, &T);
    
        int a1[3] = {B, X, T};
        adj.at(A - 1).push_back(a1);
        int a2[3] = {A, X, T};
        adj.at(B - 1).push_back(a2);
    }
    for (int i = 0; i < N; i++) {
        cin >> needs[i];
    }

    printf("%f", DFS(0, 0, adj, needs, 0));

    return 0;
}