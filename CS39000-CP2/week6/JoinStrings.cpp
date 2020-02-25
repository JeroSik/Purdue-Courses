#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;

int N, a, b;
string word;

vector<string> words;
vector<vector<int>> op;

void printWords(int a) {
    cout << words.at(a);

    for (auto &x : op.at(a)) {
        printWords(x);
    }
}

int main() {
    // added the two lines below 
    ios_base::sync_with_stdio(false); 
    cin.tie(NULL);
    cout.tie(0);

    cin >> N;

    for (int i = 0; i < N; i++) {
        cin >> word;
        words.push_back(word);

        vector<int> v;
        op.push_back(v);
    }
    
    for (int i = 0; i < N - 1; i++) {
        cin >> a >> b;
        a--; b--;

        op.at(a).push_back(b);
    }

    printWords(a);

    return 0;
}