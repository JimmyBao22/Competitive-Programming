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
typedef long double ld;
#define pb push_back

// https://cses.fi/problemset/task/1076

const int MaxN = 2e5+1;
int arr[MaxN];
map<int, int> one, two;
            // one stores everything less than or equal to median, two everything greater (or equal to)
int n, k, size1, size2, median;

void change() {         // makes sure size1 = median, so that last element of "one" is the median
    
        // move from two to one
    while (size1 < median) {
        auto cur = *two.begin();
        if (size1 + cur.second > median) {
            int need = median - size1;
            one[cur.first] += need;
            two[cur.first] -= need;
            if (two[cur.first] == 0) two.erase(cur.first);
            size1 = median;
            size2 -= need;
        }
        else {
            size1 += cur.second;
            size2 -= cur.second;
            one[cur.first] += cur.second;
            two.erase(cur.first);
        }
    }

        // move from one to two
    while (size1 > median) {
        auto cur = *(--one.end());
        if (size1 - cur.second > median) {
            size1 -= cur.second;
            size2 += cur.second;
            two[cur.first] += cur.second;
            one.erase(cur.first);
        }
        else {
            int need = size1 - median;
            one[cur.first] -= need;
            if (one[cur.first] == 0) one.erase(cur.first);
            two[cur.first] += need;
            size1 = median;
            size2 += need;
        }
    }
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> n >> k;
    for (int i=0; i<n; i++) {
        cin >> arr[i];
        if (i < k) two[arr[i]]++;
    }

    median = (k+1)/2;
    size1 = 0;
    size2 = k;

    change();

    cout << (*(--one.end())).first << " ";


    for (int i=0; i+k<n; i++) {
        if (one.count(arr[i])) {
            one[arr[i]]--;
            size1--;
            if (one[arr[i]] == 0) one.erase(arr[i]);
        }
        else {
            two[arr[i]]--;
            size2--;
            if (two[arr[i]] == 0) two.erase(arr[i]);
        }

        if (size1 != 0 && (*(--one.end())).first >= arr[i+k]) {
            one[arr[i+k]]++;
            size1++;
        }  
        else {
            two[arr[i+k]]++;
            size2++;
        }

        change();

        cout << (*(--one.end())).first << " ";   
    }
}