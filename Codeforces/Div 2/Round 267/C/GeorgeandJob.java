
import java.util.*;
import java.io.*;

public class GeorgeandJob {

	// https://codeforces.com/contest/467/problem/C
	
	static int n;
	static int m;
	static int k;
	static long[] arr;
	static long[] psums;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("GeorgeandJob"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		arr = new long[n];
		long[][] dp = new long[k+1][n];
		psums = new long[n+1];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			psums[i+1] = psums[i] + arr[i];
		}
		
		// dp[j][i] = used i sequences already, last element considered was j
		
		for (int i=1; i<=k; i++) {
			for (int j=0; j<n; j++) {
				if (i * m > j+1) {
					continue;
				}
				if (j-m >=0) {
					dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j-m] + psums[j+1] - psums[j-m+1]);
				}
				else if (j == 0) {
					dp[i][j] = psums[1];
				}
				else {
					dp[i][j] = Math.max(dp[i][j-1], psums[j+1]);
				}
			}
		}
		
		long ans=0;
		for (int i=0; i<n; i++) {
			ans = Math.max(ans, dp[k][i]);
		}
		System.out.println(ans);
	}
}


// TLE VV

//	for (int i=0; i<n; i++) {
//	dp(i, k);
//}
//long ans=-1;
//for (int i=0; i<n; i++) {
//	ans = Math.max(ans, memo[i][k]);
//}
//System.out.println(ans);
	
//	public static long dp(int canuse, int pairsleft) {
//		if (pairsleft == 0) return 0;
//		if (canuse >= n) return Integer.MIN_VALUE;
//		if (memo[canuse][pairsleft] != -1) return memo[canuse][pairsleft];
//		long cur=-1;
//		for (int i=canuse; i<n; i++) {
//			if (i+m >n) break;
//			cur = Math.max(cur, dp(i+m, pairsleft-1) + psums[i+m] - psums[i]);
//		}
//		return memo[canuse][pairsleft] = cur;
//	}