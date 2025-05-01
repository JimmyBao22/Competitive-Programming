import java.util.*;

public class HopCost {

	public static void main(String[] args) {

	}

	// https://binarysearch.com/problems/Hop-Cost
	
	int n, INF = (int)(1e9);
	int[][] memo;
	public int solve(int[] nums0, int[] nums1, int dist, int cost) {
		n = nums0.length;
		memo = new int[n][2];
			// position, which array = mincost
		for (int i=0; i<n; i++) {
			Arrays.fill(memo[i], -1);
		}
		
		int ans = Math.min(dp(nums0, nums1, dist, cost, 0, 0) + nums0[0], dp(nums0, nums1, dist, cost, 0, 1) + nums1[0]);
		return ans;
	}
	
	public int dp(int[] nums0, int[] nums1, int dist, int cost, int pos, int left) {
		if (pos == n-1) {
			return 0;
		}
		if (memo[pos][left] != -1) return memo[pos][left];
		int ans = INF;
				
		// jump forwards
		for (int i=1; i<=dist && pos+i < n; i++) {
			if (left == 0) {
				// dont switch
				ans = Math.min(ans, dp(nums0, nums1, dist, cost, pos+i, 0) + nums0[pos+i]);				
				
				// switch
				ans = Math.min(ans, dp(nums0, nums1, dist, cost, pos+i, 1) + nums1[pos+i] + cost);				
			}
			else {
				// dont switch
				ans = Math.min(ans, dp(nums0, nums1, dist, cost, pos+i, 1) + nums1[pos+i]);				
			
				// switch
				ans = Math.min(ans, dp(nums0, nums1, dist, cost, pos+i, 0) + nums0[pos+i] + cost);
			}
		}
		
		return memo[pos][left] = ans;
	}
}