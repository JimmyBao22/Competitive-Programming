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

// https://cses.fi/problemset/task/1132

const int MaxN = 2e5+10;
vector<int> g[MaxN];
int n, dist[MaxN], parent[MaxN], maxdist[MaxN];

void dfs(int node, int p) {
    parent[node] = p;
    for (auto i : g[node]) {
        if (i != p) {
            dist[i] = dist[node]+1;
            dfs(i, node);
        }
    }
}

void dfs2(int node, int p1, int p2, int d) {
    maxdist[node] += d;
    for (auto i : g[node]) {
        if (i != p1 && i != p2) {
            dfs2(i, node, node, d+1);
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
    int maxd=dist[0];
    for (int i=0; i<n; i++) {
        if (dist[i] > maxd) {
            maxd = dist[i];
            node = i;
        }
        dist[i] = 0;
    }

    dfs(node, -1);

    node = 0;
    maxd = dist[0];
    for (int i=0; i<n; i++) {
        if (dist[i] > maxd) {
            node = i;
            maxd = dist[i];
        }
    }

    vector<int> sequence;
    while (node != -1) {
        sequence.push_back(node);
        node = parent[node];
    }

    maxdist[sequence[0]] = maxd;
    maxdist[sequence[sequence.size()-1]] = maxd;
    for (int i=1; i<(int)sequence.size()-1; i++) {
        dfs2(sequence[i], sequence[i-1], sequence[i+1], max(i, (int)sequence.size()-i-1));
    }

    for (int i=0; i<n; i++) {
        cout << maxdist[i] << " ";
    }
}