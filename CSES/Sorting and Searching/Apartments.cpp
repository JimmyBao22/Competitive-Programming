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
#include <string.h>
#include <array>
#include <unordered_map>
#include <unordered_set>
using namespace std;
typedef long long ll;

// https://cses.fi/problemset/task/1084

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n,m,k;
    cin >> n >> m >> k;

    int people[n];
    int apart[m];
    for (int i=0; i<n; i++) cin >> people[i];
    for (int i=0; i<m; i++) cin >> apart[i];

    sort(people, people+n);
    sort(apart, apart+m);

    int count=0;
    int p=0;
    for (int i=0; i<n; i++) {
        while (p < m && apart[p] < people[i] - k) p++;
        if (p < m && apart[p] >= people[i] - k && apart[p] <= people[i] + k) {
            p++;
            count++;
        }
    }
    cout << count;
}