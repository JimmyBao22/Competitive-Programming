
import java.util.*;
import java.io.*;

public class Norefuel {

	// https://codeforces.com/edu/course/2/lesson/7/2/practice/contest/289391/problem/G
	
	static int n,m;
	static Edge[] edges;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Norefuel"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		edges = new Edge[m];
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			long three = Integer.parseInt(st.nextToken());
			edges[i] = new Edge(one,two,three);
		}
		Arrays.parallelSort(edges);
		dsu s = new dsu(n);
		System.out.println(MST(s));
	}
	
	public static long MST(dsu s) {
		long ans=0;
		for (int i=0; i<m; i++) {
			if (s.Union(edges[i].from, edges[i].destination)) {
				ans = edges[i].length;
			}
		}
		return ans;
	}
	
	static class Edge implements Comparable<Edge> {
		int from;
		int destination;
		long length;
		Edge(int a , int b, long c) {
			from = a;
			destination = b;
			length = c;
		}
		
		public int compareTo(Edge o) {
			return Long.compare(length, o.length);
		}
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
}