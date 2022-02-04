#include <iostream>
#include <vector>
#include <map>
#include <cmath>
#include <algorithm>
#include <deque>
#include <queue>
#include <stack>
#include <set>
#include <unordered_set>
#include <string>
#include <string.h>
#include <array>
#include <unordered_map>
#include <unordered_set>
#include <iomanip>
using namespace std;
typedef long long ll;

// https://cses.fi/problemset/task/1745

bool can[(int)1e5 + 10];

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    int arr[n];
    for (int i=0; i<n; i++) cin >> arr[i];
    
    can[0] = 1;
    for (int i=0; i<n; i++) {
        for (int j=1e5; j>=arr[i]; j--) {
            if (can[j - arr[i]]) {
                can[j] = 1;
            }
        }
    }

    vector<int> all;
    for (int i=1; i<=1e5; i++) {
        if (can[i]) all.push_back(i);
    }

    cout << all.size() << "\n";
    for (int i=0; i<all.size(); i++) cout << all[i] << " ";
}