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

    if (n == 1) {
        cout << 1;
        return 0;
    }

    int next[n+1];
    int prev[n+1];
    for (int i=1; i<=n; i++) {
        next[i] = i+1;
        if (i+1 == n+1) next[i] = 1;
        prev[i] = i-1;
        if (i-1 == 0) prev[i] = n;
    }

    int cur=2;
    for (int j=0; j<n; j++) {
        cout << cur << " ";
        next[prev[cur]] = next[cur];
        prev[next[cur]] = prev[cur];
        cur = next[next[cur]];
    } 
}