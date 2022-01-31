
import java.util.*;
import java.io.*;

public class msched {

	// http://usaco.org/index.php?page=viewproblem2&cpid=246
	
	static int n,m;
	static ArrayList<ArrayList<Integer>> greverse = new ArrayList<>();
	static long[] times;
	static long[] dp;
		// dp[i] = minimum time to finish task i
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("msched.in"));
		PrintWriter out = new PrintWriter(new FileWriter("msched.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		times = new long[n];
		dp = new long[n];
		for (int i=0; i<n; i++) {
			greverse.add(new ArrayList<>());
		}
		
		for (int i=0; i<n; i++) times[i] = Integer.parseInt(in.readLine());
		
		for (int i=0; i<m; i++ ) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			greverse.get(two).add(one);
		}
		
		for (int i=0; i<n; i++ ) {
			dfs(i, -1);
		}
		
		long ans=0;
		for (int i=0; i<n; i++) {
			if (dp[i] == 0) dp[i] = times[i];
			ans = Math.max(ans, dp[i]);
		}
				
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	public static void dfs(int node, int parent) {
		if (dp[node] != 0) return;
		if (parent != -1 && greverse.get(node).size() == 0) {
			dp[node] = times[node];
			return;
		}
		for (int i=0; i<greverse.get(node).size(); i++) {
			int to = greverse.get(node).get(i);
			if (to == parent) continue;
			
			dfs(to, node);
			dp[node] = Math.max(dp[to] + times[node], dp[node]);
		}
	}
}