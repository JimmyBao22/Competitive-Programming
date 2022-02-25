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

// https://cses.fi/problemset/task/1757

const int MaxN = 1e5+10;
vector<int> g[MaxN];
// priority_queue<int, vector<int>, greater<int>> pq;
priority_queue<int> pq;
vector<int> finalsequence;
int indegree[MaxN];
bool visited[MaxN];
int n, m;

void toposort() {
    for (int i=0; i<n; i++) {
        if (indegree[i] == 0) {
            pq.push(i);
            visited[i] = true;
        }
    }
    
    while (!pq.empty()) {
        int cur = pq.top();
        pq.pop();
        for (auto a : g[cur]) {
            indegree[a]--;
            if (indegree[a] == 0 && !visited[a]) {
                pq.push(a);
                visited[a] = true;
            }
        }
        finalsequence.push_back(cur);
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> m;

    for (int i=0; i<m; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        g[b].push_back(a);
        indegree[a]++;
    }

    toposort();

    reverse(finalsequence.begin(), finalsequence.end());
    for (int i=0; i<(int)finalsequence.size(); i++) {
        cout << finalsequence[i]+1 << " ";
    }
}