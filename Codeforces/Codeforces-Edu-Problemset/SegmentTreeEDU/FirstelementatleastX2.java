
import java.util.*;
import java.io.*;

public class FirstelementatleastX2 {

	// https://codeforces.com/edu/course/2/lesson/4/2/practice/contest/273278/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("FirstelementatleastX2"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		SegTree s = new SegTree(n); 
		long[] arr = new long[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		s.build(arr);
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			if (one == 1) {
				int two = Integer.parseInt(st.nextToken());
				long t = Integer.parseInt(st.nextToken());
				s.set(two, t);
			}
			else {
				long two = Integer.parseInt(st.nextToken());
				int t = Integer.parseInt(st.nextToken());
				System.out.println(s.comp(two, t));
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
		public int comp(long y, int l) {
			return comp(y, l, 0, 0, size);
		}
		
		public int comp(long y, int l, int x, int lx, int rx) {
			if (rx <= l) return -1;
			if (tree[x] < y) return -1;
			if (rx - lx == 1) {		
				if (tree[x] >= y) {
					return lx;
				}
				else return -1;
			}
			
			int m = (lx + rx)/2;
			int one = comp(y, l, 2*x+1, lx, m);
			if (one == -1) return comp(y,l, 2*x+2, m, rx);
			return one;
		}
		
		// arr[i] = v;
		public void set(int i, long v) {
			set(i, v, 0, 0, size);
		}
		
		public void set(int i, long v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] = v;
				return;
			}
			
			int m = (lx + rx)/2;
			if (i < m) {		// go to left subtree
				set(i, v, 2*x+1, lx, m);
			}
			else {				// go to right subtree
				set(i, v, 2*x+2, m, rx);
			}
			
			tree[x] = Math.max(tree[2*x+1],tree[2*x+2]);
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
			tree[x] = Math.max(tree[2*x+1],tree[2*x+2]);
		}
	}
}

//keep track of max. This way, if max >= x, then it works in this segment. otherwise
	// it doens't work in this segment
	// further, when you check a node, if it is out of bounds immediately can exit