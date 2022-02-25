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

// https://cses.fi/problemset/task/1137

const int MaxN = 2e5+10;
vector<int> g[MaxN];
int start[MaxN], stop[MaxN];
ll val[MaxN];
int n, t=0, q;

struct SegTree {
    int size=1;
    vector<ll> tree;
    
    SegTree (int n) {			
        while (size < n) size *= 2;
        tree.assign(2*size, 0);
    }
    
    // random computation on segment (l to r-1)
    ll comp_seg(int l, int r) { return comp_seg(l, r, 0, 0, size); }
    
    ll comp_seg(int l, int r, int x, int lx, int rx) {
        if (lx >= r || rx <= l) return 0;	// do not intersect this segment
        if (l <= lx && rx <= r) return tree[x];	// inside whole segment
        int m = (lx + rx)/2;
        ll one = comp_seg(l, r, 2*x+1, lx, m); 
        ll two = comp_seg(l, r, 2*x+2, m, rx);
        return one + two;
    }
    
    void set(int i, ll v) { set(i, v, 0, 0, size); }	// arr[i] = v;
    
    void set(int i, ll v, int x, int lx, int rx) {
        if (rx - lx == 1) {		// in leaf node aka bottom level
            tree[x] = v; return;
        }
        int m = (lx + rx)/2;
        if (i < m) set(i, v, 2*x+1, lx, m); 	// go to left subtree
        else set(i, v, 2*x+2, m, rx);			// go to right subtree
        tree[x] = tree[2*x+1] + tree[2*x+2];
    }

    void print() {
        for (int i=0; i<(int)tree.size(); i++) cout << tree[i] << " ";
        cout << "\n";
    }
};

void dfs(int node, int p) {
    start[node] = t; t++;
    for (auto i : g[node]) {
        if (i != p) dfs(i, node);
    }
    stop[node] = t-1;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> q;

    for (int i=0; i<n; i++) cin >> val[i];

    for (int i=0; i<n-1; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        g[a].push_back(b);
        g[b].push_back(a);
    }

    dfs(0,-1);

    SegTree s(t);
    for (int i=0; i<n; i++) {
        s.set(start[i], val[i]);
    }
    for (int i=0; i<q; i++) {
        int a;
        cin >> a;
        if (a == 1) {
            int node, val;
            cin >> node >> val;
            node--;
            s.set(start[node], val);
        }
        else {
            int node;
            cin >> node;
            node--;
            cout << s.comp_seg(start[node], stop[node]) << "\n";
        }
    }
}