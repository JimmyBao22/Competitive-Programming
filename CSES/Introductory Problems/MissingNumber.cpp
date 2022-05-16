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
#include <array>
#include <unordered_map>
#include <unordered_set>
using namespace std;

// https://cses.fi/problemset/task/1083

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    long n;
    cin >> n;
    long total = n*(n+1)/2;

    for (int i=0; i<n-1; ++i) {
        int c;
        cin >> c;
        total -= c;
    }

    cout << total;
}