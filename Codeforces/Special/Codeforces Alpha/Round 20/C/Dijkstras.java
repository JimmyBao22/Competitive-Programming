
import java.util.*;
import java.io.*;

public class Dijkstras {
	
	static ArrayList<ArrayList<Edge>> adj = new ArrayList<>();
	static long[] dist; 	// dist[i] = shortest distance from src to i
	static int[] parent;	// parent[i] = parent of i that gives shortest dist
	static int n, m;
	static long INF = (long)(1e18);

	// https://codeforces.com/contest/20/problem/C
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Dijkstras"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken()); 	// number of vertices
		m = Integer.parseInt(st.nextToken()); 	// number of edges
		dist = new long[n];
		parent = new int[n];
		for (int i=0; i<n; i++) adj.add(new ArrayList<>());
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1; 	
			int b = Integer.parseInt(st.nextToken())-1; 	
			long c = Long.parseLong(st.nextToken()); 
			adj.get(a).add(new Edge(b, c));
			adj.get(b).add(new Edge(a, c));
		}
		
		// int start = Integer.parseInt(in.readLine());
		dijkstras(0);
		PrintPath(n-1);
	}
	
		// O((N+M)logM)
	public static void dijkstras(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[n];
		Arrays.fill(dist, INF);
		dist[start] = 0;
		parent[start] = -1;
		
		pq.add(new Edge(start, 0));
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			int node = cur.destination;
			if (visited[node]) continue;
			visited[node] = true;
			
			for (Edge i : adj.get(node)) {
				if (visited[i.destination]) continue;
				
				if (cur.length + i.length < dist[i.destination]) {
					dist[i.destination] = cur.length + i.length;
					parent[i.destination] = node;
					pq.add(new Edge(i.destination, dist[i.destination]));
				}
				
			}
		}
	}
	
		// O(N^2)
	public static void dijkstras2(int start) {
		boolean[] visited = new boolean[n];
		Arrays.fill(dist, INF);
		dist[start] = 0;
		parent[start] = -1;
		
		for (int i=0; i<n; i++) {
			// find the smallest one
			int smallest=0;
			long minval=INF;
			for (int j=0; j<n; j++) {
				if (!visited[j] && dist[j]<minval) {
					minval = dist[j];
					smallest = j;
				}
			}
			
			for (Edge a : adj.get(smallest)) {
				if (!visited[a.destination] && dist[a.destination] > minval + a.length) {
					dist[a.destination] = minval + a.length;
					parent[a.destination] = smallest;
				}
			}
			visited[smallest] = true;
		}
	}
	
	public static void PrintPath(int dest) {
		if (dist[dest] == INF) {
			System.out.println(-1);
			return;
		}
		
		ArrayList<Integer> path = new ArrayList<>();
		while (dest!= -1) {
			path.add(dest+1);
			dest = parent[dest];
		}
		for (int i=path.size()-1; i>=0; i--) {
			System.out.print(path.get(i) + " ");
		}
	}
	
	static class Edge implements Comparable<Edge> {
		int destination;
		long length;
		Edge(int a , long b) {
			destination = a;
			length = b;
		}
		
		public int compareTo(Edge o) {
			if (length <= o.length) {
				return -1;
			}
			else return 1;
		}
	}
}
