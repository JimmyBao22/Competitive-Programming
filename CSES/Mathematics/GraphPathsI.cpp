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

// https://cses.fi/problemset/task/1723

ll mod = 1e9 + 7;

vector<vector<ll>> multiply (int n, vector<vector<ll>> a, vector<vector<ll>> b) {
    vector<vector<ll>> product(n, vector<ll>(n));
    for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++) {
            for (int k=0; k<n; k++) {
                product[i][k] += a[i][j] * b[j][k];
                product[i][k] %= mod;
            }
        }
    }
    return product;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n, m; ll k;
    cin >> n >> m >> k;
    vector<vector<ll>> arr(n, vector<ll>(n));
    vector<vector<ll>> ans(n, vector<ll>(n));
    for (int i=0; i<n; i++) ans[i][i] = 1;

    for (int i=0; i<m; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        arr[a][b]++;
    }

    while (k > 0) {
        if ((k&1) == 1) {
            ans = multiply(n, ans, arr);
        }
        arr = multiply(n, arr, arr);
        k >>= 1;
    }
    
    cout << ans[0][n-1];
}