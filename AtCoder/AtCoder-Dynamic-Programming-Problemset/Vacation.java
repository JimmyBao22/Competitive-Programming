
import java.util.*;
import java.io.*;

public class Vacation {

	// https://atcoder.jp/contests/dp/tasks/dp_c
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Vacation"));

		int n = Integer.parseInt(in.readLine());
		int[] a = new int[n];
		int[] b = new int[n];
		int[] c = new int[n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			a[i] = Integer.parseInt(st.nextToken());
			b[i] = Integer.parseInt(st.nextToken());
			c[i] = Integer.parseInt(st.nextToken());
		}

		int[][] dp = new int[n][3];
			// index, what im taking rn
		
		dp[0][0] = a[0];
		dp[0][1] = b[0];
		dp[0][2] = c[0];
		for (int i=1; i<n; i++) {
			dp[i][0] = Math.max(dp[i-1][2], dp[i-1][1]) + a[i];
			dp[i][1] = Math.max(dp[i-1][2], dp[i-1][0]) + b[i];
			dp[i][2] = Math.max(dp[i-1][0], dp[i-1][1]) + c[i];
		}
		System.out.println(Math.max(Math.max(dp[n-1][0], dp[n-1][1]), dp[n-1][2]));
	}
}