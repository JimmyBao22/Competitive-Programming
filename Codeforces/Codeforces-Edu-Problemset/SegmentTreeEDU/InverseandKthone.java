
import java.util.*;
import java.io.*;

public class InverseandKthone {

	// https://codeforces.com/edu/course/2/lesson/5/3/practice/contest/280799/problem/B
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("InverseandKthone"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		SegTree s = new SegTree(n);
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			if (a == 1) {
				int c = Integer.parseInt(st.nextToken());
				s.modify(b, c);
			}
			else {
				System.out.println(s.comp_index(b));
			}
		}
	}
	
	static class SegTree {
		int size=1;
		boolean[] invert;		// only keeps track whether or not to invert segment
		int[] tree;				// keeps track of sum of this segment
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new int[2*size];
			invert = new boolean[2*size];
		}

		// find kth one
		public long comp_index(int k) { return comp_index(k, 0, 0, size); }
		
		public long comp_index(int k, int x, int lx, int rx) {
			if (rx - lx == 1) return lx;	// in leaf node aka bottom level
			propogate(x, lx, rx);
			int m = (lx + rx)/2;
			long result = 0;
			if (k+1 <= tree[2*x+1]) result = comp_index(k, 2*x+1, lx, m);	// go to left subtree
			else result = comp_index(k - tree[2*x+1], 2*x+2, m, rx);		// go to right subtree
			return result;
		}
		
		// change segments
		public void modify(int l, int r) { modify(l, r, 0, 0, size); }
		
		public void modify(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return;		// do not intersect this segment
			propogate(x, lx, rx);
			if (l <= lx && rx <= r) {		// inside whole segment
				invert[x] = true;
				tree[x] = (rx - lx) - tree[x];
				return;
			}
			int m = (lx + rx)/2;
			modify(l, r, 2*x+1, lx, m);	modify(l, r, 2*x+2, m, rx);
			tree[x] = tree[2*x+1] + tree[2*x+2];
		}
		
		public void propogate(int x, int lx, int rx) {
			if (rx - lx == 1) return;		// leaf
			if (invert[x]) {
				int m = (lx + rx)/2;
				if (!invert[2*x+1]) invert[2*x+1] = true;
				else invert[2*x+1] = false;						// two inversions cancel out
				if (!invert[2*x+2]) invert[2*x+2] = true;
				else invert[2*x+2] = false;
				tree[2*x+1] = (m - lx) - tree[2*x+1];
				tree[2*x+2] = (rx - m) - tree[2*x+2];
				tree[x] = tree[2*x+1] + tree[2*x+2];
				invert[x] = false;
			}
		}
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
			for (int i=0; i<invert.length; i++) System.out.print(invert[i] + " ");
			System.out.println();
		}
	}
}

/*
	similar to assignment and maximal segment & Kth one
*/