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

// https://cses.fi/problemset/task/1638

const int MaxN = 1e3+1;
ll dp[MaxN][MaxN];
ll mod = 1e9+7;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    char grid[n][n];
    for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++) cin >> grid[i][j];
    }

    dp[0][0] = (grid[0][0] != '*');

    for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++) {
            if (grid[i][j] == '*') continue;
            if (i>=1 && j>=1) {
                dp[i][j] += dp[i-1][j];
                dp[i][j] += dp[i][j-1];
                dp[i][j] %= mod;    
            }
            else if (i>=1) {
                dp[i][j] += dp[i-1][j];
            }
            else if (j >= 1) {
                dp[i][j] += dp[i][j-1];
            }
        }
    }

    cout << dp[n-1][n-1];
}