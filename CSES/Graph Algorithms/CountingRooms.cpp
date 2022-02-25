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

// https://cses.fi/problemset/task/1192

const int MaxN = 1e3+10;
char grid[MaxN][MaxN];
bool visited[MaxN][MaxN];
int n, m;

int xdir[4] = {1, -1, 0, 0};
int ydir[4] = {0, 0, 1, -1};

void bfs(int x, int y) {
    queue<vector<int>> q;
    q.push({x,y});
    while (!q.empty()) {
        vector<int> cur = q.front();
        q.pop();
        x = cur[0]; y = cur[1];
        if (x < 0 || y < 0 || x >= n || y >= m) continue;
        if (visited[x][y]) continue;
        if (grid[x][y] == '#') continue;
        visited[x][y] = true;

        for (int i=0; i<4; i++) {
            q.push({x+xdir[i], y+ydir[i]});
        }

    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> m;
    for (int i=0; i<n; i++ ) {
        for (int j=0; j<m; j++) cin >> grid[i][j];
    }

    int count=0;
    for (int i=0; i<n; i++) {
        for (int j=0; j<m; j++) {
            if (!visited[i][j] && grid[i][j] != '#') {
                count++;
                bfs(i,j);
            }
        }
    }

    cout << count;
}