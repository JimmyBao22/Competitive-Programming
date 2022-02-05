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

// https://cses.fi/problemset/task/1643

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    ll arr[n];
    for (int i=0; i<n; i++) cin >> arr[i];

    ll sum = arr[0];
    ll ans = arr[0];
    if (sum < 0) sum = 0;
    for (int i=1; i<n; i++) {
        sum += arr[i];
        ans = max(ans, sum);
        if (sum < 0) {
            sum = 0;
        }
    }

    cout << ans;  
}