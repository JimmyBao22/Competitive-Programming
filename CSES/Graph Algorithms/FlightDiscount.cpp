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

// https://cses.fi/problemset/task/1195

const int MaxN = 1e5+10;
vector<pair<ll, int>> g[MaxN];      // length, dest
ll dist[MaxN][2];          // dist[i] = shortest distance from src to i
ll INF = 1e18;
int n, m;

    // O((N+M)logM)
void dijkstras(int start) {
    using T = pair<ll, pair<int, int>>;
            // weight, node, 0/1
    priority_queue<T, vector<T>, greater<T>> pq;
    bool visited[n][2];
    for (int i=0; i<n; i++) {
        for (int j=0; j<2; j++) {
            dist[i][j] = INF;
            visited[i][j] = false;
        }
    }
    dist[start][0] = 0;
    
    pq.push({0, {start, 0}});
    while (!pq.empty()) {
        T cur = pq.top(); 
        pq.pop();
        int node = cur.second.first; int type = cur.second.second;
        if (visited[node][type]) continue;
        visited[node][type] = true;

        for (auto i : g[node]) {            
            if (!visited[i.second][type] && cur.first + i.first < dist[i.second][type]) {
                dist[i.second][type] = cur.first + i.first;
                pq.push({dist[i.second][type], {i.second, type}});
            }
            if (!visited[i.second][1] && type == 0) {
                if (cur.first + i.first/2 < dist[i.second][1]) {
                    dist[i.second][1] = cur.first + i.first/2;
                    pq.push({dist[i.second][1], {i.second, 1}});
                }
            }
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> m;

    for (int i=0; i<m; i++) {
        int a, b; ll l;
        cin >> a >> b >> l;
        a--; b--;

        g[a].push_back({l, b});
    }

    dijkstras(0);

    cout << min(dist[n-1][0], dist[n-1][1]);
}