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
 
// https://oj.uz/problem/view/IZhO14_bank

const int N = 21, MaxVal = 20001;
int n, m, a[N], b[N];
bool dp[N][(1 << N)];
vector<int> all[N];
  
int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);
 
    cin >> n >> m;
    for (int i=0; i<n; ++i) cin >> a[i];
    for (int i=0; i<m; ++i) cin >> b[i];
 
    for (int i=1; i<(1 << m); i++) {
        int sum=0;
        for (int j=0; j<m; j++) {
            if (((i >> j)&1)) {
                sum += b[j];
            }
        }
        for (int j=0; j<n; j++) {
            if (a[j] == sum) {
                all[j].pb(i);
            }
        }
    }
 
    bool work=false;
    for (int i=0; i<n; i++) {
        work = false;
        for (auto one : all[i]) {
            int sum = one;
            if (i == 0) {
                dp[0][sum] = true;
                work=true;
            }
            else {
                int complement = ((1 << m) - 1) ^ sum;
                for (int j = complement; j>0; j = (j-1) & complement) {
                    int cursum = j;
                    dp[i][sum + cursum] |= dp[i-1][cursum];
                    if (dp[i][sum + cursum]) work = true;
                }
            }
        }
    }
 
    if (work) cout << "YES";
    else cout << "NO";
}