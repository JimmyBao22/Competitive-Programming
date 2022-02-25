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

// https://cses.fi/problemset/task/1679

const int MaxN = 1e5+10;
vector<int> g[MaxN];
queue<int> q;
vector<int> finalsequence;
int indegree[MaxN];
bool visited[MaxN];
bool visited1[MaxN], open[MaxN];
int n, m;

bool dfscheck(int node) { 
    if (open[node]) return true;
    if (visited1[node]) return false;
    visited1[node] = true;
    open[node] = true;
    for (auto i : g[node]) {
        if (dfscheck(i)) return true;
    }
    open[node] = false;
    return false;
}

void toposort() {
    for (int i=0; i<n; i++) {
        if (indegree[i] == 0) {
            q.push(i);
            visited[i] = true;
        }
    }
    
    while (!q.empty()) {
        int cur = q.front();
        q.pop();
        for (auto a : g[cur]) {
            indegree[a]--;
            if (indegree[a] == 0 && !visited[a]) {
                q.push(a);
                visited[a] = true;
            }
        }
        finalsequence.push_back(cur);
    }
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
        indegree[b]++;
    }

    for (int i=0; i<n; i++) {
        if (!visited1[i] && dfscheck(i)) {
            cout << "IMPOSSIBLE";
            return 0;
        }
    }
    toposort();

    for (int i=0; i<(int)finalsequence.size(); i++) {
        cout << finalsequence[i]+1 << " ";
    }
}