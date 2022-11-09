
import java.util.*;
import java.io.*;

public class SegmentwiththeMaximumSum {

	// https://codeforces.com/edu/course/2/lesson/4/2/practice/contest/273278/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SegmentwiththeMaximumSum"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		long[] arr = new long[n];
		st = new StringTokenizer(in.readLine());
		SegTree c = new SegTree(n);
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		c.build(arr);
		System.out.println(c.tree[0].sub);
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			long b = Integer.parseInt(st.nextToken());
			c.set(a,b);
			System.out.println(c.tree[0].sub);
		}
	}
	
	static class SegTree {
		
		int size;
		E[] tree;
		int MIN = (int)(-1e9 - 100);
		
		static class E {
			long sum;
			long pref;
			long suf;
			long sub;
			E(long a, long b, long c, long d) {
				sum = a;
				pref = b;
				suf = c;
				sub = d;
			}
		}
		
		public E combine(E one, E two) {
			long sum = one.sum + two.sum;
			long pref = Math.max(one.pref, one.sum + two.pref);
			long suf = Math.max(two.suf, two.sum + one.suf);
			long sub = Math.max(one.sub, Math.max(two.sub, one.suf + two.pref));
			return new E(sum, pref, suf, sub);
		}
		
		public SegTree(int n) {			
			size = 1;
			while (size < n) size *= 2;
			tree = new E[2*size];
			for (int i=0; i<2*size; i++) tree[i] = new E(0,0,0,0);
		}
		
		// arr[i] = v;
		public void set(int i, long v) {
			set(i, v, 0, 0, size);
		}
		
		public void set(int i, long v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (v > 0) {
					tree[x] = new E(v,v,v,v);
				}
				else {
					tree[x] = new E(v,0,0,0);
				}
				return;
			}
			
			int m = (lx + rx)/2;
			if (i < m) {		// go to left subtree
				set(i, v, 2*x+1, lx, m);
			}
			else {				// go to right subtree
				set(i, v, 2*x+2, m, rx);
			}
			
			tree[x] = combine(tree[2*x+1], tree[2*x+2]);
		}
		
		public void build(long[] arr) {		// arr is the orig arr
			build(arr, 0, 0, size);
		}
		
		public void build(long[] arr, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (lx < arr.length) {
					if (arr[lx] > 0) {
						tree[x] = new E(arr[lx], arr[lx],arr[lx],arr[lx]);
					}
					else {
						tree[x] = new E(arr[lx], 0, 0, 0);
					}
				}
				return;
			}
			
			int m = (lx + rx)/2;
			build(arr, 2*x+1, lx, m);
			build(arr, 2*x+2, m, rx);
			tree[x] = combine(tree[2*x+1], tree[2*x+2]);
		}
	}
}

/*

	create another class which keeps track of 4 things for each segment. 
	
		long sum --> sum of the whole segment
		long pref --> max prefix of the whole segment
		long suf --> max suffix of the whole segment
		long sub --> max subsegment of this segment
		
		for a node, once you know these things of the kid nodes, you can find all
			four of these for this node.
			
			long sum = one.sum + two.sum;		
					sum = sum of two kids
					
			long pref = Math.max(one.pref, one.sum + two.pref);
					pref is the max of the pref of the left part, and the whole
						left part + pref of right part
					
			long suf = Math.max(two.suf, two.sum + one.suf);
					suf is the max of suf of right part and the whole of right
						part + suf of left part
			
			long sub = Math.max(one.sub, Math.max(two.sub, one.suf + two.pref));
					sub is the max of sub of left part, sub of right part,
						and the left suf + right pref
			
		keep track of these for all the nodes, then can calculate stuff quick
		
*/
