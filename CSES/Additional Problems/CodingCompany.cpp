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

// https://cses.fi/problemset/task/1665

const int MaxN = 101, MaxX = 5002;
int arr[MaxN], n, x;
ll mod = 1e9+7, memo[MaxN][MaxN][MaxX];

void print() {
    for (int i=0; i<n; i++) {
        for (int j=0; j<=x; j++) {
            cout << memo[i][j] << " ";
        }
        cout << '\n';
    }
    cout << "\n";
    cout << "\n";
}

ll dp(int index, int open, int val) {
    if (val > x) return 0;
    if (index >= n) {
        if (open == 0) return 1;
        return 0;
    }
    if (memo[index][open][val] != -1) return memo[index][open][val];
    ll ans=0;

    // cout << index << " " << open << " " << val << "\n";

    if (index == 0) {
        // create new open
        ans += dp(index+1, open+1, val);
        ans %= mod;

        // create new closed
        ans += dp(index+1, open, val);
        ans %= mod;

        // close an open
        if (open >= 1) {
            ans += open * dp(index+1, open-1, val);
            ans %= mod;
        }

        // add to an open
        if (open >= 1) {
            ans += open * dp(index+1, open, val);
            ans %= mod;
        }
    }
    else {
        // create new open
        ans += dp(index+1, open+1, val + open * (arr[index] - arr[index-1]));
        ans %= mod;

        // create new closed
        ans += dp(index+1, open, val + open * (arr[index] - arr[index-1]));
        ans %= mod;

        // close an open
        if (open >= 1) {
            ans += open * dp(index+1, open-1, val + open * (arr[index] - arr[index-1]));
            ans %= mod;
        }

        // add to an open
        if (open >= 1) {
            ans += open * dp(index+1, open, val + open * (arr[index] - arr[index-1]));
            ans %= mod;
        }
    }

    return memo[index][open][val] = ans;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> x;
    for (int i=0; i<n; i++) {
        cin >> arr[i];
    }
    sort(arr, arr+n);

    for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++) {
            for (int k=0; k<=x; k++) {
                memo[i][j][k] = -1;
            }
        }
    }
    
    cout << dp(0, 0, 0);
}