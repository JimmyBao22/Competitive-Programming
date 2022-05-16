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

// https://cses.fi/problemset/task/1145

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    int arr[n];
    for (int i=0; i<n; i++) cin >> arr[i];
    
    set<int> s;
    for (int i=0; i<n; i++) {
        auto higher = s.lower_bound(arr[i]);
        if (higher != s.end()) s.erase(*higher);
        s.insert(arr[i]);
    }

    // LIS = s.size();
    cout << s.size();
}