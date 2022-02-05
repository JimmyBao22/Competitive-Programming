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

// https://cses.fi/problemset/task/2428

const int MaxN = 2e5+10;
int arr[MaxN], n, k;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> k;
    for (int i=0; i<n; i++) {
        cin >> arr[i];
    }

    map<int, int> a;
    int p=0;
    for (; p<n; p++) {
        if (!a.count(arr[p]) && int(a.size()) + 1 > k) {
            break;
        }
        else {
            a[arr[p]]++;
        }
    }

    ll ans = p;

    for (int i=1; i<n; i++) {
        a[arr[i-1]]--;
        if (!a[arr[i-1]]) a.erase(arr[i-1]);

        for (; p<n; p++) {
            if (!a.count(arr[p]) && int(a.size()) + 1 > k) {
                break;
            }
            else {
                a[arr[p]]++;
            }
        }

        ans += p - i;
    }

    cout << ans;
}