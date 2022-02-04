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

// https://cses.fi/problemset/task/1636

const int MaxM = 1e6+10;
ll dp[MaxM];

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n, x;
    cin >> n >> x;
    int arr[n];
    for (int i=0; i<n; i++) cin >> arr[i];

    ll mod = 1e9+7;
    dp[0] = 1;
    for (int i=0; i<n; i++) {
        for (int j=arr[i]; j<=x; j++) {
            dp[j] += dp[j-arr[i]];
            dp[j] %= mod;
        }
    }    

    cout << dp[x];
}