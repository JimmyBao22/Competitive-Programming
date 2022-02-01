import java.util.*;
import java.io.*;

public class XeniaandTree {
	
	static ArrayList<Integer>[] g;
	static HashSet<Integer>[] g2;
	static int[] parent, subtreeSize, dist, depth;
	static int[][] LCAparent;
	static int n, log;

	// https://codeforces.com/contest/342/problem/E
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("XeniaandTree"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		parent = new int[n];
		subtreeSize = new int[n];
		g = new ArrayList[n];
		g2 = new HashSet[n];
		log = log2(n)+1;
		LCAparent = new int[n][log];
		depth = new int[n];
		
		for (int i=0; i<n; i++) {
			g[i] = new ArrayList<>();
			g2[i] = new HashSet<>();
		}
		
		for (int i=0; i<n-1; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1; 	
			int b = Integer.parseInt(st.nextToken())-1; 	
			g[a].add(b);
			g2[a].add(b);
			g[b].add(a);
			g2[b].add(a);
		}
		
		build(0, -1);
		LCAdfs(0, -1);	// start from root node
		precomp();

		dist = new int[n];
		Arrays.fill(dist, (int)(1e9));
		update(0);
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<q; i++) {
			st = new StringTokenizer(in.readLine());
			int t = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken())-1;
			if (t == 1) {
				update(v);
			}
			else {
				sb.append(query(v) + "\n");
			}
		}
		System.out.print(sb);
	}
	
	public static int query(int node) {
		int curnode = node;
		int ans=(int)(1e9);
		while (true) {
			ans = Math.min(ans, dist[curnode] + calcdist(curnode, node));
			if (parent[curnode] == curnode) break;
			curnode = parent[curnode];
		}
		return ans;
	}
	
	public static void update(int node) {
		int curnode = node;
		while (true) {
			dist[curnode] = Math.min(dist[curnode], calcdist(curnode, node));
			if (parent[curnode] == curnode) break;
			curnode = parent[curnode];
		}
	}
	
	public static int calcdist(int node1, int node2) {
		int lca = lca(node1, node2);
		return depth[node1] + depth[node2] - 2*depth[lca];
	}
	
	public static void build(int node, int p) {
		int n = dfs(node, p); 						// find the size of each subtree
		int centroid = getCentroid(node, p, n); 			// find the centroid
		if (p == -1) p = centroid; 					// parent of root is the root itself
		parent[centroid] = p;

		// for each tree remove the centroid and build
		for (Integer i : g2[centroid]) {
			g2[i].remove(centroid);
			build(i, centroid);
		}
	}
	
	public static int dfs(int node, int p) {
		subtreeSize[node] = 1;
		for (Integer i : g2[node]) {
			if (i != p) {
				subtreeSize[node] += dfs(i, node);
			}
		}

		return subtreeSize[node];
	}

	public static int getCentroid(int node, int p, int n) {
		for (Integer i : g2[node]) {
			if (i != p && subtreeSize[i] > n/2) {
				return getCentroid(i, node, n);
			}
		}

		return node;
	}
	
	public static int lca(int u, int v) {	// lca of u and v
		if (depth[u] < depth[v]) {
			int temp = u; u = v; v = temp;	// swap u and v
		}
		// depth[u] >= depth[v];
		int diff = depth[u] - depth[v];
		for (int i=0; i<log; i++) {
			if (((1 << i) & diff) > 0) {
				u = LCAparent[u][i];
			}
		}
		if (u == v) return u;
		for (int i=log-1; i>=0; i--) {
			if (LCAparent[u][i] != LCAparent[v][i]) {
				u = LCAparent[u][i]; v = LCAparent[v][i];
			}
		}
		return LCAparent[u][0];
	}
	
	public static void precomp() {
		LCAparent[0][0] = -1;	// parent of root = -1
		for (int i=1; i<log; i++) {
			for (int j=0; j<n; j++) {
				if (LCAparent[j][i-1] != -1) {
					LCAparent[j][i] = LCAparent[LCAparent[j][i-1]][i-1];
				}
				else LCAparent[j][i] = -1;
			}
		}
	}
	
	public static void LCAdfs(int node, int p) {
		LCAparent[node][0] = p;
		for (Integer i : g[node]) {
			if (i == p) continue;
			depth[i] = depth[node] + 1;
			LCAdfs(i, node);
		}
	}
	
	public static int log2(int n) {
		return 31 - Integer.numberOfLeadingZeros(n);
	}
}