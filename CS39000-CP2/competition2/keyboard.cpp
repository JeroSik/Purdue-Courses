#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <tuple>
#include <utility>

using namespace std;
string s;

struct node {
  char data;
  struct node *next;
};

node newNode(char c, node *n) {
  node *n = new node;
  n->data = c;
  n->next = n;

  return n;
}

int main() {
  while (true) {
    cin >> s;
    if (s == "0") { break; }

    node *curr = new node;
    curr->data = '\0';
    curr->next = NULL;
    node *start = curr;
    node *end = curr;

    for (int i = 0; i < s.size(); i++) {
      if (s[i] == '@') {
        curr = start;
        continue;
      }
      if (s[i] == '$') {
        curr = end;
        continue;
      }

      node *temp = new node;
      temp->data = s[i];
      
      temp->next = curr->next;
      curr->next = temp;

      end = curr == end ? temp : end;
      curr = temp;
    }

    while (start != NULL) {
      if (start->data != '\0') {
        cout << start->data;
      }
      start = start->next;
    }
    cout << endl;
  }

  return 0;
}