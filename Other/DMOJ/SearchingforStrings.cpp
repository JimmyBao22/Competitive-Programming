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
#define pb push_back

// https://dmoj.ca/problem/ccc20s3#

const int alphabet = 26;
int narr[alphabet], harr[alphabet];
string a, b;
int n, m;
vector<set<ll>> used;
vector<ll> primes, largeprimes;
ll mod = 1e9+7;

ll pow(ll a, ll b, ll m) {
    ll ans = 1;
    while (b > 0) {
        if (b%2 == 1) {
            ans *= a;
            ans %= m;
        }
        a *= a;
        a %= m;
        b >>= 1;
    }
    return ans;
}

void print() {
    for (int i=0; i<alphabet; i++) {
        cout << narr[i] << " ";
    }
    cout << "\n";
    for (int i=0; i<alphabet; i++) {
        cout << harr[i] << " ";
    }
    cout << "\n" << "\n";
}

bool check() {
    for (int i=0; i<alphabet; i++) {
        if (narr[i] != harr[i]) return false;
    }
    return true;
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> a >> b;
    n = a.length();
    m = b.length();

    primes = {73, 79, 83, 89};
    int k = (int)primes.size();
    largeprimes.resize(k);
    for (int i=0; i<k; i++) {
        largeprimes[i] = pow(primes[i], n-1, mod);
    }
    used.resize(k);
    vector<ll> cur (k, 0);
    for (int i=0; i<n; i++) {
        narr[a[i]-'a']++;
        harr[b[i]-'a']++;
        for (int j=0; j<k; j++) {
            cur[j] = cur[j] * primes[j] + b[i] - 'a';
            cur[j] %= mod;
        }
    }
    
    // print();
    int count=0;
    bool works=false;
    for (int j=0; j<k; j++) {
        if (!used[j].count(cur[j])) {
            works = true;
            break;
        }
    }
    if (works && check()) {
        count++;
        for (int j=0; j<k; j++) {
            used[j].insert(cur[j]);
        }
    }

    for (int i=1; i+n-1<m; i++) {
        harr[b[i-1]-'a']--;
        harr[b[i+n-1]-'a']++;
        for (int j=0; j<k; j++) {
            cur[j] -= largeprimes[j] * (b[i-1] - 'a');
            cur[j] = cur[j] * primes[j] + b[i+n-1] - 'a';
            cur[j] = (cur[j]%mod + mod)%mod;
        }
        // print();
        
        works=false;
        for (int j=0; j<k; j++) {
            if (!used[j].count(cur[j])) {
                works = true;
                break;
            }
        }
        if (works && check()) {
            count++;
            for (int j=0; j<k; j++) {
                used[j].insert(cur[j]);
            }
        }
    }

    cout << count;
}