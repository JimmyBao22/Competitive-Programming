
import java.util.*;
import java.io.*;

public class AssignmenttoSegment2 {

	// https://codeforces.com/edu/course/2/lesson/5/1/practice/contest/279634/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("AssignmenttoSegment"));

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
		int size=1;
		long[] tree;
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new long[2*size];
			for (int i=0; i<2*size; i++) tree[i] = 0;
		}
		
		// use for random computation with MODIFY
		public long comp_mod(int i) { return comp_mod(i, 0, 0, size); }
		
		public long comp_mod(int i, int x, int lx, int rx) {
			if (rx - lx == 1) return tree[x];	// in leaf node aka bottom level
			propogate(x, lx, rx);
			int m = (lx + rx)/2;	long result = 0;
			if (i < m) result = comp_mod(i, 2*x+1, lx, m);	// go to left subtree
			else result = comp_mod(i, 2*x+2, m, rx);		// go to right subtree
			return result;
		}
				
		// change segments
		public void modify(int l, int r, long v) { modify(l, r, v, 0, 0, size); }
		
		public void modify(int l, int r, long v, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return;		// do not intersect this segment
			propogate(x, lx, rx);
			if (l <= lx && rx <= r) {		// inside whole segment
				tree[x] = v;
				return;
			}
			int m = (lx + rx)/2;
			modify(l, r, v, 2*x+1, lx, m);	modify(l, r, v, 2*x+2, m, rx);
		}
		
		static long NONE = (long)(1e18);	// use a value that will never be used
				// if all values can be used, use a boolean array to see if tree[x]
				// has a value stored or not
		public void propogate(int x, int lx, int rx) {
			if (rx - lx == 1) return;		// leaf
			if (tree[x] != NONE) {
				tree[2*x+1] = tree[x];	tree[2*x+2] = tree[x];
				tree[x] = NONE;
			}
		}
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
		}
	}
}

/*

	Only update at parent nodes, but when necessary always use propogation.
		Propagation spreads out higher elements so you always only keep track of the elements
		that were put in later
		
	Ex. 
	5 5
	1 0 3 3
	2 1
	1 2 4 4
	2 3
	2 4
	       0
	   0       0
	 0   0   0   0
	0 0 0 0 0 0 0 0
	
	Update 3 from indices 0 to 2 inclusive
	
	Only update to parent nodes so you don't add to every single element in last row
	
		   0
	   0       0
	 3   0   0   0
	0 0 3 0 0 0 0 0
	
	Get element at index 1. Propagate downwards so the value you get at index 1
		is the value you want
	
	       0							0
	   0       0					0       0
	 3   0   0   0		-->       0   0   0   0
	0 0 3 0 0 0 0 0				 3 3 3 0 0 0 0 0 
		
		Index 1 = 3
	
	Update 4 from indices 2 to 3 inclusive
	
	 	   0
	   0       0
	 0   4   0   0
	3 3 3 0 0 0 0 0
	
	Get element at index 3. Propagate downwards so the value you get at index 3
		is the value you want
		
		   0							0
	   0       0					0       0
	 0   4   0   0		-->       0   0   0   0
	3 3 3 0 0 0 0 0				 3 3 4 4 0 0 0 0 
		
		Index 3 = 4
		
	Get element at index 4. Propagate downwards so the value you get at index 4
		is the value you want
		
		   0							0
	   0       0					0       0
	 0   0   0   0		-->       0   0   0   0
	3 3 4 4 0 0 0 0				 3 3 4 4 0 0 0 0 

		Index 4 = 4
		
*/
