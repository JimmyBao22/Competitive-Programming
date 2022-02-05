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

// https://cses.fi/problemset/task/1753

const int MaxN = 1e6 + 1;
string a, b;
int n, m;
ll pref1[MaxN], pref2[MaxN], power[MaxN];
ll mod = 1e9+7;
ll p = 97;

void calc_power() {
    power[0] = 1;
    for (int i=1; i<MaxN; i++) {
        power[i] = power[i-1] * p;
        power[i] %= mod;
    }
}

void prefHash(string s, ll pref[]) {
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
        return (((pref1[j] - (pref1[i-1] * power[j - i + 1])%mod)%mod+mod)%mod);
    }
    else {
        return pref1[j];
    }
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> a >> b;
    n = a.length(), m = b.length();
    calc_power();
    prefHash(a, pref1);
    prefHash(b, pref2);
    int count = 0;
    for (int i=0; i+m-1<n; i++) {
        if (SubstringHash(i, i+m-1) == pref2[m-1]) {
            count++;
        }
    }
    cout << count;
}