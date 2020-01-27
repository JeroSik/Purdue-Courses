#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <iostream>
#include <stdio.h>

using namespace std;

int main() {
    int comb[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 22, 23, 25, 26, 28, 29, 33, 36, 39, 40, 44, 45, 46, 47, 48, 49, 50, 55, 56, 58, 59, 66, 69, 70, 77, 78, 79, 80, 88, 89, 99, 100, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 122, 123, 125, 126, 128, 129, 133, 136, 139, 140, 144, 145, 146, 147, 148, 149, 150, 155, 156, 158, 159, 166, 169, 170, 177, 178, 179, 180, 188, 189, 199, 200};
    int T, num;
    scanf("%i", &T);

    for (int i = 0; i < T; i++) {
        scanf("%i", &num);

        int ans = 0;
        for (int j = 0; j < 95; j++) {
            if (abs(num - comb[j]) < abs(num - ans)) {
                ans = comb[j];
            }
        }

        cout<< ans << endl;
    }
            
    return 0;
}