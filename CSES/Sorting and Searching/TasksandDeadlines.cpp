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

// https://cses.fi/problemset/task/1630

struct A {
    ll duration, deadline;
};

bool comp (A a, A b) {
    return a.duration < b.duration;
}

const int maxn = 2e5+1;
A arr[maxn];

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    for (int i=0; i<n; i++) {
        cin >> arr[i].duration >> arr[i].deadline;
    }

    sort(arr, arr+n, comp);

    ll ans=0;
    ll time=0;
    for (int i=0; i<n; i++) {
        time += arr[i].duration;
        ans += (arr[i].deadline - time);
    }

    cout << ans;
}