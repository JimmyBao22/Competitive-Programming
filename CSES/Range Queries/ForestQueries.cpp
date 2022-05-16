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

// https://cses.fi/problemset/task/1652

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n, q;
    cin >> n >> q;
    char grid[n][n];
    for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++) cin >> grid[i][j];
    }

    int psum[n][n];
    for (int i=0; i<n; i++) {
        for (int j=0; j<n; ++j) {
            psum[i][j] = 0;
            if (grid[i][j] == '*') psum[i][j]++;
            if (i-1 >= 0 && j-1>=0) psum[i][j] -= psum[i-1][j-1];
            if (i-1 >= 0) psum[i][j] += psum[i-1][j];
            if (j-1 >= 0) psum[i][j] += psum[i][j-1];
        }
    }

    for (int i=0; i<q; i++) {
        int x1, y1, x2, y2;
        cin >> x1 >> y1 >> x2 >> y2;
        x1--; y1--; x2--; y2--;

        int sum=psum[x2][y2];
        if (x1-1>=0) sum -= psum[x1-1][y2];
        if (y1-1>=0) sum -= psum[x2][y1-1];
        if (x1-1>=0 && y1-1>=0) sum += psum[x1-1][y1-1];
        cout << sum << "\n";
    }
}