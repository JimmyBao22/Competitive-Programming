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
typedef long double ld;
#define pb push_back

// https://open.kattis.com/problems/catandmice

const int N = 17;
int n;
ll INF = 1e15;
pair<pair<ld, ld>, int> all[N];
ld m[N];

ld dist(int a, int b) {
    return sqrt((all[a].first.first - all[b].first.first) * (all[a].first.first - all[b].first.first) + 
    (all[a].first.second - all[b].first.second) * (all[a].first.second - all[b].first.second));
}

bool check(double mid) {
    ld dp[n][(1 << n)];
        // last took, taken = min time
    for (int i=0; i<n; i++) {
        for (int j=0; j<(1 << n); j++) {
            dp[i][j] = INF;
        }
    }

    dp[n-1][(1 << (n-1))] = 0;

    for (int i=(1 << (n-1)) + 1; i<(1 << n); i++) {
        vector<int> curused;
        for (int j=0; j<n; j++) {
            if ((i >> j) & 1) {
                curused.pb(j);
            }
        }
        for (int j=0; j<curused.size(); j++) {
            for (int k=0; k<curused.size(); k++) {
                if (j != k) {
                    ld time = dp[curused[k]][i - (1 << curused[j])] + dist(curused[j], curused[k]) / (mid * m[curused.size()-2]);
                    // cout << j << " " << k << " " << time << "\n";
                    if (time <= all[curused[j]].second) {
                        dp[curused[j]][i] = min(dp[curused[j]][i], time);
                    }
                }
            }
        }
    }

    for (int i=0; i<n; i++) {
        if (dp[i][(1 << n) - 1] < INF) return true;
    }
    return false;
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> n;
    for (int i=0; i<n; i++) {
        cin >> all[i].first.first >> all[i].first.second >> all[i].second;
    }
    all[n] = {{0, 0}, 0};   // starting point
    n++;
    m[0] = 1;
    cin >> m[1];
    for (int j=2; j<=n; j++) {
        m[j] = m[j-1] * m[1];
    }

    double min=0;
    double max=1e9;
    for (int i=0; i<100; i++) {
        double middle = min + (max-min)/2;
        if (check(middle)) {
            max = middle;
        }
        else min = middle;
    }

    printf("%.5f", min);
}