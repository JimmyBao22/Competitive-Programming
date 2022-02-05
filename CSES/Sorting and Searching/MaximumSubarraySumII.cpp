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

// https://cses.fi/problemset/task/1644

const int MaxN = 2e5+1; 
map<ll, int> m;         // val, index
ll psum[MaxN], arr[MaxN], INF = 1e18;
int n, a, b;

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> n >> a >> b;
    for (int i=0; i<n; i++) {
        cin >> arr[i];
        if (i == 0) psum[i] = arr[i];
        else psum[i] = psum[i-1] + arr[i];
    }
    
    for (int i=0; i<b-1; i++) {
        m[psum[i]] = i;
    }


    ll ans = -INF;
    for (int left = 0; left + a - 1 < n; left++) {
        if (left + b - 1 < n) m[psum[left+b-1]] = left+b-1;
        while (m.size()) {
            auto cur = (*(--m.end()));          // max
            if (cur.second - left + 1 < a) {
                m.erase(cur.first);
            }
            else {
                if (left >= 1) ans = max(ans, psum[cur.second] - psum[left-1]);
                else ans = max(ans, psum[cur.second]);
                break;
            }
        }
    }
    cout << ans;
}