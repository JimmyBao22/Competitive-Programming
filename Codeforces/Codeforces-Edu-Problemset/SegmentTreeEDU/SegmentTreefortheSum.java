import java.util.*;
import java.io.*;
 
public class SegmentTreefortheSum {
 
	// https://codeforces.com/edu/course/2/lesson/4/1/practice/contest/273169/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SegmentTreefortheSum"));
		
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
				System.out.println(c.sum(a, b));
			}
		}
	}
 
	static class SegTree {
		
		int size;
		long[] sums;
		
		public SegTree(int n) {			
			size = 1;
			while (size < n) size *= 2;
			sums = new long[2*size];
			for (int i=0; i<2*size; i++) sums[i] = 0;
		}
		
		// return arr[l] + arr[l+1] + ... + arr[r-1]
		public long sum(int l, int r) {
			return sum(l, r, 0, 0, size);
		}
		
		public long sum(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) {		// do not intersect this segment
				return 0;
			}
			if (l <= lx && rx <= r) {		// inside whole segment
				return sums[x];
			}
			
			int m = (lx + rx)/2;
			return sum(l, r, 2*x+1, lx, m) + sum(l, r, 2*x+2, m, rx);
			
		}
		
		// arr[i] = v;
		public void set(int i, long v) {
			set(i, v, 0, 0, size);
		}
		
		public void set(int i, long v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				sums[x] = v;
				return;
			}
			
			int m = (lx + rx)/2;
			if (i < m) {		// go to left subtree
				set(i, v, 2*x+1, lx, m);
			}
			else {				// go to right subtree
				set(i, v, 2*x+2, m, rx);
			}
			
			sums[x] = sums[2*x+1] + sums[2*x+2];
		}
		
		public void build(long[] arr) {		// arr is the orig arr
			build(arr, 0, 0, size);
		}
		
		public void build(long[] arr, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (lx < arr.length) {
					sums[x] = arr[lx];
				}
				return;
			}
			
			int m = (lx + rx)/2;
			build(arr, 2*x+1, lx, m);
			build(arr, 2*x+2, m, rx);
			sums[x] = sums[2*x+1] + sums[2*x+2];
		}
	}
}

/*
	build seg tree that keeps track of sum
	keep track of the sum of all the parts within this subtree


*/