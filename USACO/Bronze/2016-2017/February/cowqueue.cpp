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

// http://www.usaco.org/index.php?page=viewproblem2&cpid=713

const int maxn = 101;
pair<int, int> a[maxn];

int main() {
    freopen("cowqueue.in", "r", stdin);
    freopen("cowqueue.out", "w", stdout);
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    for (int i=0; i<n; i++) {
        int one, two;
        cin >> one >> two;
        pair<int, int> cur = {one, two};
        a[i] = cur;
    }

    sort(a, a+n);

    int time=0;
    for (int i=0; i<n; i++) {
        time = max(time, a[i].first);
        time += a[i].second;
    }

    cout << time;
}