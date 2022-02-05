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

// https://cses.fi/problemset/task/1713

vector<int> getDivisors(int n) { 
    vector<int> a;
    for (int i = 1; i*i<=n; i++)  { 
        if (n%i==0) { 
            if (n/i == i) a.push_back(i);
            else {
                a.push_back(i);
                a.push_back(n/i);
            }
        } 
    } 
    return a;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int n;
    cin >> n;
    for (int i=0; i<n; i++) {
        int a;
        cin >> a;
        cout << getDivisors(a).size() << "\n";
    }
}