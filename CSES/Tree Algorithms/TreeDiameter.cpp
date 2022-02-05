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

// https://cses.fi/problemset/task/1131

const int MaxN = 2e5+10;
vector<int> g[MaxN];
int n, dist[MaxN];

void dfs(int node, int p) {
    for (auto i : g[node]) {
        if (i != p) {
            dist[i] = dist[node]+1;
            // cout << i << " " << dist[i] << endl;
            dfs(i, node);
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n;
    for (int i=0; i<n-1; i++ ) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        g[a].push_back(b);
        g[b].push_back(a);
    }

    dfs(0, -1);

    int node=0;
    int maxdist=dist[0];
    for (int i=0; i<n; i++) {
        if (dist[i] > maxdist) {
            maxdist = dist[i];
            node = i;
        }
        dist[i] = 0;
    }

    dfs(node, -1);

    maxdist = dist[0];
    for (int i=0; i<n; i++) {
        maxdist = max(maxdist, dist[i]);
    }

    cout << maxdist;
}