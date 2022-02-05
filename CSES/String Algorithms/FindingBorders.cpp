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
#define pb push_back

// https://cses.fi/problemset/task/1732

const int MaxN = 1e6 + 1;
ll pref[MaxN], power[MaxN];
ll mod = 1e9+7;
ll p = 97;
string s;
int n;

void calc_power() {
    power[0] = 1;
    for (int i=1; i<MaxN; i++) {
        power[i] = power[i-1] * p;
        power[i] %= mod;
    }
}

void prefHash(string s) {
    if (s.length() > 0) pref[0] = s[0] - 'a';
    for (int i=1; i<(int)(s.length()); i++) {
        pref[i] = ((pref[i-1]*p)%mod + (s[i] - 'a'))%mod;
    }
}

    // hash of substring from i to j
    // pref is array calculated in prefhash. Therefore only use this if you need
        // to calculate hash of a lot of substrings
ll SubstringHash(int i, int j) {
    if (i != 0) {
        return (((pref[j] - (pref[i-1] * power[j - i + 1])%mod)%mod+mod)%mod);
    }
    else {
        return pref[j];
    }
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    calc_power();
    cin >> s;
    n = s.length();
    prefHash(s);
    for (int i=0; i<n-1; i++) {
        int length = i-0+1;
        if (SubstringHash(0, i) == SubstringHash(n-length, n-1)) {
            cout << length << " ";
        }
    }
}