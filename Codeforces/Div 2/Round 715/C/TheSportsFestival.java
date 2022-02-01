
import java.util.*;
import java.io.*;

public class TheSportsFestival {

	// https://codeforces.com/contest/1509/problem/C
	
	static long INF = Long.MAX_VALUE;

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("three2"));

		int t = 1;
		while (t-- > 0) {
			int n = Integer.parseInt(in.readLine());
			long[] arr = new long[n];
			StringTokenizer st = new StringTokenizer(in.readLine());
			for (int i=0; i<n; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			Arrays.parallelSort(arr);
			
			long[][] dp = new long[n][n];
			for (int i=0; i<n; i++) {
				Arrays.fill(dp[i], INF);
				dp[i][i] = 0;
			}
			for (int d=1; d<n; d++) {
				for (int i=0; i+d<n; i++) {
					int j = i+d;
					
					dp[i][j] = Math.min(dp[i][j-1], dp[i+1][j]) + arr[j] - arr[i];
					
				}
			}
			
			System.out.println(dp[0][n-1]);

		}

	}
}