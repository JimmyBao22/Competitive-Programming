
import java.util.*;
import java.io.*;

public class bphoto2 {

	// http://usaco.org/index.php?page=viewproblem2&cpid=693
	
	static int n;
	static A[] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("bphoto.in"));
		PrintWriter out = new PrintWriter(new FileWriter("bphoto.out"));

		int n = Integer.parseInt(in.readLine());
		arr = new A[n];
		for (int i=0; i<n; i++) {
			arr[i] = new A(Integer.parseInt(in.readLine()), i);
		}
		Arrays.parallelSort(arr);
		
		int ans=0;
		SegTree s = new SegTree(n);
		for (int i=n-1; i>=0; i--) {
			int curindex = arr[i].index;
			int left = (int) s.comp_seg(0, curindex);
			int right = (int)s.comp_seg(curindex+1, n);
			if (left*2 < right || right*2 < left) ans++;
			s.set(curindex, 1);
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	static class SegTree {
		int size=1;
		long[] tree;
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new long[2*size];
		}
		
		// random computation on segment
		public long comp_seg(int l, int r) { return comp_seg(l, r, 0, 0, size); }
		
		public long comp_seg(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return 0;	// do not intersect this segment
			if (l <= lx && rx <= r) return tree[x];	// inside whole segment
			int m = (lx + rx)/2;
			long one = comp_seg(l, r, 2*x+1, lx, m); 
			long two = comp_seg(l, r, 2*x+2, m, rx);
			return one + two;
		}
		
		public void set(int i, long v) { set(i, v, 0, 0, size); }	// arr[i] = v;
		
		public void set(int i, long v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] = v; return;
			}
			int m = (lx + rx)/2;
			if (i < m) set(i, v, 2*x+1, lx, m); 	// go to left subtree
			else set(i, v, 2*x+2, m, rx);			// go to right subtree
			tree[x] = tree[2*x+1] + tree[2*x+2];
		}
	}
	
	static class A implements Comparable<A> {
		int val; int index;
		A(int a, int b) {
			val=a; index=b;
		}
		public int compareTo(A o) {
			return val-o.val;
		}
	}
}