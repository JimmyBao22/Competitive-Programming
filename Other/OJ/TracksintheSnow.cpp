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

// https://oj.uz/problem/view/BOI13_tracks

const int MaxN = 4e3;
int h, w;
char arr[MaxN][MaxN];
bool visited[MaxN][MaxN];
queue<pair<int, int>> temp;

bool dfs(char type) {
    queue<pair<int, int>> q;
    while (!temp.empty()) {
        q.push(temp.front());
        temp.pop();
    }
    int count=0;
    while (!q.empty()) {
        pair<int, int> cur = q.front();
        q.pop();
        int x = cur.first, y = cur.second;
        // if (cur.first < 0 || cur.second < 0 || cur.first >= h || cur.second >= w) continue;
        if (visited[cur.first][cur.second]) continue;
        if (arr[x][y] != type) {
            temp.push({x, y});
            continue;
        }
        visited[x][y] = true;
        count++;
        if (x+1 >= 0 && y >= 0 && x+1 < h && y < w && !visited[x+1][y] && arr[x+1][y] != '.') {
            q.push({x+1, y});
        }
        if (x-1 >= 0 && y >= 0 && x-1 < h && y < w && !visited[x-1][y] && arr[x-1][y] != '.') {
            q.push({x-1, y});
        }
        if (x >= 0 && y+1 >= 0 && x < h && y+1 < w && !visited[x][y+1] && arr[x][y+1] != '.') {
            q.push({x, y+1});
        }
        if (x >= 0 && y-1 >= 0 && x < h && y-1 < w && !visited[x][y-1] && arr[x][y-1] != '.') {
            q.push({x, y-1});
        }
    }
    return count != 0;
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> h >> w;
    for (int i=0; i<h; i++) {
        for (int j=0; j<w; j++) {
            cin >> arr[i][j];
        }
    }    

    bool fox = false;
    if (arr[0][0] == 'F') fox = true;

    temp.push({0,0});
    int count=0;
    while (true) {
        char type = 'F';
        if (!fox) type = 'R';
        if (!dfs(type)) break;
        count++;
        fox = !fox;
    }    

    cout << count;
}