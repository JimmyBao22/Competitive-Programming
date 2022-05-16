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

// https://cses.fi/problemset/task/1639

const int MaxN = 5e3+10, MaxM = 5e3+10;
string a, b;
int n, m;
int memo[MaxN][MaxM];
int INF = 1e9;

int dp(int first, int second) {
    if (first >= n) {
        return m - second;
    }
    if (second >= m) {
        return INF;
    }
    if (memo[first][second] != -1) return memo[first][second];

    int ans = INF;

    // don't take this first (remove it)
    ans = min(ans, dp(first+1, second)+1);

    if (a[first] == b[second]) {
            // take this first
        ans = min(ans, dp(first+1, second+1));
    }
    else {
            // replace character
        ans = min(ans, dp(first+1, second+1)+1);
    }

    // add extra character here
    ans = min(ans, dp(first, second+1)+1);

    return memo[first][second] = ans;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> a >> b;
    if (a.length() > b.length()) swap(a,b);
    n = a.length();
    m = b.length();

    for (int i=0; i<n; i++) {
        for (int j=0; j<m; j++) {
            memo[i][j] = -1;
        }
    }

    cout << dp(0,0);
}