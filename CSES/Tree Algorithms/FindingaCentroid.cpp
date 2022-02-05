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

// https://cses.fi/problemset/task/2079

const int MaxN = 2e5+1;
vector<int> g[MaxN];
int subtreeSize[MaxN];
int n;

int FindSizes(int node, int p) {
    subtreeSize[node] = 1;
    for (auto i : g[node]) {
        if (i != p) {
            subtreeSize[node] += FindSizes(i, node);
        }
    }

    return subtreeSize[node];
}

int getCentroid(int node, int p, int s) {
    for (auto i : g[node]) {
        if (i != p && subtreeSize[i] > s/2) {
            return getCentroid(i, node, s);
        }
    }

    return node;
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

    int s = FindSizes(0, -1);
    int centroid = getCentroid(0, -1, s);
    cout << centroid+1;
}