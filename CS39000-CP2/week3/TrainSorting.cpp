#include <algorithm>
#include <bits/stdc++.h>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <stdio.h>
#include <string.h>
#include <vector>

using namespace std;

typedef vector<int>        vi;
typedef vector< vi >       vvi;
typedef vector<bool>        vb;
typedef vector< vb >       vvb;

vi sol;
int n, cnt = 0;

void rec(vi& buf, vvb& hideous, vb& used, int k) {
    if (k == n) {
        if (cnt == 0)  sol = vi(buf); // first solution, copy it to sol
        cnt ++;
        return;
    }
    for(int i=0; i<n; i++) {
        if (! used[i]) {    // color i has not been used so far
            if (k>0 && hideous[buf[k-1]][i]) 
                continue;   // color i should not go with previous color buf[k-1]
            buf[k] = i;     // set k-th color to i
            used[i] = true;
            rec(buf, hideous, used, k+1);
            used[i] = false;
        }
    }
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    int n;
    cin >> n;
    
    for (int i = 0; i < cases; i++) {
        cin >> n;
        vector<string> colors(n);
        for (int i=0; i<n; i++) { cin >> colors[i]; }
        vvb  hideous(n, vb(n, false));  // initialize 2D n by n boolean array with false
        cin >> m;
        for (int s=0; s<m; s++) { 
            string c1, c2;
            cin >> c1 >> c2; 
            int i = find(colors.begin(), colors.end(), c1) - colors.begin();
            int j = find(colors.begin(), colors.end(), c2) - colors.begin();
            hideous[i][j] = hideous[j][i] = true;
        } // at this point, hideous array is set up
        cnt = 0;
        vi buf(n);
        vb used(n, false);
        rec(buf, hideous, used, 0); // this call actually computes answers
        cout << cnt << "\n";
        for (auto x : sol) {  cout << colors[x] << " ";  }
        cout << "\n";
    }
    return 0;
}