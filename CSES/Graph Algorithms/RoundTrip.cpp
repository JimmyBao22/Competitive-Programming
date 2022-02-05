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

// https://cses.fi/problemset/task/1669

const int MaxN = 1e5+10;
vector<int> g[MaxN];
bool visited[MaxN];
int parent[MaxN];
int n, m, startnode=-1, endnode=-1;

bool dfs(int node, int p) {
    if (visited[node]) return false;
    visited[node] = true;
    parent[node] = p;
    for (auto i : g[node]) {
        if (i != p) {
            if (visited[i]) {
                startnode = i;
                endnode = node;
                return true;
            }
            if (dfs(i, node)) {
                return true;
            }
        }
    }
    return false;
}

vector<int> Backtrack(int node) {
    vector<int> ret;
    while (node != startnode) {
        ret.push_back(node);
        node = parent[node];
    }
    ret.push_back(startnode);
    ret.push_back(endnode);
    return ret;
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

    for (int i=0; i<n; i++) {
        parent[i] = -1;
    }

    for (int i=0; i<n; i++) {
        if (!visited[i]) {
            if (dfs(i, i)) {
                break;
            }
        }
    }

    if (startnode == -1) {
        cout << "IMPOSSIBLE";
        return 0;
    }
    vector<int> ans = Backtrack(endnode);
    cout << ans.size() << "\n";
    for (int i=0; i<ans.size(); i++) {
        cout << ans[i]+1 << " ";
    }
}