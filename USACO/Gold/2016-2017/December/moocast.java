
import java.util.*;
import java.io.*;

public class moocast {

	// http://usaco.org/index.php?page=viewproblem2&cpid=669
	
	static int n;
	static long[][] arr;
	static long INF = (long)(1e18);
	static long[][] distance;
	static Edge[] edges;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("moocast.in"));
		PrintWriter out = new PrintWriter(new FileWriter("moocast.out"));

		n = Integer.parseInt(in.readLine());
		arr = new long[n][2];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		distance = new long[n][n];
		edges = new Edge[n*(n-1)/2];
		int pointer=0;
		for (int i=0; i<n; i++) {
			for (int j=i+1; j<n; j++) {
				distance[i][j] = dist(arr[i], arr[j]);
				edges[pointer] = new Edge(i, j, distance[i][j]);
				pointer++;
			}
		}
		
		Arrays.parallelSort(edges);
		
		dsu s = new dsu(n);
		long ans = MST(s);
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static long MST(dsu s) {
		long ans=0;
		for (int i=0; i<edges.length; i++) {
			if (s.Union(edges[i].from, edges[i].destination)) {
				if (s.size[s.FindSet(edges[i].from)] == n) return edges[i].length;
			}
		}
		return ans;
	}
	
	static class dsu {
		int n;
		int[] parent;
		int[] size;
		
		dsu (int n) {
			this.n = n;
			parent = new int[n];
			size = new int[n];
			for (int i=0; i<n; i++) {parent[i] = i; size[i] = 1;}
		}

		public int FindSet(int a) {
			if (a == parent[a]) return a;
			return parent[a] = FindSet(parent[a]);
		}
		
		public boolean Union(int a, int b) {
			a = FindSet(a);
			b = FindSet(b);	
			if (a == b) { 			// already grouped
				return false;
			}
			
			if (size[a] < size[b]) {
				parent[a] = b;
				size[b] += size[a];
			}
			else {
				parent[b] = a;
				size[a] += size[b];
			}
			return true;
		}
	}
	
	static class Edge implements Comparable<Edge> {
		int from;
		int destination;
		long length;
		Edge(int a , int b,  long c) {
			from = a; destination = b;
			length = c;
		}
		
		public int compareTo(Edge o) {
			return Long.compare(length, o.length);
		}
		void print() {
			System.out.println(from + " " + destination + " " + length);
		}
	}
	
	static long dist(long[] a, long[] b) {
		return (a[0] - b[0] ) * (a[0] - b[0] ) + (a[1] - b[1] ) * (a[1] - b[1] ); 
	}
}