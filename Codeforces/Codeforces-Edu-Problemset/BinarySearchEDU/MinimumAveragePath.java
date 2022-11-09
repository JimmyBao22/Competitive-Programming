import java.util.*;
import java.io.*;

public class MinimumAveragePath {
	
	// https://codeforces.com/edu/course/2/lesson/6/4/practice/contest/285069/problem/B
	
	static ArrayList<Edge>[] g;
	static double[] dist; 	// dist[i] = shortest distance from src to i
	static int[] parent;	// parent[i] = parent of i that gives shortest dist
	static int n, m;
	static long INF = (long)(1e18);

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MinimumAveragePath"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		dist = new double[n];
		parent = new int[n];
		g = new ArrayList[n];
		
		for (int i=0; i<n; i++) g[i] = new ArrayList<>();
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1; 	
			int b = Integer.parseInt(st.nextToken())-1; 	
			long c = Long.parseLong(st.nextToken()); 
			g[a].add(new Edge(b,c));
		}
		
		double min=0;
		double max=100;
		for (int i=0; i<50; i++) {
			double middle = (min+max)/2;
			dfs(middle);
			if (dist[n-1] <= 0) {	// works
				max = middle;
			}
			else min = middle;
		}
						
		dfs(min);
		ArrayList<Integer> path = Backtrack(n-1);
		StringBuilder s = new StringBuilder();
		s.append(path.size()-1); s.append("\n");
		for (int i=path.size()-1; i>=0; i--) {
			s.append(path.get(i)+1); s.append(" ");
		}
		System.out.println(s);
	}
	
	public static void dfs(double x) {
		Arrays.fill(dist, INF);
		parent[0] = -1; dist[0] = 0;
		
			// exploit fact that roads are one-way from lower # to higher #
		for (int i=0; i<n; i++) {
			if (dist[i] == INF) continue;
			for (Edge j : g[i]) {
				if (dist[i] + j.length - x < dist[j.destination]) {
					dist[j.destination] = dist[i] + j.length - x;
					parent[j.destination] = i;
					if (dist[n-1] <= 0) return;
				}
			}
		}
	}
	
	public static ArrayList<Integer> Backtrack(int dest) {
		ArrayList<Integer> path = new ArrayList<>();
		while (dest!= -1) {
			path.add(dest);
			dest = parent[dest];
		}
		return path;
	}
	
	static class Edge implements Comparable<Edge> {
		int destination;
		double length;
		Edge(int a , double b) {
			destination = a;
			length = b;
		}
		
		public int compareTo(Edge o) {		
			return Double.compare(length, o.length);
		}
		
		void print() {
			System.out.println(destination + " " + length);
		}
	}
}

/*
	
	For ur binary search, if your middle value that you are checking is x, 
		then this value works if there exists a min average <= x.
	Let's say the best path thing has y edges in it, so you want to check for a 
		given x if sum (the y edge cost) / y <= x is true
	sum (the y edge cost) <= x*y --> sum (edge - x) for each of the y nodes <= 0
	Find minimum path from 1 to n where each edge is just (edge-x)

*/
