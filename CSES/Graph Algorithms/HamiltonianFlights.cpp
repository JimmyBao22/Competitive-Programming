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
typedef long double ld;
#define pb push_back

// https://cses.fi/problemset/task/1690

const int MaxN = 21, MaxK = (1 << 20);
int n, needed, m;
ll mod = 1e9+7, memo[MaxN][MaxK];
vector<int> g[MaxN];

ll dfs(int node, int count) {
    if (node == n-1) {
        if (count == 0) return 1;
        return 0;
    }
    if (memo[node][count] != -1) return memo[node][count];

    ll ans=0;
    for (auto i : g[node]) {
        if (((count >> i)&1) == 1) {
            ans += dfs(i, count - (1 << i));
            if (ans >= mod) ans -= mod;
        }
    }

    return memo[node][count] = ans;
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> n >> m;
    needed = (1<<n)-1;
    for (int i=0; i<m; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        g[a].pb(b);
    }

    for (int i=0; i<n; i++) {
        for (int j=0; j<=needed; j++) {
            memo[i][j] = -1;
        }
    }

    cout << dfs(0, needed - 1);
}