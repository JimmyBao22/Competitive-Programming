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

// https://cses.fi/problemset/task/1092

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    ll n;
    cin >> n;
    ll sum = n*(n+1)/2;
    if (sum%2 != 0) {
        cout << "NO";
        return 0;
    }

    ll needed = sum/2;
    vector<int> first;
    vector<int> second;
    for (int i=n; i>=1; i--) {
        if (i <= needed) {
            first.push_back(i);
            needed -= i;
        }
        else {
            second.push_back(i);
        }
    }

    if (needed == 0) {
        cout << "YES\n";
        cout << first.size() << "\n";
        for (int i=0; i<(int)first.size(); i++) {
            cout << first[i] << " ";
        }
        cout << "\n" << second.size() << "\n";
        for (int i=0; i<int(second.size()); i++) {
            cout << second[i] << " ";
        }
    }
    else {
        cout << "NO";
    }
}