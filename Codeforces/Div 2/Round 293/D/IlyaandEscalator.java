
import java.util.*;
import java.io.*;

public class IlyaandEscalator {

	// https://codeforces.com/contest/518/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("IlyaandEscalator"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		double p = Double.parseDouble(st.nextToken());
		int t = Integer.parseInt(st.nextToken());

		double[][] dp = new double[n+1][t+1];
		dp[1][1] = p;
		dp[0][1] = 1-p;
		
		for (int i=2; i<=t; i++) {
			dp[0][i] = (1-p) * dp[0][i-1];
			for (int j=1; j<n; j++) {
				dp[j][i] = dp[j][i-1] * (1-p) + dp[j-1][i-1] * p;
			}
			// j = n
			dp[n][i] = dp[n][i-1] + dp[n-1][i-1] * p;
		}
		
		double ans=0;
		for (int i=1; i<=n; i++) {
			ans += i * dp[i][t];
		}
		
		System.out.println(ans);
		
	}
}