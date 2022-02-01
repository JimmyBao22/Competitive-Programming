
import java.util.*;
import java.io.*;

public class CycleFreeFlow {

	// https://codeforces.com/gym/102694/problem/D
	
	static int n, m, log;
	static ArrayList<Edge>[] g;
	static int[][] parent, minweight;
	static int[] depth;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("CycleFreeFlow"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		log = log2(n)+1;
		parent = new int[n][log];
		minweight = new int[n][log];
		depth = new int[n];
		g = new ArrayList[n];
		for (int i=0; i<n; i++) g[i] = new ArrayList<>();
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1; 	
			int b = Integer.parseInt(st.nextToken())-1; 
			int c = Integer.parseInt(st.nextToken());
			g[a].add(new Edge(a, b, c));
			g[b].add(new Edge(b, a, c));
		}
		
		dfs(0, 0);	// start from root node
		precomp();
				
		int q = Integer.parseInt(in.readLine());
		StringBuilder s = new StringBuilder();
		for (int i=0; i<q; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1; 	
			int b = Integer.parseInt(st.nextToken())-1;
			s.append(lca(a, b)[1]);
			s.append("\n");
		}
		
		System.out.print(s);
	}
	
	public static int[] lca(int u, int v) {	// lca of u and v
		if (depth[u] < depth[v]) {
			int temp = u; u = v; v = temp;	// swap u and v
		}
		// depth[u] >= depth[v];
		int diff = depth[u] - depth[v];
		int min = (int)(1e9);
		for (int i=0; i<log; i++) {
			if (((1 << i) & diff) > 0) {
				min = Math.min(min, minweight[u][i]);
				u = parent[u][i];
			}
		}
		if (u == v) return new int[] {u,min};
		
		for (int i=log-1; i>=0; i--) {
			if (parent[u][i] != parent[v][i]) {
				min = Math.min(min, Math.min(minweight[u][i], minweight[v][i]));
				u = parent[u][i]; v = parent[v][i];
			}
		}
		min = Math.min(min, Math.min(minweight[u][0], minweight[v][0]));
		return new int[] {parent[u][0], min};
	}
	
	public static void precomp() {
		parent[0][0] = -1;	// parent of root = -1
		minweight[0][0] = (int)(1e9);
		for (int i=1; i<log; i++) {
			for (int j=0; j<n; j++) {
				if (parent[j][i-1] != -1) {
					parent[j][i] = parent[parent[j][i-1]][i-1];
				}
				else {
					parent[j][i] = -1;
					minweight[j][i] = (int)(1e9);
				}
				
				if (parent[j][i] != -1) {
					minweight[j][i] = Math.min(minweight[j][i-1], minweight[parent[j][i-1]][i-1]);
				}
				else {
					minweight[j][i] = (int)(1e9);
				}
			}
		}
	}
	
	public static void dfs(int node, int p) {
		parent[node][0] = p;
		for (Edge i : g[node]) {
			if (i.b == p) continue;
			depth[i.b] = depth[node] + 1;
			minweight[i.b][0] = i.weight;
			dfs(i.b, node);
		}
	}
	
	public static int log2(int n) {
		return 31 - Integer.numberOfLeadingZeros(n);
	}
	
	static class Edge {
		int a, b, weight;
		Edge (int a, int b, int c) {
			this.a = a; this.b = b; weight = c;
		}
	}
	
	public static void print() {
		for (int i=0; i<n; i++) {
			System.out.println(Arrays.toString(parent[i]));
		}
		System.out.println();
		for (int i=0; i<n; i++) {
			System.out.println(Arrays.toString(minweight[i]));
		}
		System.out.println();
	}
}