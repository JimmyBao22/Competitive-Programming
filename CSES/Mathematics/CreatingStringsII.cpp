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

// https://cses.fi/problemset/task/1715

const int MaxN = 1e6+10;
const ll mod = 1e9+7;
int c[26];

ll fact[MaxN], inv_fact[MaxN];

ll C(int top, int bottom) {
    // top! / bottom! (top - bottom)!
    return fact[top] * inv_fact[bottom] %mod * inv_fact[top - bottom] %mod;
}

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

    fact[0] = inv_fact[0] = 1;
    for (int i=1; i<MaxN; i++) {
        fact[i] = fact[i-1] * i;
        fact[i] %= mod;
        inv_fact[i] = pow(fact[i], mod-2, mod);
    }
    
    string s;
    cin >> s;

    for (int i=0; i<s.length(); i++) {
        c[s[i]-'a']++;
    }

    ll prod = fact[s.length()];
    for (int i=0; i<26; i++) {
        prod *= inv_fact[c[i]];
        prod %= mod;
    }

    cout << prod;
}