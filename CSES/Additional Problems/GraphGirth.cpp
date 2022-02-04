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

// https://cses.fi/problemset/task/1707

const int MaxN = 2501, INF = 1e9;
vector<int> g[MaxN];
int n, m, ans = INF;
int visited[MaxN];

void dfs(int node, int p, int depth) {
    if (visited[node] <= depth) {
        ans = min(ans, depth + visited[node]);
        return;
    }
    visited[node] = depth;
    if (depth + visited[node] >= ans) return;
    for (auto i : g[node]) {
        if (i != p) {
            dfs(i, node, depth+1);
        }
    }
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> n >> m;
    for (int i=0; i<m; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        g[a].pb(b);
        g[b].pb(a);
    }

    for (int i=0; i<n; ++i) {
        for (int j=0; j<n; j++) visited[j] = INF;
        dfs(i, i, 0);
    }

    if (ans == 1e9) ans = -1;
    cout << ans;
}