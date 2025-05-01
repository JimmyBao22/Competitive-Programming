
public class LargestSquareMatrixwithSameValue {
	
	public static void main(String[] args) {

	}
	
	// https://binarysearch.com/problems/Largest-Square-Matrix-with-Same-Value
	
	public int solve(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m];
        int count = 1;
        boolean found=true;
        for (int i=0; i<n; i++) {
        	for (int j=0; j<m; j++) {
        		dp[i][j] = matrix[i][j];
        	}
        }
        while (found) {
        	found=false;
        	count++;
        	int[][] copy = new int[n][m];
	        for (int i=1; i<n; i++) {
	        	for (int j=1; j<m; j++) {
	        		if (dp[i][j] != Integer.MAX_VALUE && dp[i][j] == dp[i-1][j] && dp[i][j] == dp[i][j-1] && dp[i][j] == dp[i-1][j-1]) {
	        			copy[i][j] = dp[i][j]+1;
	        			found=true;
	        		}
	        		else {
	        			copy[i][j] = Integer.MAX_VALUE;
	        		}
	        	}
	        }
	        dp = copy;
        }
        return count;
    }
}