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

// https://cses.fi/problemset/task/1673

struct Edge {
    int start, dest; ll weight;
};

const int MaxN = 2501, MaxM = 5001;
ll INF = 1e18;
Edge edges[MaxM];
vector<int> g[MaxN];
ll dist[MaxN];
int n, m;
bool can_reach[MaxN][MaxN], visited[MaxN];

void dfs(int cur, int orig) {
    if (visited[cur]) return;
    visited[cur] = true;
    can_reach[orig][cur] = true;
    for (auto i : g[cur]) {
        dfs(i, orig);
    }
}

bool BF(int start) {
    for (int i=0; i<n; i++) dist[i] = -INF;
    dist[start] = 0;
    for (int i=0; i<n-1; i++) {
        for (int j=0; j<m; j++) {
            int from = edges[j].start;
            int to = edges[j].dest;
            ll weight = edges[j].weight;
            dist[to] = max(dist[to], dist[from]+weight);
        }
    }
    
    // check for infinite cycles
    for (int j=0; j<m; j++) {
        int from = edges[j].start;
        int to = edges[j].dest;
        ll weight = edges[j].weight;
        // if (((can_reach[0][to] && can_reach[to][0]) || (can_reach[n-1][to] && can_reach[to][n-1])) 
        //     && dist[from]+weight > dist[to]) return false;	// infinite cycle
        if (can_reach[0][to] && can_reach[to][n-1] && dist[from]+weight > dist[to]) return false;	// infinite cycle
    }
    return true;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> m;
    for (int i=0; i<m; i++) {
        cin >> edges[i].start >> edges[i].dest >> edges[i].weight;
        edges[i].start--; edges[i].dest--;
        g[edges[i].start].push_back(edges[i].dest);
    }

    for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++) {
            visited[j] = false;
        }
        dfs(i, i);
    }

    if (!BF(0)) {
        cout << -1;
    }
    else {
        cout << dist[n-1];
    }
}