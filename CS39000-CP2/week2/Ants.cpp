#include <cstdio>
#include <cstring>
#include <iostream>
#include <stdio.h>

using namespace std;

int min(int a, int b) {
    return a <= b ? a : b;
}
int max(int a, int b) {
    return a >= b ? a : b;
}

int main() {
    int t, l, n, ant, shortest, longest;
    // cin >> t;
    scanf("%i", &t);

    for (int i = 0; i < t; i++) {
        // cin >> l >> n;
        scanf("%i %i", &l, &n);
        shortest = 0;
        longest = 0;

        for (int j = 0; j < n; j++) {
            // cin >> ant;
            scanf("%i", &ant);
            shortest = max(shortest, min(ant, l - ant));
            longest = max(longest, max(ant, l - ant));
        }

        cout<< shortest << " " << longest << endl;
    }
            
    return 0;
}