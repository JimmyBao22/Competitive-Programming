
import java.util.*;
import java.io.*;

public class Inversions {

	// https://codeforces.com/edu/course/2/lesson/4/3/practice/contest/274545/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Inversions"));

		int n = Integer.parseInt(in.readLine());
		SegTree s = new SegTree(n+1); 
		StringTokenizer st = new StringTokenizer(in.readLine());
		int[] arr = new int[n];
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		for (int i=0; i<n; i++) {
			System.out.print(s.sum(arr[i], n+1) + " " );
			s.set(arr[i]);
		}
	}
	
	static class SegTree {
		
		int size;
		int[] tree;
		
		public void print() {
			for (int i=3; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
		}
		
		public SegTree(int n) {			
			size = 1;
			while (size < n) size *= 2;
			tree = new int[2*size];
			for (int i=0; i<2*size; i++) tree[i] = 0;
		}
				
		// return arr[l] + arr[l+1] + ... + arr[r-1]
		public int sum(int l, int r) {
			return sum(l, r, 0, 0, size);
		}
		
		public int sum(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) {		// do not intersect this segment
				return 0;
			}
			if (l <= lx && rx <= r) {		// inside whole segment
				return tree[x];
			}
			
			int m = (lx + rx)/2;
			return sum(l, r, 2*x+1, lx, m) + sum(l, r, 2*x+2, m, rx);
		}
		
		// arr[i] = v;
		public void set(int i) {
			set(i, 0, 0, size);
		}
		
		public void set(int i, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] = 1;
				return;
			}
			
			int m = (lx + rx)/2;
			if (i < m) {		// go to left subtree
				set(i, 2*x+1, lx, m);
			}
			else {				// go to right subtree
				set(i, 2*x+2, m, rx);
			}
			
			tree[x] = tree[2*x+1] + tree[2*x+2];
		}
	}
}

/*

	When you track inversions, track the nodes you have visited already and for those add in 1.
		Then it's just sum.
		
	Ex.
	Input: 5
		   4 1 3 5 2
		   
	Segtree beginning
	
		   0
	   0       0
	 0   0   0   0 
	0 0 0 0 0 0 0 0
	
	Visit 4. Calculate sum of segment from 4 to 5 inclusive
	Now that 4 is visited, update segtree. 
	
		   1
	   0       1
	 0   0   1   0 
	0 0 0 0 1 0 0 0
	
	Visit 1. Calculate sum of segment from 1 to 5 inclusive
	Now that 1 is visited, update segtree. 

		   2
	   1       1
	 1   0   1   0 
	0 1 0 0 1 0 0 0
	
	Visit 3. Calculate sum of segment from 3 to 5 inclusive
	Now that 3 is visited, update segtree. 

		   3
	   2       1
	 1   1   1   0 
	0 1 1 0 1 0 0 0
	
	and so on

*/