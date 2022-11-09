
import java.util.*;
import java.io.*;

public class AssignmenttoSegment {

	// https://codeforces.com/edu/course/2/lesson/5/1/practice/contest/279634/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("AssignmenttoSegment"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		SegTree s = new SegTree(n);
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			if (a == 1) {
				int c = Integer.parseInt(st.nextToken());
				long d = Integer.parseInt(st.nextToken());
				obj cur = new obj(i, d);
				s.modify(b, c, cur);
			}
			else {
				System.out.println(s.comp_mod(b).val);
			}
		}
	}
	
	static class obj {
		int time;
		long val;
		obj (int a, long b) {
			time = a;
			val = b;
		}
	}
	
	static class SegTree {
		
		int size;
		obj[] tree;
		obj orig = new obj(-1, 0);
		
		public SegTree(int n) {			
			size = 1;
			while (size < n) size *= 2;
			tree = new obj[2*size];
			for (int i=0; i<2*size; i++) tree[i] = orig;
		}
		
		// use for random computation with MODIFY
		public obj comp_mod(int i) {
			return comp_mod(i, 0, 0, size);
		}
		
		public obj comp_mod(int i, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				return tree[x];
			}
			
			int m = (lx + rx)/2;
			obj result;
			if (i < m) {		// go to left subtree
				result = comp_mod(i, 2*x+1, lx, m);
			}
			else {				// go to right subtree
				result = comp_mod(i, 2*x+2, m, rx);
			}
			
			if (result.time <= tree[x].time) {
				return tree[x];
			}
			else return result;
		}
				
		// change segments
		public void modify(int l, int r, obj c) {
			modify(l, r, c, 0, 0, size);
		}
		
		public void modify(int l, int r, obj c, int x, int lx, int rx) {
			if (lx >= r || rx <= l) {		// do not intersect this segment
				return;
			}
			if (l <= lx && rx <= r) {		// inside whole segment
				tree[x] = c;
				return;
			}
			
			int m = (lx + rx)/2;
			modify(l, r, c, 2*x+1, lx, m);
			modify(l, r, c, 2*x+2, m, rx);
			
		}
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
		}
	}
}

/*

	Only Update at parent nodes, except this time update with an object
		that keeps track of when you put it in, and the value.
		
		Always take the one where u put in later

*/
