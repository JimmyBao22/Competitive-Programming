
import java.util.*;
import java.io.*;

public class IntersectingSegments {

	// https://codeforces.com/edu/course/2/lesson/4/3/practice/contest/274545/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("IntersectingSegments"));

		int n = Integer.parseInt(in.readLine());
		StringTokenizer st = new StringTokenizer(in.readLine());
		SegTree s = new SegTree(2*n);
		HashMap<Integer, Integer> map = new HashMap<>();
		int[] arr= new int[2*n];
		for (int i=0; i<2*n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		int[] ans = new int[n+1];
		// left to right
		for (int i=0; i<2*n; i++) {
			if (map.containsKey(arr[i])) {
					// includes left bound, meaning it counts this current one as well
						// so -1
				ans[arr[i]] = s.sum(map.get(arr[i]), i)-1;
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
				ans[arr[i]] += s2.sum(i, map2.get(arr[i]));
				s2.set(map2.get(arr[i]));
			}
			else {
				map2.put(arr[i], i);
				s2.set(i);
			}
		}
		
		
		for (int i=1; i<=n; i++) System.out.print(ans[i] + " ");
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

/*

	Create two segtrees, and perform alg from left and from right separately
	
	Let's do start from left side for simplicity.
	
	Keep a map of the first appearance of an element.
	
	When you go to a new element, check:
		if it has been visited, this means currently you are ending a segment
			therefore you need to find how many segments are intersect this segment from
				a side! (for example we will use left side)
				
			Search the length of this segment and sum the insides
			
			Then finish by closing up this segment, so that no other segment can
				add this segment as intersecting it. Do this by setting the index of the first
				occurance to 0
		
		if it has not been visited, add it to the map
		Then, change this index to be 1. This means this segment is still open for
			other segments who are closing to intersect it

	Ex.
	Sample Input: 5
				  5 1 2 2 3 1 3 4 5 4
				  
	Beginning Segtree all 0
	
	               0
	       0               0
	   0       0       0       0
	 0   0   0   0   0   0   0   0
	0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
	
	Visit 5. Has not been visited yet, add to map. Map = {5 = 0}
		Set Segtree at this index to 1
		
				   1
	       1               0
	   1       0       0       0
	 1   0   0   0   0   0   0   0
	1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
		
		
	Visit 1. Has not been visited yet, add to map. Map = {5 = 0, 1 = 1}
		Set Segtree at this index to 1
		
				   2
	       2               0
	   2       0       0       0
	 2   0   0   0   0   0   0   0
	1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0
	
	Visit 2. Has not been visited yet, add to map. Map = {5 = 0, 1 = 1, 2 = 2}
		Set Segtree at this index to 1
		
				   3
	       3               0
	   3       0       0       0
	 2   1   0   0   0   0   0   0
	1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0
	
	Visit 2. Has been visited!
		Find sum from segment (Map.get(2),3) exclusive this returns 0.
		Now that this has finished, update segtree
			Map.get(2) should become 0
			
				   2
	       2               0
	   2       0       0       0
	 2   0   0   0   0   0   0   0
	1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0
	
	Visit 3. Has not been visited yet, add to map. Map = {5 = 0, 1 = 1, 2 = 2, 3 = 4}
		Set Segtree at this index to 1
		
				   3
	       3               0
	   2       1       0       0
	 2   0   1   0   0   0   0   0
	1 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0
	
	Visit 1. Has been visited!
		Find sum from segment (Map.get(1),5) exclusive this returns 1.
		Now that this has finished, update segtree
			Map.get(1) should become 0
			
				   2
	       2               0
	   1       1       0       0
	 1   0   1   0   0   0   0   0
	1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0
	
	And so on...
	
*/