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
typedef long double ld;
#define pb push_back

// https://cses.fi/problemset/task/2205

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    int n;
    cin >> n;

    vector<string> cur;
    cur.pb("1");
    cur.pb("0");
    for (int i=2; i<=n; i++) {
        vector<string> updated;
        for (int j=0; j<cur.size(); j++) {
            updated.pb("1" + cur[j]);
        }
        for (int j=cur.size()-1; j>=0; j--) {
            updated.pb("0" + cur[j]);
        }
        cur = updated;
    }

    for (string a : cur) {
        cout << a << "\n";
    }

}