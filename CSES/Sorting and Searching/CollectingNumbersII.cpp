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

// https://cses.fi/problemset/task/2217

const int MaxN = 2e5+10;
int arr[MaxN], n, m;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> m;
    for (int i=0 ; i<n; i++) {
        cin >> arr[i];
    }

    int c=0;
    map<int, int> mm;

    for (int i=0; i<n; i++) {
        if (mm[arr[i]+1]) {
            c++;
        }
        mm[arr[i]] = i+1;
    }

    for (int i=0; i<m; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        if (a>b) swap(a,b);

        if (arr[a] != n) {
            int higher_index = mm[arr[a]+1]-1;
            if (higher_index >= a && higher_index <= b) {
                c++;
            }
        }
        if (arr[a] != 0) {
            int lower_index = mm[arr[a]-1]-1;
            if (lower_index >= a && lower_index <= b) {
                c--;
            }
        }

        if (arr[b] != n) {
            int higher_index = mm[arr[b]+1]-1;
            if (higher_index >= a && higher_index <= b) {
                c--;
            }
        }
        if (arr[b] != 0) {
            int lower_index = mm[arr[b]-1]-1;
            if (lower_index >= a && lower_index <= b) {
                c++;
            }
        }

        if (abs(arr[a] - arr[b]) == 1) {
            // get rid of overcount
            if (arr[a] > arr[b]) {
                c++;
            }
            else {
                c--;
            }
        }

        swap(arr[a], arr[b]);
        mm[arr[a]] = a+1;
        mm[arr[b]] = b+1;

        cout << c+1 << "\n";
    }
}