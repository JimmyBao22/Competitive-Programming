#include <iostream>
#include <vector>
#include <map>
#include <cmath>
#include <algorithm>
#include <deque>
#include <queue>
#include <stack>
#include <set>
#include <string>
#include <string.h>
#include <array>
#include <unordered_map>
#include <unordered_set>
#include <iomanip>
using namespace std;
typedef long long ll;

// https://cses.fi/problemset/task/1093

const int MaxN = 2e5+10;
ll mod = 1e9+7;
ll dp[MaxN];
int n;
int needed;

ll pow(ll a, ll b, ll m) {
    ll ans = 1;
    while (b > 0) {
        if (b%2 == 1) {
            ans *= a;
            ans %= m;
        }
        a *= a;
        a %= m;
        b >>= 1;
    }
    return ans;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n;
    needed = n*(n+1)/2;
    if (needed % 2 == 1) {
        cout << 0;
        return 0;
    }
    needed /= 2;

    dp[0] = 1;
    for (int i=1; i<=n; i++) {
        for (int j=needed; j>=i; j--) {
            dp[j] += dp[j-i];
            dp[j] %= mod;
        }
    }
    cout << dp[needed] * pow(2, mod-2, mod) % mod;
}