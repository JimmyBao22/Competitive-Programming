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

// https://cses.fi/problemset/task/1095

ll pow(ll a, ll b, ll m) {
    ll ans = 1;
    while (b > 0) {
        if (b%2 == 1) {
            ans *= a;
            ans %= m;
        }
        a *= a;
        a %= m;
        b >>= 1;
    }
    return ans;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n; 
    cin >> n;
    for (int i=0; i<n; i++) {
        ll a, b;
        cin >> a >> b;
        cout << pow(a, b, 1e9+7) << "\n";
    }
}