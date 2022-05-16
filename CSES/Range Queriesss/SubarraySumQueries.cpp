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

// https://cses.fi/problemset/task/1190

struct E {
    ll sum, pref, suf, sub;
    E(ll a, ll b, ll c, ll d) {
        sum = a;
        pref = b;
        suf = c;
        sub = d;
    }
};

struct SegTree {
    int size=1;
    vector<E> tree;
    
    SegTree (int n) {			
        while (size < n) size *= 2;
        for (int i=0; i<2*size; i++) {
            tree.pb(E(0,0,0,0));
        }
    }

    E combine(E one, E two) {
        ll sum = one.sum + two.sum;
        ll pref = max(one.pref, one.sum + two.pref);
        ll suf = max(two.suf, two.sum + one.suf);
        ll sub = max(one.sub, max(two.sub, one.suf + two.pref));
        // E cur(sum, pref, suf, sub);
        // return cur;
        return E(sum, pref, suf, sub);
    }
    
    void set(int i, ll v) { set(i, v, 0, 0, size); }	// arr[i] = v;
    
    void set(int i, ll v, int x, int lx, int rx) {
        if (rx - lx == 1) {		// in leaf node aka bottom level
            if (v > 0) {
                tree[x] = E(v,v,v,v);;
            }
            else {
                tree[x] = E(v,0,0,0);;
            }
            return;
        }
        
        int m = (lx + rx)/2;
        if (i < m) {		// go to left subtree
            set(i, v, 2*x+1, lx, m);
        }
        else {				// go to right subtree
            set(i, v, 2*x+2, m, rx);
        }
        
        tree[x] = combine(tree[2*x+1], tree[2*x+2]);
    }
    
    void build(ll arr[], int n) {	build(arr, n, 0, 0, size); }	// arr is the orig arr
    
    void build(ll arr[], int n, int x, int lx, int rx) {
        if (rx - lx == 1) {		// in leaf node aka bottom level
            if (lx < n) {
                if (arr[lx] > 0) {
                    tree[x] = E(arr[lx], arr[lx],arr[lx],arr[lx]);
                }
                else {
                    tree[x] = E(arr[lx], 0, 0, 0);;
                }
            }
            return;
        }
        
        int m = (lx + rx)/2;
        build(arr, n, 2*x+1, lx, m);
        build(arr, n, 2*x+2, m, rx);
        tree[x] = combine(tree[2*x+1], tree[2*x+2]);
    }
};

const int MaxN = 2e5+1;
int n, m;
ll arr[MaxN];

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> n >> m;
    for (int i=0; i<n; i++) {
        cin >> arr[i];
    }

    SegTree c(n);
    c.build(arr, n);

    for (int i=0; i<m; i++) {
        int a, b;
        cin >> a >> b;
        c.set(a-1, b);
        cout << c.tree[0].sub << "\n";
    }
}