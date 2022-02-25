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

// https://cses.fi/problemset/task/2191

const int MaxN = 1001;
pair<ll, ll> points[MaxN];
int n;

ll ShoelaceArea (pair<ll, ll> points[], int n) {
    ll firstsum=0;
    ll secsum=0;
    for (int i=0; i<n; i++) {
        if (i == n-1) {
            firstsum += points[i].first * points[0].second;
            secsum += points[i].second * points[0].first;
        }
        else {
            firstsum += points[i].first * points[i+1].second;
            secsum += points[i].second * points[i+1].first;
        }
    }
    return abs(firstsum - secsum);
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> n;
    for (int i=0; i<n; i++) {
        cin >> points[i].first >> points[i].second;
    }

    cout << ShoelaceArea(points, n);
}