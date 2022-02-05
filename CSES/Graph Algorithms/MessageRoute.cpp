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
vector<int> g[MaxN];      // length, dest
int parent[MaxN];
bool visited[MaxN];
ll INF = 1e18;
int n, m;

void bfs(int start) {
    queue<int> q;
    parent[start] = -1;
    visited[start] = true;
    q.push(start);
    while (!q.empty()) {
        int node = q.front(); 
        q.pop();
        
        for (auto i : g[node]) {
            if (visited[i]) continue;
            visited[i] = true;
            parent[i] = node;
            q.push(i);
        }
    }
}

vector<int> Backtrack(int dest) {
    vector<int> path;
    if (!visited[dest]) {
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
        g[a].push_back(b);
        g[b].push_back(a);
    }

    bfs(0);
    vector<int> ans = Backtrack(n-1);
    if (ans.size() == 0) return 0;

    cout << ans.size() << "\n";
    for (int i=ans.size()-1; i>=0; i--) {
        cout << ans[i]+1 << " ";
    }
}