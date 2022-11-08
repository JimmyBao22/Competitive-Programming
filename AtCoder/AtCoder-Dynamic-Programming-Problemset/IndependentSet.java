
import java.util.*;
import java.io.*;

public class IndependentSet {

	// https://atcoder.jp/contests/dp/tasks/dp_p
	
	static int n;
	static long mod = (long)(1e9+7);
	static ArrayList<Integer>[] g;
	static long[][] dp;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("IndependentSet"));

		int n = Integer.parseInt(in.readLine());
		g = new ArrayList[n];
		for (int i=0; i<n; i++) g[i] = new ArrayList<>();
		for (int i=0; i<n-1; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			g[one].add(two);
			g[two].add(one);
		}
		
		dp = new long[n][2];
			// index, color (0 = white, 1 = black)
		
		dfs(0, -1);
		
		System.out.println((dp[0][0] + dp[0][1])%mod);
	}
	
	public static void dfs(int node, int parent) {
		dp[node][0] = dp[node][1] = 1;
		for (Integer i : g[node]) {
			if (i == parent) continue;
			dfs(i, node);
			dp[node][0] *= (dp[i][0] + dp[i][1]);
			dp[node][1] *= dp[i][0];
			dp[node][0] %= mod;
			dp[node][1] %= mod;
		}
	}
}