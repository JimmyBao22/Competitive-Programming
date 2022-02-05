#include <iostream>
#include <vector>
#include <map>
#include <cmath>
#include <algorithm>
#include <deque>
#include <queue>
#include <stack>
#include <set>
#include <unordered_set>
#include <string>
#include <string.h>
#include <array>
#include <unordered_map>
#include <unordered_set>
#include <iomanip>
using namespace std;
typedef long long ll;

// https://cses.fi/problemset/task/1645

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
        return max(one, two);
    }
    
    void set(int i, ll v) { set(i, v, 0, 0, size); }	// arr[i] = v;
    
    void set(int i, ll v, int x, int lx, int rx) {
        if (rx - lx == 1) {		// in leaf node aka bottom level
            tree[x] = v; return;
        }
        int m = (lx + rx)/2;
        if (i < m) set(i, v, 2*x+1, lx, m); 	// go to left subtree
        else set(i, v, 2*x+2, m, rx);			// go to right subtree
        tree[x] = max(tree[2*x+1], tree[2*x+2]);
    }
    
    void print() {
        for (int i=0; i<tree.size(); i++) cout << tree[i] << " ";
        cout << "\n";
    }
};

const int MaxN = 2e5+10;
vector<pair<ll, int>> arr(MaxN);

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    SegTree s(n);
    
    for (int i=0; i<n; i++) {
        cin >> arr[i].first;
        arr[i].second = i;
    }

    sort(arr.begin(), arr.begin() + n);
    int ans[n];
    int j=0;
    for (int i=0; i<n; i++) {
        if (arr[i].first != arr[j].first) {
            while (j < i) {                     // update those of same value at same time
                s.set(arr[j].second, arr[j].second+1);
                j++;
            }
        }
        ans[arr[i].second] = s.comp_seg(0, arr[i].second);
    }
    for (int i=0; i<n; i++) cout << ans[i] << " ";
}