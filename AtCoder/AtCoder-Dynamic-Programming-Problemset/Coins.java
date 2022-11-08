
import java.util.*;
import java.io.*;

public class Coins {

	// https://atcoder.jp/contests/dp/tasks/dp_i
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Coins"));

		int n = Integer.parseInt(in.readLine());
		double[] arr = new double[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			arr[i] = Double.parseDouble(st.nextToken());
		}
		
		double[][] dp = new double[n][n+1];
			// dp[i][j] = coin i, probability of j heads at this point
		
		dp[0][0] = 1 - arr[0];
		dp[0][1] = arr[0];
		for (int i=1; i<n; i++) {
			dp[i][0] = dp[i-1][0] * (1 - arr[i]);		// stay at tails
			for (int j=1; j<=n; j++) {
				dp[i][j] = dp[i-1][j] * (1 - arr[i]) + dp[i-1][j-1] * arr[i];
									// got tails			// got heads
			}
		}
		
		double ans=0;
		for (int i=(n+1)/2; i<=n; i++) {
			ans += dp[n-1][i];
		}
		System.out.println(ans);
	}
}
