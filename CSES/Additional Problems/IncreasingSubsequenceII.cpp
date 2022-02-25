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

// https://cses.fi/problemset/task/1748

const int MaxN = 2e5+1;
pair<int, int> arr[MaxN];           // val, index
ll mod = 1e9+7;
int n;

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
            ret %= mod;
            i -= i&-i;
        }
        return ret;
    }
    
    // add value to index i
    void add(int i, ll value) {	
        i++;
        while (i<=n) {
            f[i] += value;
            f[i] %= mod;
            i += i&-i;
        }
    }
};

bool comp(pair<int, int> one, pair<int, int> two) {
    if (one.first != two.first) return one.first < two.first;
    return two.second < one.second;     // largest second first, this way something like 2,2 won't be counted
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    
    cin >> n;
    for (int i=0; i<n; i++) {
        cin >> arr[i].first;
        arr[i].second = i;
    }

    sort(arr, arr+n, comp);

    BIT bit(n);     
                // bit at the ith position will be the number of increasing subsequences ending here
    ll ans = 0;

    for (int i=0; i<n; i++) {
        ll cur = bit.sum(arr[i].second);
        ans += (cur + 1);
        ans %= mod;
        bit.add(arr[i].second, cur + 1);
    }
    cout << ans;
}