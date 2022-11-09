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
#include <ext/pb_ds/tree_policy.hpp>
#include <ext/pb_ds/assoc_container.hpp>
using namespace __gnu_pbds;
using namespace std;
typedef long long ll;
typedef long double ld;
#define pb push_back
template <class T> using OSTree = tree<T, null_type, less<T>, rb_tree_tag, tree_order_statistics_node_update>;

// https://codeforces.com/edu/course/2/lesson/4/2/practice/contest/273278/problem/B

const int MaxN = 1e5+1;
int arr[MaxN];
OSTree<int> o;      // stores indices with ones
int n, m;

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0); 

    cin >> n >> m;
    for (int i=0; i<n; i++) {
        cin >> arr[i];
        if (arr[i] == 1) {
            o.insert(i);
        }
    }

    for (int i=0; i<m; i++) {
        int a, b;
        cin >> a >> b;
        if (a == 1) {
            if (arr[b] == 1) {
                o.erase(b);
            }
            else {
                o.insert(b);
            }
            arr[b] ^= 1;
        }
        else {
            cout << *o.find_by_order(b) << "\n";
        }
    }
}