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

// https://cses.fi/problemset/task/1675

const int MaxN = 1e5+10, MaxM = 2e5+10;
pair<ll, pair<int, int>> edges[MaxM];       // length, from, dest
int n, m;

struct dsu {
    int n;
    vector<int> parent;
    vector<int> size;
    
    dsu(int nn) {
        n = nn;
        for (int i=0; i<n; i++) {parent.push_back(i); size.push_back(1);}
    }

    void MakeSet(int a) {
        n++;
        parent.push_back(a);
        size.push_back(1);
    }

    int FindSet(int a) {
        if (a == parent[a]) return a;
        return parent[a] = FindSet(parent[a]);
    }
    
    bool Union(int a, int b) {
        a = FindSet(a);
        b = FindSet(b);
        if (a == b) return false;
        
        if (size[a] < size[b]) {
            parent[a] = b;
            size[b] += size[a];
        }
        else {
            parent[b] = a;
            size[a] += size[b];
        }
        return true;
    }
};

ll MST(dsu& s) {
    ll ans=0;
    for (int i=0; i<m; i++) {
        if (s.Union(edges[i].second.first, edges[i].second.second)) {
            ans += edges[i].first;
        }
    }
    return ans;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> m;
    for (int i=0; i<m; i++) {
        int a, b; ll l;
        cin >> a >> b >> l;
        a--; b--;

        edges[i] = {l, {a, b}};
    }

    sort(edges, edges+m);
    dsu s(n);

    ll ans = MST(s);

    if (s.size[s.FindSet(0)] != n) {
        // not everything here
        cout << "IMPOSSIBLE";
    }
    else {
        cout << ans;
    }
}