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

// https://cses.fi/problemset/task/1675

const int MaxN = 1e5+10;
const int MaxM = 1e5+10;
vector<pair<ll, int>> g[MaxN];      // length, dest
ll INF = 1e18;
int n, m;

    // O((N+M)logM)
ll MST(int start) {
    using T = pair<ll, int>;
    priority_queue<T, vector<T>, greater<T>> pq;
    bool visited[n];
    for (int i=0; i<n; i++) {
        visited[i] = false;
    }
    ll minlength = 0;
    pq.push({start, 0});

    while (!pq.empty()) {
        T cur = pq.top(); 
        pq.pop();
        int node = cur.second;
        if (visited[node]) continue;
        visited[node] = true;
        minlength += cur.first;
        
        for (auto i : g[node]) {
            if (visited[i.second]) continue;
            pq.push({i.first, i.second});
        }
    }
    for (int i=0; i<n; i++) {
        if (!visited[i]) return -1;
    }
    return minlength;
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
        g[b].push_back({l, a});
    }

    ll ans = MST(0);
    if (ans == -1) {
        cout << "IMPOSSIBLE";
    }
    else cout << ans;
}