
import java.util.*;
import java.io.*;

public class Energy {

	// https://lit.lhsmathcs.org/energy
	
	static int n,m,t;
	static ArrayList<ArrayList<Edge>> g = new ArrayList<>();
	static long[][] dist;
	static long[] cost;
	static long INF = (long)(1e18);
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Energy"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());
		
		cost = new long[n];
		dist = new long[n][t+1];
		
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			g.add(new ArrayList<>());
			cost[i] = Integer.parseInt(st.nextToken());
		}
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			int length = Integer.parseInt(st.nextToken());
			g.get(one).add(new Edge(two, length, cost[two], 0));
			g.get(two).add(new Edge(one, length, cost[one], 0));
		}
		
		dijkstras(0);
		
		StringBuilder ans = new StringBuilder();
		for (int i=1; i<n; i++) {
			long min=INF;
			for (int j=0; j<=t; j++) {
				min = Math.min(min, dist[i][j]);
			}
			if (min == INF) {
				ans.append(-1);
			}
			else {
				ans.append(min);
			}
			ans.append(" ");
		}
		
		System.out.println(ans);
	}
	
	public static void dijkstras(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[][] visited = new boolean[n][t+1];
		for (int i=0; i<n; i++) {
			Arrays.fill(dist[i], INF);			
		}
		dist[start][t] = 0;
		pq.add(new Edge(start, 0, 0l, t));
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			int node = cur.dest;
			int capacity = cur.capacity;
			if (visited[node][capacity]) continue;
			visited[node][capacity] = true;
			//cur.print();
			
			for (Edge i : g.get(node)) {
				//i.print();
				int new_capacity = capacity - i.length;
				if (new_capacity<0) continue;		// cant reach i
				int orig = new_capacity;
				if (i.cost == 0) {
					// can't buy energy here
					if (visited[i.dest][new_capacity]) continue;
					if (cur.cost < dist[i.dest][new_capacity]) {
						dist[i.dest][new_capacity] = cur.cost;
						pq.add(new Edge(i.dest, 0, dist[i.dest][new_capacity], new_capacity));
					}
				}
				else {
					for (; new_capacity<=t; new_capacity++) {
						if (visited[i.dest][new_capacity]) continue;
						if (cur.cost + i.cost*(new_capacity - orig) < dist[i.dest][new_capacity]) {
							dist[i.dest][new_capacity] = cur.cost + i.cost*(new_capacity - orig);
							pq.add(new Edge(i.dest, 0, dist[i.dest][new_capacity], new_capacity));
						}
					}
				}
			}
		}
	}
	
	static class Edge implements Comparable<Edge> {
		int dest;
		int length;
		long cost;
		int capacity;
		Edge (int a, int b, long c, int d) {
			dest = a; length = b; cost = c; capacity = d;
		}
		public int compareTo(Edge o) {
			return Long.compare(cost, o.cost);
		}
		public void print() {
			System.out.println(dest + " " + length + " " + cost + " " + capacity);
		}
	}
}