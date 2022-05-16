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

// https://cses.fi/problemset/task/2206

ll INF = 1e18;

struct SegTree {
    int size=1;
    vector<ll> tree;
    
    SegTree (int n) {			
        while (size < n) size *= 2;
        tree.assign(2*size, INF);
    }
    
    // random computation on segment (l to r-1)
    ll comp_seg(int l, int r) { return comp_seg(l, r, 0, 0, size); }
    
    ll comp_seg(int l, int r, int x, int lx, int rx) {
        if (lx >= r || rx <= l) return INF;	// do not intersect this segment
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

    void print() {
        for (int i=0; i<(int)tree.size(); i++) cout << tree[i] << " ";
        cout << "\n";
    }
};

const int MaxN = 2e5+1;
int n, q;
ll arr[MaxN];

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> q;
    SegTree left(n), right(n);
    for (int i=0; i<n; i++) {
        cin >> arr[i];
        left.set(i, arr[i] + i);
        right.set(i, arr[i] + (n-i-1));
    }

    // left.print();
    // right.print();

    while (q--) {
        int type, k;
        cin >> type >> k;
        k--;
        if (type == 1) {
            int x;
            cin >> x;
            left.set(k, x + k);
            right.set(k, x + (n-k-1));
        }
        else {
            ll l = left.comp_seg(k, n) - k;
            ll r = right.comp_seg(0, k+1) - (n-k-1);
            cout << min(l,r) << "\n";
        }
    }
}

/*
    left means that at index i, you store arr[i] + i
    right means that at index i, you store arr[i] + (n - i - 1);

    this way, when you query index k, want to find minimum arr[j] + |j - k| for some j
        split into j>=k and j<=k

        when j >= k, then when you use left and compute segment from k to n, then all you need to do is 
            subtract k out then you get arr[j] + j - k (arr[j] + j - k)
        when j <= k, then when you use right and compute seg from 0 to k, then all you need to do is subtract
            n-k-1 out and then you get arr[j] + k - j (arr[j] + n - j - 1 - (n - k - 1)) = (arr[j] + k - j)
*/