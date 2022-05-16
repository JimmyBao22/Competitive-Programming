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

// https://cses.fi/problemset/task/1094

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    long arr[n];

    for (int i=0; i<n; i++) {
        cin >> arr[i];
    }

    long ans=0;
    for (int i=1; i<n; i++) {
        long needed = max(0l, arr[i-1] - arr[i]);
        arr[i] = max(arr[i], arr[i-1]);
        ans += needed;
    }

    cout << ans;
}