
import java.util.*;
import java.io.*;

public class haircut {

	// http://usaco.org/index.php?page=viewproblem2&cpid=1041
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("haircut.in"));
		PrintWriter out = new PrintWriter(new FileWriter("haircut.out"));

		int n = Integer.parseInt(in.readLine());
		A[] arr = new A[n];
		HashMap<Integer, ArrayList<A>> map = new HashMap<>();
		StringTokenizer st = new StringTokenizer(in.readLine());
		long[] temp = new long[n];
		for (int i=0; i<n; i++) {
			int a = Integer.parseInt(st.nextToken());
			arr[i] = new A(i,a);
			if (map.containsKey(a)) {
				map.get(a).add(arr[i]);
			}
			else {
				ArrayList<A> c = new ArrayList<A>();
				c.add(arr[i]);
				map.put(a,c);
			}
			temp[i] = 1;
		}
		
		SegTree s = new SegTree(n);
		s.build(temp);
		
		long[] ans = new long[n];
		for (int i=1; i<n; i++) {
			ans[i] = ans[i-1];
			if (map.containsKey(i-1)) {
				ArrayList<A> c = map.get(i-1);
				for (int j=0; j<c.size(); ++j) {
					ans[i] += s.comp_seg(0, c.get(j).index);
					s.set(c.get(j).index, 0);
				}
			}
		}
		
		for (int i=0; i<n; ++i) {
			System.out.println(ans[i]);
			out.println(ans[i]);	
		}
		
		out.close();

	}
	
	static class SegTree {
		int size=1;
		long[] tree;
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new long[2*size];
			for (int i=0; i<2*size; i++) tree[i] = 0;
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
		
		public void build(long[] arr) {	build(arr, 0, 0, size); }	// arr is the orig arr
		
		public void build(long[] arr, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (lx < arr.length) tree[x] = arr[lx];
				return;
			}
			int m = (lx + rx)/2;
			build(arr, 2*x+1, lx, m);	build(arr, 2*x+2, m, rx);
			tree[x] = tree[2*x+1] + tree[2*x+2];
		}
	}
	
	static class A {
		int index; int val;
		A (int a, int b) {
			index = a; val = b;
		}
	}
}