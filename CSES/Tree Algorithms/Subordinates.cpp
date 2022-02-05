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

// https://cses.fi/problemset/task/1674

const int MaxN = 2e5+10;
vector<int> g[MaxN];
int n, numsub[MaxN];

void dfs(int node, int p) {
    for (auto i : g[node]) {
        if (i != p) {
            dfs(i, node);
            // cout << i << " " << node << endl;
            numsub[node] += numsub[i] + 1;
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n;
    for (int i=0; i<n-1; i++) {
        int a;
        cin >> a;
        a--;
        g[a].push_back(i+1);
    }

    dfs(0, -1);

    for (int i=0; i<n; i++) {
        cout << numsub[i] << " ";
    }
}