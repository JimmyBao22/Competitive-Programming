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

// https://cses.fi/problemset/task/1724

ll INF = 2e18;

vector<vector<ll>> add(int n, vector<vector<ll>> a, vector<vector<ll>> b) {
    vector<vector<ll>> product(n);
    for (int i=0; i<n; i++) {
        product[i].assign(n, INF);
    }
    for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++) {
            for (int k=0; k<n; k++) {
                product[i][k] = min(product[i][k], a[i][j] + b[j][k]);
            }
        }
    }
    return product;
}

void print(vector<vector<ll>> arr) {
    for (int i=0; i<(int)arr.size(); i++) {
        for (int j=0; j<(int)arr[i].size(); j++) {
            cout << arr[i][j] << " ";
        }
        cout << "\n";
    }
    cout << "\n";
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n, m; ll k;
    cin >> n >> m >> k;
    vector<vector<ll>> arr(n, vector<ll>(n));
    vector<vector<ll>> ans(n, vector<ll>(n));
    for (int i=0; i<n; i++) {
        arr[i].assign(n, INF);
        ans[i].assign(n, INF);
        ans[i][i] = 0;
    }

    for (int i=0; i<m; i++) {
        int a, b; ll c;
        cin >> a >> b >> c;
        a--; b--;
        arr[a][b] = min(arr[a][b], c);
    }

    while (k > 0) {
        if ((k&1) == 1) {
            ans = add(n, ans, arr);
        }
        arr = add(n, arr, arr);
        k >>= 1;
    }
    
    if (ans[0][n-1] == INF) {
        cout << -1;
    }
    else {
        cout << ans[0][n-1];
    }
}