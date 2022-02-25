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

// https://cses.fi/problemset/task/1680

const int MaxN = 1e5+10;
vector<int> g[MaxN], rev[MaxN];
stack<int> s;
vector<int> fs;
bool visited[MaxN];
int depth[MaxN], parent[MaxN];
int n, m;

void fillstack(int cur) {
    visited[cur] = true;
    for (auto i : g[cur]) {
        if (!visited[i]) fillstack(i);
    }
    s.push(cur);
}

void toposort() {
    for (int i=0; i<n; i++) {
        if (!visited[i]) fillstack(i);
    }
    
    while (!s.empty()) {
        fs.push_back(s.top());
        s.pop();
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
        g[a].pb(b);
        rev[b].pb(a);
    }

    toposort();

    int i=n-1;
    for (; i>=0; i--) {
        if (fs[i] == n-1) break;
    }

    for (; i>=0; i--) {
        if (fs[i] == n-1 || depth[fs[i]]) {
            for (auto j : rev[fs[i]]) {
                if (depth[fs[i]] + 1 > depth[j]) {
                    depth[j] = depth[fs[i]] + 1;
                    parent[j] = fs[i];
                }
            }
        }
    }

    if (!depth[0]) {
        cout << "IMPOSSIBLE";
        return 0;
    }
    vector<int> ans;
    int node = 0;
    while (node != n-1) {
        ans.pb(node);
        node = parent[node];
    }
    ans.pb(n-1);
    cout << ans.size() << "\n";
    for (int i=0; i<(int)ans.size(); i++) {
        cout << ans[i]+1 << " ";
    }
}