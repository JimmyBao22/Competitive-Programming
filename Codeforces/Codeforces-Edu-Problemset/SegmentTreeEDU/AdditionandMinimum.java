
import java.util.*;
import java.io.*;

public class AdditionandMinimum {

	// https://codeforces.com/edu/course/2/lesson/5/2/practice/contest/279653/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("AdditionandMinimum"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		SegTree s = new SegTree(n);
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			if (a == 1) {
				long d = Integer.parseInt(st.nextToken());
				s.modify(b, c, d);
			}
			else {
				System.out.println(s.comp(b, c));
			}
		}
	}
	
	static class SegTree {
		int size=1;
		long[] tree;	// only keeps track of sum
		long[] mins;
		
		public SegTree(int n) {			
			while (size < n) size *= 2;
			tree = new long[2*size];
			mins = new long[2*size];
			for (int i=0; i<2*size; i++) {tree[i] = 0; mins[i] = 0;}
		}
		
		public long comp(int l, int r) { return comp(l, r, 0, 0, size); }
		
		public long comp(int l, int r, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return (long)(1e18);	// do not intersect this segment
			if (l <= lx && rx <= r) return mins[x];	// inside whole segment
			int m = (lx + rx)/2;
			long one = comp(l, r, 2*x+1, lx, m); 
			long two = comp(l, r, 2*x+2, m, rx);
			return Math.min(one, two) + tree[x];
		}
				
		// change segments
		public void modify(int l, int r, long v) { modify(l, r, v, 0, 0, size); }
		
		public void modify(int l, int r, long v, int x, int lx, int rx) {
			if (lx >= r || rx <= l) return;		// do not intersect this segment
			// propagate(x, lx, rx);
			if (l <= lx && rx <= r) {		// inside whole segment
				tree[x] += v;
				mins[x] += v;
				return;
			}
			int m = (lx + rx)/2;
			modify(l, r, v, 2*x+1, lx, m);	modify(l, r, v, 2*x+2, m, rx);
			mins[x] = Math.min(mins[2*x+1], mins[2*x+2]) + tree[x];
		}
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
			for (int i=0; i<mins.length; i++) System.out.print(mins[i] + " ");
			System.out.println();
		}
	}
}

/*
	
	Use two different trees. The first tree is min, and each node keeps 
		track of the minimum amongst all its children. The second tree
		keeps track of normal sums on segments
		
	When you search the tree, you go to the bottom most nodes and take the min.
		Then, as you move upwards in the tree, repeatedly add on the added sums 
		from the second tree.
		
	Ex. 
	5 6
	1 0 3 3
	2 1 2
	1 1 4 4
	2 1 3
	2 1 4
	2 3 5
	
	tree:						min:
	       0						   0
	   0       0     			   0       0
	 0   0   0   0				 0   0   0   0
	0 0 0 0 0 0 0 0				0 0 0 0 0 0 0 0 
	
	Add 3 from indices 0 to 2 inclusive
	
	Only add to parent nodes so you don't add to every single element in last row
	
	tree:						min:
	       0						   0
	   0       0     			   0       0
	 3   0   0   0				 3   0   0   0
	0 0 3 0 0 0 0 0				0 0 3 0 0 0 0 0 
	
	Get min from indices 1 to 1 inclusive
		Go through min tree.
		Index 1 --> min=3
		Move Upwards, min stays at 3
	
	Add 4 from indices 1 to 3 inclusive
	
	tree:						min:
	       0						   0
	   0       0     			   3       0
	 3   4   0   0				 3   4   0   0
	0 4 3 0 0 0 0 0				0 4 3 0 0 0 0 0 
	
	Get min from indices 1 to 2 inclusive
		Go through min tree.
		Index 1 --> min=4; Index 2 --> min = 3
		End:
			Index 1 --> 4 + 3 (3 from min, then other 3 from TREE index 3)
			Index 2 --> 3 + 4 (3 from min, then other 4 from TREE index 4)
	Therefore min = 7
		
		
	Get min from indices 1 to 3 inclusive
		Go through min tree.
		Index 1 --> min=4; Index 2-3 --> min = 4
		End:
			Index 1 --> 4 + 3 (4 from min, then other 3 from TREE index 3)
			Index 2-3 --> 4
	Therefore min = 4


	Get min from indices 3 to 4 inclusive
		Go through min tree.
		Index 3 --> min=0; Index 4 --> min = 0
		End:
			Index 3 --> 0 + 4 (0 from min, then other 4 from tree index 4)
			Index 4 --> 0
	Therefore min = 0
	
*/