#include <cstdio>
#include <cstring>
#include <iostream>
#include <stdio.h>
#include <vector>

using namespace std;

// void findLIS(int[] nums, int n) {
//     int[] opt = new int[n];
//     opt[0] = 1;
//     int maxLIS = 0;

//     for (int i = 1; i < n; i++) {
//         opt[i] = 0;

//         for (int j = 0; j < i; j++) {
//             if (nums[i] > nums[j]) {
//                 opt[i] = Math.max(opt[i], opt[j]);
//             }
//         }
//         opt[i] = opt[i] + 1;
//         maxLIS = Math.max(maxLIS, opt[i]);
//     }

//     System.out.println(maxLIS);
//     backtrackLIS(opt, n);
// }

// void backtrackLIS(int[] opt, int n) {
//     int max = 1;
//     for (int i = 0; i < n; i++) {
//         if (opt[i] == max) {
//             System.out.print(i + " ");
//             max++;
//         }
//     }
//     System.out.println();
// }

int main() {
    int n, num;
    while(cin.eof()){
        cin >> n;
        vector<int> v;

        for (int i = 0; i < n; i++) {
            cin >> num;
            v.push_back(num);
        }

        cout << "LMAO YETT" << endl;
    }
    
            
    return 0;
}