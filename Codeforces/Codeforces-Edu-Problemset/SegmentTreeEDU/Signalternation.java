
import java.util.*;
import java.io.*;

public class Signalternation {

	// https://codeforces.com/edu/course/2/lesson/4/4/practice/contest/274684/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Signalternation"));

		int n = Integer.parseInt(in.readLine());
		StringTokenizer st = new StringTokenizer(in.readLine());
		long[] arr = new long[n];
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		SegTree s = new SegTree(n);
		s.build(arr);
		int m = Integer.parseInt(in.readLine());
		// s.print();
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken());
			int three = Integer.parseInt(st.nextToken());
			if (one == 0) {
				s.set(two-1, three);
			}
			else {
				System.out.println(s.comp(two-1, three));
			}
		}
	}

	static class SegTree {
		
		int size;
		long[] tree;
		
		public SegTree(int n) {			
			size = 1;
			while (size < n) size *= 2;
			tree = new long[2*size];
			for (int i=0; i<2*size; i++) tree[i] = 0;
		}
				
		// use for random computation
		public long comp(int l, int r) {
			return comp(l, r, 0, 0, size);
		}
		
		public long comp(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) {		// do not intersect this segment
				return Integer.MAX_VALUE;
			}
			if (l <= lx && rx <= r) {		// inside whole segment
				return tree[x];
			}
			
			int m = (lx + rx)/2;
			long one = comp(l, r, 2*x+1, lx, m);
			long two = comp(l, r, 2*x+2, m, rx);
			if (one == Integer.MAX_VALUE) {
				return two;
			}
			else if (two == Integer.MAX_VALUE) {
				return one;
			}
			else if ((m - Math.max(lx, l))%2 == 0) {
				return one + two;
			}
			else {
				return one - two;
			}
		}
		
		// arr[i] = v;
		public void set(int i, long v) {
			set(i, v, 0, 0, size);
		}
		
		public void set(int i, long v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] = v;
				return;
			}
			
			int m = (lx + rx)/2;
			if (i < m) {		// go to left subtree
				set(i, v, 2*x+1, lx, m);
			}
			else {				// go to right subtree
				set(i, v, 2*x+2, m, rx);
			}
			
			if ((m - lx)%2 == 0) {
				tree[x] = tree[2*x+1] + tree[2*x+2];
			}
			else {
				tree[x] = tree[2*x+1] - tree[2*x+2];
			}
		}
		
		public void build(long[] arr) {		// arr is the orig arr
			build(arr, 0, 0, size);
		}
		
		public void build(long[] arr, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (lx < arr.length) {
					tree[x] = arr[lx];
				}
				return;
			}
			
			int m = (lx + rx)/2;
			build(arr, 2*x+1, lx, m);
			build(arr, 2*x+2, m, rx);
			if ((m - lx)%2 == 0) {
				tree[x] = tree[2*x+1] + tree[2*x+2];
			}
			else {
				tree[x] = tree[2*x+1] - tree[2*x+2];
			}
		}
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
		}
	}
}

/*

	Within a segment, keep track of sum with alternating signs. Then, for the final, either add or subtract
		the kids together
		
	Ex.
	Input: 
	3
	1 2 3
	5
	1 1 2
	1 1 3
	1 2 3
	0 2 1
	1 1 3
	
	Build Segment Tree
	 
	      2
	 -1       3
	1   2   3   0
	
		-1 is from 1 - 2. 2 at top is from 1 - 2 + 3 - 0
		
	for 1 to 2, we return 
		1 - 2 = -1
		
	for 1 to 3, we have have 2 parts. First, for the left side, we have
		-1. For the right side, it returns 3.
		Then, decide if we want -1 - 3 or -1 + 3. Use mod2 to do this.
			return -1 + 3 = 2
			
	and so on
	

*/
