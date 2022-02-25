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

// https://cses.fi/problemset/task/1080

const int MaxN = 501;
ll mod = 1e9+7, choose[2*MaxN][MaxN];
ll dp[MaxN][MaxN];
int n;
string s;

void build() {
    for (int i=0; i<2*MaxN; i++) {
        for (int j=0; j<MaxN && j<=i; j++) {
            if (j == 0 || j == i) choose[i][j] = 1;
            else {
                choose[i][j] = choose[i-1][j-1] + choose[i-1][j];
                choose[i][j] %= mod;
            }
        }
    }
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    build();
    cin >> s;
    n = s.length();

    for (int i=0; i+1<n; i++) {
        if (s[i] == s[i+1]) dp[i][i+1] = 1;
    }
    for (int d=3; d<n; d+=2) {          // distance has to be even
        for (int l=0; l+d<n; l++) {
            int r = l+d;
            if (s[l] == s[r]) {
                dp[l][r] += dp[l+1][r-1];
            }
            if (s[l] == s[l+1]) {
                dp[l][r] += dp[l+2][r] * (r - l + 1) / 2;
                    // there are in total from r to l, (r-l+1)/2 pairs. From l+2 to r, there are (r-(l+2)+1)/2 pairs.
                        // put the l,l+1 pair inside any of the (r-(l+2)+1)/2 pairs, you have 
                        // ((r-(l+2)+1)/2 + 1) choose 1 ways to do this
                dp[l][r] %= mod;
            }
            for (int k=l+1; k<=r; k+=2) {       // range from l..k has to be even
                if (s[l] == s[k]) {
                    dp[l][r] += dp[l+1][k-1] * dp[k+1][r] % mod * choose[(r - l + 1)/2][(k - l + 1)/2] % mod;
                    dp[l][r] %= mod;
                }
            }
        }
    }

    cout << dp[0][n-1];
}