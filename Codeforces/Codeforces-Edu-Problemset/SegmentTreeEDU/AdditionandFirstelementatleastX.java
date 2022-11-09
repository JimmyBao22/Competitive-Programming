
import java.util.*;
import java.io.*;

public class AdditionandFirstelementatleastX {

	// https://codeforces.com/edu/course/2/lesson/5/3/practice/contest/280799/problem/C
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("AdditionandFirstelementatleastX"));

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
				System.out.println(s.comp_index(b, c));
			}
		}
	}
	
	static class SegTree {
		int size=1;
		long[] added;				// only keeps track of added values
		long[] tree;				// keeps track of max of this segment
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new long[2*size];
			added = new long[2*size];
		}

		// find min index
		public long comp_index(long val, int i) { return comp_index(val, i, 0, 0, size); }
		
		public long comp_index(long val, int i, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (val <= tree[x]) return lx;
				return -1;
			}
			propogate(x, lx, rx);
			int m = (lx + rx)/2;
			long result = -1;
			if (m-1 >= i && val <= tree[2*x+1]) result = comp_index(val, i, 2*x+1, lx, m);	// go to left subtree
			if (result == -1) result = comp_index(val, i, 2*x+2, m, rx);						// go to right subtree
			return result;
		}
		
		// change segments
		public void modify(int l, int r, long v) { modify(l, r, v, 0, 0, size); }
		
		public void modify(int l, int r, long v, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return;		// do not intersect this segment
			propogate(x, lx, rx);
			if (l <= lx && rx <= r) {		// inside whole segment
				tree[x] += v;
				added[x] += v;
				return;
			}
			int m = (lx + rx)/2;
			modify(l, r, v, 2*x+1, lx, m);	modify(l, r, v, 2*x+2, m, rx);
			tree[x] = Math.max(tree[2*x+1], tree[2*x+2]);
		}
		
		static long NONE = 0;
		public void propogate(int x, int lx, int rx) {
			if (rx - lx == 1) return;		// leaf
			if (added[x] != NONE) {
				added[2*x+1] += added[x];
				added[2*x+2] += added[x];
				tree[2*x+1] += added[x];
				tree[2*x+2] += added[x];
				added[x] = NONE;
			}
		}
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
			for (int i=0; i<added.length; i++) System.out.print(added[i] + " ");
			System.out.println();
		}
	}
}

/*
	similar to assignment and maximal segment & First element at least X
*/
