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

// https://cses.fi/problemset/task/2182

const int MaxN = 1e5+2;
pair<ll, ll> arr[MaxN];
int n;
ll mod = 1e9+7;

ll gcd(ll a, ll b) { 
    if (b == 0) return a; 
    return gcd(b, a%b); 
} 

ll pow(ll a, ll b, ll m) {
    ll ans=1;
    while (b >0) {
        if (b%2 == 1) {
            ans *= a;
            ans %= m;
        }
        a *= a;
        a%=m;
        b >>= 1;
    }
    return ans;
}

ll modInverse(ll a, ll m) {
    return pow(a, m - 2, m)%m;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n;
    for (int i=0; i<n; i++) {
        cin >> arr[i].first >> arr[i].second;
    }

    ll numdivisors = 1;  
    ll sumdivisors = 1;
    ll proddivisors = 1;
    for (int i=0; i<n; i++) {
        numdivisors *= (arr[i].second+1);
        numdivisors %= mod;
    }

    for (int i=0; i<n; i++) {
        sumdivisors *= (pow(arr[i].first, arr[i].second+1, mod) - 1);
        sumdivisors %= mod;
        sumdivisors *= modInverse(arr[i].first-1, mod);
        sumdivisors %= mod;
    }

    ll numdivisors2 = 1;
    for (int i=0; i<n; i++) {
        // arr[i].first^((1+2+...+arr[i].second))*((numdivisors)/(arr[i].second+1))
        // (1+2+...+arr[i].second) = arr[i].second*(arr[i].second+1)/2
        // ((numdivisors)/(arr[i].second+1)) do it as you go along the array
            // raise everything previously to (arr[i].second+1), then raise current to num divisors so far
        
        ll curexp = (arr[i].second * (arr[i].second+1))/2;
        curexp %= (mod-1);

        proddivisors = pow(proddivisors, arr[i].second+1, mod);
        proddivisors *= pow(pow(arr[i].first, curexp, mod), numdivisors2, mod);
        proddivisors %= mod;
        numdivisors2 *= (arr[i].second+1);
        numdivisors2 %= (mod-1);
    }

    numdivisors = (numdivisors%mod+mod)%mod;
    sumdivisors = (sumdivisors%mod+mod)%mod;
    proddivisors = (proddivisors%mod+mod)%mod;

    cout << numdivisors << " " << sumdivisors << " " << proddivisors;
}