#include <bits/stdc++.h>
#include <iostream>
#include <queue>
#include <stdio.h>
#include <string>
#include <tuple>
#include <unordered_map>
#include <utility>

using namespace std;

int n, q, year, k;
string country;

unordered_map<string, vector<int>> trips;
unordered_set<string> sorted;

int main() {
    cin >> n;

    for (int i = 0; i < n; i++) {
        cin >> country >> year;

        if (trips.count(country) > 0) {
            trips.at(country).push_back(year);
        } else {
            vector<int> tempV{year};
            pair<string, vector<int>> tempP(country, tempV);
            trips.insert(tempP);
        }
    }

    cin >> q;
    for (int i = 0; i < q; i++) {
        cin >> country >> k;

        if (sorted.count(country) == 0) {
            sort(trips.at(country).begin(), trips.at(country).end());
            sorted.insert(country);
        }

        cout << trips.at(country).at(k - 1) << "\n";
    }

    return 0;
}