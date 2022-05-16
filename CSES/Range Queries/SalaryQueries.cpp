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

// https://cses.fi/problemset/task/1144

struct BIT {
    int n;
    vector<ll> f;   // 1 base indexing
    
    BIT (int nn) {
        n = nn;
        f.resize(n+1, 0);
    }

    // sum from i to 0
    ll sum (int i) {		
        i++;
        ll ret=0;
        while (i>0) {
            ret += f[i];
            i -= i&-i;
        }
        return ret;
    }

    // sum from l to r
    ll sum (int l, int r) {	
        return sum(r) - sum(l-1);
    }
    
    // add value to index i
    void add(int i, ll value) {	
        i++;
        while (i<=n) {
            f[i] += value;
            i += i&-i;
        }
    }
};

struct A {
    char type; int one, two;
    A(){}
    A (char a, int b, int c) {
        type = a; one = b; two = c;
    }
};

const int MaxN = 2e5+1;
ll arr[MaxN];
A queries[MaxN];
set<ll> allSalariesSet;
vector<ll> allSalaries;
// map<ll, int> allSalariesMap;     // map --> TLE
int n, q;

int index(int value) {
    return lower_bound(allSalaries.begin(), allSalaries.end(), value) - allSalaries.begin();
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> q;
    for (int i=0; i<n; i++) {
        cin >> arr[i];
        allSalariesSet.insert(arr[i]);
    }

    for (int i=0; i<q; i++) {
        cin >> queries[i].type >> queries[i].one >> queries[i].two;
        if (queries[i].type == '!') allSalariesSet.insert(queries[i].two);
    }

        // coord compress
    for (auto i : allSalariesSet) {
        allSalaries.pb(i);
    }

    int m = allSalaries.size();

    BIT bit(m);
    for (int i=0; i<n; i++ ) {
        bit.add(index(arr[i]), 1);
    }

    for (int i=0; i<q; i++) {
        if (queries[i].type == '!') {
            int j = queries[i].one - 1;
            bit.add(index(arr[j]), -1);
            arr[j] = queries[i].two;
            bit.add(index(arr[j]), 1);
        }
        else {
            auto lowerone = lower_bound(allSalaries.begin(), allSalaries.end(), queries[i].one) - allSalaries.begin();     // java's ceilingkey
            auto upperone = upper_bound(allSalaries.begin(), allSalaries.end(), queries[i].two) - 1 - allSalaries.begin();     // java's floorkey
            cout << bit.sum(lowerone, upperone) << "\n";
        }
    }
}