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

// https://cses.fi/problemset/task/1202

const int MaxN = 1e5+10;
vector<pair<ll, int>> g[MaxN];      // length, dest
pair<ll, pair<ll, pair<int, int>>> dist[MaxN];          // dist[i] = shortest distance from src to i, count, min edges
                                                                // to reach this, max edges to reach this
ll INF = 1e18, mod = 1e9+7;
int n, m;

    // O((N+M)logM)
void dijkstras(int start) {
    using T = pair<ll, int>;
                        // length, dest, num
    priority_queue<T, vector<T>, greater<T>> pq;
    bool visited[n];
    for (int i=0; i<n; i++) {
        dist[i] = {INF, {0, {(int)1e9, 0}}};
        visited[i] = false;
    }
    dist[start] = {0, {1, {0, 0}}};
    
    pq.push({0, start});
    while (!pq.empty()) {
        T cur = pq.top();
        pq.pop();
        int node = cur.second;
        if (cur.first > dist[node].first) continue;
        if (visited[node]) continue;
        visited[node] = true;
        
        for (auto i : g[node]) {
            if (visited[i.second]) continue;
            
            if (cur.first + i.first < dist[i.second].first) {
                dist[i.second].first = cur.first + i.first;
                dist[i.second].second.first = dist[node].second.first;
                dist[i.second].second.first %= mod;
                dist[i.second].second.second.first = dist[node].second.second.first+1;
                dist[i.second].second.second.second = dist[node].second.second.second+1;
                pq.push({dist[i.second].first, i.second});
            }
            else if (cur.first + i.first == dist[i.second].first) {
                dist[i.second].second.first += dist[node].second.first;
                dist[i.second].second.first %= mod;
                dist[i.second].second.second.first = min(dist[i.second].second.second.first, dist[node].second.second.first+1);
                dist[i.second].second.second.second = max(dist[i.second].second.second.second, dist[node].second.second.second+1);
            }
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> m;

    for (int i=0; i<m; i++) {
        int a, b; ll weight;
        cin >> a >> b >> weight;
        a--; b--;

        g[a].push_back({weight, b});
    }

    dijkstras(0);

    cout << dist[n-1].first << " " << dist[n-1].second.first << " " << dist[n-1].second.second.first << " " << dist[n-1].second.second.second;
}