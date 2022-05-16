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

const int MaxN = 101, MaxX = 1e6+1, mod = 1e9+7;
int arr[MaxN];
ll dp[MaxX];

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    int n, x;
    cin >> n >> x;
    for (int i=0; i<n; i++) {
        cin >> arr[i];
    }
    sort(arr, arr+n);

    dp[0] = 1;
    for (int i=1; i<=x; i++) {
        for (int j=0; j<n && i-arr[j] >= 0; j++) {
            dp[i] += dp[i-arr[j]];
            dp[i] %= mod;
        }
    }

    cout << dp[x];
    
}