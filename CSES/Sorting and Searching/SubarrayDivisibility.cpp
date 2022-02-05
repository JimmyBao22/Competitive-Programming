#include <iostream>
#include <vector>
#include <map>
#include <cmath>
#include <algorithm>
#include <deque>
#include <queue>
#include <stack>
#include <set>
#include <unordered_set>
#include <string>
#include <string.h>
#include <array>
#include <unordered_map>
#include <unordered_set>
#include <iomanip>
using namespace std;
typedef long long ll;

// https://cses.fi/problemset/task/1662

const int MaxN = 2e5+10;
ll arr[MaxN];

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    for (int i=0; i<n; i++) cin >> arr[i];

    map<ll, ll> m;
    m[0] = 1;
    ll sum = 0;
    ll ans=0;
    for (int i=0; i<n; i++) {
        sum += arr[i];
        sum = (sum%n+n)%n;
        ans += m[sum];
        m[sum]++;
    }

    cout << ans;
}