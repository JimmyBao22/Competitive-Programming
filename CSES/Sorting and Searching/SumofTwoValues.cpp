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

// https://cses.fi/problemset/task/1640

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    ll x;
    cin >> n >> x;

    map<ll, int> m;
    ll arr[n];
    for (int i=0; i<n; i++) {
        cin >> arr[i];
        if (m.count(x - arr[i]) > 0) {
            cout << i+1 << " " << m[x-arr[i]]+1;
            return 0;
        }
        m[arr[i]] = i;
    }
    cout << "IMPOSSIBLE";
}