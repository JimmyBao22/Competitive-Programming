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

// https://cses.fi/problemset/task/1069

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    string s;
    cin >> s;

    int ans=1;
    int cur=1;
    char last = s[0];
    for (int i=1; i<s.size(); i++) {
        if (s[i] == last) {
            cur++;
        }
        else {
            ans = max(ans,cur);
            cur=1;
            last=s[i];
        }
    }

    ans=max(ans,cur);

    cout << ans;
}