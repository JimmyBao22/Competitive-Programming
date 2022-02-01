import java.util.*;

public class TicketOrder {
    
	public int[] solve(int[] tickets) {
        int n = tickets.length;
        A[] arr = new A[n];
        for (int i=0; i<n; i++) {
            arr[i] = new A(tickets[i], i);
        }
        Arrays.sort(arr);
        // BIT/Segtree
        SegTree s = new SegTree(n);
        int[] ans = new int[n];
        int bad = 0;
        for (int i=0; i<n; i++ ) {
            if (i != 0 && arr[i].num != arr[i-1].num) {
                bad += (arr[i].num - arr[i-1].num) * i;
            }
            System.out.println((int)s.comp_seg(arr[i].i, n));
            ans[arr[i].i] = (arr[i].num-1) * (n) + arr[i].i+1 - bad + (int)s.comp_seg(arr[i].i, n);
            s.set(arr[i].i, 1);
            
        }
        return ans;
    }

    class SegTree {
		int size=1;
		long[] tree;
		
		public SegTree(int n) {			
			while (size < n) size <<= 1;
			tree = new long[2*size];
		}
		
		// random computation on segment (l to r-1)
		public long comp_seg(int l, int r) { return comp_seg(l, r, 0, 0, size); }
		
		public long comp_seg(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return 0;	// do not intersect this segment
			if (l <= lx && rx <= r) return tree[x];	// inside whole segment
			int m = (lx + rx) >> 1;
			long one = comp_seg(l, r, 2*x+1, lx, m); 
			long two = comp_seg(l, r, 2*x+2, m, rx);
			return one + two;
		}
		
		public void set(int i, long v) { set(i, v, 0, 0, size); }	// arr[i] = v;
		
		public void set(int i, long v, int x, int lx, int rx) {
			if (rx - lx == 1) {		// in leaf node aka bottom level
				tree[x] += v; return;
			}
			int m = (lx + rx) >> 1;
			if (i < m) set(i, v, 2*x+1, lx, m); 	// go to left subtree
			else set(i, v, 2*x+2, m, rx);			// go to right subtree
			tree[x] = tree[2*x+1] + tree[2*x+2];
		}

		public void print() {
			System.out.println(Arrays.toString(tree));
			System.out.println();
		}
    }

    class A implements Comparable<A> {
        int num, i;
        A (int a, int b) {
            num = a; i = b;
        }
        public int compareTo(A o) {
            // if (num == o.num) {
            //     return o.i - i; // last first
            // }
            return num - o.num;
        }
    }
}