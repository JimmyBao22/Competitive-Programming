
import java.util.*;
import java.io.*;

public class wifi {

	// http://usaco.org/index.php?page=viewproblem2&cpid=209
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("wifi.in"));
		PrintWriter out = new PrintWriter(new FileWriter("wifi.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long a = Integer.parseInt(st.nextToken());
		long b = Integer.parseInt(st.nextToken());
		long[] arr = new long[n+1];
		for (int i=1; i<=n; i++) arr[i] = Integer.parseInt(in.readLine());
		Arrays.parallelSort(arr);
		
		double[] dp = new double[n+1];
		for (int i=1; i<n+1; i++) {
			dp[i] = (arr[i] - arr[1]) * b/2.0 + a;
				// choose midpoint between arr[i] and arr[j+1] to install wifi station
					// this means your radius will be  (arr[i] - arr[j+1])/2
			for (int j=1; j<i; j++) {
				dp[i] = Math.min(dp[i], dp[j] + (arr[i] - arr[j+1]) * b/2.0 + a);
			}
		}
		
		if (dp[n] == (int)(dp[n])) {
			System.out.println((int)dp[n]);
			out.println((int)dp[n]);
			out.close();
			return;
		}
		System.out.println(dp[n]);
		out.println(dp[n]);
		out.close();
	}
}