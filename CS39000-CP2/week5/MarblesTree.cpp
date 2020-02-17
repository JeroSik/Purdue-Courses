#include <bits/stdc++.h>
#include <iostream>
#include <queue>
#include <stdio.h>
#include <tuple>
#include <unordered_map>
#include <utility>

using namespace std;
/* lecture notes */
// cost of marble moving up an edge
// you can figure that out from the subtree

// count how many marbles must cross an edge
// you can do this by traversing up tree

void printQueue(queue<int> q)
{
	//printing content of queue
	while (!q.empty()){
		cout<<" "<<q.front();
		q.pop();
	}
	cout<<endl;
}

void printArray(int a[], int n) {
	//printing content of queue
	for (int i = 0; i < n; i++) {
		cout<<" "<<a[i];
	}
	cout<<endl;
}

int main() {
    while (true) {
        int n;
        cin >> n;
        if (!n) {
            break;
        }

        int parent[n] = {0};
        int marbles[n];

        for (int i = 0; i < n; i++) {
            int v, m, d;
            cin >> v >> m >> d;

            marbles[v - 1] = m;
            for (int i = 0; i < d; i++) {
                int index;
                cin >> index;
                parent[index - 1] = v;
            }
        }

        unordered_map<int, int> inCount;
        for (int i = 0; i <= n; i++) {
            inCount.insert(make_pair<int, int>(int(i), 0));
        }
        for (int i = 0; i < n; i++) {
            inCount.at(parent[i]) = inCount.at(parent[i]) + 1;
        }

        queue<int> leaves;
        for (int i = 0; i <= n; i++) {
            if (inCount.at(i) == 0) {
                leaves.push(i);
            }
        }

        // cout << "Queue:";
        // printQueue(leaves);
        // cout << "Marbles:";
        // printArray(marbles, n);
        // cout << "Parents: ";
        // printArray(parent, n);

        int ans = 0;
        while (!leaves.empty()) {
            int c = leaves.front();
            leaves.pop();
            
            int p = parent[c - 1];

            inCount.at(p) = inCount.at(p) - 1;
            if (inCount.at(p) == 0) {
                if (p == 0) {
                    break;
                }
                leaves.push(p);
            }

            ans += abs(marbles[c - 1] - 1);
            marbles[p - 1] += marbles[c - 1] - 1;

            // cout << "Queue:";
            // printQueue(leaves);
            // cout << "Marbles:";
            // printArray(marbles, n);
            // cout << "Answer: " << ans << endl;
        }

        cout << ans << endl;
    }
    return 0;
}