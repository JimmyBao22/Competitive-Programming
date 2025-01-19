class MaximumSumof3NonOverlappingSubarrays {

    // https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/?envType=daily-question&envId=2024-12-28

    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        int[] psum = new int[n];
        psum[0] = nums[0];
        for (int i = 1; i < n; i++) psum[i] = psum[i-1] + nums[i];

        // dp[i][j] = max sum up to index i using j subarrays
        int[][] dp = new int[n][4];
        int[][] parent = new int[n][4];

        for (int i = 0; i < n; i++) {
            parent[i][0] = Integer.MIN_VALUE;
        }

        parent[0][1] = -k;
        dp[0][1] = psum[k-1];
        for (int i = 1; k+i-1 < n; i++) {
            for (int j = 1; j < 4; j++) {
                int currentSum = psum[k+i-1] - psum[i-1];
                // either take this value or skip
                // take = come from i-k; skip = come from i-1
                int prevSum = (i-k >= 0) ? dp[i-k][j-1] : 0;
                if (prevSum + currentSum > dp[i-1][j]) {
                    dp[i][j] = prevSum + currentSum;
                    parent[i][j] = i-k;
                } else {
                    dp[i][j] = dp[i-1][j];
                    parent[i][j] = parent[i-1][j];
                }
            }
        }

        // now, recurse through parents
        int[] ans = new int[3];
        int j = 3;
        int i = n-k;
        while (i >= 0 && parent[i][j] != Integer.MIN_VALUE) {
            int prevI = parent[i][j];
            ans[--j] = prevI + k;
            i = prevI;
        }

        return ans;
    }
}