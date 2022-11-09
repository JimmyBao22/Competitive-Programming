
import java.util.*;
import java.io.*;

public class BitwiseORandAND {

	// https://codeforces.com/edu/course/2/lesson/5/2/practice/contest/279653/problem/C
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("BitwiseORandAND"));

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
		long[] tree;	// only keeps track of OR factor
		long[] sums;	// keeps track of AND
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new long[2*size];
			sums = new long[2*size];
		}
		
		public long comp(int l, int r) { return comp(l, r, 0, 0, size); }
		
		public long comp(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return (1 << 30)-1;	// do not intersect this segment (need 2^30-1) so that AND works
			if (l <= lx && rx <= r) return sums[x];	// inside whole segment
			int m = (lx + rx)/2;
			long one = comp(l, r, 2*x+1, lx, m); 
			long two = comp(l, r, 2*x+2, m, rx);
			return (one & two) | tree[x];
		}
				
		// change segments
		public void modify(int l, int r, long v) { modify(l, r, v, 0, 0, size); }
		
		public void modify(int l, int r, long v, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return;		// do not intersect this segment
			// propagate(x, lx, rx);
			if (l <= lx && rx <= r) {		// inside whole segment
				tree[x] |= v;
				sums[x] |= v;
				return;
			}
			int m = (lx + rx)/2;
			modify(l, r, v, 2*x+1, lx, m);	modify(l, r, v, 2*x+2, m, rx);
			sums[x] = (sums[2*x+1] & sums[2*x+2]) | tree[x];
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
