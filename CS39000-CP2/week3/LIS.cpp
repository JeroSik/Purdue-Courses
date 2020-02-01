#include <algorithm>
#include <bits/stdc++.h>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <stdio.h>
#include <vector>
#include <string.h>

using namespace std;

void backtrackLIS(int opt[], int maxLIS, int n) {
    int ans[maxLIS];
    for (int i = n - 1; i >= 0; i--) {
        if (opt[i] == maxLIS) {
            ans[maxLIS - 1] = i;
            
            maxLIS--;
            if (maxLIS == 0) {
                break;
            }
        }
    }

    for (int i = 0; i < maxLIS; i++) {
        cout << ans[i] << " ";
    }
    cout << endl;
}

int nums[100001];
int opt[100001];
int ans[100001];

int main() {
    int n, num;
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    while(scanf("%d", &n) != EOF){
        opt[0] = 1;
        int maxLIS = 1;

        // Recurrence
        for (int i = 0; i < n; i++) {
            scanf("%d", &num);
            nums[i] = num;

            opt[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    opt[i] = max(opt[i], opt[j] + 1);
                }
            }
            maxLIS = max(maxLIS, opt[i]);
        }

        cout << maxLIS << endl;
        int k = maxLIS;
        // Backtracking
        for (int i = n - 1; i >= 0; i--) {
            if (opt[i] == maxLIS) {
                ans[maxLIS - 1] = i;

                maxLIS--;
                if (maxLIS == 0) {
                    break;
                }
            }
        }

        for (int i = 0; i < k; i++) {
            cout << ans[i] << " ";
        }
        cout << endl;

        memset(nums, 0, sizeof(nums));
        memset(opt, 0, sizeof(opt));
        memset(ans, 0, sizeof(ans));
    }
    exit(0);
    return 0;
}