#include <vector>
using namespace std;

class Solution {
public:
    int n, k;
    vector<vector<int>> memo;

    static bool comparator(vector<int>& a, vector<int>& b) {
        return a[0] < b[0]; // sort by start date
    }

    int maxValue(vector<vector<int>>& events, int k) {
        n = events.size();
        this->k = k;
        sort(events.begin(), events.end(), comparator);
        
        // memo[i][j] = at index i, attended j events
        memo = vector<vector<int>>(n, vector<int>(k+1, -1));
        int ans = dp(events, 0, 0);
        return ans;
    }

    int dp(vector<vector<int>>& events, int i, int j) {
        if (i >= n || j >= k) {
            return 0;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }

        // skip this event
        int maxSum = dp(events, i + 1, j);

        // use this event
        // binary search for next event possible
        int minV = i+1;
        int maxV = n;
        while (minV < maxV) {
            int mid = (minV + maxV) >> 1;
            if (events[i][1] < events[mid][0]) {
                maxV = mid;
            } else {
                minV = mid + 1;
            }
        }
        maxSum = max(maxSum, dp(events, minV, j+1) + events[i][2]);

        return memo[i][j] = maxSum;
    }
};