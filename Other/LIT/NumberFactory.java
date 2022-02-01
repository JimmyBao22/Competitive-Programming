
import java.util.*;
import java.io.*;

public class NumberFactory {

	// https://lit.lhsmathcs.org/numberfactory
	
	static long mod=(long)(1e9+7);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("NumberFactory"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		SegTree s = new SegTree(n);
		A[] arr = new A[n];
		for (int i=0; i<n; i++ ) {
			st = new StringTokenizer(in.readLine());
			char a = st.nextToken().charAt(0);
			int b = Integer.parseInt(st.nextToken());
			if (a == '*') {
				arr[i] = new A(b,0);
			}
			else if (a == '+') {
				arr[i] = new A(1,b);
			}
			else if (a == '-') {
				arr[i] = new A(1,-b);
			}
		}
		
		s.build(arr);
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			char cur = st.nextToken().charAt(0);
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			if (cur == 'q') {
				A c = s.comp_seg(0, b);
				long ans = c.first*(long)(a);
				ans %= mod;
				ans += c.second;
				ans %= mod;
				if (ans<0) ans += mod;
				System.out.println(ans);
			}
			else {
				a--;
				char c = st.nextToken().charAt(0);
				if (c == '*') {
					s.set(a, new A(b,0));
				}
				else if (c == '+') {
					s.set(a,new A(1,b));
				}
				else if (c == '-') {
					s.set(a, new A(1,-b));
				}
			}
		}		
	}

	static class A {
		long first;
		long second;
		// first*x+second
		A (long a, long b) {
			first=a; second=b;
		}
		void print1() {
			System.out.println(first + " " + second); 
		}
		void print2() {
			System.out.print("(" + first + " " + second + ")"); 
		}
	}
	
	static class SegTree {
		int size=1;
		A[] tree;
		A none = new A(1, 0);
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new A[2*size];
			for (int i=0; i<2*size; i++) tree[i] = none;
		}
		
		public A mod(A one, A two) {
			long mult = one.first * two.first;
			mult%=mod;
			long add = one.second*two.first+two.second;
			add %= mod;
			return new A(mult, add);
		}
	
		// random computation on segment
		public A comp_seg(int l, int r) { return comp_seg(l, r, 0, 0, size); }
		
		public A comp_seg(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return none;	// do not intersect this segment
			if (l <= lx && rx <= r) return tree[x];	// inside whole segment
			int m = (lx + rx)/2;
			A one = comp_seg(l, r, 2*x+1, lx, m); 
			A two = comp_seg(l, r, 2*x+2, m, rx);
			return mod(one,two);
		}
		
		public void set(int i, A v) { set(i, v, 0, 0, size); }	// arr[i] = v;
		
		public void set(int i, A v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] = v; return;
			}
			int m = (lx + rx)/2;
			if (i < m) set(i, v, 2*x+1, lx, m); 	// go to left subtree
			else set(i, v, 2*x+2, m, rx);			// go to right subtree
			tree[x] = mod(tree[2*x+1], tree[2*x+2]);
		}
		
		public void build(A[] arr) {	build(arr, 0, 0, size); }	// arr is the orig arr
		
		public void build(A[] arr, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (lx < arr.length) tree[x] = arr[lx];
				return;
			}
			int m = (lx + rx)/2;
			build(arr, 2*x+1, lx, m);	build(arr, 2*x+2, m, rx);
			tree[x] = mod(tree[2*x+1], tree[2*x+2]);
		}
		
		public void print() {
			for (int i=0; i<tree.length; i++) tree[i].print2();
			System.out.println();
		}
	}
}