import java.util.*;

public class PathwithMaximumProbability {

	public static void main(String[] args) {

	}
	
	// https://leetcode.com/problems/path-with-maximum-probability/
	
	static ArrayList<Edge>[] g;
	static double[] dist;
	static int n;
	static long INF = (long)(1e18);
	
	@SuppressWarnings("unchecked")
	public static double maxProbability(int num, int[][] edges, double[] succProb, int start, int end) {
		n = num;
		g = new ArrayList[n];
		for (int i=0; i<n; i++) {
			g[i] = new ArrayList<>();
		}
        dist = new double[n];
		
		for (int i=0; i<edges.length; i++) {
			g[edges[i][0]].add(new Edge(edges[i][1], succProb[i]));
			g[edges[i][1]].add(new Edge(edges[i][0], succProb[i]));
		}
		
		dijkstras(start);
		
        return dist[end];
    }
	
	public static void dijkstras(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[n];
		dist[start] = 1;
		
		pq.add(new Edge(start, 1));
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			int node = cur.destination;
			if (visited[node]) continue;
			visited[node] = true;
			
			for (Edge i : g[node]) {
				if (visited[i.destination]) continue;
				
				if (cur.length * i.length > dist[i.destination]) {
					dist[i.destination] = cur.length * i.length;
					pq.add(new Edge(i.destination, dist[i.destination]));
				}
				
			}
		}
	}
	
	static class Edge implements Comparable<Edge> {
		int destination;
		double length;
		Edge(int a , double b) {
			destination = a;
			length = b;
		}
		
		public int compareTo(Edge o) {
//			return Long.compare(length, o.length);
			return Double.compare(o.length, length);
		}
        
        public String toString() {
            return destination + " " + length;
        }
	}
}
