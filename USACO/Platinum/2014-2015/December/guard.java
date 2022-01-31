
import java.util.*;
import java.io.*;

public class guard {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=494
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("guard.in"));
		PrintWriter out = new PrintWriter(new FileWriter("guard.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long h = Integer.parseInt(st.nextToken());
		long[][] arr = new long[n][3];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
			arr[i][2] = Integer.parseInt(st.nextToken());
		}
		
		long[] dp  = new long[(1 << n)];
		Arrays.fill(dp, -1);
		dp[0] = (long)(1e18);
		for (int i=1; i<(1<<n); i++) {
			for (int j=0; j<n; j++) {
				if (((i >> j)&1) == 1) {
					if (dp[i - (1 << j)] - arr[j][1] >= 0) {
						dp[i] = Math.max(dp[i], Math.min(dp[i - (1 << j)] - arr[j][1], arr[j][2]));
					}
				}
			}
		}
		
		long ans=-1;
		for (int i=1; i<(1<<n); i++) {
			if (dp[i] == -1) continue;
			int curheight=0;
			for (int j=0; j<n; j++) {
				if (((i >> j)&1) == 1) {
					curheight += arr[j][0];
				}
			}
			if (curheight >= h) {
				ans = Math.max(ans, dp[i]);
			}
		}
		
		if (ans == -1) {
			out.print("Mark is too tall");
			out.close();
			return;
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
}