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

// https://cses.fi/problemset/task/1653
// Solution = https://usaco.guide/CPH.pdf#page=113

const int MaxN = 20;
ll arr[MaxN];
pair<ll, ll> dp[1 << MaxN];     // count, lasttook
int n, x;
ll INF = 1e9;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> x;
    for (int i=0; i<n; i++) cin >> arr[i];
    dp[0] = {1, 0}; 
    for (int i=1; i<(1 << n); i++) {
        dp[i] = {INF, INF};
        for (int j=0; j<n; j++) {
            if ((i >> j)&1) {
                if (dp[i - (1 << j)].second + arr[j] <= x) {
                    dp[i] = min(dp[i], {dp[i - (1 << j)].first, dp[i - (1 << j)].second + arr[j]});
                }
                else {
                    dp[i] = min(dp[i], {dp[i - (1 << j)].first + 1, arr[j]});
                }
            }
        }
    }

    cout << dp[(1 << n) - 1].first;
}