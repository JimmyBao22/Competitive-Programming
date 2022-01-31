
import java.util.*;
import java.io.*;

public class snakes {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=945
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("snakes.in"));
		PrintWriter out = new PrintWriter(new FileWriter("snakes.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		long INF = (long)(1e10);
		
		st = new StringTokenizer(in.readLine());
		long[] arr = new long[n];
		long[] psum = new long[n+1];
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			psum[i+1] = psum[i] + arr[i];
		}
		
		long[][] max = new long[n][n];
			// max[i][j] = max from i to j
		for (int i=0; i<n; i++) max[i][i] = arr[i];
		
		for (int i=0; i<n; i++) {
			for (int j=i+1; j<n; j++) {
				max[i][j] = Math.max(arr[j], max[i][j-1]);
			}
		}
		
		long[][] dp = new long[n][k+1];
			// index, changes made
		
		// changes made = 0
		for (int i=0; i<n; i++) {
			dp[i][0] = max[0][i]*(i - 0 + 1) - (psum[i+1]);
		}
		
		for (int i=0; i<n; i++) {
			for (int j=1; j<=k; j++) {
				dp[i][j] = INF;
				for (int x=0; x<i; x++) {
					dp[i][j] = Math.min(dp[i][j], dp[x][j-1] + 
							(max[x+1][i]*(i - (x+1) + 1) - (psum[i+1] - psum[x+1])));
				}
			}
		}
				
		long ans=INF;
		for (int i=0; i<=k; i++) {
			ans = Math.min(ans, dp[n-1][i]);
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	public static void print(long[][] arr) {
		for (int i=0; i<arr.length; i++) {
			System.out.println(Arrays.toString(arr[i]));
		}
		System.out.println();
	}
}

/*
	dp[index][changes made]
	
	2 more arrays max[i][j] —> max from i to j
		psum		
		psum[i] - psum[j-1]
	
	base case —> j = 0
	
	dp[i][j] loop x from i-1 to 0
		dp[x][j-1] + (max[x+1][i]*(i-x) - (psum[i] - psum[j-1]))
	
	O(n^3)
	
*/