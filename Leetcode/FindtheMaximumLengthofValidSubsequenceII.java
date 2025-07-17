public class FindtheMaximumLengthofValidSubsequenceII {

    // https://leetcode.com/problems/find-the-maximum-length-of-valid-subsequence-ii/

    public int maximumLength(int[] nums, int k) {
        int n = nums.length;
        int[][] prevIndex = new int[n][k];  // last index with that modulo value
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                if (i == 0) prevIndex[i][j] = -1;
                else prevIndex[i][j] = prevIndex[i-1][j];
            }
            if (i-1 >= 0) prevIndex[i][nums[i-1] % k] = i-1;
        }

        int ans = 0;
        for (int modulo = 0; modulo < k; modulo++) {
            // what is the longest valud sequence with this modulo
            int[] dp = new int[n];
            for (int i = 1; i < n; i++) {
                int curMod = nums[i] % k;
                int neededMod = (modulo - curMod + k) % k;
                int neededModIndex = prevIndex[i][neededMod];
                if (neededModIndex != -1) {
                    dp[i] = 1 + Math.max(dp[neededModIndex], 1);
                    ans = Math.max(ans, dp[i]);
                }
            }
        }

        return ans;
    }
}
