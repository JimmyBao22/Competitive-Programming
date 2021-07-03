
import java.util.*;
import java.io.*;

public class GasPipeline {

	// https://codeforces.com/problemset/problem/1207/C
	
	static long INF = (long)(1e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("GasPipeline"));

		int t = Integer.parseInt(in.readLine());
		while (t --> 0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken());
			long a = Integer.parseInt(st.nextToken());
			long b = Integer.parseInt(st.nextToken());
			char[] arr = in.readLine().toCharArray();
			
			long[][] dp = new long[n+1][2];
			dp[0][1] = INF;
			dp[0][0] = b;
			
			for (int i=1; i<=n; i++) {
				if (i == n) {
					dp[i][0] = Math.min(dp[i-1][0] + a + b, dp[i-1][1] + 2*a + b);
					dp[i][1] = INF;
					continue;
				}
				if (arr[i] == '1') {
					dp[i][0] = INF;
					dp[i][1] = Math.min(dp[i-1][0] + 2*a + 2*b, dp[i-1][1] + a + 2*b);
				}
				else if (arr[i-1] == '1' && arr[i] == '0') {
					dp[i][0] = INF;
					dp[i][1] = Math.min(dp[i-1][0] + 2*a + 2*b, dp[i-1][1] + a + 2*b);
				}
				else {
					dp[i][0] = Math.min(dp[i-1][0] + a + b, dp[i-1][1] + 2*a + b);
					dp[i][1] = Math.min(dp[i-1][0] + 2*a + 2*b, dp[i-1][1] + a + 2*b);
				}
			}
			
			System.out.println(dp[n][0]);
			
		}
	}
}