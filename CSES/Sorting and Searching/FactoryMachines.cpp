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

// https://cses.fi/problemset/task/1620

bool check(ll mid, int n, ll t, ll k[]) {
    ll count=0;
    for (int i=0; i<n; i++) {
        count += mid/k[i];
        if (count >= t) return true;
    }
    return count >= t;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n; ll t;
    cin >> n >> t;
    ll k[n];
    for (int i=0; i<n; i++) cin >> k[i];

    ll min=0;
    ll max=1e18;
    while (min<max) {
        ll middle = min + (max-min)/2;
        if (check(middle, n, t, k)) {
            max = middle;
        }
        else min = middle+1;
    }

    cout << min;
}