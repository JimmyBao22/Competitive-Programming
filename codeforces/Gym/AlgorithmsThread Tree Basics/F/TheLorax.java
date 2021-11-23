
import java.util.*;
import java.io.*;

public class TheLorax {

	// https://codeforces.com/problemset/gymProblem/102694/F
	
	static int n, time;
	static ArrayList<Integer>[] g;
	static int[] start, end, depth;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("TheLorax"));

		int t = Integer.parseInt(in.readLine());
		while (t-->0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			int q = Integer.parseInt(st.nextToken());
			time = 0;
			g = new ArrayList[n];
			for (int i=0; i<n; i++) g[i] = new ArrayList<>();
			for (int i=0; i<n-1; i++) {
				st = new StringTokenizer(in.readLine());
				int a = Integer.parseInt(st.nextToken())-1; 	
				int b = Integer.parseInt(st.nextToken())-1;
				g[a].add(b);
				g[b].add(a);
			}
			
			start = new int[n]; end = new int[n]; depth = new int[n];
			dfs(0, -1, 0);
			
			SegTree s = new SegTree(n);
			StringBuilder sb = new StringBuilder();
			for (int i=0; i<q; i++) {
				st = new StringTokenizer(in.readLine());
				int a = Integer.parseInt(st.nextToken())-1; 	
				int b = Integer.parseInt(st.nextToken())-1;
				int x = Integer.parseInt(st.nextToken());
				if (x == 0) {
					if (depth[a] < depth[b]) {
						int temp = a; a = b; b = temp;
					}
					A ret = s.comp_seg(start[a], end[a] + 1);
					sb.append(Math.abs(ret.plants - ret.pots));
					sb.append("\n");
				}
				else {
					s.set(start[a], x, 0);
					s.set(start[b], 0, x);
				}
			}
			System.out.print(sb);
		}
	}
	
	static class SegTree {
		int size=1;
		A[] tree;
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new A[2*size];
			for (int i=0; i<tree.length; i++) tree[i] = new A();
		}
		
		// random computation on segment (l to r-1)
		public A comp_seg(int l, int r) { return comp_seg(l, r, 0, 0, size); }
		
		public A comp_seg(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return new A();	// do not intersect this segment
			if (l <= lx && rx <= r) return tree[x];	// inside whole segment
			int m = (lx + rx)/2;
			A one = comp_seg(l, r, 2*x+1, lx, m); 
			A two = comp_seg(l, r, 2*x+2, m, rx);
			A cur = new A();
			cur.plants = one.plants + two.plants; cur.pots = one.pots + two.pots;
			return cur;
		}
		
		public void set(int i, long v1, long v2) { set(i, v1, v2, 0, 0, size); }	// arr[i] = v;
		
		public void set(int i, long v1, long v2, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x].plants += v1; tree[x].pots += v2; return;
			}
			int m = (lx + rx)/2;
			if (i < m) set(i, v1, v2, 2*x+1, lx, m); 	// go to left subtree
			else set(i, v1, v2, 2*x+2, m, rx);			// go to right subtree
			tree[x].plants = tree[2*x+1].plants + tree[2*x+2].plants;
			tree[x].pots = tree[2*x+1].pots + tree[2*x+2].pots;
		}
		
		public void print() {
			System.out.println(Arrays.toString(tree));
			System.out.println();
		}
	}
	
	static class A {
		long plants=0; long pots=0;
	}
	
	public static void dfs(int node, int p, int d) {
		start[node] = time; time++; depth[node] = d;
		for (Integer i : g[node]) {
			if (i != p) dfs(i, node, d+1);
		}
		end[node] = time-1;
	}
}