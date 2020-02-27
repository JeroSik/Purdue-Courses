#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;
int n;
vector<int> v;

int main() {
    ios_base::sync_with_stdio(false); 
    cin.tie(NULL);
    cout.tie(0);

    while(scanf("%d", &n) != EOF){
        v.push_back(n);
    }
    int k = v[v.size() - 1];
    int n = v[v.size() - 2];

    for (int i = 0; i < n; i++) {
        printf("%d ", v[(k+i)%n]);
    }
    return 0;
}