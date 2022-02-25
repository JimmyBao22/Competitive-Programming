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

// https://cses.fi/105/list/

const int MaxN = 1e5+1;
vector<pair<int, int>> arr;
int n;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n;
    for (int i=0; i<n; i++) {
        arr.push_back({});
        cin >> arr[i].first >> arr[i].second;
    }

    sort(arr.begin(), arr.end(), greater<pair<int, int>>());

    set<int> s;
    for (int i=0; i<n; i++) {
        auto higher = s.lower_bound(arr[i].second);
        if (higher != s.end()) s.erase(*higher);
        s.insert(arr[i].second);
    }

    cout << s.size();
}