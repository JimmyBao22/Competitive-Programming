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

const int MaxN = 1e5+10;
vector<int> g[MaxN];

int main() {
    freopen("planting.in", "r", stdin);
    freopen("planting.out", "w", stdout);
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;

    for (int i=0; i<n-1; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        g[a].push_back(b);
        g[b].push_back(a);
    }

    int ans=0;
    for (int i=0; i<n; i++) {
        ans = max(ans, (int)g[i].size());
    }

    cout << ans+1;
}