
import java.util.*;
import java.io.*;

public class NumberofMinimumsonaSegment {

	// https://codeforces.com/edu/course/2/lesson/4/1/practice/contest/273169/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("NumberofMinimumsonaSegment"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		SegTree c = new SegTree(n);
		st = new StringTokenizer(in.readLine());
		long[] arr = new long[n];
		for (int i=0; i<n; i++) {
			long b = Long.parseLong(st.nextToken());
			arr[i] = b;
		}
		c.build(arr);
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int choice = Integer.parseInt(st.nextToken());
			if (choice == 1) {
				int a = Integer.parseInt(st.nextToken());
				long b = Long.parseLong(st.nextToken());
				c.set(a, b);
			}
			else {
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				long[] cur = c.comp(a, b);
				System.out.println(cur[0] + " " + cur[1]);
			}
		}
	}
	
	static class SegTree {
		
		int size;
		long[][] tree;
		
		public SegTree(int n) {			
			size = 1;
			while (size < n) size *= 2;
			tree = new long[2*size][2];
			for (int i=0; i<2*size; i++) {tree[i][0] = 0; tree[i][1] = 0;}
		}
		
		// use for random computation
		public long[] comp(int l, int r) {
			return comp(l, r, 0, 0, size);
		}
		
		public long[] comp(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) {		// do not intersect this segment
				return new long[] {Integer.MAX_VALUE, 0};
			}
			if (l <= lx && rx <= r) {		// inside whole segment
				return new long[] {tree[x][0], tree[x][1]};
			}
			
			int m = (lx + rx)/2;
			long[] one = comp(l, r, 2*x+1, lx, m);
			long[] two = comp(l, r, 2*x+2, m, rx);
			
			if (one[0] < two[0]) {
				return one;
			}
			else if (one[0] > two[0]) {
				return two;
			}
			else {
				return new long[] {one[0], one[1] + two[1]};
			}
		}
		
		// arr[i] = v;
		public void set(int i, long v) {
			set(i, v, 0, 0, size);
		}
		
		public void set(int i, long v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x][0] = v;
				return;
			}
			
			int m = (lx + rx)/2;
			if (i < m) {		// go to left subtree
				set(i, v, 2*x+1, lx, m);
			}
			else {				// go to right subtree
				set(i, v, 2*x+2, m, rx);
			}
			
			if (tree[2*x+1][0] < tree[2*x+2][0]) {
				tree[x][0] = tree[2*x+1][0];
				tree[x][1] = tree[2*x+1][1];
			}
			else if (tree[2*x+1][0] > tree[2*x+2][0]) {
				tree[x][0] = tree[2*x+2][0];
				tree[x][1] = tree[2*x+2][1];
			}
			else {
				tree[x][0] = tree[2*x+1][0];
				tree[x][1] = tree[2*x+1][1] + tree[2*x+2][1];
			}
		}
		
		public void build(long[] arr) {		// arr is the orig arr
			build(arr, 0, 0, size);
		}
		
		public void build(long[] arr, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (lx < arr.length) {
					tree[x][0] = arr[lx];
					tree[x][1] = 1;
				}
				return;
			}
			
			int m = (lx + rx)/2;
			build(arr, 2*x+1, lx, m);
			build(arr, 2*x+2, m, rx);
			
			if (tree[2*x+1][0] < tree[2*x+2][0]) {
				tree[x][0] = tree[2*x+1][0];
				tree[x][1] = tree[2*x+1][1];
			}
			else if (tree[2*x+1][0] > tree[2*x+2][0]) {
				tree[x][0] = tree[2*x+2][0];
				tree[x][1] = tree[2*x+2][1];
			}
			else {
				tree[x][0] = tree[2*x+1][0];
				tree[x][1] = tree[2*x+1][1] + tree[2*x+2][1];
			}
		}
	}
}

/*
	
	use a 2d array. col 0 will be the minimum element, col 1 will be the count of this
	
	each node contains the minimum value in the subtree in col 0 and the amount of times 
		it occurs in col 1
		
	whenever you go into two kids of this node, update based on which one of these kids 
		has a smaller value. if they have equal values, then just add the count together

*/