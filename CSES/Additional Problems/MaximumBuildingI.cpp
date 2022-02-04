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
#define pb push_back

// https://cses.fi/problemset/task/1147

const int MaxN = 1001;
char grid[MaxN][MaxN];
int c[MaxN][MaxN], n, m;

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> n >> m;
    for (int i=0; i<n; i++) {
        for (int j=0; j<m; j++) {
            cin >> grid[i][j];
        }
    }
    for (int i=0; i<m; i++) {
        if (grid[0][i] == '*') c[0][i] = 0;
        else c[0][i] = 1;
    }
    for (int i=1; i<n; i++) {
        for (int j=0; j<m; j++) {
            if (grid[i][j] == '*') c[i][j] = 0;
            else c[i][j] = 1 + c[i-1][j];
        }
    }

    int ans = 0;
    for (int i=0; i<n; i++) {
            // max histogram problem for each row
        stack<int> s;
        int j=0;
        while (j < m) {
            if (s.empty() || c[i][j] >= c[i][s.top()]) {
                s.push(j++);
            }
            else {
                int cur = s.top();
                s.pop();
                int left = 0;
                if (!s.empty()) left = s.top()+1;
                int right = j-1;
                ans = max(ans, c[i][cur] * (right - left + 1));
            }
        }
        while (!s.empty()) {
            int cur = s.top();
            s.pop();
            int left = 0;
            if (!s.empty()) left = s.top()+1;
            int right = j-1;
            ans = max(ans, c[i][cur] * (right - left + 1));
        }
    }
    cout << ans;
}