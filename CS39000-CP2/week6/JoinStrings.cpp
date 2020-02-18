#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;

vector<string> words;
vector<vector<int>> op;

void printWords(int a) {
    cout << words.at(a);

    for (auto &x : op.at(a)) {
        printWords(x);
    }
}

int main() {
    int N;
    scanf("%d", &N);

    for (int i = 0; i < N; i++) {
        string word;
        cin >> word;
        words.push_back(word);

        vector<int> v;
        op.push_back(v);
    }
    
    int a, b;
    for (int i = 0; i < N - 1; i++) {
        scanf("%d %d", &a, &b);
        a -= 1; b -= 1;

        op.at(a).push_back(b);
    }

    printWords(a);

    return 0;
}