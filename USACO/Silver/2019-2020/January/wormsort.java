
import java.util.*;
import java.io.*;

public class wormsort {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=992
	
	static int n,m;
	static int[] p;
	static ArrayList<Edge>[] g;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("wormsort.in"));
		PrintWriter out = new PrintWriter(new FileWriter("wormsort.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		p = new int[n];
		g = new ArrayList[n];
		boolean all_good=true;
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			p[i] = Integer.parseInt(st.nextToken()) - 1;
			if (p[i] != i) all_good=false;
			g[i] = new ArrayList<>();
		}

		if (all_good) {
			out.println(-1);
			out.close();
			return;
		}
		
		int min=(int)(1e9);
		int max = 0;
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken()) - 1;
			int two = Integer.parseInt(st.nextToken()) - 1;
			int three = Integer.parseInt(st.nextToken());
			g[one].add(new Edge(one,two,three));
			g[two].add(new Edge(two,one,three));
			min = Math.min(min, three);
			max = Math.max(max, three);
		}
		
		while (min<max) {
			int middle = min + (max-min+1)/2;
			if (check(middle)) {
				min = middle;
			}
			else max = middle - 1;
		}
		
		System.out.println(min);
		out.println(min);
		out.close();

	}
	
	public static boolean check(int mid) {
		dsu s = new dsu(n);
		boolean[] visited = new boolean[n];
		for (int i=0; i<n; i++) {
			if (!visited[i]) {
				dfs(i, -1, mid, visited, s);
			}
			if (s.FindSet(p[i]) != s.FindSet(i)) return false;
		}
		
		return true;
	}
	
	public static void dfs(int node, int parent, int mid, boolean[] visited, dsu s) {
		if (visited[node]) return;
		visited[node] = true;
		if (parent != -1) s.Union(node, parent);
		for (Edge i : g[node]) {
			if (i.weight >= mid) {
				dfs(i.b, node, mid, visited, s);
			}
		}
	}
	
	static class dsu {
		int n;
		int[] parent;
		int[] size;
		
		dsu (int n) {
			this.n = n;
			parent = new int[n];
			size = new int[n];
			for (int i=0; i<n; i++) {parent[i] = i; size[i] = 1;}
		}

		public int FindSet(int a) {
			if (a == parent[a]) return a;
			return parent[a] = FindSet(parent[a]);
		}
		
		public void Union(int a, int b) {
			a = FindSet(a);
			b = FindSet(b);
			if (a == b) return;
			
			if (size[a] < size[b]) {
				parent[a] = b;
				size[b] += size[a];
			}
			else {
				parent[b] = a;
				size[a] += size[b];
			}
		}
	}
	
	static class Edge {
		int a, b; int weight;
		Edge (int a, int b, int c) {
			this.a = a;
			this.b = b;
			weight = c;
		}
	}
}