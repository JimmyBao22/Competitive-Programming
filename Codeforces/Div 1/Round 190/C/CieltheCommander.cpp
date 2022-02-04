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

// https://codeforces.com/problemset/problem/321/C

const int MaxN = 1e5+1;
vector<int> g[MaxN];
set<int> g2[MaxN];
int subtreeSize[MaxN];
char ans[MaxN];
int n;

int FindSizes(int node, int p) {
    subtreeSize[node] = 1;
    for (auto i : g2[node]) {
        if (i != p) {
            subtreeSize[node] += FindSizes(i, node);
        }
    }

    return subtreeSize[node];
}

int getCentroid(int node, int p, int s) {
    for (auto i : g2[node]) {
        if (i != p && subtreeSize[i] > s/2) {
            return getCentroid(i, node, s);
        }
    }

    return node;
}

void CDbuild(int node, int p, int d) {
    int s = FindSizes(node, p); 						// find the size of each subtree
    int centroid = getCentroid(node, p, s); 			// find the centroid
    if (p == -1) p = centroid; 					// parent of root is the root itself
    ans[centroid] = (char)(d + 'A');

    // for each tree remove the centroid and build
    for (auto i : g2[centroid]) {
        g2[i].erase(centroid);
        CDbuild(i, centroid, d+1);
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
        g2[a].insert(b);
        g[b].pb(a);
        g2[b].insert(a);
    }

    CDbuild(0, -1, 0);
    for (int i=0; i<n; i++) {
        cout << ans[i] << " ";
    }		
}