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

// https://cses.fi/problemset/task/1130

const int MaxN = 2e5+10;
vector<int> g[MaxN];
int n, upwards[MaxN], c=0;

void dfs(int node, int p) {
    for (auto i : g[node]) {
        if (i != p) {
            dfs(i, node);
            upwards[node] += upwards[i];
        }
    }
    // cout << node << " " << upwards[node] << endl;
    if (upwards[node]) {
        c++;
        upwards[node] = 0;
    }
    else {
        upwards[node] = 1;
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n;
    for (int i=0; i<n-1; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        g[a].push_back(b);
        g[b].push_back(a);
    }

    dfs(0, -1);
    cout << c;
}