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

// https://open.kattis.com/problems/quantumsuperposition

const int MaxN = 1001;
vector<int> g1[MaxN], g2[MaxN];
vector<set<int>> one(MaxN), two(MaxN);
bool visited1[MaxN], visited2[MaxN];
int n1, n2, m1, m2, q;

void dfs(int node, bool visited[MaxN], vector<int> g[MaxN], vector<set<int>>& a) {
    if (visited[node]) return;
    visited[node] = true;
    for (auto i : g[node]) {
        dfs(i, visited, g, a);
        for (auto b : a[i]) {
            a[node].insert(b + 1);
        }
    }
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> n1 >> n2 >> m1 >> m2;

    for (int i=0; i<m1; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        g1[a].pb(b);
    }

    for (int i=0; i<m2; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        g2[a].pb(b);
    }
    
    one[n1-1].insert(0);
    two[n2-1].insert(0);

    dfs(0, visited1, g1, one);
    dfs(0, visited2, g2, two);

    cin >> q;
    for (int i=0; i<q; i++) {
        int need;
        cin >> need;
        bool work=false;
        for (auto a : one[0]) {
            if (two[0].count(need - a)) {
                cout << "Yes";
                work=true;
                break;
            }
        }
        if (!work) cout << "No";
        cout << "\n";
    }
}