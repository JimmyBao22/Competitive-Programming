
import java.util.*;
import java.io.*;

public class AdditiontoSegment2 {

	// https://codeforces.com/edu/course/2/lesson/5/1/practice/contest/279634/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("AdditiontoSegment2"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		SegTree s = new SegTree(n);
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken());
			if (one == 1) {
				int three = Integer.parseInt(st.nextToken());
				long v = Integer.parseInt(st.nextToken());
				s.modify(two, three, v);
			}
			else {
				System.out.println(s.comp(two, two+1));
			}
			//s.print();
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
				return 0;
			}
			if (l <= lx && rx <= r) {		// inside whole segment
				return tree[x];
			}
			
			int m = (lx + rx)/2;
			long one = comp(l, r, 2*x+1, lx, m);
			long two = comp(l, r, 2*x+2, m, rx);
			return one + two + tree[x];
		}
		
		// change whole segments
		public void modify(int l, int r, long v) {
			modify(l, r, v, 0, 0, size);
		}
		
		public void modify(int l, int r, long v, int x, int lx, int rx) {
			if (lx >= r || rx <= l) {		// do not intersect this segment
				return;
			}
			if (l <= lx && rx <= r) {		// inside whole segment
				tree[x] += v;
				return;
			}
			
			int m = (lx + rx)/2;
			modify(l, r, v, 2*x+1, lx, m);
			modify(l, r, v, 2*x+2, m, rx);
		}
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
		}
		
	}	
}

/*

	Change the value only at parent nodes. Then, when you want some lower node, 
		keep track of the sum as you move up
		
	Ex. 
	5 6
	1 0 3 3
	2 1
	1 2 4 4
	2 3
	2 4
	2 2
	       0
	   0       0
	 0   0   0   0
	0 0 0 0 0 0 0 0
	
	Add 3 from indices 0 to 2 inclusive
	
	Only add to parent nodes so you don't add to every single element in last row
	
		   0
	   0       0
	 3   0   0   0
	0 0 3 0 0 0 0 0
	
	Get element at index 1
		Start from index one on tree and move up. Current value is 0.
			Then move up tree, and you reach a 3.  Current value is 3.
			Then move up tree, and you reach a 0.  Current value is 3.
			Then move up tree, and you reach the root, a 0.  Current value is 3.
		Therefore element at index 1 = 3
	
	Add 4 from indices 2 to 3 inclusive
	
	 	   0
	   0       0
	 3   4   0   0
	0 0 3 0 0 0 0 0
	
	Get element at index 3
		Start from index one on tree and move up. Current value is 0.
			Then move up tree, and you reach a 4.  Current value is 4.
			Then move up tree, and you reach a 0.  Current value is 4.
			Then move up tree, and you reach the root, a 0.  Current value is 4.
		Therefore element at index 3 = 4
		
	Get element at index 4
		Start from index one on tree and move up. Current value is 0.
			Then move up tree, and you reach a 0.  Current value is 0.
			Then move up tree, and you reach a 0.  Current value is 0.
			Then move up tree, and you reach the root, a 0.  Current value is 0.
		Therefore element at index 4 = 0

	Get element at index 2
		Start from index one on tree and move up. Current value is 3.
			Then move up tree, and you reach a 4. Current value is 4+3=7.
			Then move up tree, and you reach a 0. Current value is 7.
			Then move up tree, and you reach the root, a 0.  Current value is 7.
		Therefore element at index 2 = 7
*/
