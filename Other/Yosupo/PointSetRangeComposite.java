
import java.util.*;
import java.io.*;

public class PointSetRangeComposite {

	// https://judge.yosupo.jp/problem/point_set_range_composite
	
	static long mod = (long)(998244353);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("PointSetRangeComposite"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		A[] arr = new A[n];
		for (int i=0; i<n; ++i) {
			st = new StringTokenizer(in.readLine());
			arr[i] = new A(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		
		SegTree s = new SegTree(n);
		s.build(arr);
		
		StringBuilder sb = new StringBuilder();
		while (q-->0) {
			st = new StringTokenizer(in.readLine());
			int type = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			if (type == 0) {
				s.set(a, new A(b,c));
			}
			else {
				A ret = s.comp_seg(a, b);
				sb.append(((ret.a * c)%mod + ret.b)%mod);
				sb.append("\n");
			}
		}
		System.out.print(sb);
	}
	
	static class A {
		long a, b;
			// ax + b
		A (long a, long b) {
			this.a = a;
			this.b = b;
		}
	}
	
	public static A update(A one, A two) {
		A cur = new A(1,0);
		cur.a = one.a * two.a;
		cur.a %= mod;
		cur.b = (one.b * two.a)%mod + two.b;
		cur.b %= mod;
		return cur;
	}
	
	static class SegTree {
		int size=1;
		A[] tree;
		A NONE = new A(1, 0);
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new A[2*size];
			for (int i=0; i<tree.length; i++) {
				tree[i] = NONE;
			}
		}
		
		// random computation on segment (l to r-1)
		public A comp_seg(int l, int r) { return comp_seg(l, r, 0, 0, size); }
		
		public A comp_seg(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return NONE;	// do not intersect this segment
			if (l <= lx && rx <= r) return tree[x];	// inside whole segment
			int m = (lx + rx)/2;
			A one = comp_seg(l, r, 2*x+1, lx, m); 
			A two = comp_seg(l, r, 2*x+2, m, rx);
			return update(one , two);
		}
		
		public void set(int i, A v) { set(i, v, 0, 0, size); }	// arr[i] = v;
		
		public void set(int i, A v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] = v; return;
			}
			int m = (lx + rx)/2;
			if (i < m) set(i, v, 2*x+1, lx, m); 	// go to left subtree
			else set(i, v, 2*x+2, m, rx);			// go to right subtree
			tree[x] = update(tree[2*x+1], tree[2*x+2]);
		}
		
		public void build(A[] arr) {	build(arr, 0, 0, size); }	// arr is the orig arr
		
		public void build(A[] arr, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (lx < arr.length) tree[x] = arr[lx];
				return;
			}
			int m = (lx + rx)/2;
			build(arr, 2*x+1, lx, m);	build(arr, 2*x+2, m, rx);
			tree[x] = update(tree[2*x+1], tree[2*x+2]);
		}
		
		public void print() {
			System.out.println(Arrays.toString(tree));
			System.out.println();
		}
	}
}