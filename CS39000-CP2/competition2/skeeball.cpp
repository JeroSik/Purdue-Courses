#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;

int S, H, n, b;
int highest = -1;
int closest = 1000000000;
int opt[100001];

int main() {
  cin >> S >> H;
  cin >> n >> b;

  int rings[n];
  for (int i = 0; i < n; i++) {
    cin >> rings[i];
    highest = max(rings[i], highest);
  }

  int set[n*b];
  for (int i = 0; i < b; i++) {
    for (int j = 0; j < n; j++) {
      set[i*n + j] = rings[j];
    }
  }

  highest *= b;
  highest++;
  opt[0] = 0;
  for (int w = 1; w < highest; w++) {
    opt[w] = 100001;
  }

  for (int i = 0; i < n*b; i++) {
    for (int w = highest; w >= 0; w--) {
      if (set[i] <= w) {
        opt[w] = min(opt[w], opt[w - set[i]] + 1);
      }
    }
  }

  for (int i = 1; i < highest; i++) {
    if (opt[i] != 100001) {
      if (opt[i] <= b && S + i > H) {
        closest = min(S + i, closest);
      }
    }
  }


  if (closest == 1000000000) {
    cout << -1;
  } else {
    cout << closest;
  }
  return 0;
}