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

// https://cses.fi/problemset/task/1629

struct A {
    int start, end;
};

bool comp(A a, A b) {
    return a.end < b.end;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    A arr[n];
    for (int i=0; i<n; i++) {
        cin >> arr[i].start >> arr[i].end;
    }

    sort(arr, arr+n, comp);

    int count=1;
    int lasttime = arr[0].end;
    for (int i=0; i<n; i++) {
        if (lasttime <= arr[i].start) {
            count++;
            lasttime = arr[i].end;
        }
    }

    cout << count;  
}