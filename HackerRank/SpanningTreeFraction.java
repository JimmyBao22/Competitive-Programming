
import java.util.*;
import java.io.*;

public class SpanningTreeFraction {

	// https://www.hackerrank.com/contests/w31/challenges/spanning-tree-fraction/problem
	
	static int n, m;
	static ArrayList<Edge>[] g;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SpanningTreeFraction"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		g = new ArrayList[n];
		for (int i=0; i<n; i++) g[i] = new ArrayList<>();
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken()); 	
			int b = Integer.parseInt(st.nextToken()); 	
			int c = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			g[a].add(new Edge(b,c,d));
			g[b].add(new Edge(a,c,d));
		}
		
		double min=0;
		double max=100;
		for (int i=0; i<30; i++) {
			double middle = (min+max)/2;
			if (MST(0, middle)) {
				min = middle;
			}
			else max = middle;
		}
		
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[n];
		pq.add(new Edge(0, 0, 0, min));
		int numerator = 0;
		int denominator = 0;
		
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			if (visited[cur.destination]) continue;
			visited[cur.destination] = true;
			numerator += cur.a;
			denominator += cur.b;
			
			for (Edge i : g[cur.destination]) {
				if (visited[i.destination]) continue;
				pq.add(new Edge(i.destination, i.a, i.b, min));
			}
		}

		int gcd = gcd(numerator, denominator);
		numerator /= gcd;
		denominator /= gcd;
		System.out.println(numerator + "/" + denominator);
	}
	
	public static boolean MST(int start, double mid) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[n];
		pq.add(new Edge(start, 0, 0, mid));
		double minlength = 0;
		
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			if (visited[cur.destination]) continue;
			visited[cur.destination] = true;
			minlength += cur.a - cur.b*mid;
			
			for (Edge i : g[cur.destination]) {
				if (visited[i.destination]) continue;
				pq.add(new Edge(i.destination, i.a, i.b, mid));
			}
		}
		return minlength >= 0;
	}
	
	static class Edge implements Comparable<Edge> {
		int destination;
		int a, b;
		double x;
		Edge(int a, int b, int c) {
			destination = a;
			this.a = b;
			this.b = c;
		}
		Edge(int a, int b, int c, double d) {
			destination = a;
			this.a = b;
			this.b = c;
			x = d;
		}
		
		public int compareTo(Edge o) {
			return Double.compare(o.a - o.b*x, a-b*x);		// take largest one
		}
	}
	
	public static int gcd(int a, int b) { 
        if (b == 0) return a; 
        return gcd(b, a%b); 
	}
}