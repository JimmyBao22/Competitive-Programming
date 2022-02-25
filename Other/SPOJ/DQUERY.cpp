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

struct A {
    int left, right, index;
    A(){}
    A(int a, int b, int c) {
        left = a; right = b; index = c;
    }
};

const int MaxN = 30001, MaxQ = 200001;
int arr[MaxN], answer[MaxQ], c[(int)(1e6+1)];
A queries[MaxQ];
int n, q, k;

bool compare(A a, A b) {
    if (a.left/k == b.left/k) {
        return a.right < b.right;
    }
    else {
        return a.left/k < b.left/k;
    }
}

int add(int index, int arr[], int ans) {
    arr[index]++;
    if (arr[index] == 1) ans++;
    return ans;
}

int remove(int index, int arr[], int ans) {
    arr[index]--;
    if (arr[index] == 0) ans--;
    return ans;
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    int n;
    cin >> n;
    for (int i=0; i<n; i++) cin >> arr[i];
    k = sqrt(n);
    cin >> q;
    for (int i=0; i<q; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        queries[i] = A(a, b, i);
    }

    sort(queries, queries+q, compare);
    
    int ans = 0;
    int left = 0;
    int right = 0;
    ans = add(arr[0], c, ans);
    for (int i=0; i<q; i++) {
        while (left < queries[i].left) {
            ans = remove(arr[left], c, ans);
            left++;
        }
        while (left > queries[i].left) {
            left--;
            ans = add(arr[left], c, ans);
        }
        while (right < queries[i].right) {
            right++;
            ans = add(arr[right], c, ans);
        }
        while (right > queries[i].right) {
            ans = remove(arr[right], c, ans);
            right--;
        }
        answer[queries[i].index]= ans;
    }
    
    for (int i=0; i<q; i++) {
        cout << answer[i] << "\n";
    }
}