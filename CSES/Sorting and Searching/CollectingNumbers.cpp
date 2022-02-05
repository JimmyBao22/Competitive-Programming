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

// https://cses.fi/problemset/task/2216

const int MaxN = 2e5+10;
int arr[MaxN], n;
bool seen[MaxN];

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n;
    for (int i=0 ; i<n; i++) {
        cin >> arr[i];
    }

    int c=0;
    for (int i=0; i<n; i++) {
        if (seen[arr[i]+1]) c++;
        seen[arr[i]] = true;
    }

    cout << c+1;
}