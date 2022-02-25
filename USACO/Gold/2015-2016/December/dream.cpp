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

// http://www.usaco.org/index.php?page=viewproblem2&cpid=575

const int MaxN = 1001, MaxM = 1001;
int n, m, grid[MaxN][MaxM];
ll memo[MaxN][MaxM][2][4], INF = 1e18;

void bfs() {
    queue<vector<ll>> d;
    d.push({0,0,0,0,0});
        // x,y, orange smell, value, direction (if purple)
                                    // 0 = north, 1 = south, 2 = east, 3 = west
    while (!d.empty()) {
        vector<ll> cur = d.front();
        d.pop();
        int x = (int)cur[0]; int y = (int)cur[1]; int smell = (int)cur[2]; ll val = cur[3]; int dir = (int)cur[4];
        if (x<0 || y<0 || x>=n || y>= m) continue;
        if (memo[x][y][smell][dir] <= val) continue;
        
        if (grid[x][y] == 0) continue;
        else if (grid[x][y] == 1) {
            d.push({x+1, y, smell, val+1, 1});
            d.push({x-1, y, smell, val+1, 0});
            d.push({x, y+1, smell, val+1, 2});
            d.push({x, y-1, smell, val+1, 3});
        }
        else if (grid[x][y] == 2) {
            d.push({x+1, y, 1, val+1, 1});
            d.push({x-1, y, 1, val+1, 0});
            d.push({x, y+1, 1, val+1, 2});
            d.push({x, y-1, 1, val+1, 3});
        }
        else if (grid[x][y] == 3) {
            if (smell == 0) continue;
            d.push({x+1, y, 1, val+1, 1});
            d.push({x-1, y, 1, val+1, 0});
            d.push({x, y+1, 1, val+1, 2});
            d.push({x, y-1, 1, val+1, 3});
        }
        else {
            if (dir == 1) {
                if (x+1==n || (x+1<n && (grid[x+1][y] == 0 ||
                        (grid[x+1][y] == 3 && smell == 0)))) {
                    d.push({x+1, y, 0, val+1, 1});
                    d.push({x-1, y, 0, val+1, 0});
                    d.push({x, y+1, 0, val+1, 2});
                    d.push({x, y-1, 0, val+1, 3});
                }
                else {
                    d.push({x+1, y, 0, val+1, 1});
                }
            }
            else if (dir == 0) {
                if (x-1 == -1 || (x-1>=0 && grid[x-1][y] == 0 || 
                        (grid[x-1][y] == 3 && smell == 0))) {
                    d.push({x+1, y, 0, val+1, 1});
                    d.push({x-1, y, 0, val+1, 0});
                    d.push({x, y+1, 0, val+1, 2});
                    d.push({x, y-1, 0, val+1, 3});
                }
                else {
                    d.push({x-1, y, 0, val+1, 0});
                }
            }
            else if (dir == 2) {
                if (y+1==m || (y+1<m && grid[x][y+1] == 0 || 
                        (grid[x][y+1] == 3 && smell == 0))) {
                    d.push({x+1, y, 0, val+1, 1});
                    d.push({x-1, y, 0, val+1, 0});
                    d.push({x, y+1, 0, val+1, 2});
                    d.push({x, y-1, 0, val+1, 3});
                }
                else {
                    d.push({x, y+1, 0, val+1, 2});
                }
            }
            else {
                if (y-1 == -1 || (y-1>=0 && grid[x][y-1] == 0 || 
                        (grid[x][y-1] == 3 && smell == 0))) {
                    d.push({x+1, y, 0, val+1, 1});
                    d.push({x-1, y, 0, val+1, 0});
                    d.push({x, y+1, 0, val+1, 2});
                    d.push({x, y-1, 0, val+1, 3});
                }
                else {
                    d.push({x, y-1, 0, val+1, 3});
                }
            }
        }
        memo[x][y][smell][dir] = val;	
    }
}

int main() {
    freopen("dream.in", "r", stdin);
    freopen("dream.out", "w", stdout);
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> m;
    for (int i=0; i<n; i++) {
        for (int j=0; j<m; j++) {
            for (int k=0; k<2; k++) {
                for (int x=0; x<4; x++) {
                    memo[i][j][k][x] = INF;
                }
            }
            cin >> grid[i][j];
        }
    }

    bfs();

    ll ans = INF;
    for (int k=0; k<2; k++) {
        for (int j=0; j<4; j++) {
            ans = min(ans, memo[n-1][m-1][k][j]);
        }
    }

    if (ans >= INF) {
        cout << -1;
    }
    else {
        cout << ans;
    }
}