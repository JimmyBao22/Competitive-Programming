
import java.util.*;
import java.io.*;

public class timeline {

	// http://usaco.org/index.php?page=viewproblem2&cpid=1017
	
	static int n,m,c;
	static int[] s;
	static int[] final_times;
	static ArrayList<ArrayList<Edge>> g = new ArrayList<>();
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("timeline.in"));
		PrintWriter out = new PrintWriter(new FileWriter("timeline.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());

		s = new int[n]; final_times = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			s[i] = Integer.parseInt(st.nextToken());
			g.add(new ArrayList<>());
		}
		
		for (int i=0; i<c; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			int three = Integer.parseInt(st.nextToken());
			g.get(two).add(new Edge(two, one, three));
		}
		
		boolean[] visited = new boolean[n];
		for (int i=0; i<n; i++) {
			if (!visited[i]) dfs(i, visited);
		}
		
		StringBuilder s = new StringBuilder();
		for (int i=0; i<n; i++) {
			s.append(final_times[i]);
			s.append("\n");
		}
		System.out.print(s);
		out.print(s);
		out.close();

	}
	
	public static void dfs(int node, boolean[] visited) {
		if (visited[node]) return;
		visited[node] = true;
		int time=s[node];
		for (int i=0; i<g.get(node).size(); i++) {
			dfs(g.get(node).get(i).to, visited);
			time = Math.max(time, final_times[g.get(node).get(i).to] + g.get(node).get(i).dist);
		}
		final_times[node] = time;
	}
	
	static class Edge {
		int from; int to; int dist;
		Edge (int a, int b, int c) {
			from = a; to = b; dist = c;
		}
	}
}