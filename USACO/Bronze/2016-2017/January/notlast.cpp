#include <iostream>
#include <vector>
#include <map>
#include <cmath>
#include <algorithm>
#include <deque>
#include <queue>
#include <set>
#include <unordered_set>
#include <string>
#include <array>
#include <unordered_map>
#include <unordered_set>
using namespace std;

// http://www.usaco.org/index.php?page=viewproblem2&cpid=687

int main() {
    freopen("notlast.in", "r", stdin);
    freopen("notlast.out", "w", stdout);
    ios::sync_with_stdio(false);
    cin.tie(0);

    string names[] = {"Bessie", "Elsie", "Daisy", "Gertie", "Annabelle", "Maggie", "Henrietta"};
    map<string, int> m;
    for (string i : names) {
        m[i] = 0;
    }

    int n;
    cin >> n;
    for (int i=0; i<n; i++) {
        string cur; int val;
        cin >> cur >> val;
        m[cur] += val;
    }

    int vals[7];
    for (int i=0; i<7; i++) {        
        vals[i] = m[names[i]];
    }
    sort(vals, vals + 7);

    if (vals[0] == vals[6]) {
        cout << "Tie\n";
        return 0;
    }
    
    int i=1;
    for (; i<7; i++) {
        if (vals[i] != vals[i-1]) break;
    }

    string ans;
    bool found=false;
    for (int j=0; j<7; j++) {
        if (m[names[j]] == vals[i]) {
            if (found) {
                cout << "Tie\n";
                return 0;
            }
            ans=names[j];
            found=true;
        }
    }
    
    cout << ans << "\n";
}