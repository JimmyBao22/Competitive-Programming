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

// https://cses.fi/problemset/task/1649/

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
        if (lx >= r || rx <= l) return 1e9;	// do not intersect this segment
        if (l <= lx && rx <= r) return tree[x];	// inside whole segment
        int m = (lx + rx)/2;
        ll one = comp_seg(l, r, 2*x+1, lx, m); 
        ll two = comp_seg(l, r, 2*x+2, m, rx);
        return min(one, two);
    }
    
    void set(int i, ll v) { set(i, v, 0, 0, size); }	// arr[i] = v;
    
    void set(int i, ll v, int x, int lx, int rx) {
        if (rx - lx == 1) {		// in leaf node aka bottom level
            tree[x] = v; return;
        }
        int m = (lx + rx)/2;
        if (i < m) set(i, v, 2*x+1, lx, m); 	// go to left subtree
        else set(i, v, 2*x+2, m, rx);			// go to right subtree
        tree[x] = min(tree[2*x+1], tree[2*x+2]);
    }
    
    void build(ll arr[], int n) {	build(arr, n, 0, 0, size); }	// arr is the orig arr
    
    void build(ll arr[], int n, int x, int lx, int rx) {
        if (rx - lx == 1) {		// in leaf node aka bottom level
            if (lx < n) tree[x] = arr[lx];
            return;
        }
        int m = (lx + rx)/2;
        build(arr, n, 2*x+1, lx, m);	build(arr, n, 2*x+2, m, rx);
        tree[x] = min(tree[2*x+1], tree[2*x+2]);
    }
    
    void print() {
        for (int i=0; i<(int)tree.size(); i++) cout << tree[i] << " ";
        cout << "\n";
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n, q;
    cin >> n >> q;
    ll arr[n];
    for (int i=0; i<n; i++) cin >> arr[i];
    SegTree s(n);
    s.build(arr, n);

    for (int i=0; i<q; i++) {
        int t, a, b; 
        cin >> t >> a >> b;
        if (t == 1) {
            s.set(a-1, b);
        }
        else {
            cout << s.comp_seg(a-1, b) << "\n";
        }
    }
}