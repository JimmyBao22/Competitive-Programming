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
#include <string.h>
#include <array>
#include <unordered_map>
#include <unordered_set>
using namespace std;
typedef long long ll;

// https://cses.fi/problemset/task/1623

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    ll arr[n];
    ll sum=0;
    for (int i=0; i<n; i++) {
        cin >> arr[i];
        sum += arr[i];
    }

    ll ans = sum;
    for (int i=0; i < (1 << n); i++) {
        ll cursum=0;
        for (int j=0; j<n; j++) {
            if ((i >> j) & 1 >= 1) {
                cursum += arr[j];
            }
        }
        ll rest = sum - cursum;
        ans = min(ans, abs(rest - cursum));
    }

    cout << ans;
}