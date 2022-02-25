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

// https://cses.fi/problemset/task/1678

const int MaxN = 1e5+10;
vector<int> g[MaxN], cycle;
bool visited[MaxN], open[MaxN];
int n, m;

bool findcycle(int node) {
    visited[node] = true;
    open[node] = true;
    for (auto i : g[node]) {
        if (open[i]) {
            cycle.pb(node);     // start cycle
            open[node] = false;
            open[i] = false;
            return true;
        }
        else if (!visited[i]) {
            if (findcycle(i)) {         // continue cycle
                if (open[node]) {
                    cycle.pb(node);
                    open[node] = false;
                    return true;
                }
                else {
                    cycle.pb(node);     // end cycle
                    return false;
                }
            }
            if (!cycle.empty()) return false;       // finished cycle
        }
    }
    open[node] = false;
    return false;
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
        // g[b].push_back(a);       // if undirected
    }

    for (int i=0; i<n; i++) {
        if (!visited[i]) {
            findcycle(i);
            if (!cycle.empty()) break;
        }
    }
    reverse(cycle.begin(), cycle.end());

    if (cycle.empty()) {
        cout << "IMPOSSIBLE";
    }
    else {
        cout << cycle.size()+1 << "\n";
        for (int i=0; i<(int)(cycle.size()); i++) {
            cout << cycle[i]+1 << " ";
        }
        cout << cycle[0]+1;
    }
}