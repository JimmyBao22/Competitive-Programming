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

// https://cses.fi/problemset/task/1749

struct SegTree {
    int size=1;
    vector<ll> tree;
    
    SegTree (int n) {			
        while (size < n) size *= 2;
        tree.assign(2*size, 0);
    }

    // random computation on index
    int comp_index(int i) { return comp_index(i, 0, 0, size); }
    
    int comp_index(int i, int x, int lx, int rx) {
        if (rx - lx == 1) {
            tree[x] = 0;
            return lx;	// in leaf node aka bottom level
        }
        int m = (lx + rx)/2;	
        int result = 0;
        if (i <= tree[2*x+1]) {
            result = comp_index(i, 2*x+1, lx, m);	// go to left subtree
        }
        else {
            i -= tree[2*x+1];
            result = comp_index(i, 2*x+2, m, rx);		// go to right subtree
        }
        tree[x] = tree[2*x+1] + tree[2*x+2];
        return result;
    }

    void build(ll arr[], int n) {	build(arr, n, 0, 0, size); }	// arr is the orig arr
    
    void build(ll arr[], int n, int x, int lx, int rx) {
        if (rx - lx == 1) {		// in leaf node aka bottom level
            if (lx < n) tree[x] = 1;
            return;
        }
        int m = (lx + rx)/2;
        build(arr, n, 2*x+1, lx, m);	build(arr, n, 2*x+2, m, rx);
        tree[x] = tree[2*x+1] + tree[2*x+2];
    }
    
    void print() {
        for (int i=0; i<(int)tree.size(); i++) cout << tree[i] << " ";
        cout << "\n";
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    ll arr[n];
    for (int i=0; i<n; ++i) cin >> arr[i];
    SegTree s(n);
    s.build(arr, n);

    for (int i=0; i<n; i++) {
        int cur;
        cin >> cur;
        cout << arr[s.comp_index(cur)] << " ";
    }   
}