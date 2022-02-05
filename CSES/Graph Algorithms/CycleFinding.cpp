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

// https://cses.fi/problemset/task/1197

struct Edge {
    int start, dest; ll weight;
};

const int MaxN = 2501, MaxM = 5001;
ll INF = 1e18;
Edge edges[MaxM];
ll dist[MaxN];
int n, m, parent[MaxN], s, e;

bool BF(int start) {
    for (int i=0; i<n; i++) dist[i] = INF;
    dist[start] = 0;
    for (int i=0; i<n; i++) {
        for (int j=0; j<m; j++) {
            int from = edges[j].start;
            int to = edges[j].dest;
            ll weight = edges[j].weight;
            if (dist[from] + weight < dist[to]) {
                dist[to] = dist[from]+weight;
                parent[to] = from;
            }
        }
    }
    
    // check for negative cycles
    for (int j=0; j<m; j++) {
        int from = edges[j].start;
        int to = edges[j].dest;
        ll weight = edges[j].weight;
        if (dist[from]+weight < dist[to]) {
            s = from; e = to;
            return false;	// negative cycle
        }
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
    }
    
    if (BF(0)) {
        cout << "NO";
        return 0;
    }

    cout << "YES" << "\n";
    for (int i=0; i<n; i++) {
        e = parent[e];
    }
    vector<int> ans;
    for (int cur = e; ; cur = parent[cur]) {
        ans.pb(cur);
        if (ans.size() > 1 && cur == e) {
            break;
        }
    }
    reverse(ans.begin(), ans.end());
    for (auto i : ans) {
        cout << i+1 << " ";
    }
}