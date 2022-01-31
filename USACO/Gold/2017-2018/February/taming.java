
import java.util.*;
import java.io.*;

public class taming {

	// http://usaco.org/index.php?page=viewproblem2&cpid=815
	
	static int[] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("taming.in"));
		PrintWriter out = new PrintWriter(new FileWriter("taming.out"));

		int n = Integer.parseInt(in.readLine());
		StringTokenizer st = new StringTokenizer(in.readLine());
		arr = new int[n];
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		for (int i=1; i<=n; i++) {
			long[][][] memo = new long[n][i+1][n];
				// cur index, number of breakouts used, last point broken out
			for (int j=0; j<n; j++) {
				for (int k=0; k<=i; k++) {
					Arrays.fill(memo[j][k], -1);
				}
			}
			long ans = 0;
			if (arr[0] != 0) {
				ans = dp(memo, 1, 1, 0)+1;
			}
			else {
				ans = dp(memo, 1, 1, 0);
			}
			
			System.out.println(ans);
			out.println(ans);
		}
		
		out.close();

	}
	
	public static long dp(long[][][] memo, int index, int used, int last) {
		if (index >= memo.length) {
			if (used != memo[0].length-1) {
				return (long)(1e14);
			}
			else return 0;
		}
		if (memo[index][used][last] != -1) return memo[index][used][last];
				
		// no break out
		int num_supposed = index - last;
		long ans = dp(memo, index+1, used, last);
		if (num_supposed != arr[index]) ans++;
		
		if (used + 1 < memo[0].length) {
			// break out
			if (arr[index] == 0) {
				ans = Math.min(ans, dp(memo, index+1, used+1, index));
			}
			else {
				ans = Math.min(ans, dp(memo, index+1, used+1, index)+1);
			}
		}
		
		return memo[index][used][last] = ans;
	}
}