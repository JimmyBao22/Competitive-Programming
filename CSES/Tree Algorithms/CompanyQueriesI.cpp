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

// https://cses.fi/problemset/task/1687

const int MaxN = 2e5+10, LOG = 32;
vector<int> g[MaxN];
int parent[MaxN][LOG];
int n, q;

void dfs(int node, int p) {
    parent[node][0] = p;
    for (auto i : g[node]) {
        if (i == p) continue;
        dfs(i, node);
    }
}

void precomp() {
    parent[0][0] = -1;	// parent of root = -1
    for (int i=1; i<LOG; i++) {
        for (int j=0; j<n; j++) {
            if (parent[j][i-1] != -1) {
                parent[j][i] = parent[parent[j][i-1]][i-1];
            }
            else parent[j][i] = -1;
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> q;
    for (int i=0; i<n-1; i++) {
        int a;
        cin >> a;
        a--;
        g[i+1].push_back(a);
        g[a].push_back(i+1);
    }

    dfs(0, -1);	// start from root node
    precomp();

    for (int i=0; i<q; i++) {
        int x, k;
        cin >> x >> k;
        x--;
        for (int i=LOG-1; i>=0; i--) {
            if ((k >> i) & 1) {
                x = parent[x][i];
                if (x == -1) break;
            }
        }
        if (x == -1) cout << x << "\n";
        else cout << x+1 << "\n";
    }
}