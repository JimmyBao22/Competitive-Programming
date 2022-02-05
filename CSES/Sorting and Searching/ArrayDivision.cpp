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

// https://cses.fi/problemset/task/1085

const int MaxN = 2e5+10;
ll arr[MaxN];
int n, k;

bool check(ll mid) {
    int count=0;
    ll cursum=0;
    for (int i=0; i<n; i++) {
        if (cursum + arr[i] > mid) {
            count++;
            cursum = 0;
        }
        cursum += arr[i];
        if (cursum > mid) return false;
    }
    count++;
    return count <= k;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> k;
    for (int i=0; i<n; i++) cin >> arr[i];

    ll min = -1e18;
    ll max = 1e18;
    while (min<max) {
        ll middle = min + (max-min)/2;
        if (check(middle)) {
            max = middle;
        }
        else min = middle+1;
    }
    cout << min;
}