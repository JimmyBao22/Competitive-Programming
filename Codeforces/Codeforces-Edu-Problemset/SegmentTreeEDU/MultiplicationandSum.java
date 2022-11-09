
import java.util.*;
import java.io.*;

public class MultiplicationandSum {

	// https://codeforces.com/edu/course/2/lesson/5/2/practice/contest/279653/problem/B
	
	static long mod = (long)(1e9+7);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MultiplicationandSum"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		SegTree s = new SegTree(n);
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			if (a == 1) {
				long d = Integer.parseInt(st.nextToken());
				s.modify(b, c, d);
			}
			else {
				System.out.println(s.comp(b, c));
			}
		}
	}
	
	static class SegTree {
		int size=1;
		long[] tree;	// only keeps track of multiplication factor
		long[] sums;	// keeps track of sum
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new long[2*size];
			sums = new long[2*size];
			Arrays.fill(tree, 1);
			build(n);
		}
		
		public long comp(int l, int r) { return comp(l, r, 0, 0, size); }
		
		public long comp(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return 0;	// do not intersect this segment
			if (l <= lx && rx <= r) return sums[x];	// inside whole segment
			int m = (lx + rx)/2;
			long one = comp(l, r, 2*x+1, lx, m); 
			long two = comp(l, r, 2*x+2, m, rx);
			return (one + two) * tree[x] % mod;
		}
				
		// change segments
		public void modify(int l, int r, long v) { modify(l, r, v, 0, 0, size); }
		
		public void modify(int l, int r, long v, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return;		// do not intersect this segment
			// propagate(x, lx, rx);
			if (l <= lx && rx <= r) {		// inside whole segment
				tree[x] *= v;
				tree[x] %= mod;
				sums[x] *= v;
				sums[x] %= mod;
				return;
			}
			int m = (lx + rx)/2;
			modify(l, r, v, 2*x+1, lx, m);	modify(l, r, v, 2*x+2, m, rx);
			sums[x] = (sums[2*x+1] + sums[2*x+2]) * tree[x] % mod;
		}
		
		public void build(int n) { build(n, 0, 0, size); }	// arr is the orig arr
		
		public void build(int n, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (lx < n) sums[x] = 1;
				return;
			}
			int m = (lx + rx)/2;
			build(n, 2*x+1, lx, m);	build(n, 2*x+2, m, rx);
			sums[x] = sums[2*x+1] + sums[2*x+2];
		}
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
			for (int i=0; i<sums.length; i++) System.out.print(sums[i] + " ");
			System.out.println();
		}
	}
}

/*
	similar to addition and minimum
*/
