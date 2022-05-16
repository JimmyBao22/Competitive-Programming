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

// https://cses.fi/problemset/task/1637

unordered_map<int, int> memo;
int INF = 1e9;

int dp(int cur) {
    if (cur == 0) return 0;
    if (cur < 0) return INF;
    if (memo[cur]) return memo[cur];
    int ans=INF;
    int copy=cur;
    while (copy > 0) {
        int rem = copy % 10;
        copy /= 10;
        if (rem == 0) continue;
        ans = min(ans, dp(cur - rem) + 1);
    }
    return memo[cur] = ans;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;

    cout << dp(n);
}