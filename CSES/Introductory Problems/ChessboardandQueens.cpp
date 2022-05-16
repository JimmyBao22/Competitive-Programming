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

// https://cses.fi/problemset/task/1624

const int n = 8;
vector<vector<char>> arr(n, vector<char>(n));
int c=0;

bool check(int x, int y) {
    for (int i=0; i<n; i++) {
        if (x != i && arr[i][y] == 'Q') {
            return false;
        }
        if (y != i && arr[x][i] == 'Q') {
            return false;
        }

        int sum = x+y;
        int j = sum - i;
        if (j < n && j >= 0 && i != x && arr[i][j] == 'Q') {
            return false;
        }

        int diff = x-y;
        // i - j = diff
        j = i - diff;
        if (j < n && j >= 0 && i != x && arr[i][j] == 'Q') {
            return false;
        }
    }
    return true;
}

void rec(int i, int j, int queens) {
    if (queens == 8) {
        c++;
        return;
    }

    for (; j<n; j++) {
        if (arr[i][j] != '.') continue;
        if (check(i,j)) {
            arr[i][j] = 'Q';
            rec(i, j, queens+1);
            arr[i][j] = '.';        // reset
        }
    }

    i++;

    for (; i<n; i++) {
        for (j=0; j<n; j++) {
            if (arr[i][j] != '.') continue;
            if (check(i,j)) {
                arr[i][j] = 'Q';
                rec(i, j, queens+1);
                arr[i][j] = '.';        // reset
            }
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++) cin >> arr[i][j];
    }

    rec(0, 0, 0);

    cout << c;
    
}