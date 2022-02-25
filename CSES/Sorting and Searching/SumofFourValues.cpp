#include <iostream>
#include <vector>
#include <map>
#include <cmath>
#include <algorithm>
#include <deque>
#include <queue>
#include <stack>
#include <set>
#include <unordered_set>
#include <string>
#include <string.h>
#include <array>
#include <unordered_map>
#include <unordered_set>
#include <iomanip>
using namespace std;
typedef long long ll;

// https://cses.fi/problemset/task/1642

const int MaxN = 1e3+10;
ll arr[MaxN];
int n; ll x;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    cin >> n >> x;
    for (int i=0; i<n; i++) cin >> arr[i];

    vector<pair<ll, pair<int, int>>> allpairs;
    for (int i=0; i<n; i++) {
        pair<ll, pair<int, int>> cur;
        for (int j=i+1; j<n; j++) {
            cur.first = arr[i] + arr[j];
            cur.second.first = i;
            cur.second.second = j;
            allpairs.push_back(cur);
        }
    }

    sort(begin(allpairs), end(allpairs));

    int m = allpairs.size();
    int j=m-1;

    for (int i=0; i<m; i++) {
        while (j-1>=i && j-1>=0 && allpairs[i].first + allpairs[j-1].first >= x) j--;
        int k = j;
        while (k<m && allpairs[j].first == allpairs[k].first) {
            if (allpairs[i].first + allpairs[k].first == x) {
                if (allpairs[i].second.first != allpairs[k].second.first &&
                    allpairs[i].second.first != allpairs[k].second.second
                    && allpairs[i].second.second != allpairs[k].second.first && 
                    allpairs[i].second.second != allpairs[k].second.second) {
                            
                        cout << allpairs[i].second.first+1 << " " << allpairs[i].second.second+1 << " ";
                        cout << allpairs[k].second.first+1 << " " << allpairs[k].second.second+1;
                        return 0;
                    }
            }
            else break;
            k++;
        }
    }

    cout << "IMPOSSIBLE";
}