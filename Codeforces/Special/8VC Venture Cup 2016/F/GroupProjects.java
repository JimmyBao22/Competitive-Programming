
import java.util.*;
import java.io.*;

public class GroupProjects {

	// https://codeforces.com/contest/626/problem/F
	
	// sol: https://codeforces.com/blog/entry/47764
	
	static long mod = (long)(1e9+7);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("GroupProjects"));

		int t = 1;
		while (t-- > 0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			int[] arr = new int[n];
			st = new StringTokenizer(in.readLine());
			for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
			Arrays.parallelSort(arr);
			
			long[][] dp = new long[n][k+1];
				// index, number open, k so far
			
			// i = 0;
			dp[0][0] = 1;
			if (n > 1) dp[1][0] = 1;
			
			for (int i=1; i<n; i++) {
				long[][] temp = new long[n][k+1];
				for (int j=0; j<n; j++) {
					for (int x=0; x<=k; x++) {
						int newx = x + j * (arr[i] - arr[i-1]);
						if (newx > k) break;
						
						// create a new open group
						if (j+1 < n) {
							temp[j+1][newx] += dp[j][x];
							temp[j+1][newx] %= mod;
						}
						
						// create a new closed group
						temp[j][newx] += dp[j][x];
						temp[j][newx] %= mod;
						
						// add to an open group and close it
						if (j-1 >= 0) {
							temp[j-1][newx] += j * dp[j][x];
							temp[j-1][newx] %= mod;
						}

						// add to an open group but don't close it
						if (j > 0) {
							temp[j][newx] += j * dp[j][x];
							temp[j][newx] %= mod;
						}
						
//						System.out.println(i + " " + j + " " + newx + " " + temp[j][newx]);
					}
				}
				dp = temp;
			}
			
			long ans=0;
			for (int i=0; i<=k; i++) {
				ans += dp[0][i];
				ans %= mod;
			}
			
			System.out.println(ans);
		}
	}
}
	
/*
	public static long dp(int index, int open, int curk) {
		if (index >= n) {
			if (curk <= k && open == 0) return 1;
			return 0;
		}
		if (curk > k) return 0;
		if (memo[index][open][curk] != -1) return memo[index][open][curk];
		long ans=0;
		
		// create a new open group
		ans += dp(index+1, open+1, curk + (arr[index] - arr[index-1]) * open);
		
		// create a new closed group
		ans += dp(index+1, open, curk + (arr[index] - arr[index-1]) * open);
		
		// add to an open group and close it
		if (open-1 >= 0) ans += open*dp(index+1, open-1, curk + (arr[index] - arr[index-1]) * open);
		ans %= mod;
		
		// add to an open group but don't close
		if (open > 0) ans += open*dp(index+1, open, curk + (arr[index] - arr[index-1]) * open);
		ans %= mod;
		
		
		return memo[index][open][curk] = ans;
	}
*/
