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

// https://cses.fi/problemset/task/1717

const int MaxN = 1e6+10;
ll mod = 1e9+7;
ll arr[MaxN];

// formula
// arr[i] = (i-1)*(arr[i-1] + arr[i-2])

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    arr[1] = 0;
    arr[2] = 1;
    arr[3] = 2;
    for (ll i=4; i<=n; i++) {
        arr[i] = (i-1)*(arr[i-1] + arr[i-2]);
        arr[i] %= mod;
    }

    cout << arr[n];
}