
import java.util.*;
import java.io.*;

public class Densespanningtree {

	// https://codeforces.com/edu/course/2/lesson/7/2/practice/contest/289391/problem/F
	
	static int n,m;
	static Edge[] edges;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Densespanningtree"));

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
		long ans = MST();
		if (ans == (long)(1e18)) {
			System.out.println("NO");
		}
		else {
			System.out.println("YES \n" + ans);
		}
	}
	
	public static long MST() {
		long ans=(long)(1e18);
		for (int start=0; start<m; start++) {
			if (start!=0 && edges[start].length == edges[start-1].length) continue;
			dsu s = new dsu(n);
			int num_edges=0;
			for (int i=start; i<m && edges[i].length - edges[start].length < ans; i++) {
				if (s.Union(edges[i].from, edges[i].destination)) {
					num_edges++;
				}
				if (num_edges == n-1) {
					ans = Math.min(ans, edges[i].length - edges[start].length);
					break;
				}
			}
			if (ans == (long)(1e18)) return ans;
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

/*

	Start with:
		for (int start=0; start<m; start++) 
		
			edges[start].length is the minimum length in this case. You want to run MST
				here to find the minimum answer such that a tree is built
					
			If ans == (long)(1e18) after the first cycle is run,
				that means a tree was never found.
				
			for every future start, run MST to find the closest it can go and still have a tree,
				and if any time you find that edges[i].length - edges[start].length >= ans,
					then there is no point continuing this iteration because it will
					never improve the answer
				
			In total, you are building a tree, so there are n nodes and n-1 edges.
				once num_edges == n-1, a tree has been built.
				

*/