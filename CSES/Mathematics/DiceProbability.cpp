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

// https://cses.fi/problemset/task/1725

const int MaxN = 1e3+1;
double arr[MaxN][6*MaxN];
int n;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n;
    for (int i=1; i<=6; i++) {
        arr[0][i] = 1.0/6;
    }

    for (int i=1; i<n; i++) {
        for (int k=1; k<=6; k++) {
            for (int j=6*n; j>=k; j--) {
                arr[i][j] += 1.0/6 * arr[i-1][j-k];
            }
        }
    }    

    int a, b;
    cin >> a >> b;
    double ans=0;
    for (int i=a; i<=b; i++) {
        ans += arr[n-1][i];
    }

    cout << fixed << setprecision(6) << ans;
}