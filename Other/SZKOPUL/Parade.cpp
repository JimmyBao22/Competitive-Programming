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
typedef long double ld;
#define pb push_back

// https://szkopul.edu.pl/problemset/problem/1QaUWE_ePAmitZjgAszOVD1U/site/?key=statement

const int MaxN = 2e5+1;
vector<int> g[MaxN];
int n, ans = 0;

int dfs(int node, int p) {
    vector<int> children;
    for (auto i : g[node]) {
        if (i != p) {   
            int ret = dfs(i, node);
            if (ret >= 0) children.pb(ret);
        }
    }
    if (children.size() == 0) return g[node].size()-2;
    sort(children.begin(), children.end());
    ans = max(ans, children[children.size() - 1] + (int)g[node].size());        // end here
    if (children.size() == 1) {
        return g[node].size() - 2 + children[0];
    }
    else {
        // combine two subroutes
        int comb = children[children.size()-1] + children[children.size()-2] + g[node].size();
        ans = max(ans, comb);

        // no combine, include this node
        return g[node].size() - 2 + children[children.size()-1];
    }
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> n;
    for (int i=0; i<n-1; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        g[a].pb(b);
        g[b].pb(a);
    }

    ans = max(ans, dfs(0, -1) + 1);
    cout << ans;
}