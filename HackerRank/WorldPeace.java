
import java.util.*;
import java.io.*;

public class WorldPeace {

	// https://www.hackerrank.com/contests/ioi-2014-practice-contest-2/challenges/world-peace-ioi14/problem
	
	static int[][] pairs;
	static Edge[] edges;
	static int r,c,p;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("WorldPeace.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());

		edges = new Edge[c];
		
		for (int i=0; i<c; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken());
			edges[i] = new Edge(one,two,c);
		}
		
		p = Integer.parseInt(in.readLine());
		pairs = new int[p][2];
		
		for (int i=0; i<p; i++) {
			st = new StringTokenizer(in.readLine());
			pairs[i][0] = Integer.parseInt(st.nextToken())-1;
			pairs[i][1] = Integer.parseInt(st.nextToken())-1;
		}
		
		Arrays.parallelSort(edges);
		
		int min=0;
		int max=(int)(1e7);
		while (min<max) {
			int middle = (min+max)/2;
			if (check(middle)) {
				max = middle;
			}
			else min = middle+1;
		}
		
		if (max > (int)(1e6)) {
			System.out.println("MISSION IMPOSSIBLE");
		}
		else {
			System.out.println(min);
		}
	}
	
	public static boolean check(int mid) {
		dsu s = new dsu(r);
		for (int i=0; i<c; i++) {
			if (edges[i].val>mid) break;
			s.Union(edges[i].from, edges[i].to);
		}
		for (int i=0; i<p; i++) {
			if (s.FindSet(pairs[i][0]) != s.FindSet(pairs[i][1])) return false;
		}
		return true;
	}
	
	static class Edge implements Comparable<Edge> {
		int from; int to; int val;
		Edge (int a, int b, int c) {
			from = a; to = b; val = c;
		}
		public int compareTo(Edge o) {
			return val-o.val;
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
			if (a == b) { 	// cycle found
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
World Peace
Binary search on answer
	Then simulate to check
		connect all trees with union find, 
		then loop through all pairs to see if each pair has same parent (same tree)

*/