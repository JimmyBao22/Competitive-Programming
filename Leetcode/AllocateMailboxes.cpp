#include <vector>
using namespace std;

class AllocateMailboxes {
public:

    // https://leetcode.com/problems/allocate-mailboxes/description/

    const int INF = 1e9;

    int findSumLeftToRight(vector<int>& houses, int leftSide, int rightSide, vector<int>& psum) {
        int index = (rightSide + leftSide) / 2;
        int median = houses[index];
        int sum = 0;
        sum += (index - leftSide + 1) * median;
        sum -= psum[index];
        if (leftSide > 0) sum += psum[leftSide - 1];
        
        sum += psum[rightSide];
        sum -= psum[index];
        sum -= (rightSide - (index+1) + 1) * median;

        return sum;
    }

    int dp(int n, int k, vector<int>& houses, int leftSide, int rightSide, int kUsed, vector<vector<vector<int>>>& memo, vector<int>& psum) {
        if (memo[leftSide][rightSide][kUsed] != -1) return memo[leftSide][rightSide][kUsed];
        if (rightSide == n-1) {
            if (kUsed != k-1) return INF;
            // add last mailbox for houses [leftSide, n-1]
            // put at median position
            int sum = findSumLeftToRight(houses, leftSide, rightSide, psum);
            return memo[leftSide][rightSide][kUsed] = sum;
        }
        if (kUsed == k-1) {
            return dp(n, k, houses, leftSide, n-1, kUsed, memo, psum);
        }
    
        int ans = INF;
        
        // don't add mailbox yet
        ans = dp(n, k, houses, leftSide, rightSide + 1, kUsed, memo, psum);

        // add a mailbox for houses [leftSide, rightSide]
        // put at median position
        int sum = findSumLeftToRight(houses, leftSide, rightSide, psum);

        ans = min(ans, dp(n, k, houses, rightSide+1, rightSide+1, kUsed+1, memo, psum) + sum);

        return memo[leftSide][rightSide][kUsed] = ans;
    }

    int minDistance(vector<int>& houses, int k) {
        int n = houses.size();
        sort(houses.begin(), houses.end());
        vector<int> psum(n);
        psum[0] = houses[0];
        for (int i = 1; i < n; i++) {
            psum[i] = psum[i - 1] + houses[i];
        }

        // memo[n][n][k+1] -> 2 indices for houses (left side & right side), how many mailboxes (k) used
        vector<vector<vector<int>>> memo(n, vector<vector<int>>(n, vector<int>(k+1, -1)));

        return dp(n, k, houses, 0, 0, 0, memo, psum);
    }
};