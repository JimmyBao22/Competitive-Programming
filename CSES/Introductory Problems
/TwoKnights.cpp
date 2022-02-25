#include <iostream>
#include <vector>
#include <map>
#include <cmath>
#include <algorithm>
#include <deque>
#include <queue>
#include <set>
#include <unordered_set>
#include <string>
#include <array>
#include <unordered_map>
#include <unordered_set>
using namespace std;
typedef long long ll;

// https://cses.fi/problemset/task/1072

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    ll n;
    cin >> n;

    ll ans[n+1];
    for (ll i=1; i<=n; i++) {
        ans[i] = i*i*(i*i-1);
        ans[i] -= 8;
        ans[i] -= 24;
        ans[i] -= (i-4)*4*4;
        ans[i] -= 16;
        ans[i] -= (i-4)*6*4;
        ans[i] -= 8*(i-4)*(i-4);
        ans[i] /= 2;
        cout << ans[i] << "\n";
    }
}