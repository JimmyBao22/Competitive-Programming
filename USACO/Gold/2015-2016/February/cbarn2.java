
import java.util.*;
import java.io.*;

public class cbarn2 {

	// http://usaco.org/index.php?page=viewproblem2&cpid=622
	
	static int n,k;
	static long[] arr;
	static long INF = (long)(1e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cbarn2.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cbarn2.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		arr = new long[n];
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(in.readLine());
		
		long min=(int)(1e9);
		for (int start=0; start<n; start++) {
			long[] cur = new long[n];
			for (int j=0; j<n; j++) cur[j] = arr[(start + j)%n];
			min = Math.min(find(cur), min);
		}
		
		System.out.println(min);
		out.println(min);
		out.close();

	}
	
	public static long find(long[] cur) {
		long[][] dp = new long[n+1][k+2];
		for (int i=0; i<=n; i++) {
			Arrays.fill(dp[i], INF);
		}
		dp[0][1] = 0;
		for (int i=1; i<=n; i++) {
			for (int j=2; j<=k+1; j++) {
				// put another entrance at pos i
				for (int prev = 0; prev < i; prev++) {
					// come from position prev
					long curval=dp[prev][j-1];
					if (curval == INF) continue;
					for (int d=prev; d<i; d++) {
						curval += (d-prev) * cur[d];
					}
					dp[i][j] = Math.min(dp[i][j], curval);
				}
			}
			//print(dp);
		}
		
		// k+1 entrance is just put entrance at the very end
		// therefore dp[n][k+1] is just the end
		return dp[n][k+1];
	}
	
	public static void print(int[][] dp) {
		for (int i=0; i<dp.length; i++) {
			for (int j=0; j<dp[0].length; ++j ) {
				System.out.print(dp[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}