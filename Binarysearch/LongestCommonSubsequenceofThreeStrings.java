public class LongestCommonSubsequenceofThreeStrings {

	public static void main(String[] args) {

	}

	// https://binarysearch.com/problems/Longest-Common-Subsequence-of-Three-Strings
	
	public int solve(String a, String b, String c) {
		char[] aa = a.toCharArray();
		char[] bb = b.toCharArray();
		char[] cc = c.toCharArray();
        int n = a.length(); 
        int m = b.length(); 
        int k = c.length();
        if (n == 0 || m == 0 || k == 0) return 0;
        int[][][] dp = new int[n][m][k];
        for (int i=0; i<m; i++) {
        	for (int j=0; j<k; j++) {
        		if (a.charAt(0) == b.charAt(i) && a.charAt(0) == c.charAt(j)) {
        			dp[0][i][j] = 1;
        		}
        		else {
        			if (i-1 >= 0) dp[0][i][j] = Math.max(dp[0][i][j], dp[0][i-1][j]);
        			if (j-1 >= 0) dp[0][i][j] = Math.max(dp[0][i][j], dp[0][i][j-1]);
        		}
        	}
        }
        for (int i=0; i<n; i++) {
        	for (int j=0; j<k; j++) {
        		if (a.charAt(i) == b.charAt(0) && b.charAt(0) == c.charAt(j)) {
        			dp[i][0][j] = 1;
        		}
        		else {
        			if (i-1 >= 0) dp[i][0][j] = Math.max(dp[i][0][j], dp[i-1][0][j]);
        			if (j-1 >= 0) dp[i][0][j] = Math.max(dp[i][0][j], dp[i][0][j-1]);
        		}
        	}
        }
        for (int i=0; i<n; i++) {
        	for (int j=0; j<m; j++) {
        		if (a.charAt(i) == b.charAt(j) && a.charAt(i) == c.charAt(0)) {
        			dp[i][j][0] = 1;
        		}
        		else {
        			if (i-1 >= 0) dp[i][j][0] = Math.max(dp[i][0][j], dp[i-1][j][0]);
        			if (j-1 >= 0) dp[i][j][0] = Math.max(dp[i][0][j], dp[i][j-1][0]);
        		}
        	}
        }
        
        for (int i=1; i<n; i++) {
        	for (int j=1; j<m; j++) {
        		for (int x=1; x<k; x++) {
        			//take
        			if (aa[i] == bb[j] && aa[i] == cc[x]) {
						dp[i][j][x] = Math.max(dp[i][j][x], dp[i-1][j-1][x-1]+1);
        			}
        			
        			// dont take
					dp[i][j][x] = Math.max(dp[i][j][x], Math.max(dp[i][j-1][x], dp[i][j][x-1]));
					dp[i][j][x] = Math.max(dp[i][j][x], dp[i-1][j][x]);
        		}
        	}
        }
        
        return dp[n-1][m-1][k-1];
    }
}