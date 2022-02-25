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

// https://cses.fi/problemset/task/2183

const int MaxN = 2e5+10;
ll arr[MaxN];
int n;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n;
    for (int i=0; i<n; i++) {
        cin >> arr[i];
    }

    sort(arr, arr+n);

    ll good=0;
    for (int i=0; i<n; i++) {
        if (arr[i] - good > 1) {
            break;
        }
        good += arr[i];
    }

    cout << good+1;
}