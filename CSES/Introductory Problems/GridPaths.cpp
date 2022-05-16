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
#include <chrono>
using namespace std;
typedef long long ll;

// https://cses.fi/problemset/task/1625

const int n = 48;
vector<char> s(n);
bool visited[7][7];
ll c=0;

bool outbound(int x, int y) {
    return x < 0 || y < 0 || x >= 7 || y >= 7;
}

void rec(int i, int j, int index) {
    if (index == n) {
        if (i == 6 && j == 0) c++;
        return;
    }

    if (abs(6-i) + abs(j) > n-index) return;
    if (i == 6 && j == 0) return;
    if (visited[i][j]) return;

    // https://cses.fi/book/book.pdf#page=62
    if ((i-1<0 || visited[i-1][j]) && (i+1 >= 7 || visited[i+1][j]) && (j+1<7 && !visited[i][j+1]) && (j-1>=0 && !visited[i][j-1])) {
        return;
    }
    if ((j-1<0 || visited[i][j-1]) && (j+1 >= 7 || visited[i][j+1]) && (i+1<7 && !visited[i+1][j]) && (i-1>=0 && !visited[i-1][j])) {
        return;
    }

    visited[i][j] = true;

    if (s[index] != '?') {
        if (!outbound(i-1, j) && !visited[i-1][j] && s[index] == 'U') rec(i-1, j, index+1);
        if (!outbound(i, j+1) && !visited[i][j+1] && s[index] == 'R') rec(i, j+1, index+1);
        if (!outbound(i+1, j) && !visited[i+1][j] && s[index] == 'D') rec(i+1, j, index+1);
        if (!outbound(i, j-1) && !visited[i][j-1] && s[index] == 'L') rec(i, j-1, index+1);
        visited[i][j] = false;
        return;
    }

    if (i > 0 && !visited[i-1][j]) {
        rec(i-1, j, index+1);
    }
    if (i < 6 && !visited[i+1][j]) {
        rec(i+1, j, index+1);
    }
    if (j > 0 && !visited[i][j-1]) {
        rec(i, j-1, index+1);
    }
    if (j < 6 && !visited[i][j+1]) {
        rec(i, j+1, index+1);
    }

    visited[i][j] = false;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    for (int i=0; i<n; i++) cin >> s[i];

    rec(0, 0, 0);

    cout << c;
}