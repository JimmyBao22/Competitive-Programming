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

// https://cses.fi/problemset/task/1667

const int MaxN = 1e5+10;
vector<pair<ll, int>> g[MaxN];      // length, dest
ll dist[MaxN];
int parent[MaxN];
bool visited[MaxN];
ll INF = 1e18;
int n, m;

    // O((N+M)logM)
void dijkstras(int start) {
    priority_queue<pair<ll, int>, vector<pair<ll,int>>, greater<pair<ll, int>>> pq;
    for (int i=0; i<n; i++) dist[i] = INF;
    dist[start] = 0;
    parent[start] = -1;
    
    pq.push({0, start});
    while (!pq.empty()) {
        pair<ll, int> cur = pq.top(); 
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

vector<int> Backtrack(int dest) {
    vector<int> path;
    if (dist[dest] == INF) {
        cout << "IMPOSSIBLE";
        return path;
    }
    while (dest!= -1) {
        path.push_back(dest);
        dest = parent[dest];
    }
    return path;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> m;
    for (int i=0; i<m; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        g[a].push_back({1, b});
        g[b].push_back({1, a});
    }

    dijkstras(0);
    vector<int> ans = Backtrack(n-1);
    if (ans.size() == 0) return 0;

    cout << ans.size() << "\n";
    for (int i=ans.size()-1; i>=0; i--) {
        cout << ans[i]+1 << " ";
    }
}