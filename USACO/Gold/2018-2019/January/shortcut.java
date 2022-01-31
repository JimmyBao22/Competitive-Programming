
import java.util.*;
import java.io.*;

public class shortcut {

	// http://usaco.org/index.php?page=viewproblem2&cpid=899
	
	static ArrayList<ArrayList<Edge>> g = new ArrayList<>();
	static int n,m;
	static long INF = (long)(1e18);
	static int[] parent;
	static long[] dist;
	static ArrayList<Long> visit;
	static long[] cows;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("shortcut.in"));
		PrintWriter out = new PrintWriter(new FileWriter("shortcut.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		long t = Integer.parseInt(st.nextToken());
		cows = new long[n];
		parent = new int[n];
		dist = new long[n];
		visit = new ArrayList<>();
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			cows[i] = Integer.parseInt(st.nextToken());
			g.add(new ArrayList<>());
			visit.add(0l);
		}
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			long val = Integer.parseInt(st.nextToken());
			g.get(a).add(new Edge(b,val));
			g.get(b).add(new Edge (a,val));
		}
		
		dijk(0);
		for (int i=1; i<n; i++) {
			// find number of cows that visit each node
			Backtrack(i, i);
		}
		
		long maxans=0;
		for (int i=1; i<n; i++) {
			long cur=0;
			if (dist[i]>t) {
				cur -= dist[i]*visit.get(i);
				cur += t*visit.get(i);
				// cur is now how much less time it takes. Negate it
				cur = -cur;
			}
			maxans = Math.max(maxans, cur);
		}

		System.out.println(maxans);
		out.println(maxans);
		out.close();
	}
	
	public static void Backtrack(int end, int big) {
		while (end!=-1) {
			visit.set(end, visit.get(end) + cows[big]);
			end = parent[end];
		}
	}
	
	public static void dijk(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[n];
		Arrays.fill(dist, INF);
		dist[start] =0;
		parent[start] = -1;
		pq.add(new Edge(start,0));
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			int node = cur.dest;
			if (visited[node]) continue;
			visited[node] = true;
			
			for (Edge a : g.get(node)) {
				if (visited[a.dest]) continue;
				if (dist[a.dest]> a.value + cur.value) {
					dist[a.dest] = a.value + cur.value;
					parent[a.dest] = node;
					pq.add(new Edge(a.dest, dist[a.dest]));
				}
				else if (dist[a.dest]== a.value + cur.value && parent[a.dest] > node) {
					parent[a.dest] = node;
					pq.add(new Edge(a.dest, dist[a.dest]));
				}
			}
		}
	}
	
	static class Edge implements Comparable<Edge> {
		int dest;
		long value;
		Edge (int a, long b) {
			dest =a; value = b;
		}
		public int compareTo(Edge o) {
			if (value == o.value) {
				return dest - o.dest;
			}
			return Long.compare(value, o.value);
		}
	}
}