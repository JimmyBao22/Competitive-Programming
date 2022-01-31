
import java.util.*;
import java.io.*;

public class marathon {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=495
	
	static long INF = (long)(1e12);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("marathon.in"));
		PrintWriter out = new PrintWriter(new FileWriter("marathon.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());

		Point[] arr = new Point[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		
		long[] dist = new long[n-1];
		for (int i=0; i+1<n; i++) {
			dist[i] = dist(arr[i], arr[i+1]);
		}
		
		long[] dist2 = new long[n-2];
			// dist2[i-1] means skipping arr[i]
		for (int i=1; i+1<n; i++) {
			dist2[i-1] = dist(arr[i-1], arr[i+1]) - dist[i-1] - dist[i];
		}
		
		SegTree s1 = new SegTree(n-1);
		s1.build(dist);
		SegTreeMin s2 = new SegTreeMin(n-2);
		s2.build(dist2);
		
		StringBuilder sb = new StringBuilder();
		while (q --> 0) {
			st = new StringTokenizer(in.readLine());
			char type = st.nextToken().charAt(0);
			if (type == 'Q') {
				int left = Integer.parseInt(st.nextToken())-1;
				int right = Integer.parseInt(st.nextToken())-1;
				int curdist = (int) s1.comp_seg(left, right);
				curdist += s2.comp_seg(left, right-1);
				sb.append(curdist + "\n");
			}
			else {
				int i = Integer.parseInt(st.nextToken())-1;
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				arr[i] = new Point (x,y);
				if (i-1 >= 0) {
					dist[i-1] = dist(arr[i-1], arr[i]);
					s1.set(i-1, dist[i-1]);
				}
				if (i+1 < n) {
					dist[i] = dist(arr[i], arr[i+1]);
					s1.set(i, dist[i]);
				}
				if (i-2 >= 0) {
					dist2[i-2] = dist(arr[i-2], arr[i]) - dist[i-2] - dist[i-1];
					s2.set(i-2, dist2[i-2]);
				}
				if (i-1 >= 0 && i+1 < n) {
					dist2[i-1] = dist(arr[i-1], arr[i+1]) - dist[i-1] - dist[i];
					s2.set(i-1, dist2[i-1]);
				}
				if (i+2 < n) {
					dist2[i] = dist(arr[i], arr[i+2]) - dist[i] - dist[i+1];
					s2.set(i, dist2[i]);
				}
			}
		}
		
		System.out.print(sb);
		out.print(sb);
		out.close();
	}
	
	public static int dist(Point a, Point b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}
	
	static class Point {
		int x, y;
		Point (int a, int b) {
			x = a; y = b;
		}
	}
	
	static class SegTree {
		int size=1;
		long[] tree;
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new long[2*size];
		}
		
		// random computation on segment (l to r-1)
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
		
		public void print() {
			System.out.println(Arrays.toString(tree));
			System.out.println();
		}
	}
	
	static class SegTreeMin {
		int size=1;
		long[] tree;
		
		public SegTreeMin(int n) {			
			while (size < n) size *= 2;
			tree = new long[2*size];
		}
		
		// random computation on segment (l to r-1)
		public long comp_seg(int l, int r) { return comp_seg(l, r, 0, 0, size); }
		
		public long comp_seg(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return INF;	// do not intersect this segment
			if (l <= lx && rx <= r) return tree[x];	// inside whole segment
			int m = (lx + rx)/2;
			long one = comp_seg(l, r, 2*x+1, lx, m); 
			long two = comp_seg(l, r, 2*x+2, m, rx);
			return Math.min(one, two);
		}
		
		public void set(int i, long v) { set(i, v, 0, 0, size); }	// arr[i] = v;
		
		public void set(int i, long v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] = v; return;
			}
			int m = (lx + rx)/2;
			if (i < m) set(i, v, 2*x+1, lx, m); 	// go to left subtree
			else set(i, v, 2*x+2, m, rx);			// go to right subtree
			tree[x] = Math.min(tree[2*x+1] , tree[2*x+2]);
		}
		
		public void build(long[] arr) {	build(arr, 0, 0, size); }	// arr is the orig arr
		
		public void build(long[] arr, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (lx < arr.length) tree[x] = arr[lx];
				return;
			}
			int m = (lx + rx)/2;
			build(arr, 2*x+1, lx, m);	build(arr, 2*x+2, m, rx);
			tree[x] = Math.min(tree[2*x+1] , tree[2*x+2]);
		}
		
		public void print() {
			System.out.println(Arrays.toString(tree));
			System.out.println();
		}
	}
}