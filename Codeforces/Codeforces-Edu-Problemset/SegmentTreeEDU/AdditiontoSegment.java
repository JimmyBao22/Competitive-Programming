
import java.util.*;
import java.io.*;

public class AdditiontoSegment {

	// https://codeforces.com/edu/course/2/lesson/4/3/practice/contest/274545/problem/E
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("AdditiontoSegment"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		SegTree s = new SegTree(n+1);
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken());
			if (one == 1) {
				int three = Integer.parseInt(st.nextToken());
				long v = Integer.parseInt(st.nextToken());
				s.set(two, v);
				//s.print();
				s.set(three, -v);
				//s.print();
			}
			else {
				System.out.println(s.comp(0, two+1));
			}
		}
		
	}
	
	static class SegTree {
		
		int size;
		long[] tree;
		
		public void print() {
			System.out.println();
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
		}
		
		public SegTree(int n) {			
			size = 1;
			while (size < n) size *= 2;
			tree = new long[2*size];
			for (int i=0; i<2*size; i++) tree[i] = 0;
		}
				
		// use for random computation
		public long comp(int l, int r) {
			return comp(l, r, 0, 0, size);
		}
		
		public long comp(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) {		// do not intersect this segment
				return 0;
			}
			if (l <= lx && rx <= r) {		// inside whole segment
				return tree[x];
			}
			
			int m = (lx + rx)/2;
			long one = comp(l, r, 2*x+1, lx, m);
			long two = comp(l, r, 2*x+2, m, rx);
			return one + two;
		}
		
		// arr[i] = v;
		public void set(int i, long v) {
			set(i, v, 0, 0, size);
		}
		
		public void set(int i, long v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] += v;
				return;
			}
			
			int m = (lx + rx)/2;
			if (i < m) {		// go to left subtree
				set(i, v, 2*x+1, lx, m);
			}
			else {				// go to right subtree
				set(i, v, 2*x+2, m, rx);
			}
			
			tree[x] = tree[2*x+1] + tree[2*x+2];
		}
	}
}

/*
	Update using coordinate compression. Then, when you want to find the value of the 
		index, just find the sum from 0 to this index
		
*/
