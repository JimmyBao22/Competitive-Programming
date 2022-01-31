
import java.util.*;
import java.io.*;

public class teamwork {

	// http://usaco.org/index.php?page=viewproblem2&cpid=863
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("teamwork.in"));
		PrintWriter out = new PrintWriter(new FileWriter("teamwork.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		long[] arr = new long[n];
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(in.readLine());
		}
		
		long[][] max = new long[n][k];
			// max[i][j] = Math.max(arr[i], arr[i+1], ... , arr[i+j])
		for (int i=0; i<n; i++) {
			long curmax=0;
			for (int j=0; j<k && i+j<n; j++) {
				curmax = Math.max(curmax, arr[i+j]);
				max[i][j] = curmax;
			}
		}
		
		long[] dp = new long[n];
			// max value up to i
		dp[0] = arr[0];
		for (int i=1; i<n; i++) {			
			for (int l=0; l<k && i-l>=0; l++) {
				
				// dp[i] from dp[i-l-1]. Currently taking from arr[i-l] to arr[i]
				
				if (i-l-1 >= 0) dp[i] = Math.max(dp[i], dp[i-l-1] + max[i-l][l] * (l+1));
				else dp[i] = Math.max(dp[i], max[i-l][l] * (l+1));
			}
		}
		
		System.out.println(dp[n-1]);
		out.println(dp[n-1]);
		out.close();
	}
}