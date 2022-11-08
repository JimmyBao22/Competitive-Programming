
import java.util.*;
import java.io.*;

public class Grid1 {

	// https://atcoder.jp/contests/dp/tasks/dp_h
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Grid1"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		char[][] arr = new char[n][];
		for (int i=0; i<n; i++) {
			arr[i] = in.readLine().toCharArray();
		}
		
		long mod = (long)(1e9+7);
		long[][] dp = new long[n][m];
		if (arr[0][0] == '.') dp[0][0] = 1;
		
		for (int i=1; i<n; i++) {
			if (arr[i][0] == '.') dp[i][0] += dp[i-1][0];
		}
		for (int i=1; i<m; i++) {
			if (arr[0][i] == '.') dp[0][i] += dp[0][i-1];
		}
		for (int i=1; i<n; i++) {
			for (int j=1; j<m; j++) {
				if (arr[i][j] == '.') {
					dp[i][j] = dp[i-1][j] + dp[i][j-1];
					dp[i][j] %= mod;
				}
			}
		}
		
		System.out.println(dp[n-1][m-1]%mod);
	}
}