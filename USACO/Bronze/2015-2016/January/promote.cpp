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

// http://www.usaco.org/index.php?page=viewproblem2&cpid=591

int main() {
    freopen("promote.in", "r", stdin);
    freopen("promote.out", "w", stdout);
    ios::sync_with_stdio(false);
    cin.tie(0);

    int a[4][2];
    for (int i=0; i<4; i++) {
        for (int j=0; j<2; j++) {
            cin >> a[i][j];
        }
    }

    int diff=0;
    diff = a[3][1] - a[3][0];
    int ans[3];
    ans[2] = diff;

    a[2][0] -= diff;
    diff = a[2][1] - a[2][0];
    ans[1] = diff;

    a[1][0] -= diff;
    diff = a[1][1] - a[1][0];
    ans[0] = diff;

    for (int i=0; i<3; i++) {
        cout << ans[i] << "\n";
    }
}