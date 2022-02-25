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

// https://cses.fi/problemset/task/2190

struct Point {
    ll x; ll y;
};

int t;
// ll x1, x2, x3, x4, yy1, yy2, yy3, yy4;
Point one, two, three, four;

double ccw(Point a, Point b, Point c) {
    double val = (double)(b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y);
    return val == 0 ? 0 : val < 0 ? -1 : 1;
}

bool onSegment(Point a, Point c, Point b) {
    if (c.x <= max(a.x, b.x) && c.x >= min(a.x, b.x) && 
            c.y <= max(a.y, b.y) && c.y >= min(a.y, b.y)) return true; 
        
    return false; 
}

// returns whether a1 - a2 line segment intersects b1 - b2 line segment
bool Intersect(Point a1, Point a2, Point b1, Point b2) {
    double o1 = ccw(a1, a2, b1); 
    double o2 = ccw(a1, a2, b2); 
    double o3 = ccw(b1, b2, a1); 
    double o4 = ccw(b1, b2, a2); 
    
    if (o1 != o2 && o3 != o4) return true;
    
    if (o1 == 0 && onSegment(a1, b1, a2)) return true; 
    if (o2 == 0 && onSegment(a1, b2, a2)) return true; 	  
    if (o3 == 0 && onSegment(b1, a1, b2)) return true; 	  
    if (o4 == 0 && onSegment(b1, a2, b2)) return true; 
    
    return false;
}

// WRong
int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    cin >> t;
    while (t--) {
        // cin >> x1 >> yy1 >> x2 >> yy2 >> x3 >> yy3 >> x4 >> yy4;
        cin >> one.x >> one.y >> two.x >> two.y >> three.x >> three.y >> four.x >> four.y;
        if (Intersect(one, two, three, four)) {
            cout << "YES";
        }
        else {
            cout << "NO";
        }
        cout << "\n";
    }
}