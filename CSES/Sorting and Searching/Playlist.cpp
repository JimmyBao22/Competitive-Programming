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

// https://cses.fi/problemset/task/1141

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    ll arr[n];
    for (int i=0; i<n; i++) cin >> arr[i];

    set<int> cur;
    int endp=0;
    ll ans=0;
    for (int i=0; i<n; i++) {
        while (true) {
            if (endp >= n || cur.count(arr[endp]) > 0) break;
            cur.insert(arr[endp]);
            endp++;
        }
        ans = max(ans, (ll)(cur.size()));
        cur.erase(arr[i]);
    }

    cout << ans;   
}