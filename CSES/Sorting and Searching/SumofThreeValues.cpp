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

// https://cses.fi/problemset/task/1641

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n; ll x;
    cin >> n >> x;
    pair<ll, int> arr[n];

    for (int i=0; i<n; i++) {
        cin >> arr[i].first;
        arr[i].second = i+1;
    }

    sort(arr, arr+n);

    for (int i=0; i<n; i++) {
        int thirdp = n-1;
        for (int j=i+1; j<thirdp; j++) {
            while (thirdp - 1 > j && arr[i].first + arr[j].first + arr[thirdp].first > x) thirdp--;
            if (arr[i].first + arr[j].first + arr[thirdp].first == x) {
                cout << arr[i].second << " " << arr[j].second << " " << arr[thirdp].second;
                return 0;
            }
        }
    }
    
    cout << "IMPOSSIBLE";
}