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

// https://cses.fi/problemset/task/1096

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

void print(vector<vector<ll>> arr) {
    for (int i=0; i<(int)arr.size(); i++) {
        for (int j=0; j<(int)(arr.size()); j++) {
            cout << arr[i][j] << " ";
        }
        cout << "\n";
    }
    cout << "\n";
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    ll n;
    cin >> n;

    vector<vector<ll>> arr(6, vector<ll>(6)), ans(6, vector<ll>(6));
    for (int i=0; i<5; i++) {
        arr[i][i+1] = 1;
        ans[i][i+1] = 1;
    }  
    for (int i=0; i<6; i++) {
        arr[5][i] = 1;
        ans[5][i] = 1;
    }

    while (n > 0) {
        if (n%2 == 1) {
            ans = multiply(arr.size(), ans, arr);
        }
        arr = multiply(arr.size(), arr, arr);
        n >>= 1;
    }
    cout << ans[5][0]%mod;
}