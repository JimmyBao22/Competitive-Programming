
import java.util.*;
import java.io.*;

public class fcolor {

	// http://usaco.org/index.php?page=viewproblem2&cpid=1042
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("fcolor.in"));
		PrintWriter out = new PrintWriter(new FileWriter("fcolor.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		dsu s = new dsu(n);
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;

			s.edges[one].add(two);
		}
		
		ArrayDeque<Integer> d = new ArrayDeque<>();
		for (int i=0; i<n; i++) if (s.edges[i].size() > 1) d.add(i);
		
		while (!d.isEmpty()) {
			int i = d.poll();
			int parent = s.FindSet(i);
			if (s.edges[parent].size() > 1) {

				// combine everything this points to
				int prev=-1;
				for (int j=0; j<s.edges[parent].size(); j++) {
					int a = s.edges[parent].get(j);
					if (prev == -1) {
						prev = a;
						continue;
					}
					s.Union(a, prev);
				}

				s.edges[parent].clear();
				int parent2 = s.FindSet(prev);
				s.edges[parent].add(parent2);
				d.add(parent2);
			}
		}
		
		ArrayList<ArrayList<Integer>> f = new ArrayList<>();
		HashMap<Integer, Integer> map = new HashMap<>();
			// points parent to index of arraylist in f
		
		int p=0;
		for (int i=0; i<n; i++) {
			int par = s.FindSet(i);
			if (!map.containsKey(par)) {
				map.put(par, p);
				p++;
				f.add(new ArrayList<>());
			}
			
			f.get(map.get(par)).add(i);			// put each element in arraylist with its parent
		}
		
		int[] ans = new int[n];
		int color=1;
		for (int i=0; i<f.size(); i++ ) {
			for (int j=0; j<f.get(i).size(); j++) {
				ans[f.get(i).get(j)] = color;
			}
			color++;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<n; i++) {
			sb.append(ans[i]);
			sb.append("\n");
		}
		
		System.out.print(sb);
		out.print(sb);
		out.close();

	}
	
	static class A implements Comparable<A> {
		int index; int val;
		A (int a, int b) {
			index = a; val = b;
		}
		public int compareTo(A o) {
			return val - o.val;
		}
	}
	
	static class dsu {
		int n;
		int[] parent;
		ArrayList<Integer>[] edges;
		
		dsu (int n) {
			this.n = n;
			parent = new int[n];
			edges = new ArrayList[n];
			for (int i=0; i<n; i++) {parent[i] = i; edges[i] = new ArrayList<>();}
		}

		public int FindSet(int a) {
			if (a == parent[a]) return a;
			return parent[a] = FindSet(parent[a]);
		}
		
		public void Union(int a, int b) {
			a = FindSet(a);
			b = FindSet(b);
			if (a == b) return;
			
			if (edges[a].size() < edges[b].size()) {
				parent[a] = b;
				for (Integer c : edges[a]) {
					edges[b].add(c);
				}
			}
			else {
				parent[b] = a;
				for (Integer c : edges[b]) {
					edges[a].add(c);
				}
			}
		}
		
		void print(int j) {
			for (Integer i : edges[j]) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}
}