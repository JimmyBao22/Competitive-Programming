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

// https://cses.fi/problemset/task/1073

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    ll k[n];
    for (int i=0; i<n; i++) cin >> k[i];

    map<ll,int> m;
        // val, count
    for (int i=0; i<n; i++) {
        auto it = m.upper_bound(k[i]);
        if (it == m.end()) {

        }
        else {
            pair<ll, int> cur = *it;
            m[cur.first]--;
            if (m[cur.first] == 0) m.erase(cur.first);
        }
        m[k[i]]++;
    }

    int ans=0;
    for (auto i : m) {
        ans += i.second;
    }
    cout << ans;
}