
import java.util.*;
import java.io.*;

public class RaccoonMischief {

	// https://mbit.mbhs.edu/archive/2020/advanced.pdf
	// https://codeforces.com/gym/102621/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("RaccoonMischief"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		int[] arr = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		int[][] queries = new int[q][3];
		int[] times = new int[n+1];
		
		for (int i=0; i<q; i++) {
			st = new StringTokenizer(in.readLine());
			queries[i][0] = Integer.parseInt(st.nextToken())-1;
			queries[i][1] = Integer.parseInt(st.nextToken())-1;
			queries[i][2] = Integer.parseInt(st.nextToken());
			times[queries[i][0]]++;
			times[queries[i][1]+1]--;
		}
		
		SegTree s = new SegTree(n);
		for (int i=0; i<q; i++) {
			s.modify(queries[i][0], queries[i][1]+1, queries[i][2]);
		}
				
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<n; i++) {
			if (i != 0) times[i] += times[i-1];
			if (arr[i] == 0) {
				if (times[i] % 2 == 1) {
					arr[i] = (int)s.comp_index(i);
				}
			}
			else {
				if (times[i] == 0) {
					
				}
				else if (times[i] % 2 == 0) {
					arr[i] = (int)s.comp_index(i);
				}
				else {
					arr[i] = 0;
				}
			}
			sb.append(arr[i] + " ");
		}
		
		System.out.println(sb);
	}
	
	static class SegTree {
		int size=1;
		long[] tree;
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new long[2*size];
			for (int i=0; i<2*size; i++) tree[i] = 0;
		}
		
		// random computation on index
		public long comp_index(int i) { return comp_index(i, 0, 0, size); }
		
		public long comp_index(int i, int x, int lx, int rx) {
			if (rx - lx == 1) return tree[x];	// in leaf node aka bottom level
			propogate(x, lx, rx);
			int m = (lx + rx)/2;	long result = 0;
			if (i < m) result = comp_index(i, 2*x+1, lx, m);	// go to left subtree
			else result = comp_index(i, 2*x+2, m, rx);		// go to right subtree
			return Math.max(result, tree[x]);
		}
				
		// change segments
		public void modify(int l, int r, long v) { modify(l, r, v, 0, 0, size); }
		
		public void modify(int l, int r, long v, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return;		// do not intersect this segment
			propogate(x, lx, rx);
			if (l <= lx && rx <= r) {		// inside whole segment
				tree[x] = v;
				return;
			}
			int m = (lx + rx)/2;
			modify(l, r, v, 2*x+1, lx, m);	modify(l, r, v, 2*x+2, m, rx);
		}
		
		static long NONE = 0;	// use a value that will never be used
				// if all values can be used, use a boolean array to see if tree[x]
				// has a value stored or not
		public void propogate(int x, int lx, int rx) {
			if (rx - lx == 1) return;		// leaf
			if (tree[x] != NONE) {
				tree[2*x+1] = tree[x];	tree[2*x+2] = tree[x];
				tree[x] = NONE;
			}
		}
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
		}
	}
}