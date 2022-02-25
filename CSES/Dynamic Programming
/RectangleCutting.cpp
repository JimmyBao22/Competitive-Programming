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
#include <iomanip>
using namespace std;
typedef long long ll;

// https://cses.fi/problemset/task/1744

const int MaxA = 500 + 10;
int a, b;
int INF = 1e9;
int dp[MaxA][MaxA];

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> a >> b;

    for (int i=1; i<MaxA; i++) {
        for (int j=1; j<i; j++) {
            dp[i][j] = INF;

            for (int k=1; k<j; k++) {           // cut vertically
                dp[i][j] = min(dp[i][j], dp[i][j-k] + dp[i][k]);
            }
            for (int k=1; k<i; k++) {           // cut horizontally
                dp[i][j] = min(dp[i][j], dp[i-k][j] + dp[k][j]);
            }
        }
        for (int j=1; j<i; j++) {
            dp[j][i] = INF;

            for (int k=1; k<j; k++) {
                dp[j][i] = min(dp[j][i], dp[j-k][i] + dp[k][i]);
            }
            for (int k=1; k<i; k++) {
                dp[j][i] = min(dp[j][i], dp[j][i-k] + dp[j][k]);
            }
        }
        dp[i][i] = 1; 
    }

    cout << dp[a][b]-1;
}