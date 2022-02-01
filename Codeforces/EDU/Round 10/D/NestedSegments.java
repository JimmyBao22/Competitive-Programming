
import java.util.*;
import java.io.*;

public class NestedSegments {

	// https://codeforces.com/contest/652/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("NestedSegments"));

		int n = Integer.parseInt(in.readLine());
		SegTree s = new SegTree(2*n);
		HashMap<Integer, Integer> map = new HashMap<>();
		A[] arr= new A[2*n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[2*i] = new A(i, Integer.parseInt(st.nextToken()));
			arr[2*i+1] = new A(i, Integer.parseInt(st.nextToken()));
		}
		Arrays.parallelSort(arr);
		
		int[] ans = new int[n+1];
		for (int i=0; i<2*n; i++) {
			if (map.containsKey(arr[i].index)) {
				ans[arr[i].index] = s.sum(map.get(arr[i].index), i);
				s.set(map.get(arr[i].index));
			}
			else {
				map.put(arr[i].index, i);
			}		
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<n; i++) {
			sb.append(ans[i]);
			sb.append("\n");
		}
		System.out.println(sb);
	}
	
	static class A implements Comparable<A> {
		int index; int val;
		public A (int a, int b) {
			index = a; val = b;
		}
		public int compareTo(A o) {
			return val - o.val;
		}
	}

	static class SegTree {
		
		int size;
		int[] tree;
		
		public SegTree(int n) {			
			size = 1;
			while (size < n) size *= 2;
			tree = new int[2*size];
			for (int i=0; i<2*size; i++) tree[i] = 0;
		}
				
		// return arr[l] + arr[l+1] + ... + arr[r-1]
		public int sum(int l, int r) {
			return sum(l, r, 0, 0, size);
		}
		
		public int sum(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) {		// do not intersect this segment
				return 0;
			}
			if (l <= lx && rx <= r) {		// inside whole segment
				return tree[x];
			}
			
			int m = (lx + rx)/2;
			return sum(l, r, 2*x+1, lx, m) + sum(l, r, 2*x+2, m, rx);
		}
		
		// arr[i] = v;
		public void set(int i) {
			set(i, 0, 0, size);
		}
		
		public void set(int i, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] = 1;
				return;
			}
			
			int m = (lx + rx)/2;
			if (i < m) {		// go to left subtree
				set(i, 2*x+1, lx, m);
			}
			else {				// go to right subtree
				set(i, 2*x+2, m, rx);
			}
			
			tree[x] = tree[2*x+1] + tree[2*x+2];
		}
	}
}