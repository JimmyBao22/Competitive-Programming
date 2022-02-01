
import java.util.*;
import java.io.*;

public class MashmokhandACM {

	// https://codeforces.com/problemset/problem/414/B
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MashmokhandACM"));

		long mod = (long)1e9 + 7;
		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());  
		int k = Integer.parseInt(st.nextToken());  
		long[][] dp = new long[k+1][n+1];
			// first index is length, second one is value
		for (int i=1; i<n+1; i++) {
			dp[1][i] = 1;
		}
		for (int i=1; i<k+1; i++) {
			for (int h=1; h<n+1; h++) {
				// h is the previous value
				if (dp[i-1][h] == 0) continue;
				for (int j = h; j < n+1; j+= h) { 	// loop through all multiples
					dp[i][j] += dp[i-1][h];
					dp[i][j] %= mod;
				}
			}
		}
		long total = 0;
		for (int i=1; i<n+1; i++) {
			total += dp[k][i];
			total %= mod;
		}
		System.out.println(total);
	}
}