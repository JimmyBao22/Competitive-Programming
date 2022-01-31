
import java.util.*;
import java.io.*;

public class piggyback {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=491
	
	static int n, m;
	static long b,e,p;
	static ArrayList<ArrayList<Integer>> g = new ArrayList<>();
	static long[][] dist;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("piggyback.in"));
		PrintWriter out = new PrintWriter(new FileWriter("piggyback.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		b = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		p = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		for (int i=0; i<n; i++) g.add(new ArrayList<>());
		dist = new long[3][n];
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			g.get(a).add(b);
			g.get(b).add(a);
		}
		
		bfs(0, 0);
		bfs(1, 1);
		bfs(n-1, 2);
		
		long min=(long)(1e18);
		for (int i=0; i<n; i++) {
			long cur=0;
			cur += dist[0][i]*b + dist[1][i]*e + dist[2][i]*p;
			min = Math.min(min, cur);
		}

		System.out.println(min);
		out.println(min);
		out.close();
	}
	
	public static void bfs(int node, int num) {
		ArrayDeque<int[]> a = new ArrayDeque<>();
		boolean[] visited = new boolean[n];
		a.add(new int[] {node,0});
		while (!a.isEmpty()) {
			int[] cur = a.poll();
			if (visited[cur[0]]) continue;
			dist[num][cur[0]] = cur[1];
			visited[cur[0]] = true;
			for (Integer b : g.get(cur[0])) {
				a.add(new int[] {b, cur[1]+1});
			}
		}
	}
}