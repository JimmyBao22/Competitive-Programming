public class LongestPalindromicSubsequence {

	public static void main(String[] args) {

	}
	
	// https://leetcode.com/problems/longest-palindromic-subsequence/
	
	public static int longestPalindromeSubseq(String s) {
        char[] arr = s.toCharArray();
		int n = arr.length;
		int[][] dp = new int[n][n];

        for (int i=0; i<n; i++) {
			dp[i][i] = 1;
		}
		
		for (int diff=1; diff<n; diff++) {
			for (int i=0; i+diff<n; i++) {
				int j = i+diff;
                dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
				if (arr[i] == arr[j]) {
					dp[i][j] = Math.max(dp[i][j], dp[i+1][j-1] + 2);					
				}
			}
		}
		
		int max=0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				max = Math.max(max, dp[i][j]);
			}
		}	
		return max;
	}
}