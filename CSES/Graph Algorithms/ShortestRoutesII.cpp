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

// https://cses.fi/problemset/task/1672

const int MaxN = 501;
vector<pair<ll, int>> g[MaxN];      // length, dest
ll dist[MaxN][MaxN];
ll INF = 1e18;
int n, m;

void FW () {
    for (int k=0; k<n; k++) {
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]);
            }
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int q;
    cin >> n >> m >> q;

    for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++) {
            dist[i][j] = INF;
        }
        dist[i][i] = 0;
    }

    for (int i=0; i<m; i++) {
        int a, b; ll weight;
        cin >> a >> b >> weight;
        a--; b--;

        dist[a][b] = min(dist[a][b], weight);
        dist[b][a] = min(dist[b][a], weight);
    }

    FW();

    for (int i=0; i<q; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        if (dist[a][b] == INF) {
            cout << -1 << "\n";
        }
        else {
            cout << dist[a][b] << "\n";
        }
    }
}