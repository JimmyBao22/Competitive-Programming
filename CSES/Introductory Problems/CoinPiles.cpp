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
#include <array>
#include <unordered_map>
#include <unordered_set>
using namespace std;
typedef long long ll;

// https://cses.fi/problemset/task/1754

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int t;
    cin >> t;
    while (t-->0 ) {
        ll l,r;
        cin >> l >> r;
        if ((l+r)%3 != 0) {
            cout << "NO\n";
            continue;
        }
        ll ab = (l+r)/3;
        if (ab > l || ab > r) {
            cout << "NO";
        }
        else {
            cout << "YES";
        }
        cout << "\n";
    }
    
}