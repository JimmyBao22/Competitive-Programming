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

int main() {
    freopen("whereami.in", "r", stdin);
    freopen("whereami.out", "w", stdout);
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    string s;
    cin >> s;

    int k=1;
    for (; k<n; k++) {
        bool works=true;
        set<string> all;
        for (int i=0; i+k-1<n; i++) {
            // i to i+k-1
            string cur = s.substr(i, k);
            if (all.count(cur)) {
                works=false;
                break;
            }
            all.insert(cur);
        }
        if (works) break;
    }

    cout << k;
}