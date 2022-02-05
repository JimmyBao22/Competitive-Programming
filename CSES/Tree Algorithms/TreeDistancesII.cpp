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

// https://cses.fi/problemset/task/1133

const int MaxN = 2e5+10;
vector<int> g[MaxN];
int n, numsub[MaxN];
        // number of nodes in subtree
ll dist[MaxN];

void finddist(int node, int p, int d) {
    dist[0] += d;
    for (auto i : g[node]) {
        if (i != p) {
            finddist(i, node, d+1);
            numsub[node] += numsub[i] + 1;
        }
    }
}

void dfs(int node, int p) {
    if (p != -1) {
        dist[node] = dist[p];
        dist[node] += n - 1 - numsub[node];
        dist[node] -= (numsub[node] + 1);
    }
    for (auto i : g[node]) {
        if (i != p) {
            dfs(i, node);
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n;
    for (int i=0; i<n-1; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        g[a].push_back(b);
        g[b].push_back(a);
    }

    finddist(0, -1, 0);

    dfs(0, -1);

    for (int i=0; i<n; i++) {
        cout << dist[i] << " ";
    }
}