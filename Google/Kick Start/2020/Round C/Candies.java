
import java.util.*;
import java.io.*;

public class Candies {

	// https://codingcompetitions.withgoogle.com/kickstart/round/000000000019ff43/0000000000337b4d
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Candies"));

		int t = Integer.parseInt(in.readLine());
		for (int j = 1; j < t + 1; j++) {
			
			StringTokenizer st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken());  
			int q = Integer.parseInt(st.nextToken());  
			long[] arr = new long[n];
			st = new StringTokenizer(in.readLine());
			for (int i=0; i<n; i++) {
				arr[i] = Integer.parseInt(st.nextToken());  
			}
			
			SegTree s = new SegTree(n);
			s.build(arr);
			long sum=0;
			//s.print();
			for (int i=0; i<q; i++) {
				st = new StringTokenizer(in.readLine());
				char c = st.nextToken().charAt(0);
				int one = Integer.parseInt(st.nextToken());  
				int two = Integer.parseInt(st.nextToken());  
				if (c == 'U') {
					s.set(one-1, two);
				}
				else {
					long single = s.comp(one-1, two, true);
					long notsingle = s.comp(one-1, two, false);
					long total = notsingle - (one-1)*single + single;
					sum += total;
				}
			}
			
			System.out.println("Case #" + j + ": " + sum);
		}
	}
	
	static class SegTree {
		
		int size;
		long[] tree1, tree2;
		
		public SegTree(int n) {			
			size = 1;
			while (size < n) size *= 2;
			tree1 = new long[2*size];
			tree2 = new long[2*size];
		}
				
		// use for random computation
		public long comp(int l, int r, boolean single) {
			return comp(l, r, single, 0, 0, size);
		}
		
		public long comp(int l, int r, boolean single, int x, int lx, int rx) {
			if (lx >= r || rx <= l) {		// do not intersect this segment
				return Integer.MAX_VALUE;
			}
			if (l <= lx && rx <= r) {		// inside whole segment
				if (single) return tree1[x];
				else return tree2[x];
			}
			
			int m = (lx + rx)/2;
			long one = comp(l, r, single, 2*x+1, lx, m);
			long two = comp(l, r, single, 2*x+2, m, rx);
			if (one == Integer.MAX_VALUE) {
				return two;
			}
			else if (two == Integer.MAX_VALUE) {
				return one;
			}
			else if ((m - Math.max(lx, l))%2 == 0) {
				return one + two;
			}
			else {
				return one - two;
			}
		}
		
		// arr[i] = v;
		public void set(int i, long v) {
			set(i, v, 0, 0, size);
		}
		
		public void set(int i, long v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree1[x] = v;
				tree2[x] = i*v;
				return;
			}
			
			int m = (lx + rx)/2;
			if (i < m) {		// go to left subtree
				set(i, v, 2*x+1, lx, m);
			}
			else {				// go to right subtree
				set(i, v, 2*x+2, m, rx);
			}
			
			if ((m - lx)%2 == 0) {
				tree1[x] = tree1[2*x+1] + tree1[2*x+2];
				tree2[x] = tree2[2*x+1] + tree2[2*x+2];
			}
			else {
				tree1[x] = tree1[2*x+1] - tree1[2*x+2];
				tree2[x] = tree2[2*x+1] - tree2[2*x+2];
			}
		}
		
		public void build(long[] arr) {		// arr is the orig arr
			build(arr, 0, 0, size);
		}
		
		public void build(long[] arr, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				if (lx < arr.length) {
					tree1[x] = arr[lx];
					tree2[x] = lx*arr[lx];
				}
				return;
			}
			
			int m = (lx + rx)/2;
			build(arr, 2*x+1, lx, m);
			build(arr, 2*x+2, m, rx);
			if ((m - lx)%2 == 0) {
				tree1[x] = tree1[2*x+1] + tree1[2*x+2];
				tree2[x] = tree2[2*x+1] + tree2[2*x+2];
			}
			else {
				tree1[x] = tree1[2*x+1] - tree1[2*x+2];
				tree2[x] = tree2[2*x+1] - tree2[2*x+2];
			}
		}
		
		public void print() {
			System.out.println(Arrays.toString(tree1));
			System.out.println(Arrays.toString(tree2));
			System.out.println();
		}
	}
}