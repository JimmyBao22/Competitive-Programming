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

// https://cses.fi/problemset/task/1194

const int MaxN = 1e3+10;
char grid[MaxN][MaxN];
int n, m;
int monstertime[MaxN][MaxN], atime[MaxN][MaxN];
int INF = 1e9, xdir[4] = {1, -1, 0, 0}, ydir[4] = {0, 0, 1, -1};
pair<int, int> parent[MaxN][MaxN];

bool outbound(int x, int y) {
    return x < 0 || y < 0 || x >= n || y >= m;
}

void bfs1() {
    queue<vector<int>> q;
        // x, y, times
    for (int i=0; i<n; i++) {
        for (int j=0; j<m; j++) {
            if (grid[i][j] == 'M') {
                q.push({i, j, 0});
            }
        }
    }
    while (!q.empty()) {
        vector<int> cur = q.front();
        q.pop();
        if (outbound(cur[0], cur[1])) continue;
        if (grid[cur[0]][cur[1]] == '#') continue;
        if (monstertime[cur[0]][cur[1]] <= cur[2]) continue;
        monstertime[cur[0]][cur[1]] = cur[2];

        for (int i=0; i<4; i++) {
            q.push({cur[0] + xdir[i], cur[1] + ydir[i], cur[2] + 1});
        }
    }
}

void bfs2(int x, int y) {
    queue<vector<int>> q;
        // x, y, times, fromx, fromy
    q.push({x, y, 0, -1, -1});
    while (!q.empty()) {
        vector<int> cur = q.front();
        q.pop();
        if (outbound(cur[0], cur[1])) continue;
        if (grid[cur[0]][cur[1]] == '#') continue;
        if (monstertime[cur[0]][cur[1]] <= cur[2]) continue;
        if (atime[cur[0]][cur[1]] <= cur[2]) continue;
        atime[cur[0]][cur[1]] = cur[2];
        parent[cur[0]][cur[1]] = {cur[3], cur[4]};

        for (int i=0; i<4; i++) {
            q.push({cur[0] + xdir[i], cur[1] + ydir[i], cur[2] + 1, cur[0], cur[1]});
        }
    }
}

vector<char> Backtrack(int x, int y) {
    vector<char> s;
    while (parent[x][y].first != -1) {
        int prevx = parent[x][y].first;
        int prevy = parent[x][y].second;
        if (prevx == x-1) {
            s.push_back('D');
        }
        else if (prevx == x+1) {
            s.push_back('U');
        }
        else if (prevy == y-1) {
            s.push_back('R');
        }
        else {
            s.push_back('L');
        }
        x = prevx; y = prevy;
    }
    return s;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> m;
    int startx=0, starty=0;
    for (int i=0; i<n; i++) {
        for (int j=0; j<m; j++) {
            cin >> grid[i][j];
            monstertime[i][j] = INF;
            atime[i][j] = INF;
            if (grid[i][j] == 'A') {
                startx = i; starty = j;
            }
        }
    }
    
    bfs1();
    bfs2(startx, starty);

    int endx=-1, endy=-1;
    for (int i=0; i<n; i++) {
        if (grid[i][0] != '#' && atime[i][0] < monstertime[i][0]) {
            endx = i; endy = 0;
            break;
        }
        if (grid[i][m-1] != '#' && atime[i][m-1] < monstertime[i][m-1]) {
            endx = i; endy = m-1;
            break;
        }
    }
    for (int i=0; i<m; i++) {
        if (grid[0][i] != '#' && atime[0][i] < monstertime[0][i]) {
            endx = 0; endy = i;
            break;
        }
        if (grid[n-1][i] != '#' && atime[n-1][i] < monstertime[n-1][i]) {
            endx = n-1; endy = i;
            break;
        }
    }
    if (endx == -1) {
        cout << "NO";
        return 0;
    }
    cout << "YES\n";
    vector<char> s = Backtrack(endx, endy);
    cout << s.size() << "\n";
    for (int i=s.size()-1; i>=0; i--) {
        cout << s[i];
    }
}