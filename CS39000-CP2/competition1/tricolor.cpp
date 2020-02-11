#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;

int counter, N, K, x, y;
char c;
vector<vector<int>> same;
vector<vector<int>> diff;

bool isValid(vector<vector<int>>& same, vector<vector<int>>& diff, int painting[], int index) {
    for (int i = 0; i < N; i++) {
        for(size_t j = 0; j < same[i].size(); j++) {
            if (painting[i] != painting[same[i][j]]) {
                return false;
            }
        }

        for(size_t j = 0; j < diff[i].size(); j++) {
            if (painting[i] == painting[diff[i][j]]) {
                return false;
            }
        }
    }

    return true;
}

void getAllComb(vector<vector<int>>& same, vector<vector<int>>& diff, int painting[], int index) {
    if (index == N) {
        if (isValid(same, diff, painting, index)) {
            counter++;
        }
        return;
    }

    for (int i = 0; i <= 2; i++) {
        painting[index] = i;
        getAllComb(same, diff, painting, index + 1); 
    }
    
    return;
}

int main() {
    scanf("%d", &N);
    scanf("%d", &K);

    same.resize(N, vector<int>(0));
    diff.resize(N, vector<int>(0));

    for (int i = 0; i < K; i++) {
        scanf(" %c", &c);
        scanf("%d", &x);
        scanf("%d", &y);
        x--;
        y--;

        if (c == 'S') {
            same[x].push_back(y);
        } else {
            diff[x].push_back(y);
        }
    }

    int painting[N];
    painting[0] = 0;
    counter = 0;
    getAllComb(same, diff, painting, 1);
    
    cout << counter * 3 << endl;

    return 0;
}