#include <iostream>
#include <vector>
#include <map>
#include <cmath>
#include <algorithm>
#include <deque>
#include <queue>
#include <stack>
#include <set>
#include <unordered_set>
#include <string>
#include <string.h>
#include <array>
#include <unordered_map>
#include <unordered_set>
#include <iomanip>
using namespace std;
typedef long long ll;

// https://cses.fi/problemset/task/1645

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n; cin >> n;
    stack<ll> s;
    ll arr[n];
    for (int i=0; i<n; i++) cin >> arr[i];

    for (int i=0; i<n; i++) {
        while (!s.empty() && arr[s.top()] >= arr[i]) s.pop();

        if (s.empty()) {
            cout << "0 ";
        }
        else {
            cout << s.top()+1 << " ";
        }
        s.push(i);
    }
}