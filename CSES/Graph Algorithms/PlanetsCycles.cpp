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

// https://cses.fi/problemset/task/1751

const int MaxN = 2e5+1;
int go[MaxN], dist[MaxN], curtimes[MaxN];
int n;

pair<int, int> dfs(int node, int time) {
    if (dist[node] != 0) {
        return {-1, dist[node]+1};
    }
    if (curtimes[node] != 0) {
        return {node, time - curtimes[node]};
    }
    curtimes[node] = time;
    pair<int, int> ret = dfs(go[node], time+1);
    dist[node] = ret.second;
    if (ret.first == node) {
        ret.first = -1;
        ret.second++;
    }
    else if (ret.first == -1) {
        ret.second++;
    }
    return ret;
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> n;
    for (int i=0; i<n; i++) {
        cin >> go[i];
        go[i]--;
    }

    for (int i=0; i<n; i++) {
        if (dist[i] == 0) {
            dfs(i, 1);
        }
    }

    for (int i=0; i<n; i++) {
        cout << dist[i] << " ";
    }
}