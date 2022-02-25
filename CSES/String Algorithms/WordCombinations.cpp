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

// https://cses.fi/problemset/task/1731

const int MaxK = 1e5+1, MaxN = 5003;
string s;
int n, k;
string all[MaxK];
ll mod = 1e9+7, memo[MaxN];

ll dp(int cur) {
    if (cur >= n) return 1;
    if (memo[cur] != -1) return memo[cur];
    ll ans=0;

    int mmin=0;
    int min=0;
    int max = k-1;
    while (min<max) {
        int middle = (min+max)/2;
        if (all[middle][0] < s[cur]) {
            min = middle+1;
        }
        else if (all[middle][0] >= s[cur]) {
            max = middle;
        }
    }
    mmin = min;

    int mmax=0;
    min=0;
    max = k-1;
    while (min<max) {
        int middle = (min+max+1)/2;
        if (all[middle][0] <= s[cur]) {
            min = middle;
        }
        else if (all[middle][0] > s[cur]) {
            max = middle-1;
        }
    }
    mmax = min;
    for (int j=mmin; j<=mmax; j++) {
        if (all[j].length() + cur - 1 < n) {
            if (all[j] == s.substr(cur, all[j].length())) {
                // cout << all[j] << " here " << cur + all[j].length() << "\n";
                ans += dp(cur + all[j].length());
                ans %= mod;
            }
        }
    }

    return memo[cur] = ans;
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> s >> k;
    n = s.length();
    for (int i=0; i<k; i++) {
        cin >> all[i];
    }
    sort(all, all+k);
    fill(memo, memo+n, -1);
    
    cout << dp(0);
}