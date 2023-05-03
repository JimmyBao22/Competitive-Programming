import java.util.*;
import java.io.*;

public class MinimumInsertionStepstoMakeaStringPalindrome {

	// https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/description/
	
	int n;
    int[][] memo;

    public int minInsertions(String s) {
        n = s.length();
        memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }

        return dp(s, 0, n-1);
    }

    public int dp(String s, int i, int j) {
        if (i >= j) return 0;
        if (memo[i][j] != -1) return memo[i][j];
        int ans = (int)(1e6);
        if (s.charAt(i) == s.charAt(j)) {
            ans = dp(s, i+1, j-1);
        }
        ans = Math.min(ans, dp(s, i+1, j) + 1);
        ans = Math.min(ans, dp(s, i, j-1) + 1);
        return memo[i][j] = ans;
    }
}