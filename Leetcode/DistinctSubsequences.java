import java.util.*;

public class DistinctSubsequences {

    // https://leetcode.com/problems/distinct-subsequences/

    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();

        /*

            rabbbit
            rabbit

            dp[i][j] = number of ways that I can make t[0:j] using s[0:i]
                dp[i][j] = dp[i-1][j]
                if (s[i] == t[j]) dp[i][j] += dp[i-1][j-1]

        */

        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++) {
            dp[i][0] = s.charAt(i) == t.charAt(0) ? 1 : 0;
            if (i != 0) dp[i][0] += dp[i-1][0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = dp[i-1][j];
                if (s.charAt(i) == t.charAt(j)) {
                    dp[i][j] += dp[i-1][j-1];
                }
            }
        }

        // print(n, m, dp);

        return dp[n-1][m-1];
    }

    public void print(int n, int m, int[][] dp) {
        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
        System.out.println("\n");
    }
}
