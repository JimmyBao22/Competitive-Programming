
import java.util.*;
import java.io.*;

public class FilthyRichTrees {

	// https://codeforces.com/gym/102694/problem/E
	
	static int n, time;
	static ArrayList<Integer>[] g;
	static int[] start, end;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("FilthyRichTrees"));

		n = Integer.parseInt(in.readLine());
		time = 0;
		g = new ArrayList[n];
		for (int i=0; i<n; i++) g[i] = new ArrayList<>();
		for (int i=0; i<n-1; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1; 	
			int b = Integer.parseInt(st.nextToken())-1;
			g[a].add(b);
			g[b].add(a);
		}
		
		start = new int[n]; end = new int[n];
		dfs(0, -1);
		
		SegTree s = new SegTree(n+1);
		StringBuilder sb = new StringBuilder();
		int q = Integer.parseInt(in.readLine());

		for (int i=0; i<q; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int t = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken())-1; 	
			int b = Integer.parseInt(st.nextToken())-1;
			if (t == 1) {
				b++;
				double log = log(b, 2);
				s.set(start[a], log);
			}
			else {
				double one = s.comp_seg(start[a], end[a]+1);
				double two = s.comp_seg(start[b], end[b]+1);
				one -= two;
				if (one >= log((int)(1e9),2)) {
					sb.append("1000000000");
				}
				else {
					sb.append(Math.pow(2, one));
				}
				sb.append("\n");
			}
		}
		System.out.print(sb);
	}
	
	public static void dfs(int node, int p) {
		start[node] = time; time++;
		for (Integer i : g[node]) {
			if (i != p) dfs(i, node);
		}
		end[node] = time-1;
	}
	
	static class SegTree {
		int size=1;
		double[] tree;
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new double[2*size];
			for (int i=0; i<tree.length; i++) tree[i] = 0;
		}
		
		// random computation on segment (l to r-1)
		public double comp_seg(int l, int r) { return comp_seg(l, r, 0, 0, size); }
		
		public double comp_seg(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return 0;	// do not intersect this segment
			if (l <= lx && rx <= r) return tree[x];	// inside whole segment
			int m = (lx + rx)/2;
			double one = comp_seg(l, r, 2*x+1, lx, m); 
			double two = comp_seg(l, r, 2*x+2, m, rx);
			return one + two;
		}
		
		public void set(int i, double v) { set(i, v, 0, 0, size); }	// arr[i] = v;
		
		public void set(int i, double v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] = v; return;
			}
			int m = (lx + rx)/2;
			if (i < m) set(i, v, 2*x+1, lx, m); 	// go to left subtree
			else set(i, v, 2*x+2, m, rx);			// go to right subtree
			tree[x] = tree[2*x+1] + tree[2*x+2];
		}
		
		public void print() {
			System.out.println(Arrays.toString(tree));
			System.out.println();
		}
	}

	public static double log(long top, long base) {
		return (double)(Math.log(top)/(double)Math.log(base));
	}
}