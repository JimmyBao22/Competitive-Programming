
import java.util.*;
import java.io.*;

public class team {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=673
	
	static int n, m, k;
	static int[] one, two;
	static long mod = (long)(1e9+9);
	static long[][][] memo;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("team.in"));
		PrintWriter out = new PrintWriter(new FileWriter("team.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		one = new int[n];
		two = new int[m];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			one[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<m; i++) {
			two[i] = Integer.parseInt(st.nextToken());
		}
		
		memo = new long[n][m][k];
			// n, m, number of pairs already selected
		
		for (int i=0; i<n; i++) {
			for (int j=0; j<m; j++) {
				Arrays.fill(memo[i][j], -1);
			}
		}
		
		long ans = dp(0, 0, 0);
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static long dp(int nn, int mm, int selected) {
		if (selected == k) return 1;
		if (nn >= n || mm >= m) return 0;
		if (memo[nn][mm][selected] != -1) return memo[nn][mm][selected];
		if (k - selected > n - nn || k - selected > m - mm) return 0;		// not enough cows left to select
		
		long ans=0;
		
		// skip this nn
		ans += dp(nn+1, mm, selected);
		
		// skip this mm
		ans += dp(nn, mm+1, selected);
		ans %= mod;
	
		// overcount (I think this is why not sure)
			// nn, mm --> nn+1, mm --> nn+1, mm+1
			// same as nn, mm --> nn, mm+1 --> nn+1, mm+1
		ans -= dp(nn+1, mm+1, selected);
		
		// take!
		if (one[nn] > two[mm]) {
			ans += dp(nn+1, mm+1, selected+1);
		}
		ans = (ans%mod+mod)%mod;
		
		return memo[nn][mm][selected] = ans;
	}
}