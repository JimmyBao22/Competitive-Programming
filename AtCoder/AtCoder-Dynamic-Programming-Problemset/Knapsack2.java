
import java.util.*;
import java.io.*;

public class Knapsack2 {

	// https://atcoder.jp/contests/dp/tasks/dp_d
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Knapsack2"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int w = Integer.parseInt(st.nextToken());
		long[] weights = new long[n];
		int[] v = new int[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			weights[i] = Integer.parseInt(st.nextToken());
			v[i] = Integer.parseInt(st.nextToken());
		}
		
		long[] dp = new long[(int)(1e5+2)];
			// i = value, dp[i] = minimum weight to achieve this value i
		
		Arrays.fill(dp, (long)(1e18));
		dp[0] = 0;
		for (int i=0; i<n; i++) {
			for (int j=dp.length-1; j-v[i]>=0; j--) {
				dp[j] = Math.min(dp[j], dp[j-v[i]]+weights[i]);
			}
		}
		
		for (int i=dp.length-1; i>=0; i--) {
			if (dp[i] <= w) {
				System.out.println(i);
				return;
			}
		}
	}
}