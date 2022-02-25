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

// https://cses.fi/problemset/task/1164

struct A {
    int start, end, index;
    
    void print() {
        cout << start << " " << end << " " << index << "\n";
    }
};

bool comp(A a, A b) {
    if (a.start == b.start) return a.end < b.end;
    return a.start < b.start;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    A a[n];
    for (int i=0; i<n; i++) {
        cin >> a[i].start >> a[i].end;
        a[i].index = i;
    }

    sort(a, a+n, comp);

    map<int, vector<int>> m;
    int ans[n];
    int maxroom=1;
    for (int i=0; i<n; i++) {
        auto min = m.begin();
        if (min == m.end()) {
            // make new room
            m[a[i].end].push_back(maxroom);
            ans[a[i].index] = maxroom;
            maxroom++;
        }
        else {
            int val = (*min).first;
            if (val < a[i].start) {
                // can put here
                int put = m[val][m[val].size()-1];
                m[val].pop_back();
                if (m[val].size() == 0) {
                    m.erase(val);
                }
                //cout << val << " " << put;
                ans[a[i].index] = put;
                m[a[i].end].push_back(put);
            }
            else {
                // make new room
                m[a[i].end].push_back(maxroom);
                ans[a[i].index] = maxroom;
                maxroom++;
            }
        }
    }

    cout << maxroom-1 << "\n";
    for (int i=0; i<n; i++) {
        cout << ans[i] << " ";
    } 
}