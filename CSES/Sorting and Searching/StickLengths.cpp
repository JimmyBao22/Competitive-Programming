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

// https://cses.fi/problemset/task/1074

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;

    ll p[n];
    for (int i=0; i<n; i++) cin >> p[i];

    sort(p, p+n);

    ll ans=0;
    for (int i=0; i<n; i++) {
        ans += abs(p[i] - p[n/2]);
    }
    
    cout << ans;
}