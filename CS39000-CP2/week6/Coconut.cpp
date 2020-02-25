#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;

int s, n;

vector<pair<int,int>> hands;

int main() {
    ios_base::sync_with_stdio(false); 
    cin.tie(NULL);
    cout.tie(0);

    scanf("%d %d", &s, &n);

    for (int i = 1; i <= n; i++) {
        hands.push_back({i, 2});
    }
    
    int curr = 0;
    while (hands.size() > 1) {
        curr = (curr + s - 1) % hands.size();

        if (hands[curr].second == 2) {
            hands[curr].second = 1;
            hands.insert(hands.begin() + curr, {hands[curr].first, 1});
        } else if (hands[curr].second == 1) {
            hands[curr].second = 0;
            curr++;
        } else {
            hands.erase(hands.begin() + curr);
        }
    }

    printf("%d\n", hands[0].first);
    
    return 0;
}