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

// https://cses.fi/problemset/task/1193

const int MaxN = 1e3+10;
char grid[MaxN][MaxN];
pair<int, int> parent[MaxN][MaxN];
bool visited[MaxN][MaxN];
int n, m;

int xdir[4] = {1, -1, 0, 0};
int ydir[4] = {0, 0, 1, -1};

bool outbounds(int x, int y) {
    return x < 0 || y < 0 || x>=n || y>=m;
}

void bfs(int x, int y) {
    queue<pair<int, int>> q;
    q.push({x,y});
    visited[x][y] = true;
    parent[x][y] = {-1,-1};
    while (!q.empty()) {
        pair<int, int> cur = q.front();
        q.pop();
        x = cur.first; y = cur.second;
        if (outbounds(x,y)) continue;
        if (grid[x][y] == '#') continue;
        if (grid[x][y] == 'B') break;

        for (int i=0; i<4; i++) {
            if (!outbounds(x+xdir[i], y+ydir[i]) && !visited[x+xdir[i]][y+ydir[i]]) {
                visited[x+xdir[i]][y+ydir[i]] = true;
                parent[x+xdir[i]][y+ydir[i]] = {x,y};
                q.push({x + xdir[i], y + ydir[i]});
            }
        }

    }
}

vector<char> Backtrack(int x, int y) {
    vector<char> sequence;
    while (parent[x][y].first != -1) {
        int prevx = parent[x][y].first;
        int prevy = parent[x][y].second;
        // cout << x << " " << y << " " << prevx << " " << prevy << "\n";
        if (prevx == x-1) {
            sequence.push_back('D');
        }
        else if (prevx == x+1) {
            sequence.push_back('U');
        }
        else if (prevy == y-1) {
            sequence.push_back('R');
        }
        else {
            sequence.push_back('L');
        }
        x = prevx;
        y = prevy;
    }
    return sequence;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> m;
    int startx=0, starty=0, endx=0, endy=0;
    for (int i=0; i<n; i++) {
        for (int j=0; j<m; j++) {
            cin >> grid[i][j];
            if (grid[i][j] == 'A') {
                startx = i; starty = j;
            }
            if (grid[i][j] == 'B') {
                endx = i; endy = j;
            }
        }
    }

    bfs(startx, starty);

    if (!visited[endx][endy]) {
        cout << "NO";
        return 0;
    }
    
    vector<char> sequence = Backtrack(endx, endy);
    cout << "YES" << "\n" << sequence.size() << "\n";
    for (int i=sequence.size()-1; i>=0; i--) {
        cout << sequence[i];
    }
}