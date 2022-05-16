#include <iostream>
#include <vector>
#include <map>
#include <cmath>
#include <algorithm>
#include <deque>
#include <queue>
#include <set>
#include <unordered_set>
#include <string>
#include <string.h>
#include <array>
#include <unordered_map>
#include <unordered_set>
using namespace std;
typedef long long ll;

// https://cses.fi/problemset/task/1622

set<string> all;

void permutation(string s, int i, int n) {
    if (i == n) {
        string cur;
        for (int i=0; i<n; i++) {
            cur += s[i];
        }
        all.insert(cur);
        return;
    }
    for (int j=i; j<n; j++) {
        swap(s[i], s[j]);
        permutation(s, i+1, n);
        swap(s[i], s[j]);
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    string s;
    cin >> s;
    int n = s.size();

    permutation(s, 0, n);
    cout << all.size() << "\n";
    for (string a : all) {
        cout << a << "\n";
    }   
}