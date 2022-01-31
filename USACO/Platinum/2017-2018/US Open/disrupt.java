
import java.util.*;
import java.io.*;

public class disrupt {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=842
	
	static int n,m, log;
	static ArrayList<ArrayList<Integer>> g = new ArrayList<>();
	static int[] ans;
	static ArrayList<TreeMap<Integer, Integer>> vals = new ArrayList<>();
		// node = set of lengths
	static ArrayList<HashMap<Integer, Integer>> add = new ArrayList<>();
	static int[][] parent;
	static int[] depth;
	static HashMap<Integer, HashMap<Integer, Integer>> queries = new HashMap<>();
			// first, second, index in ans array
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("disrupt.in"));
		PrintWriter out = new PrintWriter(new FileWriter("disrupt.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		ans = new int[n-1];
		log = log(n)+1;
		parent = new int[n][log];
		depth = new int[n];
		
		for (int i=0; i<n; i++) {
			g.add(new ArrayList<>());
			add.add(new HashMap<>());
			vals.add(new TreeMap<>());
		}
		
		for (int i=0; i<n-1; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			g.get(one).add(two);
			g.get(two).add(one);
			if (!queries.containsKey(one)) queries.put(one, new HashMap<>());
			if (!queries.containsKey(two)) queries.put(two, new HashMap<>());
			queries.get(one).put(two, i);
			queries.get(two).put(one, i);
		}

		dfs(0, 0);
		precomp();
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			int l = Integer.parseInt(st.nextToken());
			int lca = lca(one, two);
			add.get(one).put(l, add.get(one).getOrDefault(l, 0)+1);
			add.get(two).put(l, add.get(two).getOrDefault(l, 0)+1);
			add.get(lca).put(l, add.get(lca).getOrDefault(l, 0)-2);
		}
		
		smalltolarge(0,-1);
		
		StringBuilder s = new StringBuilder();
		for (int i=0; i<n-1; i++) {
			s.append(ans[i] + "\n");
		}
		
		//System.out.print(s);
		out.print(s);
		out.close();
	}
	
	public static void smalltolarge(int node, int parent) {
		int biggest = node;
		for (int i=0; i<g.get(node).size(); i++) {
			int to = g.get(node).get(i);
			if (to == parent) continue;
			smalltolarge(to, node);
			if (vals.get(to).size() > vals.get(biggest).size()) {
				biggest = to;
			}
		}
		
		TreeMap<Integer, Integer> c = vals.get(biggest);
		vals.set(biggest, vals.get(node));
		vals.set(node, c);
		
		for (int i=0; i<g.get(node).size(); i++) {
			int to = g.get(node).get(i);
			if (to == parent || to == biggest) continue;
			for (Integer a : vals.get(to).keySet()) {
				vals.get(node).put(a, vals.get(node).getOrDefault(a, 0) + vals.get(to).get(a));
			}
		}
		
		for (Integer a : add.get(node).keySet()) {
			vals.get(node).put(a, vals.get(node).getOrDefault(a, 0) + add.get(node).get(a));
			if (vals.get(node).get(a) <= 0) vals.get(node).remove(a);
		}
		
		// query is parent, node
		if (parent == -1) return;
		if (vals.get(node).size() == 0) {
			ans[queries.get(parent).get(node)] = -1;
		}
		else {
			ans[queries.get(parent).get(node)] = vals.get(node).firstKey();
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
		for (int i=1; i<log; i++) {
			for (int j=0; j<n; j++) {
				parent[j][i] = parent[parent[j][i-1]][i-1];
			}
		}
	}
	
	public static void dfs(int node, int p) {
		parent[node][0] = p;
		for (int i=0; i<g.get(node).size(); i++) {
			if (g.get(node).get(i) == p) continue;
			depth[g.get(node).get(i)] = depth[node]+1;
			dfs(g.get(node).get(i), node);
		}
	}
	
	public static int log(int n) {
		int x = 1;
		while ((1<<(x+1)) <= n) x++;
		return x;
	}
}