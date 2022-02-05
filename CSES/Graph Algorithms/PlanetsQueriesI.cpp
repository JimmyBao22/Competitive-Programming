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

// https://cses.fi/problemset/task/1750

const int MaxN = 2e5+1, LOG = 31;
int n, q;
int go[MaxN], cyclelength[MaxN];
int nnext[MaxN][LOG];

void precomp() {
    for (int i=1; i<LOG; i++) {
        for (int j=0; j<n; j++) {
            if (nnext[j][i-1] != -1) {
                nnext[j][i] = nnext[nnext[j][i-1]][i-1];
            }
            else nnext[j][i] = -1;
        }
    }
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> n >> q;
    for (int i=0; i<n; i++) {
        cin >> go[i];
        go[i]--;
        nnext[i][0] = go[i];
    }

    precomp();

    while (q--) {
        int x, k;
        cin >> x >> k;
        x--;
        for (int j=LOG; j>=0; j--) {
            if ((k >> j)&1) {
                x = nnext[x][j];
            }
        }
        cout << x+1 << "\n";
    }
}