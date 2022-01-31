
import java.util.*;
import java.io.*;

public class dining {

	// http://usaco.org/index.php?page=viewproblem2&cpid=861
	
	static ArrayList<ArrayList<Edge>> g = new ArrayList<>();
	static ArrayList<ArrayList<Edge>> g2 = new ArrayList<>();
	static long[] dist1;
	static long[] dist2; 
	static int n, m, k;
	static long INF = (long)(1e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("dining.in"));
		PrintWriter out = new PrintWriter(new FileWriter("dining.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		for (int i=0; i<n; i++) {
			g.add(new ArrayList<>());
			g2.add(new ArrayList<>());
		}
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			long three = Integer.parseInt(st.nextToken());
			g.get(one).add(new Edge(two, three));
			g.get(two).add(new Edge(one, three));
		}
		
		HashMap<Integer, Long> hay = new HashMap<>();
		dist1 = new long[n]; 
		dist2 = new long[n];
		for (int i=0; i<k; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			long two = Integer.parseInt(st.nextToken());
			hay.put(one, Math.max(hay.getOrDefault(one, 0l), two));
		}
		
		dijkstras1(n-1);
		
		for (int i=0; i<n-1; i++) {
			for (int j=0; j<g.get(i).size(); j++) {
				if (g.get(i).get(j).destination == n-1) {
					
				}
				else {
					g2.get(i).add(g.get(i).get(j));
				}
			}
		}

		for (Integer a : hay.keySet()) {
			g2.get(n-1).add(new Edge(a, dist1[a]-hay.get(a)));
		}
		
		for (int i=0; i<g2.get(n-1).size(); i++) {
			if (g2.get(n-1).get(i).length + dist1[g2.get(n-1).get(i).destination] <= 0) {
				// all work
				for (int j=0; j<n-1; j++) {
					System.out.println(1);
					out.println(1);
				}
				out.close();
				return;
			}
		}
				
		dijkstras2(n-1);
		
		for (int i=0; i<n-1; i++) {
			if (dist2[i] <= dist1[i]) {
				System.out.println(1);
				out.println(1);
			}
			else {
				System.out.println(0);
				out.println(0);
			}
		}
		
		out.close();
	}
	
		// normal length to each node
	public static void dijkstras2(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[n];
		Arrays.fill(dist2, INF);
		dist2[start] = 0;
		
		pq.add(new Edge(start, 0));
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			int node = cur.destination;
			if (visited[node]) continue;
			visited[node] = true;
			
			for (Edge i : g2.get(node)) {
				if (visited[i.destination]) continue;
				
				if (cur.length + i.length < dist2[i.destination]) {
					dist2[i.destination] = cur.length + i.length;
					pq.add(new Edge(i.destination, dist2[i.destination]));
				}
			}
		}
	}
	
		// normal length to each node
	public static void dijkstras1(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[n];
		Arrays.fill(dist1, INF);
		dist1[start] = 0;
		
		pq.add(new Edge(start, 0));
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			int node = cur.destination;
			if (visited[node]) continue;
			visited[node] = true;
			
			for (Edge i : g.get(node)) {
				if (visited[i.destination]) continue;
				
				if (cur.length + i.length < dist1[i.destination]) {
					dist1[i.destination] = cur.length + i.length;
					pq.add(new Edge(i.destination, dist1[i.destination]));
				}
				
			}
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
			return Long.compare(length, o.length);
		}
	}
	
	static void print(long[] arr) {
		for (int i=0; i<arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
}

/*
	first find original distance to all edges from node n
	then create a new graph. draw all edges in first graph except those involving node n. 
	draw directed edges from n to edges with hay. distance is (new value = orig of 
	distance to hay - hay yummy)
	then move outwards, if new weight <= old weight, then it can be taken
	
	note: for any hay, if new value + old ≤ 0 —> can get to any node on the graph	
*/