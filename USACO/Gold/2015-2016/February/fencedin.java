
import java.util.*;
import java.io.*;

public class fencedin {

	// http://usaco.org/index.php?page=viewproblem2&cpid=623
	
	static long A,B;
	static int n, m;
	static long[] a, b;
	static Edge[] edges, edges1;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("fencedin.in"));
		PrintWriter out = new PrintWriter(new FileWriter("fencedin.out"));
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		A = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		a = new long[n]; b = new long[m];
		for (int i=0; i<n; i++ ) {
			a[i] = Integer.parseInt(in.readLine());
		}
		for (int i=0; i<m; i++ ) {
			b[i] = Integer.parseInt(in.readLine());
		}

		Arrays.parallelSort(a);
		Arrays.parallelSort(b);
		
	    edges1 = new Edge[m+n+2];
		edges = new Edge[2*m*n+m+n];
		int pointer=0;
		
		edges1[pointer] = new Edge(0, 0, 1, 0, b[0]);
		pointer++;
		for (int j=1; j<m; j++) {
			edges1[pointer] = new Edge(0, j, 1, j, b[j] - b[j-1]);
			pointer++;
		}
		edges1[pointer] = new Edge(0, m, 1, m, B-b[m-1]);
		pointer++;
		
		edges1[pointer] = new Edge(0, 0, 0, 1, a[0]);
		pointer++;
		for (int j=1; j<n; j++) {
			edges1[pointer] = new Edge(j, 0, j, 1, a[j] - a[j-1]);
			pointer++;
		}
		edges1[pointer] = new Edge(n, 0, n, 1, A-a[n-1]);
		pointer++;
		
		Arrays.sort(edges1);
		
		pointer=0;
		for (int i=0; i<edges1.length; i++) {
			if (edges1[i].one1 != edges1[i].two1) {
				for (int j=0; j<n; j++) {
					edges[pointer] = new Edge(j, edges1[i].one2, j+1, edges1[i].two2, edges1[i].length);
					pointer++;
				}
			}
			else {
				for (int j=0; j<m; j++) {
					edges[pointer] = new Edge(edges1[i].one1, j, edges1[i].two1, j+1, edges1[i].length);
					pointer++;
				}
			}
		}
		
		dsu2D s = new dsu2D(n+1,m+1);
		long ans = MST(s);
		
		out.println(ans);
		out.close();
	}
	
	public static long MST(dsu2D s) {
		long ans=0;
		for (int i=0; i<edges.length; i++) {
			int set = s.Union(edges[i].one1, edges[i].one2, edges[i].two1, edges[i].two2);
			if (set != -1) {
				ans += edges[i].length;
				if (s.size[set/s.mult][set%s.mult] == (n+1)*(m+1)) {
					return ans;
				}
			}
		}
		return ans;
	}
	
	static class Edge implements Comparable<Edge> {
		int one1; int one2; int two1; int two2;
		long length;
		Edge (int a, int b, int c, int d, long e) {
			one1 = a; one2 = b;
			two1 = c; two2 = d;
			length = e;
		}
		public int compareTo(Edge o) {
			return Long.compare(length, o.length);
		}
	}
	
	static class dsu2D {
		int n, m;
		int mult = (int)3e4;
		int[][] parent;
		int[][] size;
		
		dsu2D (int n, int m) {
			this.n = n; this.m = m;
			parent = new int[n][n];
			size = new int[n][n];
			for (int i=0; i<n; i++) {
				for (int j=0; j<m; j++) {
					parent[i][j] = i*mult+j; size[i][j] = 1;
				}	
			}
		}

		public int FindSet(int i, int j) {
			if (i*mult+j == parent[i][j]) return i*mult+j;
			return parent[i][j] = FindSet(parent[i][j]/mult, parent[i][j]%mult);
		}
		
		public int Union(int i1, int j1, int i2, int j2) {
			int a = FindSet(i1,j1);
			int b = FindSet(i2,j2);
			i1 = a/mult;
			j1 = a%mult;
			i2 = b/mult;
			j2 = b%mult;
			if (a == b) { 			
				return -1;
			}
			
			if (size[i1][j1] < size[i2][j2]) {
				parent[i1][j1] = b;
				size[i2][j2] += size[i1][j1];
				return b;
			}
			else {
				parent[i2][j2] = a;
				size[i1][j1] += size[i2][j2];
				return a;
			}
		}
	}
}

/*
	idea is to have a graph that is just the different regions, and connect each region 
		separated by fences, with an edge with weight equal to length of that fence part.
	
	Once you have connected all the (n+1)(m+1) regions, the addition of all weights so 
		far is ur answer. Use MST.

	Note that creating all 2*m*n+m+n edges, then sorting these, will be too slow for Java
		Therefore, since there are only m+n+2 different possible lengths that these
		edges can be, find the lengths of these m+n+2 edges, sort, then construct all
			edges
	
*/