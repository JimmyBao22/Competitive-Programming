import java.util.*;

public class MinimumCosttoSplitanArray {

    // https://leetcode.com/problems/minimum-cost-to-split-an-array/

    final int INF = (int)(2e9);

    public int minCost(int[] nums, int k) {
        int n = nums.length;
        int ans = INF;
        int[] left = new int[n];    // last index of nums[i] on the left side
        Arrays.fill(left, -1);
        HashMap<Integer, Integer> mapNumToIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (mapNumToIndex.containsKey(nums[i])) {
                left[i] = mapNumToIndex.get(nums[i]);
            }
            mapNumToIndex.put(nums[i], i);
        }

                // dp[i][j] = at index i, current region starts at index j
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[0][0] = k;
        for (int i = 1; i < n; i++) {
            // create a new region
            for (int j = 0; j < i; j++) {
                if (dp[i-1][j] + k < 0) continue;
                dp[i][i] = Math.min(dp[i][i], dp[i-1][j] + k);
            }

            // add onto the old region
            for (int j = 0; j < i; j++) {
                if (dp[i-1][j] != INF && left[i] >= j) {
                    if (left[left[i]] < j) {
                        dp[i][j] = Math.min(dp[i][j], dp[i-1][j] + 2);
                    }
                    else {
                        dp[i][j] = Math.min(dp[i][j], dp[i-1][j] + 1);
                    }
                }
                else {
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][j]);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, dp[n-1][i]);
        }

        return ans;
    }
}
