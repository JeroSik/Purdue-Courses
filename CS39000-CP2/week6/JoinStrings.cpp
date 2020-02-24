#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;

// inline int readint(){
//     int c=getchar_unlocked(), x=0;
//     for(;c<48||c>57;c=getchar_unlocked());
//     for(;c>47&&c<58;c=getchar_unlocked()) x=(x<<3)+(x<<1)+c-48;
//     return x;
// }

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

    int N;
    // int x = scanf("%d", &N);
    cin >> N;

    for (int i = 0; i < N; i++) {
        string word;
        cin >> word;
        words.push_back(word);

        vector<int> v;
        op.push_back(v);
    }
    
    int a, b;
    for (int i = 0; i < N - 1; i++) {
        // int a = readint() - 1;
        // int b = readint() - 1;
        // int y = scanf("%d %d", &a, &b);
        cin >> a >> b;
        a -= 1; b -= 1;

        op.at(a).push_back(b);
    }

    printWords(a);

    return 0;
}