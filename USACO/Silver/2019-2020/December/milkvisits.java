
import java.util.*;
import java.io.*;

public class milkvisits {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=968
	
	static int n, m, curpos;
	static ArrayList<ArrayList<Integer>> g;
	static A[] arr;
	static SegTree s;
	
		// note: This is the gold implementation. Official solution is a lot better
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("milkvisits.in"));
		PrintWriter out = new PrintWriter(new FileWriter("milkvisits.out"));
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		g = new ArrayList<>();
		arr = new A[n];
		curpos = 0;
		s = new SegTree(n+1);
		
		char[] type = in.readLine().toCharArray();
		
		for (int i=0; i<n; i++) {
			arr[i] = new A();
			g.add(new ArrayList<>());	
		}
		
		for (int i=0; i<n-1; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			g.get(one).add(two); 
			g.get(two).add(one);
		}
		
		dfs(0, 0, 0);
		hld(0, 0);
			
		ArrayList<B> typeH = new ArrayList<>();
		ArrayList<B> typeG = new ArrayList<>();
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			char c = st.nextToken().charAt(0);
			if (c == 'H') {
				typeH.add(new B(i, one, two, c));
			}
			else {
				typeG.add(new B(i, one, two, c));
			}
		}
		
		for (int i=0; i<n; i++) {
			if (type[i] == 'H') {
				s.set(arr[i].stpos, 1);
			}
		} 
		
		int[] ans = new int[m];
		
		for (B a : typeH) {
			if (query(a.from, a.to) == 1) ans[a.index] = 1;
		}
		
		for (int i=0; i<n; i++) {
			if (type[i] == 'G') {
				s.set(arr[i].stpos, 2);
			}
		}
		
		for (B a : typeG) {
			if (query(a.from, a.to) == 2) ans[a.index] = 1;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<m; i++) sb.append(ans[i]);
		
		//System.out.print(sb);
		out.print(sb);
		out.close();
	}
	
	static class B implements Comparable<B> {
		int index; int type; int from; int to;
		B (int a, int b) { 
			index = a; type = b;
		}
		B (int a, int c, int d, int e) { 
			index = a; from = c; to = d; type = e;
		}
		public int compareTo(B o) {
			return type - o.type;
		}
		void print() {
			System.out.println(index + " " + type + " " + from + " " + to);
		}
	}
	
	static long query(int a, int b) {
		long ans=0;
		while (arr[a].head != arr[b].head) {		// while part of different chains
			if (arr[arr[a].head].depth > arr[arr[b].head].depth) {
				int temp = a; a = b; b = temp;		// swap so now b greater depth
			}
			int current_head = arr[b].head;			
			ans = Math.max(ans, s.comp_seg(arr[current_head].stpos, arr[b].stpos+1));
			b = arr[current_head].parent;		// move b to parent of head so you are on a new chain
		}
		if (arr[a].depth > arr[b].depth) {
			int temp = a; a = b; b = temp;
		}
		// now a and b on same chain
		ans = Math.max(ans, s.comp_seg(arr[a].stpos, arr[b].stpos+1));
		return ans;
	}
	
	public static void hld(int node, int head) {
		arr[node].head = head;
		arr[node].stpos = curpos;
		curpos++;
		if (arr[node].heavy != -1) {	
			hld(arr[node].heavy, head);
		}
		for (int i=0; i<g.get(node).size(); i++) {
			if (g.get(node).get(i) == arr[node].parent || g.get(node).get(i) == arr[node].heavy) continue;
			hld(g.get(node).get(i), g.get(node).get(i));		// start a new chain
		}
	}
	
	public static int dfs(int node, int p, int d) {
		arr[node].size = 1;
		arr[node].parent = p;
		arr[node].depth = d;
		
		int max_subtree_size = 0;
		for (int i=0; i<g.get(node).size(); i++) {
			if (g.get(node).get(i) == p) continue;
			int cursize = dfs(g.get(node).get(i), node, d+1);
			arr[node].size += cursize;
			if (cursize > max_subtree_size) {
				max_subtree_size = cursize;
				arr[node].heavy = g.get(node).get(i);
			}
		}
		return arr[node].size;
	}
	
	static class A {
		int size;
		int heavy = -1; 	// child at other end of heavy edge from this node
		int head;			// head of heavy path that this node is in
		int stpos;			// position in segment tree
		int parent;
		int depth;
		A () { }
	}
	
	static class SegTree {
		int size=1;
		long[] tree;
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new long[2*size];
			for (int i=0; i<2*size; i++) tree[i] = 0;
		}
		
		// random computation on segment
		public long comp_seg(int l, int r) { return comp_seg(l, r, 0, 0, size); }
		
		public long comp_seg(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return 0;	// do not intersect this segment
			if (l <= lx && rx <= r) return tree[x];	// inside whole segment
			int m = (lx + rx)/2;
			long one = comp_seg(l, r, 2*x+1, lx, m); 
			long two = comp_seg(l, r, 2*x+2, m, rx);
			return Math.max(one, two);
		}
		
		public void set(int i, long v) { set(i, v, 0, 0, size); }	// arr[i] = v;
		
		public void set(int i, long v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] = v; return;
			}
			int m = (lx + rx)/2;
			if (i < m) set(i, v, 2*x+1, lx, m); 	// go to left subtree
			else set(i, v, 2*x+2, m, rx);			// go to right subtree
			tree[x] = Math.max(tree[2*x+1], tree[2*x+2]);
		}
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
		}
	}
}