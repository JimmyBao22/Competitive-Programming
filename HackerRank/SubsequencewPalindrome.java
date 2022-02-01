
import java.util.*;
import java.io.*;

public class SubsequencewPalindrome {

	// https://www.hackerrank.com/contests/potw-s4/challenges/palindrome-subsequence-1
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SubsequencewPalindrome"));

		char[] arr = in.readLine().toCharArray();
		int n = arr.length;
		int[][] dp = new int[n][n];
		int[][] maxvals = new int[n][n];
		
		for (int i=0; i<n; i++) {
			dp[i][i] = 1;
			for (int j=i; j<n; j++) {
				maxvals[i][j] = 1;
			}
		}
		
		for (int diff=1; diff<n; diff++) {
			for (int i=0; i+diff<n; i++) {
				int j = i+diff;
				
				maxvals[i][j] = Math.max(maxvals[i][j], Math.max(maxvals[i+1][j], maxvals[i][j-1]));
				
				if (arr[i] != arr[j]) continue;
				
				dp[i][j] = maxvals[i+1][j-1] + 2;
				
				maxvals[i][j] = Math.max(maxvals[i][j], dp[i][j]);
				
				//print(dp, maxvals, n);
			}
		}
		
		
		int max=0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				max = Math.max(max, dp[i][j]);
			}
		}
	
		System.out.println(max);

	}
	
	static void print(int[][] dp, int[][] maxvals, int n) {
		for (int k=0; k<n; k++) {
			System.out.println(Arrays.toString(dp[k]));
		}
		System.out.println();
		for (int k=0; k<n; k++) {
			System.out.println(Arrays.toString(maxvals[k]));
		}
		System.out.println();
	}
}