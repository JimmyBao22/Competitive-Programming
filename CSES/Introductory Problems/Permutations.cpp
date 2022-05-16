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

// https://cses.fi/problemset/task/1070

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    
    if (n == 1) {
        cout << 1;
        return 0;
    }
    
    if (n <= 3) {
        cout << "NO SOLUTION";
        return 0;
    }

    for (int i=2; i<=n; i+=2) {
        cout << i << " ";
    }

    for (int i=1; i<=n; i+=2) {
        cout << i << " ";
    }
}