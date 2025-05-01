import java.util.*;

public class ShortestCommonSupersequence {

	public static void main(String[] args) {
		System.out.println(solve("bell", "yellow"));
	}
	
	// https://binarysearch.com/problems/Shortest-Common-Supersequence

	static int[][] memo;
	static int n,m;
	
	public static int solve(String a, String b) {
        n = a.length(); m = b.length();
        memo = new int[n][m];
        for (int i=0; i<n; ++i) Arrays.fill(memo[i], -1);
        
        return dp(0,0,a,b);
    }
	
	public static int dp(int posa, int posb, String a, String b) {
		if (posa == n) {
			return m - posb;
		}
		if (posb == m) {
			return n - posa;
		}
		if (memo[posa][posb] != -1) return memo[posa][posb];		
		
		// take on a
		int ans = dp(posa+1, posb, a, b)+1;
		
		// take on b
		ans = Math.min(ans, dp(posa, posb+1, a, b) + 1);
		
		// take both
		if (a.charAt(posa) == b.charAt(posb)) {
			ans = Math.min(ans, dp(posa+1, posb+1, a, b) + 1);
		}
		
		return memo[posa][posb] = ans;
	}
}