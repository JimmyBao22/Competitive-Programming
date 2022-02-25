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

// https://cses.fi/problemset/task/1158

const int MaxN = 1e3+1;
const int MaxX = 1e5+3;
pair<int, int> books[MaxN];
int dp[MaxN][MaxX];
int n, x;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> x;
    for (int i=0; i<n; i++) cin >> books[i].first;
    for (int i=0; i<n; i++) cin >> books[i].second;

    dp[0][books[0].first] = books[0].second;
    
    int ans=0;
    if (books[0].first <= x) ans = books[0].second;

    for (int i=1; i<n; i++) {

        for (int j=x; j>=0; j--){
            // don't take
            dp[i][j] = dp[i-1][j];

            // take
            if (j - books[i].first >= 0) dp[i][j] = max(dp[i][j], dp[i-1][j - books[i].first] + books[i].second);            

            ans = max(ans, dp[i][j]);
        }
    }

    cout << ans;
}