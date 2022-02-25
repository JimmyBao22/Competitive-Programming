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

// https://cses.fi/problemset/task/1671

const int MaxN = 1e5+10;
vector<pair<ll, int>> g[MaxN];      // length, dest
ll dist[MaxN];          // dist[i] = shortest distance from src to i
int parent[MaxN];       // parent[i] = parent of i that gives shortest dist
ll INF = 1e18;
int n, m;

    // O((N+M)logM)
void dijkstras(int start) {
    using T = pair<ll, int>;
    priority_queue<T, vector<T>, greater<T>> pq;
    bool visited[n];
    for (int i=0; i<n; i++) {
        dist[i] = INF;
        visited[i] = false;
    }
    dist[start] = 0;
    parent[start] = -1;
    
    pq.push({0, start});
    while (!pq.empty()) {
        T cur = pq.top(); 
        pq.pop();
        int node = cur.second;
        if (visited[node]) continue;
        visited[node] = true;
        
        for (auto i : g[node]) {
            if (visited[i.second]) continue;
            
            if (cur.first + i.first < dist[i.second]) {
                dist[i.second] = cur.first + i.first;
                parent[i.second] = node;
                pq.push({dist[i.second], i.second});
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
    for (int i=0; i<n; i++) {
        cout << dist[i] << " ";
    }
}