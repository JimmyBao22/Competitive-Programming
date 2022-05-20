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

// https://cses.fi/problemset/task/2413/

const int MaxN = 1e6+1;
ll mod = 1e9+7;
ll dp[MaxN][2][2][2];   // row, (op/cl) for left block, (op/cl) for right block, width of last block
                        //      0 = open, 1 = closed                          0 = width of 1, 1 = width of 2
                        // open = this current block is still open to add on to. 
                        // closed = end this block, next blockw will start a new block

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    for (int i=0; i<MaxN; i++) {
        for (int j=0; j<2; j++) {
            for (int k=0; k<2; k++) {
                fill(dp[i][j][k], dp[i][j][k]+2, 0);
            }
        }
    }

    dp[0][0][0][0] = 1;
    dp[0][0][0][1] = 1;

    for (int i=1; i<MaxN; i++) {
        for (int j=0; j<2; j++) {
            for (int k=0; k<2; k++) {
                // current width = 0
                // come from previous 2 width 0
                for (int z=0; z<2; z++) {
                    for (int x=0; x<2; x++) {
                        dp[i][j][k][0] += dp[i-1][z][x][0];
                        dp[i][j][k][0] %= mod;
                    }
                }
                // come from previous width 1
                dp[i][j][k][0] += dp[i-1][1][1][1];
                dp[i][j][k][0] %= mod;

                // current width = 1
                // come from previous 2 width 0
                dp[i][j][k][1] += dp[i-1][1][1][0];
                dp[i][j][k][1] %= mod;
                // come from previous width 1
                dp[i][j][k][1] += dp[i-1][0][0][1];
                dp[i][j][k][1] %= mod;
                dp[i][j][k][1] += dp[i-1][1][1][1];
                dp[i][j][k][1] %= mod;
            }
        }
    }
        
    int t;
    cin >> t;
    while (t--) {
        int n;
        cin >> n;
        ll ans = dp[n][1][1][0] + dp[n][1][1][1];
        ans %= mod;
        cout << ans << "\n";
    }
}

/*
ll dp(int nn, int op, int op2, int width) {
    if (nn >= n) {
        if (op == 1 && op2 == 1) {
            return 1;
        }
        return 0;
    }
    if (memo[nn][op][op2][width] != -1) return memo[nn][op][op2][width];
    
    ll ans = 0;

    if (width == 0 || (width == 1 && op == 1 && op2 == 1)) {
        for (int i=0; i<2; i++) {
            for (int j=0; j<2; j++) {
                ans += dp(nn+1, i, j, 0);
                ans %= mod;
            }
        }

        if (op == 1 && op2 == 1) {
            // if both closed, then can add a block of width 2
            ans += dp(nn+1, 0, 0, 1);
            ans %= mod;
            ans += dp(nn+1, 1, 1, 1);
            ans %= mod;
        }
    }
    else {
        // width = 1 and it's open --> can close or continue open
        ans += dp(nn+1, 0, 0, 1);
        ans %= mod;
        ans += dp(nn+1, 1, 1, 1);
        ans %= mod;
    }

    return memo[nn][op][op2][width] = ans;
}
*/