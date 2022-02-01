
import java.util.*;
import java.io.*;

public class WoodenFence {

	// https://codeforces.com/contest/182/problem/E
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("WoodenFence"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int l = Integer.parseInt(st.nextToken());
		
		int[][] arr = new int[n][2];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		
		long[][][] memo = new long[l][n][2];
			// length, last used index, last used rotation
		for (int i=0; i<l; i++) {
			for (int j=0; j<n; j++) {
				Arrays.fill(memo[i][j], -1);		
			}
		}
		int mod = (int)(1e9+7);
		long t=0;
		for (int i=0; i<n; i++) {
			if (arr[i][0] != arr[i][1]) {
				t += dp(memo, arr, i, 0, arr[i][0], mod)%mod;
				t += dp(memo, arr, i, 1, arr[i][1], mod)%mod;
			}
			else {
				t += dp(memo, arr, i, 0, arr[i][0], mod)%mod;
			}
		}
		System.out.println(t%mod);
	}
	
	public static long dp(long[][][] memo, int[][] arr, int lastused, int k, int length, int mod) {
		if (length == memo.length) return 1;
		if (length > memo.length) return 0;
		if (memo[length][lastused][k] != -1) return memo[length][lastused][k];
		long t=0;
		for (int i=0; i<arr.length; i++) {
			if (i == lastused) continue;
			if (arr[i][0] != arr[i][1]) {
				if (arr[i][0] == arr[lastused][1^k]) {
					t += dp(memo, arr, i, 0, length + arr[i][0], mod)%mod;
				}
				if (arr[i][1] == arr[lastused][1^k]) {
					t += dp(memo, arr, i, 1, length + arr[i][1], mod)%mod;
				}
			}
			else {
				if (arr[i][0] == arr[lastused][1^k]) {
					t += dp(memo, arr, i, 0, length + arr[i][0], mod)%mod;
				}
			}
		}
		return memo[length][lastused][k] = t;
	}
}