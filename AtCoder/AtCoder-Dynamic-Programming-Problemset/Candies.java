
import java.util.*;
import java.io.*;

public class Candies {

	// https://atcoder.jp/contests/dp/tasks/dp_m
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Candies"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[] a = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) a[i] = Integer.parseInt(st.nextToken());
		
		long mod = (long)(1e9+7);
		long[][] dp = new long[n][k+1];
			// index, sum
		long[][] pref = new long[n][k+1];
			// pref[i][j] = index i, prefix sum from 0 to j
		
		for (int i=0; i<n; i++) {
			dp[i][0] = 1;
			pref[i][0] = 1;
		}
		
		// index 0
		for (int i=1; i<=a[0]; i++) dp[0][i] = 1;
		pref[0][0] = dp[0][0];
		for (int i=1; i<=k; i++) {
			pref[0][i] = dp[0][i] + pref[0][i-1];
		}
			
		for (int i=1; i<n; i++) {
			for (int j=1; j<=k; j++) {
				// dp[i][j] += dp[i-1][r] from r = j - a[i] to j
				if (j - a[i] - 1 < 0) {
					dp[i][j] += pref[i-1][j];
				}
				else {
					dp[i][j] += (pref[i-1][j] - pref[i-1][j - a[i] - 1]);
				}
				pref[i][j] = pref[i][j-1] + dp[i][j];
				dp[i][j] %= mod;
				pref[i][j] %= mod;
			}
		}
		
		System.out.println(((dp[n-1][k]%mod + mod)%mod));
	}
	
	public static void print(long[][] arr) {
		for (int i=0; i<arr.length; i++) {
			for (int j=0; j<arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}