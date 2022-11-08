
import java.util.*;
import java.io.*;

public class Permutation {

	// https://atcoder.jp/contests/dp/tasks/dp_t
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Permutation"));

		int n = Integer.parseInt(in.readLine());
		String s = in.readLine();
		long mod = (long)(1e9+7);
		long[][] dp = new long[n+1][n+1];
			// index, last number chosen
		long[][] pref = new long[n+1][n+1];
		
		dp[1][1] = pref[1][1] = 1;
		for (int i=2; i<=n; i++) pref[1][i] += pref[1][i-1];
		for (int i=2; i<=n; i++) {
			for (int j=1; j<=i; j++) {
				// dp[i][j]
				if (s.charAt(i-2) == '<') {
					dp[i][j] = pref[i-1][j-1];		// previous digit from 1 to j-1
				}
				else {
					dp[i][j] = (pref[i-1][n] - pref[i-1][j-1]);	
							// previous digit n to j
							// if previous digit is j, then just shift previous digits up 1
								// so it still works
					dp[i][j] %= mod;
				}
				pref[i][j] = dp[i][j];
			}
			for (int j=1; j<=n; j++) {
				pref[i][j] += pref[i][j-1];
				pref[i][j] %= mod;
			}
		}
		
		System.out.println((pref[n][n]%mod+mod)%mod);
	}
	
	public static void print(long[][] arr) {
		for (int i=0; i<arr.length; i++) {
			for (int j=0; j<arr[0].length; j++) System.out.print(arr[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}
}
