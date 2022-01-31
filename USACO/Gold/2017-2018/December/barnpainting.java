
import java.util.*;
import java.io.*;

public class barnpainting {

	// http://usaco.org/index.php?page=viewproblem2&cpid=766
	
	static long mod = (long)(1e9+7);
	static int n,k;
	static ArrayList<ArrayList<Integer>> g = new ArrayList<>();
	static long[][] dp;
		// node, color
	static int[] color;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("barnpainting.in"));
		PrintWriter out = new PrintWriter(new FileWriter("barnpainting.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		for (int i=0; i<n; i++) g.add(new ArrayList<>());
		for (int i=0; i<n-1; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			g.get(one).add(two);
			g.get(two).add(one);
		}
		
		color = new int[n];
		for (int i=0; i<k; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken());
			color[one] = two;
		}
		
		dp = new long[n][4];
		
		long ans = 0;
		for (int i=1; i<=3; i++) {
			ans += dfs(0, -1, i);
			ans %= mod;
		}
		
		ans %= mod;
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
		// returns how many ways given node has color c
	public static long dfs(int node, int parent, int c) {
		if (dp[node][c] != 0) return dp[node][c];	
		if (color[node] != 0 && color[node] != c) return 0;		
			// cant go here with this color			
			
		long ans=1;
		for (int i=0; i<g.get(node).size(); i++) {
			if (g.get(node).get(i) == parent) continue;
			long cur=0;
			for (int j=1; j<=3; j++ ) {
				if (j == c) continue;
				cur += dfs(g.get(node).get(i), node, j);
			}
			ans *= cur;
			ans %= mod;
		}
		
		return dp[node][c] = ans;
	}
}