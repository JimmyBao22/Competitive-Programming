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

// https://cses.fi/problemset/task/1739

const int MaxN = 1e3+10;
const int MaxM = 1e3+10;

struct BIT2D {
    int n,m;
    ll f[MaxN][MaxM];		// 1 base indexing
    
    BIT2D (int x, int y) {
        n = x; m = y;
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=m; j++) {
                f[i][j] = 0;
            }
        }
    }

    // sum from (i,j) to (0,0)
    ll sum (int i, int j) {
        i++; j++;
        ll ret=0;
        while (i>0) {
            int y=j;
            while (y>0) {
                ret += f[i][y];
                y -= y&-y;
            }
            i -= i&-i;
        }
        return ret;
    }
    
    // sum from (i1, j1) to (i2, j2)
    ll sum(int i1, int j1, int i2, int j2) {
        return sum(i2, j2) - sum(i1-1, j2) - sum(i2, j1-1) + sum(i1-1, j1-1);
    }
    
    // add value to (i,j)
    void add(int i, int j, ll val) {
        i++; j++;
        while (i<=n) {
            int y=j;
            while (y<=m) {
                f[i][y] += val;
                y += y&-y;
            }
            i += i&-i;
        }
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n, q;
    cin >> n >> q;
    BIT2D bit(n, n);
    char arr[n][n];
    for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++) {
            cin >> arr[i][j];
            if (arr[i][j] == '*') bit.add(i, j, 1);
        }
    }

    for (int i=0; i<q; i++) {
        int t; cin >> t;
        if (t == 1) {
            int x, y;
            cin >> x >> y;
            x--; y--;
            if (arr[x][y] == '*') {
                bit.add(x, y, -1);
                arr[x][y] = '.';
            }
            else {
                bit.add(x, y, 1);
                arr[x][y] = '*';
            }
        }
        else {
            int x1, y1, x2, y2;
            cin >> x1 >> y1 >> x2 >> y2;
            x1--; y1--; x2--; y2--;
            cout << bit.sum(x1, y1, x2, y2) << "\n";
        }
    }
}