
import java.util.*;
import java.io.*;

public class tractor {

	// http://usaco.org/index.php?page=viewproblem2&cpid=245

	static int n, needed;
	static long INF = (long)(1e18);
	static int[][] arr;
	static Edge[] edges;

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("tractor.in"));
		PrintWriter out = new PrintWriter(new FileWriter("tractor.out"));

		n = Integer.parseInt(in.readLine());
		arr = new int[n][n];
		for (int i=0; i<n; ++i) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			for (int j=0; j<n; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		needed = (n*n+1)/2;
		dsu s = new dsu(n);
		edges = new Edge[2*n*n-2*n];
		int pointer=0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n-1; j++) {
				edges[pointer] = new Edge(i,j,i,j+1, Math.abs(arr[i][j] - arr[i][j+1]));
				pointer++;
			}
			if (i!=n-1) {
				for (int j=0; j<n; j++) {
					edges[pointer] = new Edge(i,j,i+1,j, Math.abs(arr[i][j] - arr[i+1][j]));
					pointer++;
				}
			}
		}
		Arrays.parallelSort(edges);
		
		long ans=MST(s);
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	public static long MST(dsu s) {
		for (int i=0; i<edges.length; i++) {
			//edges[i].print();
			if (s.Union(edges[i].fromx, edges[i].fromy, edges[i].destx, edges[i].desty)) {
				int set = s.FindSet(edges[i].fromx, edges[i].fromy);
				if (s.size[set/n][set%n] >= needed) {
					return edges[i].length;
				}
			}
		}
		return 0;
	}
	
	static class Edge implements Comparable<Edge> {
		int fromx, fromy, destx, desty;
		long length;
		Edge(int a , int b, int c, int d, long e) {
			fromx = a; fromy = b; destx = c; desty = d;
			length = e;
		}
		
		public int compareTo(Edge o) {
			return Long.compare(length, o.length);
		}
	}
	
	static class dsu {
		int n;
		int[][] parent;
		int[][] size;
		
		dsu (int n) {
			this.n = n;
			parent = new int[n][n];
			size = new int[n][n];
			for (int i=0; i<n; i++) {
				for (int j=0; j<n; j++) {
					parent[i][j] = i*n+j; size[i][j] = 1;
				}	
			}
		}

		public int FindSet(int i, int j) {
			if (i*n+j == parent[i][j]) return i*n+j;
			return parent[i][j] = FindSet(parent[i][j]/n, parent[i][j]%n);
		}
		
		public boolean Union(int i1, int j1, int i2, int j2) {
			int a = FindSet(i1,j1);
			int b = FindSet(i2,j2);
			i1 = a/n;
			j1 = a%n;
			i2 = b/n;
			j2 = b%n;
			if (a == b) { 			// already grouped
				return false;
			}
			
			if (size[i1][j1] < size[i2][j2]) {
				parent[i1][j1] = b;
				size[i2][j2] += size[i1][j1];
			}
			else {
				parent[i2][j2] = a;
				size[i1][j1] += size[i2][j2];
			}
			return true;
		}
	}
}