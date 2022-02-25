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

// https://cses.fi/problemset/task/1722

// finds the nth fibonacci number  -  O(logn)

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

    ll n;
    cin >> n;
    vector<vector<ll>> arr = {{0,1}, {1,1}};
    vector<vector<ll>> ans = {{0,1}, {1,1}};
    while (n > 0) {
        if ((n&1) == 1) {
            ans = multiply(2, ans, arr);
        }
        arr = multiply(2, arr, arr);
        n >>= 1;
    }

    cout << ans[0][0]%mod;
}