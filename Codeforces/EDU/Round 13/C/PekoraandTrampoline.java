
import java.util.*;
import java.io.*;

public class PekoraandTrampoline {

	// https://codeforces.com/contest/1491/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("three"));

		int t = Integer.parseInt(in.readLine());
		while (t-- > 0) {
			int n = Integer.parseInt(in.readLine());
			long[] arr = new long[n];
			StringTokenizer st = new StringTokenizer(in.readLine());
			for (int i=0; i<n; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			long ans=0;
			for (int i=0; i<n; i++) {
				long val = Math.max(n-i, Math.max(i*(i+1)/2, 1));
				if (arr[i] > val ) {
					ans += (arr[i] - val);
					arr[i] = val;
				}
			}
			
			SegTree s = new SegTree(n);
			for (int i=0; i<n; i++) {
				long rem = s.comp_index(i);
				if (Math.min(n, (int)(i+arr[i]))+1 > i+2) {
					s.modify(i+2, Math.min(n, (int)(i+arr[i]))+1, 1);
				}
				if (arr[i] == 1) {
					if (i+1 < n) s.modify(i+1, i+2, rem);
				}
				else if (rem > arr[i]-1) {
					long left = rem - (arr[i] - 1);
					if (i+1 < n) s.modify(i+1, i+2, left);
				}
				arr[i] = Math.max(1, arr[i] - rem);
				ans += (arr[i] - 1);
				arr[i] = 1;
			}
			
			System.out.println(ans);	
		}
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
			// propogate(x, lx, rx);
			int m = (lx + rx)/2;	long result = 0;
			if (i < m) result = comp_index(i, 2*x+1, lx, m);	// go to left subtree
			else result = comp_index(i, 2*x+2, m, rx);		// go to right subtree
			return result + tree[x];
		}
				
		// change segments (l to r-1)
		public void modify(int l, int r, long v) { modify(l, r, v, 0, 0, size); }
		
		public void modify(int l, int r, long v, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return;		// do not intersect this segment
			// propogate(x, lx, rx);
			if (l <= lx && rx <= r) {		// inside whole segment
				tree[x] += v;
				return;
			}
			int m = (lx + rx)/2;
			modify(l, r, v, 2*x+1, lx, m);	modify(l, r, v, 2*x+2, m, rx);
		}
		
		static long NONE = (long)(1e18);	// use a value that will never be used
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
			System.out.println(Arrays.toString(tree));
			System.out.println();
		}
	}
}