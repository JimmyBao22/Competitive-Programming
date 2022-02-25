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

// https://cses.fi/problemset/task/1163

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    ll x;
    int n;
    cin >> x >> n;
    ll arr[n];

    for (int i=0; i<n; i++) cin >> arr[i];

    map<ll, int> segments;
    set<ll> lights;
    lights.insert(0);
    lights.insert(x);
    segments[x] = 1;
    for (int i=0; i<n; i++) {
        auto lower = lights.upper_bound(arr[i]);
        lower--;
        auto upper = lights.upper_bound(arr[i]);
        segments[*upper - *lower]--;
        if (segments[*upper - *lower] == 0) segments.erase(*upper - *lower);
        lights.insert(arr[i]);
        segments[*upper - arr[i]]++;
        segments[arr[i] - *lower]++;
        cout << (*(--segments.end())).first << " ";
    }
}