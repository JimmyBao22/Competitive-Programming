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

// https://cses.fi/problemset/task/1681

const int MaxN = 1e5+10;
vector<int> g[MaxN];
stack<int> s;
vector<int> fs;
bool visited[MaxN];
ll val[MaxN], mod = 1e9+7;
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
    }

    toposort();

    int i=0;
    for (; i<n; i++) {
        if (fs[i] == 0) break;
    }
    val[0] = 1;
    for (; i<n; i++) {
        if (fs[i] == 0 || val[fs[i]]) {
            for (auto j : g[fs[i]]) {
                val[j] += val[fs[i]];
                val[j] %= mod;
            }
        }
    }

    cout << val[n-1];
}