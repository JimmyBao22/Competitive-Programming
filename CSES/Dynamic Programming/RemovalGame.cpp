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

// https://cses.fi/problemset/task/1097

const int MaxN = 5e3+10;
ll arr[MaxN];
ll memo[MaxN][MaxN][2];
int n;
ll INF = 1e18;

ll dp(int left, int right, int turn) {
    if (left > right) return 0;
    if (memo[left][right][turn] != -1) return memo[left][right][turn];
    ll ans = 0;

    if (turn == 0) {
        ans = max(dp(left + 1, right, 1) + arr[left], dp(left, right-1, 1) + arr[right]);
    }
    else {
        ans = min(dp(left+1, right, 0), dp(left, right-1, 0));
    }

    return memo[left][right][turn] = ans;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n;
    for (int i=0; i<n; i++) cin >> arr[i];

    for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++) {
            for (int k=0; k<2; k++) {
                memo[i][j][k] = -1;
            }
        }
    }

    cout << dp(0, n-1, 0);
}