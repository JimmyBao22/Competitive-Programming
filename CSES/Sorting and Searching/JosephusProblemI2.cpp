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
 
// https://cses.fi/problemset/task/2162

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
 
    int n;
    cin >> n;
    set<int> pos;
    for (int i=1; i<=n; i++) pos.insert(i);
    int cur = 2;    
    while (pos.size() > 1) {
        cout << cur << " ";
        auto it = pos.upper_bound(cur);
        pos.erase(cur);
 
        if (it == pos.end()) {
            it = pos.begin();
            it++;
            cur = *it;
            continue;
        }
 
        it++;
        if (it == pos.end()) {
            it = pos.begin();
            cur = *it;
            continue;
        }
 
        cur = *it;
    }
 
    cout << (*pos.begin());
}