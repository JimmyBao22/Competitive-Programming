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

// https://cses.fi/problemset/task/1746

const int MaxN = 1e5+10;
const int MaxM = 101;
ll dp[MaxN][MaxM], mod = 1e9+7;
int n, m;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> m;
    int arr[n];
    for (int i=0; i<n; i++) cin >> arr[i];

    if (arr[0] == 0) {
        for (int i=1; i<=m; i++) dp[0][i] = 1;
    }
    else dp[0][arr[0]] = 1;

    for (int i=1; i<n; i++) {
        if (arr[i] == 0) {
            for (int j=1; j<=m; j++) {
                for (int k=max(j-1, 1); k<=j+1 && k<=m; k++) {
                    dp[i][j] += dp[i-1][k];
                    dp[i][j] %= mod;
                }
            }
        }
        else {
            for (int k=max(arr[i]-1, 1); k<=arr[i]+1 && k<=m; k++) {
                dp[i][arr[i]] += dp[i-1][k];
                dp[i][arr[i]] %= mod;
            }
        }
    }

    ll ans = 0;
    for (int i=1; i<=m; i++) {
        ans += dp[n-1][i];
        ans %= mod;
    }

    cout << ans;
}