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

// https://cses.fi/problemset/task/1140

struct A {
    ll start, end, reward;
};

const int MaxN = 2e5+10;
A arr[MaxN];
int n;
ll dp[MaxN];

bool comp (A a, A b) {
    return a.end < b.end;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n;
    for (int i=0; i<n; i++) {
        cin >> arr[i].start >> arr[i].end >> arr[i].reward;
    }

    sort(arr, arr+n, comp);
    dp[0] = arr[0].reward;
    for (int i=1; i<n; i++) {
        int min=-1;
        int maxind=i;
        while (min<maxind) { 
            int middle = (min+maxind+1)/2;
            if (arr[middle].end < arr[i].start) {
                min = middle;
            }
            else maxind = middle-1;
        }
        if (min == -1) {
            dp[i] = max(dp[i-1], arr[i].reward);
        }
        else {
            dp[i] = max(dp[i-1], dp[min] + arr[i].reward);
        }
    }

    cout << dp[n-1];
}