
import java.util.*;
import java.io.*;

public class LongestPath {

	// https://atcoder.jp/contests/dp/tasks/dp_g
	
	static int n,m;
	static ArrayList<ArrayList<Integer>> g = new ArrayList<>();
	static int[] dp;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("LongestPath"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		dp = new int[n];
		
		for (int i=0; i<n; i++) g.add(new ArrayList<>());
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			g.get(one).add(two);
		}

		boolean[] visited = new boolean[n];
		for (int i=0; i<n; i++) {
			if (!visited[i]) dfs(i, visited);
		}
		
		int ans=0;
		for (int i=0; i<n; i++) {
			ans = Math.max(ans, dp[i]);
		}
		System.out.println(ans - 1);
	}
	
	public static void dfs(int node, boolean[] visited) {
		if (visited[node]) return;
		visited[node] = true;
		int max=0;
		for (int i=0; i<g.get(node).size(); i++) {
			dfs(g.get(node).get(i), visited);
			max = Math.max(max, dp[g.get(node).get(i)]);
		}
		dp[node] = max + 1;
	}
}