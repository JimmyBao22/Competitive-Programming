
import java.util.*;
import java.io.*;

public class Kthone {

	// https://codeforces.com/edu/course/2/lesson/4/2/practice/contest/273278/problem/B
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Kthone"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		SegTree s = new SegTree(n); 
		long[] arr = new long[n];
		st = new StringTokenizer(in.readLine());
		for(int i=0; i<n; i++) arr[i] = Long.parseLong(st.nextToken());
		
		s.build(arr);
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			if (a == 1) {
				int index = Integer.parseInt(st.nextToken());
				s.set(index);
			}
			else {
				int k = Integer.parseInt(st.nextToken());
				System.out.println(s.comp(k));
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
		public int comp(int k) {
			return comp(k, 0, 0, size);
		}
		
		public int comp(int k, int x, int lx, int rx) {
			if (rx - lx == 1) {
				return lx;
			}
			
			int m = (lx + rx)/2;
			if (k+1 <= tree[2*x+1]) {
				return comp(k, 2*x+1, lx, m);
			}
			else {
				k -= tree[2*x+1];
				return comp(k, 2*x+2, m, rx);
			}
		}
		
		// arr[i] = v;
		public void set(int i) {
			set(i, 0, 0, size);
		}
		
		public void set(int i, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] = 1^tree[x];
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
			tree[x] = tree[2*x+1] + tree[2*x+2];
		}
	}
}

/*
	find the kth one
	
	for each node, keep track of how many 1's appear in this subtree
	to compute the index, find where it is.
		
		
			   4
		   3       1
		 1   2   0   1
		1 0 1 1 0 0 1 0
		
		Let's say you want to find the third one
		
	just traverse downwards and keep track of 
		how many 1's you need
		
	see if it can be on the left. If it can, go down the left side.
		Otherwise, go down the right side, but take away how many you visited on the left
		
	In this case it will visit nodes 0 (3 left), 1 (3 left), 4 (3-1=2 left), 10 (found)

*/
