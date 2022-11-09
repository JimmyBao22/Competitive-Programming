
import java.util.*;
import java.io.*;

public class AssignmentandSum {

	// https://codeforces.com/edu/course/2/lesson/5/2/practice/contest/279653/problem/F
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("AssignmentandSum"));

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
		long[] tree;	// only keeps track of set values
		long[] sum;		// keeps track of sum
		long NONE = (long)(1e18);		// value that will never be assigned
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new long[2*size];
			sum = new long[2*size];
			Arrays.fill(sum, 0);
			Arrays.fill(tree, NONE);
		}
		
		public long comp(int l, int r) { return comp(l, r, 0, 0, size); }
		
		public long comp(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return 0;	// do not intersect this segment
			propogate(x, lx, rx);
			if (l <= lx && rx <= r) return sum[x];	// inside whole segment
			int m = (lx + rx)/2;			
			long one = comp(l, r, 2*x+1, lx, m); 
			long two = comp(l, r, 2*x+2, m, rx);
			return one + two;
		}

		// change segments
		public void modify(int l, int r, long v) { modify(l, r, v, 0, 0, size); }
		
		public void modify(int l, int r, long v, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return;		// do not intersect this segment
			propogate(x, lx, rx);
			if (l <= lx && rx <= r) {		// inside whole segment
				tree[x] = v;
				sum[x] = v * (rx - lx);
				return;
			}
			int m = (lx + rx)/2;
			modify(l, r, v, 2*x+1, lx, m);	modify(l, r, v, 2*x+2, m, rx);
			sum[x] = sum[2*x+1] + sum[2*x+2];
		}
		
		public void propogate(int x, int lx, int rx) {
			if (rx - lx == 1) return;		// leaf
			if (tree[x] != NONE) {
				int m = (lx + rx)/2;
				tree[2*x+1] = tree[x];
				sum[2*x+1] = tree[x] * (m - lx);
				tree[2*x+2] = tree[x];
				sum[2*x+2] = tree[x] * (rx - m);
				sum[x] = sum[2*x+1] + sum[2*x+2];
				tree[x] = NONE;
			}
		}
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
			for (int i=0; i<sum.length; i++) System.out.print(sum[i] + " ");
			System.out.println();
		}
	}
}

/*
	similar to addition and sum 2
*/
