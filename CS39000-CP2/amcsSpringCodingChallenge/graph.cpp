#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;

vector<vector<int>> prereq;
unordered_set<int> s;
stack <int> sk;

int main() {
    int n;
    cin >> n;

    for (int i = 0; i < n; i++) {
        vector<int> v;
        prereq.push_back(v);
        for (int j = 0; j < n; j++) {
            int x;
            cin >> x;

            if (x == 1) {
                prereq.at(j).push_back(i);
            }
        }
    }
    
    for (int i = n - 1; i >= 0; i--) {
        sk.push(i);
    }

    while (!sk.empty()) {
        int x = sk.top();

        if (prereq.at(x).size() == 0) {
            sk.pop();
            if (s.count(x) == 0) {
                cout << x << " ";
                s.insert(x);
            }
        } else {
            for (auto &x : prereq.at(x)) {
                sk.push(x);
            }
            vector<int> v(0);
            prereq.at(x) = v;
        }
    }

    return 0;
}