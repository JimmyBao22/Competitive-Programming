import java.util.*;
import java.io.*;
 
public class Present {
 
	// https://codeforces.com/contest/460/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("three"));
 
		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long m = Integer.parseInt(st.nextToken());
		long w = Integer.parseInt(st.nextToken());
		long[] arr = new long[n];
		st = new StringTokenizer(in.readLine());
		long min=(long)(1e14);
		long max = 0;
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			min = Math.min(min, arr[i]);
			max = Math.max(max, arr[i] + m);
		}
 
		while (min<max) {
			long middle = min + (max-min+1)/2;
			if (check(n, m, w, arr, middle)) {
				min = middle;
			}
			else max = middle-1;
		}
		
		System.out.println(min);
	}
	
	public static boolean check(int n, long m, long w, long[] arr, long mid) {
		long[] needed = new long[n];
		for (int i=0; i<n; i++) needed[i] = Math.max(0, mid - arr[i]);
		
		SegTree s = new SegTree(n);
		s.build(needed);
 
		long ans=0;
		for (int i=0; i<n; i++) {
			long val = s.comp_index(i);
			if (val <= 0) continue;
			ans += val;
			s.modify(i, (int)Math.min(n, i+w), -val);	// just added val from i to min(n, i+w) 
		}
		
		return ans <= m;
	}
 
	static class SegTree {
		int size=1;
		long[] tree;
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new long[2*size];
		}
		
		// random computation on index
		public long comp_index(int i) { return comp_index(i, 0, 0, size); }
		
		public long comp_index(int i, int x, int lx, int rx) {
			if (rx - lx == 1) return tree[x];	// in leaf node aka bottom level
			propogate(x, lx, rx);
			int m = (lx + rx)/2;	long result = 0;
			if (i < m) result = comp_index(i, 2*x+1, lx, m);	// go to left subtree
			else result = comp_index(i, 2*x+2, m, rx);		// go to right subtree
			return result + tree[x];
		}
				
		// change segments (l to r-1)
		public void modify(int l, int r, long v) { modify(l, r, v, 0, 0, size); }
		
		public void modify(int l, int r, long v, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return;		// do not intersect this segment
			propogate(x, lx, rx);
			if (l <= lx && rx <= r) {		// inside whole segment
				tree[x] += v;
				return;
			}
			int m = (lx + rx)/2;
			modify(l, r, v, 2*x+1, lx, m);	modify(l, r, v, 2*x+2, m, rx);
		}
		
		static long NONE = 0;
		
		public void propogate(int x, int lx, int rx) {
			if (rx - lx == 1) return;		// leaf
			if (tree[x] != NONE) {
				tree[2*x+1] += tree[x];	tree[2*x+2] += tree[x];
				tree[x] = NONE;
			}
		}
		
		public void build(long[] arr) {	build(arr, 0, 0, size); }	// arr is the orig arr
		
		public void build(long[] arr, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (lx < arr.length) tree[x] = arr[lx];
				return;
			}
			int m = (lx + rx)/2;
			build(arr, 2*x+1, lx, m);	build(arr, 2*x+2, m, rx);
		}
		
		public void print() {
			System.out.println(Arrays.toString(tree));
			System.out.println();
		}
	}
}