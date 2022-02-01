
import java.util.*;
import java.io.*;

public class SlothNaptime {

	// https://codeforces.com/gym/102694/problem/C
	
	static int n, m, log;
	static ArrayList<Integer>[] g;
	static int[][] parent;
	static int[] depth;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SlothNaptime"));

		n = Integer.parseInt(in.readLine());
		log = log2(n)+1;
		parent = new int[n][log];
		depth = new int[n];
		g = new ArrayList[n];
		for (int i=0; i<n; i++) g[i] = new ArrayList<>();
		
		for (int i=0; i<n-1; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1; 	
			int b = Integer.parseInt(st.nextToken())-1; 	
			g[a].add(b);
			g[b].add(a);
		}
		
		dfs(0, 0);	// start from root node
		precomp();
		
		StringBuilder s = new StringBuilder();
		int q = Integer.parseInt(in.readLine());
		for (int i=0; i<q; ++i) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			int energy = Integer.parseInt(st.nextToken()); 	
			int lca = lca(a,b);
			int moveup = depth[a] - depth[lca];
			int movedown = depth[b] - depth[lca];
			if (energy <= moveup) {
				// depth[a] going up by energy
				for (int j=log-1; j>=0; j--) {
					if (((energy >> j) & 1) == 1) {
						a = parent[a][j];
					}
				}
				s.append(a+1);
			}
			else {
				energy -= moveup;
				energy = movedown - energy;
				if (energy < 0) energy = 0;
				
				// depth[b] going up by energy
				for (int j=log-1; j>=0; j--) {
					if (((energy >> j) & 1) == 1) {
						b = parent[b][j];
					}
				}
				s.append(b+1);
			}
			s.append("\n");
		}
		System.out.print(s);
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