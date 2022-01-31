
import java.util.*;
import java.io.*;

public class maxflow {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=576
	
	static int n, k, log;
	static ArrayList<Integer>[] g;
	static int[][] parent;
	static int[] depth, milk;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("maxflow.in"));
		PrintWriter out = new PrintWriter(new FileWriter("maxflow.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		log = log2(n)+1;
		parent = new int[n][log];
		depth = new int[n];
		milk = new int[n];
		g = new ArrayList[n];
		for (int i=0; i<n; i++) g[i] = new ArrayList<>();
		
		for (int i=0; i<n-1; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			g[a].add(b);
			g[b].add(a);
		}
		
		dfs(0, -1);	// start from root node
		precomp();
		
		while (k-->0) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			int lca = lca(a,b);
			milk[a]++;
			milk[b]++;
			milk[lca]--;
			lca = parent[lca][0];
			if (lca != -1) milk[lca]--;
//			System.out.println(Arrays.toString(milk));
		}
		
		dfs2(0, -1);
		
		int ans=0;
		for (int i=0; i<n; i++ ) {
			ans = Math.max(ans, milk[i]);
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static void dfs2(int node, int p) {
		for (Integer i : g[node]) {
			if (i == p) continue;
			dfs2(i, node);
			milk[node] += milk[i];
		}
	}
	
	public static int lca(int u, int v) {	// lca of u and v
		if (depth[u] < depth[v]) {
			int temp = u; u = v; v = temp;	// swap u and v
		}
		// depth[u] >= depth[v];
		int diff = depth[u] - depth[v];
		for (int i=0; i<log; i++) {
			if (((1 << i) & diff) > 0) {
				u = parent[u][i];
			}
		}
		if (u == v) return u;
		for (int i=log-1; i>=0; i--) {
			if (parent[u][i] != parent[v][i]) {
				u = parent[u][i]; v = parent[v][i];
			}
		}
		return parent[u][0];
	}
	
	public static void precomp() {
		parent[0][0] = -1;	// parent of root = -1
		for (int i=1; i<log; i++) {
			for (int j=0; j<n; j++) {
				if (parent[j][i-1] != -1) {
					parent[j][i] = parent[parent[j][i-1]][i-1];
				}
				else parent[j][i] = -1;
			}
		}
	}
	
	public static void dfs(int node, int p) {
		parent[node][0] = p;
		for (Integer i : g[node]) {
			if (i == p) continue;
			depth[i] = depth[node] + 1;
			dfs(i, node);
		}
	}
	
	public static int log2(int n) {
		return 31 - Integer.numberOfLeadingZeros(n);
	}
}