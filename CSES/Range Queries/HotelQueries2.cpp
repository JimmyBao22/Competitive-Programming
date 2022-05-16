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

// https://cses.fi/problemset/task/1143

struct SegTree {
    int size=1;
    vector<ll> tree;
    
    SegTree (int n) {			
        while (size < n) size *= 2;
        tree.assign(2*size, 0);
    }
    
    // random computation on index
    int comp_index(int val) { return comp_index(val, 0, 0, size); }
    
    int comp_index(int val, int x, int lx, int rx) {
        if (rx - lx == 1) {
            if (tree[x] >= val) {
                tree[x] -= val;
                return lx;	// in leaf node aka bottom level
            }
            else {
                return rx;                
            }
        }
        int m = (lx + rx)/2;	int result = 0;
        if (tree[2*x+1] >= val) {
            result = comp_index(val, 2*x+1, lx, m);	// go to left subtree
        }
        else {
            result = comp_index(val, 2*x+2, m, rx);		// go to right subtree
        }
        tree[x] = max(tree[2*x+1], tree[2*x+2]);
        return result;
    }

    void build(ll arr[], int n) {	build(arr, n, 0, 0, size); }	// arr is the orig arr
    
    void build(ll arr[], int n, int x, int lx, int rx) {
        if (rx - lx == 1) {		// in leaf node aka bottom level
            if (lx < n) tree[x] = arr[lx];
            return;
        }
        int m = (lx + rx)/2;
        build(arr, n, 2*x+1, lx, m);	build(arr, n, 2*x+2, m, rx);
        tree[x] = max(tree[2*x+1], tree[2*x+2]);
    }
    
    void print() {
        for (int i=0; i<(int)tree.size(); i++) cout << tree[i] << " ";
        cout << "\n";
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n, m;
    cin >> n >> m;
    ll h[n];
    for (int i=0; i<n; ++i) {
        cin >> h[i];
    }
    SegTree s(n);
    s.build(h, n);

    for (int i=0; i<m; i++) {
        ll x;
        cin >> x;
        int ans = s.comp_index(x);

        if (ans >= n) {
            cout << "0 ";
        }
        else cout << ans+1 << " ";
    }
}