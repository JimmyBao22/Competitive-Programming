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

// https://cses.fi/problemset/task/2165/

int val(string a) {
    if (a == "Left") return 1;
    if (a == "Middle") return 2;
    return 3;
}

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);

    int n;
    cin >> n;

    vector<pair<string, string>> moves;
    moves.pb({"Left", "Right"});

    // first move everything (except the last, largest one) to the middle. Move the last, largest one to
        // the right. Finally, move everything else to the right.
    for (int i=2; i<=n; i++) {
        vector<pair<string, string>> updated;
        
        for (int j=0; j<moves.size(); j++) {
            // flip middle and right, becaues you want to put the full stack of the previous #
                // in the middle rather than at the right
            if (moves[j].first == "Middle") {
                if (moves[j].second == "Right") {
                    updated.pb({"Right", "Middle"});
                }
                else {
                    updated.pb({"Right", moves[j].second});
                }
            }
            else if (moves[j].first == "Right") {
                if (moves[j].second == "Middle") {
                    updated.pb({"Middle", "Right"});
                }
                else {
                    updated.pb({"Middle", moves[j].second});
                }
            }
            else {
                if (moves[j].second == "Right") {
                    updated.pb({"Left", "Middle"});
                }
                else if (moves[j].second == "Middle") {
                    updated.pb({"Left", "Right"});
                }
            }
        }
        // move the last one to the right
        updated.pb({"Left", "Right"});
        
        for (int j=0; j<moves.size(); j++) {
            // flip left and middle, because now the full stack of the previous # is starting in the middle
                // rather than on the left
            if (moves[j].first == "Left") {
                if (moves[j].second == "Middle") {
                    updated.pb({"Middle", "Left"});
                }
                else {
                    updated.pb({"Middle", moves[j].second});
                }
            }
            else if (moves[j].first == "Middle") {
                if (moves[j].second == "Left") {
                    updated.pb({"Left", "Middle"});
                }
                else {
                    updated.pb({"Left", moves[j].second});
                }
            }
            else {
                if (moves[j].second == "Left") {
                    updated.pb({"Right", "Middle"});
                }
                else if (moves[j].second == "Middle") {
                    updated.pb({"Right", "Left"});
                }
            }
        }
        moves = updated;
    }
    
    cout << moves.size() << "\n";
    for (int i=0; i<moves.size(); i++) {
        cout << val(moves[i].first) << " " << val(moves[i].second) << "\n";
    }
}