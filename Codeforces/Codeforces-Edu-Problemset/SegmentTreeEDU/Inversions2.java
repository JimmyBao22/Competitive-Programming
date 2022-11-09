
import java.util.*;
import java.io.*;

public class Inversions2 {

	// https://codeforces.com/edu/course/2/lesson/4/3/practice/contest/274545/problem/B
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Inversions2"));

		int n = Integer.parseInt(in.readLine());
		SegTree s = new SegTree(n+1); 
		StringTokenizer st = new StringTokenizer(in.readLine());
		int[] arr = new int[n];
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		s.build(arr);
		
		int[] ans = new int[n];
		for (int i=n-1; i>=0; i--) {
			int ret = s.comp(arr[i]);
			ans[i] = ret;
			s.set(ret);
		}
		
		for (int i=0; i<n; i++) System.out.print(ans[i] + " ");
		
	}
	
	static class SegTree {
		
		int size;
		int[] tree;
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
		}
		
		public SegTree(int n) {			
			size = 1;
			while (size < n) size *= 2;
			tree = new int[2*size];
			for (int i=0; i<2*size; i++) tree[i] = 0;
		}
				
		public int comp(int i) {
			return comp(i, 0, 0, size);
		}
		
		public int comp(int i, int x, int lx, int rx) {
			if (rx - lx == 1) return lx;
			int m = (lx + rx)/2;
			if (tree[2*x + 2] >= i+1) {
				return comp(i, 2*x+2, m, rx);
			}
			else {
				i -= tree[2*x+2];
				return comp(i, 2*x+1, lx, m);
			}
		}
		
		// arr[i] = v;
		public void set(int i) {
			set(i, 0, 0, size);
		}
		
		public void set(int i, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] = 0;
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
		
		public void build(int[] arr) {		// arr is the orig arr
			build(arr, 0, 0, size);
		}
		
		public void build(int[] arr, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (lx >= 1 && lx <= arr.length) {
					tree[x] = 1;
				}
				return;
			}
			
			int m = (lx + rx)/2;
			build(arr, 2*x+1, lx, m);
			build(arr, 2*x+2, m, rx);
			tree[x] = tree[2*x+1] + tree[2*x+2];
		}
	}
}

/*

	Do opposite of inversion aka work backwards
	
	Ex.
	Input: 5
		   0 1 1 0 3
		   
	In the beginning all possible nodes are set to 1. Then, moving right to left, 
		as you pass something you can find which one it is because its sum 
		will be equal to the curnum
	
	Segtree beginning
		   5
	   3       2
	 1   2   2   0 
	0 1 1 1 1 1 0 0
	
	Find which one has sum 3 for stuff strictly on its right
		This comes out to be number 2, because the stuff strictly on its right
			has sum 3
			
	Now that 2 has been 'unvisited', change it to 0
	
		   4
	   2       2
	 1   1   2   0 
	0 1 0 1 1 1 0 0

	Now Find which one has sum 0 for stuff strictly on its right
		This comes out to be number 5, because the stuff strictly on its right
			has sum 0
		
	Now that 5 has been 'unvisited', change it to 0
	
		   3
	   2       1
	 1   1   1   0 
	0 1 0 1 1 0 0 0
	
	And so on...

*/