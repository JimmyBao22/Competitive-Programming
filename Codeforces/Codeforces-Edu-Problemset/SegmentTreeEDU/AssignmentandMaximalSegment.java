
import java.util.*;
import java.io.*;

public class AssignmentandMaximalSegment {

	// https://codeforces.com/edu/course/2/lesson/5/3/practice/contest/280799/problem/A
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("AssignmentandMaximalSegment"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		SegTree s = new SegTree(n);
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			s.modify(a, b, c);
			System.out.println(s.tree[0].sub);
		}
	}
	
	static class A {
		long sum;
		long pref;
		long suf;
		long sub;
		A(long a, long b, long c, long d) {
			sum = a;
			pref = b;
			suf = c;
			sub = d;
		}
	}
	
	public static A combine(A one, A two) {
		long sum = one.sum + two.sum;
		long pref = Math.max(one.pref, one.sum + two.pref);
		long suf = Math.max(two.suf, two.sum + one.suf);
		long sub = Math.max(one.sub, Math.max(two.sub, one.suf + two.pref));
		return new A(sum, pref, suf, sub);
	}
	
	static class SegTree {
		int size=1;
		long[] set;		// only keeps track of set values
		A[] tree;		// keeps track of A
		long NONE = (long)(1e18);
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new A[2*size];
			set = new long[2*size];
			Arrays.fill(tree, new A(0,0,0,0));
			Arrays.fill(set, NONE);
		}

		// change segments
		public void modify(int l, int r, long v) { modify(l, r, v, 0, 0, size); }
		
		public void modify(int l, int r, long v, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return;		// do not intersect this segment
			propogate(x, lx, rx);
			if (l <= lx && rx <= r) {		// inside whole segment
				if (v > 0) tree[x] = new A(v * (rx - lx), v * (rx - lx), v * (rx - lx), v * (rx - lx));
				else tree[x] = new A(v * (rx - lx), 0,0,0);
				set[x] = v;
				return;
			}
			int m = (lx + rx)/2;
			modify(l, r, v, 2*x+1, lx, m);	modify(l, r, v, 2*x+2, m, rx);
			tree[x] = combine(tree[2*x+1], tree[2*x+2]);
		}
		
		public void propogate(int x, int lx, int rx) {
			if (rx - lx == 1) return;		// leaf
			if (set[x] != NONE) {
				int m = (lx + rx)/2;
				set[2*x+1] = set[x];
				set[2*x+2] = set[x];
				if (set[x] > 0) tree[2*x+1] = new A(set[x] * (m - lx), set[x] * (m - lx), set[x] * (m - lx), set[x] * (m - lx));
				else tree[2*x+1] = new A(set[x] * (m - lx), 0,0,0);
				if (set[x] > 0) tree[2*x+2] = new A(set[x] * (rx - m), set[x] * (rx - m), set[x] * (rx - m), set[x] * (rx - m));
				else tree[2*x+2] = new A(set[x] * (rx - m), 0,0,0);
				set[x] = NONE;
			}
		}
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
			for (int i=0; i<set.length; i++) System.out.print(set[i] + " ");
			System.out.println();
		}
	}
}

/*
	similar to addition and sum 2 & Segment with the Maximum Sum
*/