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

// https://cses.fi/problemset/task/1619

struct A {
    int time, id;
};

bool comp(A a, A b) {
    return a.time < b.time;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    A arr[2*n];
    for (int i=0; i<n; i++) {
        cin >> arr[2*i].time;
        arr[2*i].id = i;
        cin >> arr[2*i+1].time;
        arr[2*i+1].id = i;
    }    

    sort(arr, arr+2*n, comp);

    int ans=0;
    // set<int> cur;
    unordered_set<int> cur;
    for (int i=0; i<2*n; i++) {
        if (cur.count(arr[i].id) > 0) {
            cur.erase(arr[i].id);
        }
        else {
            cur.insert(arr[i].id);
        }
        ans = max(ans,(int)cur.size());
    }

    cout << ans;
}