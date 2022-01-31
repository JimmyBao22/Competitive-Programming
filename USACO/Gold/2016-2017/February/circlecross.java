
import java.util.*;
import java.io.*;

public class circlecross {

	// http://usaco.org/index.php?page=viewproblem2&cpid=719
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("circlecross.in"));
		PrintWriter out = new PrintWriter(new FileWriter("circlecross.out"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[2*n];
		for (int i=0; i<2*n; i++) arr[i] = Integer.parseInt(in.readLine());
		HashMap<Integer, Integer> map = new HashMap<>();
		SegTree s = new SegTree(2*n);

		int ans=0;
		// left to right
		for (int i=0; i<2*n; i++) {
			if (map.containsKey(arr[i])) {
					// includes left bound, meaning it counts this current one as well
						// so -1
				ans += s.sum(map.get(arr[i]), i)-1;
				s.set(map.get(arr[i]));
			}
			else {
				map.put(arr[i], i);
				s.set(i);
			}		
		}
		
		HashMap<Integer, Integer> map2 = new HashMap<>();
		// right to left
		SegTree s2 = new SegTree(2*n);
		for (int i=2*n-1; i>=0; i--) {
			if (map2.containsKey(arr[i])) {
					// doesn't include right bound so no -1
				ans += s2.sum(i, map2.get(arr[i]));
				s2.set(map2.get(arr[i]));
			}
			else {
				map2.put(arr[i], i);
				s2.set(i);
			}
		}
		
		System.out.println(ans/2);
		out.println(ans/2);
		out.close();

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
				tree[x] = 1^tree[x];
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