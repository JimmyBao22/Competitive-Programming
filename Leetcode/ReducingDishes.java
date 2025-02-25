import java.util.*;

public class ReducingDishes {

	public static void main(String[] args) {

	}

	int n, INF = (int)(1e9);
	int[][] memo;
	public int maxSatisfaction(int[] satisfaction) {
        Arrays.sort(satisfaction);
        n = satisfaction.length;
        memo = new int[n][n+1];
        for (int i=0; i<n; i++) {
        	Arrays.fill(memo[i], -INF);
        }
        return dp(satisfaction, 0, 1);
    }
	
	public int dp(int[] arr, int pos, int time) {
		if (pos >= n) return 0;
		if (memo[pos][time] != -INF) return memo[pos][time];
		int ans=0;
		
		// take
		ans = dp(arr, pos+1, time+1) + time * arr[pos];
		
		// dont take
		ans = Math.max(ans, dp(arr, pos+1, time));
		
		return memo[pos][time] = ans;
	}
}