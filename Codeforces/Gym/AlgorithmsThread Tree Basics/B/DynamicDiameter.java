
import java.util.*;
import java.io.*;

public class DynamicDiameter {

	// https://codeforces.com/gym/102694/problem/B
	
	static ArrayList<Integer>[] g;
	static int n;
	static int[] dist, parent;
	static boolean[] visited;
	static HashSet<Integer> ret;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("DynamicDiameter"));

		n = Integer.parseInt(in.readLine());
		g = new ArrayList[n];
		for (int i=0; i<n; i++) {
			g[i] = new ArrayList<>();
		}
		
		for (int i=0; i<n-1; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			g[one].add(two);
			g[two].add(one);
		}
		
		dist = new int[n];
		visited = new boolean[n];
		parent = new int[n];
		dfs(0, -1, 0);
		int max=0; int maxindex=0;
		for (int i=0; i<n; i++) {
			if (dist[i] > max) {
				max = dist[i];
				maxindex = i;
			}
		}
		
		dist = new int[n];
		visited = new boolean[n];
		parent = new int[n];
		dfs(maxindex, -1, 0);
		max=0; maxindex=0;
		for (int i=0; i<n; i++) {
			if (dist[i] > max) {
				max = dist[i];
				maxindex = i;
			}
		}
		
		ArrayList<Integer> back = new ArrayList<>();
		while (maxindex != -1) {
			back.add(maxindex);
			maxindex = parent[maxindex];
		}
		
		// use strat where u redraw tree with diameter as a line with nodes coming down from it
		
		visited = new boolean[n];
		ret = new HashSet<>();
		for (int i=1; i<back.size()-1; i++) {
			int maxdepth = Math.min(i, back.size()-1-i);
			dfs2(back.get(i), back.get(i-1), back.get(i+1), 0, maxdepth);
		}
		ret.add(back.get(0)); ret.add(back.get(back.size()-1));
		
		StringBuilder s = new StringBuilder();
		for (int i=0; i<n; i++) {
			if (ret.contains(i)) s.append(max+1);
			else s.append(max);
			s.append("\n");
		}
		
		System.out.print(s);
	}
	
	public static void dfs2(int node, int p1, int p2, int d, int maxdepth) {
		if (visited[node]) return;
		visited[node] = true;
		if (d == maxdepth) {
			ret.add(node); return;
		}
		for (Integer i : g[node]) if (i != p1 && i != p2) dfs2(i, node, node, d+1, maxdepth);
	}
	
	public static void dfs(int node, int p, int d) {
		if (visited[node]) return;
		visited[node] = true;
		dist[node] = d;
		parent[node] = p;
		for (Integer i : g[node]) {
			if (i != p) dfs(i, node, d+1);
		}
	}
}