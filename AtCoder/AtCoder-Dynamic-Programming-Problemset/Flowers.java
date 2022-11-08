
import java.util.*;
import java.io.*;

public class Flowers {

	// https://atcoder.jp/contests/dp/tasks/dp_q
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Flowers"));

		int n = Integer.parseInt(in.readLine());
		int[] h = new int[n]; 
		long[] a = new long[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) h[i] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) a[i] = Integer.parseInt(st.nextToken());
		
		long[] dp = new long[n];
			// dp[i] = max from 0 to i
		
		SegTree s = new SegTree(2*n);
		// stores indices h = value a
		// Therefore, at index i, when you query h values for indices 0 to i-1, 
			// you can find the max dp of those
	
		for (int i=0; i<n; i++) {
			dp[i] = s.comp_seg(0, h[i]) + a[i];
			s.set(h[i], dp[i]);
		}
		
		long ans=0;
		for (int i=0; i<n; i++) ans = Math.max(ans, dp[i]);
		System.out.println(ans);
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
			return Math.max(one, two);
		}
		
		public void set(int i, long v) { set(i, v, 0, 0, size); }	// arr[i] = v;
		
		public void set(int i, long v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] = v; return;
			}
			int m = (lx + rx)/2;
			if (i < m) set(i, v, 2*x+1, lx, m); 	// go to left subtree
			else set(i, v, 2*x+2, m, rx);			// go to right subtree
			tree[x] = Math.max(tree[2*x+1], tree[2*x+2]);
		}
	}
}

/*
	brute force
	for (int i=0; i<n; i++) {
		for (int j=0; j<i; j++) {
			if (h[i] > h[j]) {
				dp[i] = Math.max(dp[i], dp[j]);
			}
		}
		dp[i] += a[i];
	}
*/