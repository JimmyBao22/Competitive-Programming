
import java.util.*;
import java.io.*;

public class mroute {

	// http://usaco.org/index.php?page=viewproblem2&cpid=210
		// my solution
	
	static long[] capacities;
	static ArrayList<ArrayList<Edge>> g = new ArrayList<>();
	static int n, m;
	static double x;
	static long[][] distance;
		// node, minimum capacity (index of capacities array) = Min L
	static long INF = (long)(1e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("mroute.in"));
		PrintWriter out = new PrintWriter(new FileWriter("mroute.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		distance = new long[n][m];
		capacities = new long[m];
		for (int i=0; i<n; i++) g.add(new ArrayList<>());
		for (int i=0; i<m; i++ ) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			long l = Integer.parseInt(st.nextToken());
			long c = Integer.parseInt(st.nextToken());
			g.get(a).add(new Edge(b, l,c,i));
			g.get(b).add(new Edge(a, l,c,i));
			capacities[i] = c;
		}
		
		dijk(0);
		
		long min=INF;
		for (int i=0; i<m; i++) {
			min = Math.min(min, (long)(distance[n-1][i] + (long)(x)/capacities[i]));
		}
		
		System.out.println(min);
		out.println(min);
		out.close();

	}
	
	public static void dijk(int start) {
		for (int i=1; i<n; i++) {
			Arrays.fill(distance[i], INF);
		}
		
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		
		boolean[][] visited = new boolean[n][m];
		for (Edge a : g.get(start)) {
			//a.print();
			if (distance[a.dest][a.index] > a.l) {
				distance[a.dest][a.index] = a.l;
				pq.add(new Edge(a.dest, a.l, a.c, a.index));
			}
		}
				
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			//cur.print();
			if (visited[cur.dest][cur.index]) continue;
			visited[cur.dest][cur.index] = true;
			for (Edge a : g.get(cur.dest)) {
				//a.print();
				long capacity=0;
				int capacity_index=0;
				if (cur.c < a.c) {
					capacity = cur.c;
					capacity_index = cur.index;
				}
				else {
					capacity = a.c;
					capacity_index = a.index;
				}
				if (visited[a.dest][capacity_index]) continue;
				if (distance[a.dest][capacity_index] > cur.l + a.l) {
					distance[a.dest][capacity_index] = cur.l + a.l;
					pq.add(new Edge(a.dest, distance[a.dest][capacity_index], capacity, capacity_index));
				}
			}
		}
	}

	static class Edge implements Comparable<Edge> {
		int dest;
		long l;
		long c;
		int index;
		Edge (int a, long b, long c, int d) {
			dest = a; l = b; this.c = c; index  =d;
		}
		
		public int compareTo(Edge o) {
			return Double.compare((double)(l+x/(double)(c)), o.l+x/(double)(o.c));
		}
		
		void print() {
			System.out.println(dest + " " + l + " " + c + " " + index);
		}
	}
}