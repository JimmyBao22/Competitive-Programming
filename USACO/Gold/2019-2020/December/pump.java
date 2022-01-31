import java.util.*;
import java.io.*;

public class pump {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=969
	
	static ArrayList<ArrayList<Edge>> g = new ArrayList<>();
	static long[][] dist; 	
	static int n, m;
	static long INF = (long)(1e18);

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("pump.in"));
		PrintWriter out = new PrintWriter(new FileWriter("pump.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		dist = new long[n][1001];
			// node, flow
		
		for (int i=0; i<n; i++) g.add(new ArrayList<>());
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1; 	
			int b = Integer.parseInt(st.nextToken())-1; 	
			long c = Long.parseLong(st.nextToken()); 
			int d = Integer.parseInt(st.nextToken()); 
			g.get(a).add(new Edge(b, c, d));
			g.get(b).add(new Edge(a, c, d)); 		// if undirected
		}
		
		dijkstras(n-1);
		
		double ans=0;
		for (int i=0; i<1001; i++) {
			ans = Math.max(ans, (double)i / (double)dist[0][i]);
		}
		int answer = (int) (ans * (int)(1e6));
		System.out.println(answer);
		out.print(answer);
		out.close();
	}
	
		// O((N+M)logM)
	public static void dijkstras(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[][] visited = new boolean[n][1001];
		for (int i=0; i<n; i++) Arrays.fill(dist[i], INF);
		for (int i=0; i<1001; i++) dist[start][i] = 0;
		
		pq.add(new Edge(start, 0, 1000));
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			int node = cur.destination;
			if (visited[node][cur.flow]) continue;
			visited[node][cur.flow] = true;
			
			for (Edge i : g.get(node)) {
				
				if (cur.flow <= i.flow) {
					if (visited[i.destination][cur.flow]) continue;
					
					if (cur.length + i.length < dist[i.destination][cur.flow]) {
						dist[i.destination][cur.flow] = cur.length + i.length;
						pq.add(new Edge(i.destination, dist[i.destination][cur.flow], cur.flow));
					}
				}
				else {
					if (visited[i.destination][i.flow]) continue;
					
					if (cur.length + i.length < dist[i.destination][i.flow]) {
						dist[i.destination][i.flow] = cur.length + i.length;
						pq.add(new Edge(i.destination, dist[i.destination][i.flow], i.flow));
					}
				}
			}
		}
	}
	
	static class Edge implements Comparable<Edge> {
		int destination;
		long length;
		int flow;
		Edge(int a , long b, int c) {
			destination = a;
			length = b;
			flow = c;
		}
		Edge(int a , long b) {
			destination = a;
			length = b;
		}
		
		public int compareTo(Edge o) {
			return Long.compare(length, o.length);
		}
	}
}