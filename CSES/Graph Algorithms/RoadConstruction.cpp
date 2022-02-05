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

// https://cses.fi/problemset/task/1676

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

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n, m;
    cin >> n >> m;
    dsu s(n);

    int numcomponents = n;
    int maxsize = 1;
    for (int i=0; i<m; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        if (s.Union(a,b)) {
            numcomponents--;
            maxsize = max(maxsize, s.size[s.FindSet(a)]);
        }
        cout << numcomponents << " " << maxsize << "\n";
    }
}