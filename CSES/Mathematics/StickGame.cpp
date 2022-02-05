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

// https://cses.fi/problemset/task/1729

const int MaxN = 1e6+10, MaxK = 110;
bool win[MaxN][2];          // {first person winning strat, second person winning strat}
int p[MaxK], n, k;

void print() {
    for (int i=0; i<=n; i++) {
        cout << win[i][0] << " " << win[i][1] << "\n";
    }
    cout << "\n";
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> k;
    for (int i=0; i<k; i++) {
        cin >> p[i];
    }

    win[0][1] = true; 
    for (int i=1; i<=n; i++) {
        for (int j=0; j<k; j++) {
            if (i - p[j] >= 0 && win[i-p[j]][1]) {
                win[i][0] = true;
            }
        }
        win[i][1] = !win[i][0];
        if (win[i][0]) {
            cout << 'W';
        }
        else {
            cout << 'L';
        }
    }
}