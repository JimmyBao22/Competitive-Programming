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

// https://cses.fi/problemset/task/1668

const int MaxN = 1e5+10;
vector<int> g[MaxN];
bool visited[MaxN], works=true;
int n, m, type[MaxN];

void dfs (int node, int t) {
    if (type[node] != 0 && type[node] != t) {
        works=false;
        return;
    }
    if (visited[node]) return;
    visited[node] = true;
    type[node] = t;
    
    for (auto i : g[node]) {
        if (t == 1) {
            dfs(i, 2);
        }
        else {
            dfs(i, 1);
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> m;
    for (int i=0; i<m; ++i) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        g[a].push_back(b);
        g[b].push_back(a);
    }

    for (int i=0; i<n; i++) {
        if (!visited[i]) {
            dfs(i, 1);
        }
    }

    if (!works) {
        cout << "IMPOSSIBLE";
        return 0;
    }

    for (int i=0; i<n; i++) {
        cout << type[i] << " ";
    }
}