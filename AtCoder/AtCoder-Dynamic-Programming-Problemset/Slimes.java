
import java.util.*;
import java.io.*;

public class Slimes {

	// https://atcoder.jp/contests/dp/tasks/dp_n
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Slimes2"));

		int n = Integer.parseInt(in.readLine());
		long[] arr = new long[n];
		long[] pref = new long[n+1];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			pref[i+1] = pref[i] + arr[i];
		}
				
		long[][] dp = new long[n][n];
			// dp[i][j] = min sum from i to j
		
		for (int i=0; i<n; i++) {
			Arrays.fill(dp[i], (long)(1e18));
		}
		
		for (int i=0; i<n; i++) dp[i][i] = 0;
		
		for (int i=n-1; i>=0; i--) {
			for (int diff = 1; diff<n; diff++) {
				if (i + diff >= n) break;
				int j = i + diff;
				for (int k=i; k<j; k++) {
					dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k+1][j] + 
							pref[k+1] - pref[i] + pref[j+1] - pref[k+1]);
				}
			}
		}
		
		System.out.println(dp[0][n-1]);
	}
}