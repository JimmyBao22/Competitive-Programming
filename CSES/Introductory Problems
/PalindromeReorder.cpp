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

// https://cses.fi/problemset/task/1755

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    string s;
    cin >> s;
    int n = int(s.size());
    int c[26];
    memset(c, 0, sizeof(c));
    for (int i=0; i<n; i++) {
        c[s[i] - 'A']++;
    }

    if (n%2 == 1) {
        bool onefound = false;
        char one;
        string forwards;
        for (int i=0; i<26; i++) {
            if (c[i]%2 == 1) {
                if (onefound) {
                    cout << "NO SOLUTION";
                    return 0;
                }
                onefound=true;
                one = i+'A';
            }
            while (c[i] > 1) {
                forwards += (i + 'A');
                c[i] -= 2;
            }
       }
       if (!onefound) {
            cout << "NO SOLUTION";
            return 0;
       }
       string backwards = forwards;
       reverse(backwards.begin(), backwards.end());
       cout << forwards << one << backwards;
    }
   else {
       string forwards;
       for (int i=0; i<26; i++) {
           if (c[i]%2 == 1) {
               cout << "NO SOLUTION";
               return 0;
           }
           while (c[i] > 0) {
               forwards += (i + 'A');
               c[i] -= 2;
           }
       }
       string backwards = forwards;
       reverse(backwards.begin(), backwards.end());
       cout << forwards << backwards;
    }
}