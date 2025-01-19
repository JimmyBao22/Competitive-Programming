import java.util.*;

public class MinimumCostTreeFromLeafValues {

	// https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
	
	public static void main(String[] args) {
		//int[] arr = new int[] {6,2,4};
		//int[] arr = new int[] {7,12,8,10};
		//int[] arr = new int[] {15,13,5,3,15};
		int[] arr = new int[] {4,11};
		System.out.println(mctFromLeafValues(arr));
	}
	
	// note that the elements in arr are in order
	public static int mctFromLeafValues(int[] arr) {
		int n = arr.length;
		int[][] dp = new int[n][n];
			// dp[l][r] = min from arr[l] tp arr[r]
		int[][] max = new int[n][n];
			// max[l][r] = max from arr[l] tp arr[r]
		
		for (int i=0; i<n; i++) max[i][i] = arr[i];
		
		for (int l=0; l<n; l++) {
			for (int r=l+1; r<n; r++) {
				max[l][r] = Math.max(max[l][r-1], arr[r]);
			}
 		}
		
		for (int i=0; i<n; i++) {
			Arrays.fill(dp[i], (int)(1e9));
		}
		for (int i=0; i<n; i++) dp[i][i] = 0;
		
		for (int diff = 1; diff<n; diff++) {
			for (int l=0; l<n-diff; l++) {
				int r = diff + l;
				for (int k=l; k<r; k++) {
					dp[l][r] = Math.min(dp[l][r], dp[l][k] + dp[k+1][r] + max[l][k]*max[k+1][r]);
											// left subtree, right subtree, current value
				}
			}
		}
		return dp[0][n-1];	
    }
}