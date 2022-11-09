
import java.util.*;
import java.io.*;

public class ApplyingMAXtoSegment {
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ApplyingMAXtoSegment"));
		
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
				long d = Integer.parseInt(st.nextToken());
				s.modify(b, c, d);
			}
			else {
				System.out.println(s.comp_mod(b));
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
		
		// use for random computation with MODIFY
		public long comp_mod(int i) {
			return comp_mod(i, 0, 0, size);
		}
		
		public long comp_mod(int i, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				return tree[x];
			}
			
			int m = (lx + rx)/2;
			long result = 0;
			if (i < m) {		// go to left subtree
				result = comp_mod(i, 2*x+1, lx, m);
			}
			else {				// go to right subtree
				result = comp_mod(i, 2*x+2, m, rx);
			}
			
			return Math.max(result , tree[x]);
		}
				
		// change segments
		public void modify(int l, int r, long v) {
			modify(l, r, v, 0, 0, size);
		}
		
		public void modify(int l, int r, long v, int x, int lx, int rx) {
			if (lx >= r || rx <= l) {		// do not intersect this segment
				return;
			}
			if (l <= lx && rx <= r) {		// inside whole segment
				tree[x] = Math.max(tree[x], v);
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
		keep track of the max as you move up
		
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
	
	update 3 from indices 0 to 2 inclusive
	
	Only update to parent nodes so you don't add to every single element in last row
	
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
	
	Update 4 from indices 2 to 3 inclusive
	
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
			Then move up tree, and you reach a 4. 4>3 --> Current value is 4.
			Then move up tree, and you reach a 0. Current value is 4.
			Then move up tree, and you reach the root, a 0.  Current value is 4.
		Therefore element at index 2 = 4


*/
