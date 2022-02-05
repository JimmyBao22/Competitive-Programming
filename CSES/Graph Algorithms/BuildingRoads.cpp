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

// https://cses.fi/problemset/task/1666

const int MaxN = 1e5+10;
vector<int> g[MaxN];
int n, m;
vector<vector<int>> components;
bool visited[MaxN];

void dfs(int node, vector<int>& cur) {
    if (visited[node]) return;
    visited[node] = true;
    cur.push_back(node);
    for (auto i : g[node]) {
        dfs(i, cur);
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
        g[b].push_back(a);
    }

    for (int i=0; i<n; i++) {
        if (!visited[i]) {
            vector<int> cur;
            dfs(i, cur);
            components.push_back(cur);
        }
    }

    cout << components.size()-1 << "\n";
    for (int i=1; i<(int)components.size(); i++) {
        cout << components[0][0]+1 << " " << components[i][0]+1 << "\n";
    }
}