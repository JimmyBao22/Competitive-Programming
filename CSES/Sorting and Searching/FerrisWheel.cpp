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

// https://cses.fi/problemset/task/1090

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n,x;
    cin >> n >> x;
    int arr[n];
    for (int i=0; i<n; i++) cin >> arr[i];

    sort(arr, arr+n);
    int p=0;
    int count=0;
    for (int i=n-1; i>=p; i--) {
        if (arr[i] + arr[p] <= x) {
            p++;
            count++;
        }
        else {
            count++;
        }
    }

    cout << count;   
}