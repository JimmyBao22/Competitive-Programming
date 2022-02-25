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

// https://cses.fi/problemset/task/1631

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n; 
    cin >> n;
    ll arr[n];
    for (int i=0; i<n; i++) cin >> arr[i];
    sort(arr, arr+n);
    ll sum=0;
    for (int i=0; i<n-1; i++) {
        sum += arr[i];
    }

    if (sum <= arr[n-1]) {
        cout << 2 * arr[n-1];
    }
    else {
        cout << sum + arr[n-1];
    }
}