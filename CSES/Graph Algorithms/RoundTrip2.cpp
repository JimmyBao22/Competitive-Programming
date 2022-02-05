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
int n, m;

bool dfs(int node, vector<int>& cur, set<int>& v) {
    if (cur.size() >= 3 && v.count(node) && cur[cur.size()-1] != node && cur[cur.size()-2] != node) {
        cur.push_back(node);
        return true;
    }
    if (visited[node]) return false;
    visited[node] = true;
    cur.push_back(node);
    v.insert(node);
    for (auto i : g[node]) {
        if (dfs(i, cur, v)) {
            return true;
        }
    }
    cur.erase(cur.end()-1);        // remove myself
    v.erase(node);
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
        g[b].push_back(a);
    }

    for (int i=0; i<n; i++) {
        if (!visited[i]) {
            vector<int> cur; set<int> v;
            if (dfs(i, cur, v)) {
                vector<int> good;
                for (int i=cur.size()-1; i>=0; i--) {
                    good.push_back(cur[i]);
                    if (i != cur.size()-1 && cur[i] == cur[cur.size()-1]) break;
                }
                cout << good.size() << "\n";
                for (int i=0; i<(int)good.size(); i++) {
                    cout << good[i]+1 << " ";
                }
                return 0;
            }
        }
    }
    cout << "IMPOSSIBLE";
}