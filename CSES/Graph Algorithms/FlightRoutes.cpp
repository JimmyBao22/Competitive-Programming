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

// https://cses.fi/problemset/task/1196

const int MaxN = 1e5+10, c = 20;
vector<pair<ll, int>> g[MaxN];      // length, dest
ll dist[MaxN][c];          // dist[i] = shortest distance from src to i
int pointer[MaxN];          // number of times this node has been passed
ll INF = 1e18;
int n, m, k;

    // O((N+M)logM)
void dijkstras(int start) {
    using T = pair<ll, int>;
    priority_queue<T, vector<T>, greater<T>> pq;
    for (int i=0; i<n; i++) {
        for (int j=0; j<c; j++) {
            dist[i][j] = INF;
        }
    }
    dist[start][0] = 0;
    pointer[start]++;

    pq.push({0, start});
    while (!pq.empty()) {
        T cur = pq.top(); 
        pq.pop();
        int node = cur.second;
        if (pointer[node] >= c) continue;
        dist[node][pointer[node]] = cur.first;
        pointer[node]++;
        
        for (auto i : g[node]) {
            if (pointer[i.second] >= c) continue;
            
            if (cur.first + i.first < dist[i.second][pointer[i.second]]) {
                // dist[i.second][pointer[i.second]] = cur.first + i.first;
                // pointer[i.second]++;
                pq.push({cur.first + i.first, i.second});
            }
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> m >> k;

    for (int i=0; i<m; i++) {
        int a, b; ll weight;
        cin >> a >> b >> weight;
        a--; b--;

        g[a].push_back({weight, b});
    }

    dijkstras(0);

    sort(dist[n-1], dist[n-1] + c);

    for (int j=0; j<k; j++) {
        cout << dist[n-1][j] << " ";
    }
}