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

// https://cses.fi/problemset/task/1646

const int MaxN = 2e5+10;

struct BIT {
    int n;
    ll f[MaxN];	// 1 base indexing
    
    BIT(int x) {
        n = x;
        for (int i=0; i<=n; i++) f[i] = 0;
    }

    // sum from i to 0
    ll sum (int i) {		
        i++;
        ll ret=0;
        while (i>0) {
            ret += f[i];
            i -= i&-i;
        }
        return ret;
    }

    // sum from l to r
    ll sum (int l, int r) {	
        return sum(r) - sum(l-1);
    }
    
    // add value to index i
    void add(int i, ll value) {	
        i++;
        while (i<=n) {
            f[i] += value;
            i += i&-i;
        }
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    
    int n, q;
    cin >> n >> q;
    BIT bit(n);
    ll arr[n];
    for (int i=0; i<n; i++) {
        cin >> arr[i];
        bit.add(i, arr[i]);
    }
    
    for (int i=0; i<q; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        cout << bit.sum(a, b) << "\n";
    }
}