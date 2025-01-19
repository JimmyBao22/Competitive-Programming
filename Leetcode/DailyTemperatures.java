import java.util.*;

public class DailyTemperatures {

	// https://leetcode.com/problems/daily-temperatures/
	
	public static void main(String[] args) {
		int[] a = {73,74,75,71,69,72,76,73};
		dailyTemperatures(a);
	}
	
	public static int[] dailyTemperatures(int[] T) {
		// stack
		ArrayDeque<Integer> d = new ArrayDeque<>();
		int n = T.length;
		int[] arr = new int[n];
		for (int i=0; i<n; i++) {
			while (!d.isEmpty() && T[i] > T[d.peek()]) {
									// current temp is greater than some previous
				int index = d.poll();
				arr[index] = i - index;
			}
				// while loop got rid of all T[j] less than T[i] for j<i.
					// now T[i] is largest element in deque.
			d.addFirst(i);
		}
		return arr;
	}
	
	static int INF = (int)(1e9);
	public static int[] dailyTemperaturesSegTree(int[] T) {
		int n = T.length;
        int[] arr = new int[n];
        A[] t = new A[n];
        for (int i=0; i<n; i++ ) {
        	t[i] = new A(T[i], i);
        }
        Arrays.parallelSort(t);
        
        SegTree s = new SegTree(n);
        
        for (int i=0; i<n; i++) {
        	long ret = s.comp_seg(t[i].index, n);
        	if (ret != INF) {
        		arr[t[i].index] = (int) (ret - t[i].index);
        	}
        	s.set(t[i].index, t[i].index);
        }
        
        return arr;
    }
	
	static class A implements Comparable<A> {
		int val; int index;
		A (int a, int b) {
			val = a; index = b;
		}
		public int compareTo(A o) {
			return o.val - val;
		}
	}
	
	static class SegTree {
		int size=1;
		long[] tree;
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new long[2*size];
			for (int i=0; i<2*size; i++) tree[i] = INF;
		}
		
		// random computation on segment
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
			tree[x] = Math.min(tree[2*x+1], tree[2*x+2]);
		}
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
		}
	}
}