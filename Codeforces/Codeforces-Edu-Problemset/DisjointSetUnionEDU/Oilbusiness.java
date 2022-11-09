
import java.util.*;
import java.io.*;

public class Oilbusiness {

	// https://codeforces.com/edu/course/2/lesson/7/2/practice/contest/289391/problem/H
	
	static int n, m;
	static long s;
	static Edge[] edges;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Oilbusiness"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken()); 	
		m = Integer.parseInt(st.nextToken()); 	
		s = Long.parseLong(st.nextToken()); 	
		edges = new Edge[m];
				
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1; 	
			int b = Integer.parseInt(st.nextToken())-1; 	
			long c = Long.parseLong(st.nextToken()); 
			edges[i] = new Edge(a,b,c, i);
		}
		Arrays.parallelSort(edges);
		dsu d = new dsu(n);
		ArrayList<Integer> ans = MST(d);
		System.out.println(ans.size());
		StringBuilder s = new StringBuilder();
		for (int i=0; i<ans.size(); i++ ) {
			s.append(ans.get(i)+1 + " ");
		}
		System.out.println(s);
	}
	
	public static ArrayList<Integer> MST(dsu d) {
		ArrayList<Integer> not_used = new ArrayList<>();
		for (int i=m-1; i>=0; i--) {
			if (!d.Union(edges[i].from, edges[i].destination)) {
				not_used.add(i);
			}
		}
		ArrayList<Integer> not_used_final = new ArrayList<>();
		for (int i=not_used.size()-1; i>=0; i--) {
			if (s - edges[not_used.get(i)].length >= 0) {
				s -= edges[not_used.get(i)].length;
				not_used_final.add(edges[not_used.get(i)].index);
			}
		}
		return not_used_final;
	}
	
	static class Edge implements Comparable<Edge> {
		int from;
		int destination;
		long length;
		int index;
		Edge(int a , int b, long c, int d) {
			from = a;
			destination = b;
			length = c;
			index = d;
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

	not_used keeps track of indices of edges that were not used, sorted with length
		in decreasing order
		
	Therefore, the specific edge is edges[not_used.get(i)]
	
	not_used_final keeps track of final answer indices. These are of the form
		edges[not_used.get(i)].index

*/
