#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;

int main() {
    int H;
    string direction;
    cin >> H >> direction;

    int max = (1 << (H + 1));
    int curr = 1;

    for (char const &c : direction) {
        // cout << c << endl;
        if (c == 'L') {
            curr = curr * 2;
        } else {
            curr = curr * 2 + 1;
        }
    }

    cout << max - curr << endl;
    return 0;
}