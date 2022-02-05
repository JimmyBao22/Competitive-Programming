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

// https://cses.fi/problemset/task/1082

ll n;
ll mod = 1e9+7;

ll pow(ll a, ll b, ll m) {
    ll ans=1;
    while (b > 0) {
        if (b%2 == 1) {
            ans *= a;
            ans %= m;
        }
        a *= a;
        a%=m;
        b >>= 1;
    }
    return ans;
}

ll modInverse(ll a, ll m) {
    return pow(a, m - 2, m)%m;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n;
    ll ans = 0;
    ll low = n;
    ll inverse_two = modInverse(2, mod);
    for (ll i=1; i*i<n; i++) {
        ll cur = (n/i + n/(i+1) + 1) % mod;
        cur *= ((n/i - (n/(i+1) + 1) + 1)%mod);
        cur %= mod;
        cur *= inverse_two;
        // ll cur = (n/i + n/(i+1) + 1) * (n/i - (n/(i+1) + 1) + 1) / 2;
        cur %= mod;
        cur *= i;
        cur %= mod;
        ans += cur;
        ans %= mod;
        low = n/(i+1)+1;
    }
    for (ll i=1; i<low; i++) {
        ans += (n/i) * i;
        ans %= mod;
    }

    if (n == 1) ans = 1; // special case

    cout << ans;
}