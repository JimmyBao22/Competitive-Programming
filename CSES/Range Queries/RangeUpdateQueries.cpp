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

// https://cses.fi/problemset/task/1651

struct SegTree {
    int size=1;
    vector<ll> tree;
    
    SegTree (int n) {			
        while (size < n) size *= 2;
        tree.assign(2*size, 0);
    }
    
    // random computation on index
    ll comp_index(int i) { return comp_index(i, 0, 0, size); }
    
    ll comp_index(int i, int x, int lx, int rx) {
        if (rx - lx == 1) return tree[x];	// in leaf node aka bottom level
        int m = (lx + rx)/2;	ll result = 0;
        if (i < m) result = comp_index(i, 2*x+1, lx, m);	// go to left subtree
        else result = comp_index(i, 2*x+2, m, rx);		// go to right subtree
        return result + tree[x];
    }
            
    // change segments (l to r-1)
    void modify(int l, int r, ll v) { modify(l, r, v, 0, 0, size); }
    
    void modify(int l, int r, ll v, int x, int lx, int rx) {
        if (lx >= r || rx <= l) return;		// do not intersect this segment
        if (l <= lx && rx <= r) {		// inside whole segment
            tree[x] += v;
            return;
        }
        int m = (lx + rx)/2;
        modify(l, r, v, 2*x+1, lx, m);	modify(l, r, v, 2*x+2, m, rx);
    }
    
    void build(ll arr[], int n) {	build(arr, n, 0, 0, size); }	// arr is the orig arr
    
    void build(ll arr[], int n, int x, int lx, int rx) {
        if (rx - lx == 1) {		// in leaf node aka bottom level
            if (lx < n) tree[x] = arr[lx];
            return;
        }
        int m = (lx + rx)/2;
        build(arr, n, 2*x+1, lx, m);	build(arr, n, 2*x+2, m, rx);
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
        int t;
        cin >> t;
        if (t == 1) {
            int a, b, u;
            cin >> a >> b >> u;
            s.modify(a-1, b, u);
        }
        else {
            int a;
            cin >> a;
            cout << s.comp_index(a-1) << "\n";
        }
    }
}