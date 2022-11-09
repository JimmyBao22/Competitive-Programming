
import java.util.*;
import java.io.*;

public class MinimummaximumonthePath {

	// https://codeforces.com/edu/course/2/lesson/6/3/practice/contest/285083/problem/D
	
	static ArrayList<ArrayList<Edge>> g = new ArrayList<>();
	static boolean works;
	static int n,m,d;
	static ArrayList<Integer> path = new ArrayList<>();
	static long[] dist; 	// dist[i] = shortest distance from src to i
	static int[] parent;	// parent[i] = parent of i that gives shortest dist
	static long INF = (long)(1e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MinimummaximumonthePath"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());
		dist = new long[n];
		parent = new int[n];
		for (int i=0; i<n; i++) g.add(new ArrayList<>());
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			long val = Integer.parseInt(st.nextToken());
			g.get(one).add(new Edge(two, val, 0));
		}
		
		dijkstras(0, (long)(1e10));
		if (dist[n-1]==INF) {
			// can't get to node n
			System.out.println(-1);
			return;
		}
		
		long min=0;
		long max = (long)(1e10);
		while (min<max) {
			long middle = min+(max-min)/2;
			dijkstras(0, middle);
			if (dist[n-1]!=INF) {
				max=middle;
			}
			else min = middle+1;
		}
		
		Backtrack(n-1);
		System.out.println(path.size()-1);
		StringBuilder s = new StringBuilder();
		for (int i=path.size()-1; i>=0; i--) {
			s.append(path.get(i)+1);
			s.append(" ");
		}
		System.out.println(s);
	}
	
	public static void Backtrack(int dest) {
		while (dest!= -1) {
			path.add(dest);
			dest = parent[dest];
		}
	}
	
	public static void dijkstras(int start, long mid) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[n];
		Arrays.fill(dist, INF);
		dist[start] = 0;
		parent[start] = -1;
		
		pq.add(new Edge(start, 0, 0));
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			int node = cur.to;
			if (visited[node]) continue;
			visited[node] = true;
			if (cur.num == d) continue;
			
			for (Edge i : g.get(node)) {
				if (visited[i.to]) continue;
				if (i.val > mid) continue;
				if (cur.num+1 > d) continue;
				if (dist[i.to] > cur.num+1) {
					dist[i.to] = cur.num+1;
					parent[i.to]= node; 
					pq.add(new Edge(i.to, i.val, cur.num+1));					
				}
			}
		}
	}

	static class Edge implements Comparable<Edge> {
		int to; long val; int num;
		Edge (int b, long c, int d) {
			to = b;
			val = c;
			num = d;
		}
		public int compareTo(Edge o) {
			return num-o.num;
		}
	}
}