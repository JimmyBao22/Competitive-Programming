import java.util.*;
import java.io.*;

public class XeniaandBitOperations {

	// https://codeforces.com/problemset/problem/339/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("XeniaandBitOperations"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int N = 1 << n;
		long[] arr = new long[N];
		st = new StringTokenizer(in.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		SegTree segTree = new SegTree(N);
		segTree.build(arr, n);
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			long b = Long.parseLong(st.nextToken());
			segTree.set(a, b, n);
			sb.append(segTree.comp_seg(0, N, n));
			sb.append("\n");
		}
		System.out.println(sb);
	}
	
	static class SegTree {
		int size=1;
		long[] tree;
		
		public SegTree(int n) {			
			while (size < n) size <<= 1;
			tree = new long[2*size];
		}
		
		// random computation on segment (l to r-1)
		public long comp_seg(int l, int r, int depth) { return comp_seg(l, r, 0, 0, size, depth); }
		
		public long comp_seg(int l, int r, int x, int lx, int rx, int depth) {
			if (lx >= r || rx <= l) return 0;	// do not intersect this segment
			if (l <= lx && rx <= r) return tree[x];	// inside whole segment
			int m = (lx + rx) >> 1;
			long one = comp_seg(l, r, 2*x+1, lx, m, depth-1); 
			long two = comp_seg(l, r, 2*x+2, m, rx, depth-1);
			if (depth % 2 == 1) {
				return one | two;
			}
			else {
				return one ^ two;
			}
		}
		
		public void set(int i, long v, int depth) { set(i, v, 0, 0, size, depth); }	// arr[i] = v;
		
		public void set(int i, long v, int x, int lx, int rx, int depth) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] = v; return;
			}
			int m = (lx + rx) >> 1;
			if (i < m) set(i, v, 2*x+1, lx, m, depth-1); 	// go to left subtree
			else set(i, v, 2*x+2, m, rx, depth-1);			// go to right subtree
			if (depth % 2 == 1) {
				tree[x] = tree[2*x+1] | tree[2*x+2];
			}
			else {
				tree[x] = tree[2*x+1] ^ tree[2*x+2];
			}
		}		
	
		
		public void build(long[] arr, int depth) {	build(arr, 0, 0, size, depth); }	// arr is the orig arr
		
		public void build(long[] arr, int x, int lx, int rx, int depth) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (lx < arr.length) tree[x] = arr[lx];
				return;
			}
			int m = (lx + rx) >> 1;
			build(arr, 2*x+1, lx, m, depth-1);	build(arr, 2*x+2, m, rx, depth-1);
			if (depth % 2 == 1) {
				tree[x] = tree[2*x+1] | tree[2*x+2];
			}
			else {
				tree[x] = tree[2*x+1] ^ tree[2*x+2];
			}
		}
		
		public void print() {
			System.out.println(Arrays.toString(tree));
			System.out.println();
		}
	}
}
