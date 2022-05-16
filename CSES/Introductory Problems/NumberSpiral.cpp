#include <iostream>
#include <vector>
#include <map>
#include <cmath>
#include <algorithm>
#include <deque>
#include <queue>
#include <set>
#include <unordered_set>
#include <string>
#include <array>
#include <unordered_map>
#include <unordered_set>
using namespace std;

// https://cses.fi/problemset/task/1071

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    int t;
    cin >> t;
    while (t-->0) {
        long x,y;
        cin >> x >> y;
        x--; y--;
        
        if (x >= y) {
            if (x == 0) {
                cout << 1;
            }
            else {
                if (x%2==1) {
                    long val = (x+1)*(x+1);
                    val -= y;
                    cout << val;
                }
                else {
                    long val = x*x + 1;
                    val += y;
                    cout << val;
                }
            }
        }
        else {
            if (y%2==0) {
                long val = (y+1)*(y+1);
                val -= x;
                cout << val;
            }
            else {
                long val = y*y + 1;
                val += x;
                cout << val;
            }
        }

        cout << "\n";
    }
}