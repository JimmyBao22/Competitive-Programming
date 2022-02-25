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

// https://cses.fi/problemset/task/1091

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n,m;
    cin >> n >> m;
    ll h[n];
    ll t[m];
    ll minval=1e9;

        // can also use a map
    multiset<ll> ms;
    for (int i=0; i<n; i++) {
        cin >> h[i];
        ms.insert(h[i]);
        minval = min(minval,h[i]);
    }

    for (int i=0; i<m; i++) {
        cin >> t[i];
        auto it = ms.upper_bound(t[i]);
        if (it == ms.begin()) {
            cout << -1 << "\n";
        }
        else {
            it--;
            cout << *it << "\n";
            ms.erase(ms.find(*it));
        }
    }
}