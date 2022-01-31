
import java.util.*;
import java.io.*;

public class help {

	// http://usaco.org/index.php?page=viewproblem2&cpid=1018
	
	static long mod = (long)(1e9+7);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("help.in"));
		PrintWriter out = new PrintWriter(new FileWriter("help.out"));

		int n = Integer.parseInt(in.readLine());
		int[][] regions = new int[n][2];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			regions[i][0] = Integer.parseInt(st.nextToken());
			regions[i][1] = Integer.parseInt(st.nextToken());
		}
			
		Arrays.parallelSort(regions, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				if (a[0] == b[0]) return a[1] - b[1];
				return a[0] - b[0];
			}
		});
		
		long[] pow2 = new long[n];
		pow2[0] = 1;
		for (int i=1; i<n; i++) {
			pow2[i] = pow2[i-1] * 2;
			pow2[i] %= mod;
		}
		
		SegTree s = new SegTree(4*n);
		
		long[] memo = new long[n];
			// cur
		
		for (int i=0; i<n; i++) memo[i] = 1;
		
		s.set(regions[0][1], 1);
		for (int i=1; i<n; i++) {
			int except=0;
			memo[i] += 2*memo[i-1];
			memo[i] %= mod;
			
			except = (int) s.comp_seg(regions[i][0], s.size);
			s.set(regions[i][1], 1);
			memo[i] += pow2[i-except]-1;
			memo[i] %= mod;
		}
		
		//print(memo);
		
		long ans = memo[n-1];
		ans %= mod;
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	public static void print(long[] memo) {
		int n = memo.length;
		for (int i=0; i<n; i++) {
			System.out.print(memo[i] + " ");
		}
		System.out.println();
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
		
		public void print() {
			for (int i=0; i<tree.length; i++) System.out.print(tree[i] + " ");
			System.out.println();
		}
	}
}

/*
	Start thinking about a dp solution
	First sort all the data by the first value. This way you know that the left bounds go
		up always, so don't need to worry about that
		
	First start thinking about a 2d dp
	
	long[][] memo = new long[n][n];
			// cur, max right index
		
	for (int i=0; i<n; i++) memo[i][i] = 1;
	for (int i=1; i<n; i++) {
		int except=0;
		for (int j=0; j<i; j++) {
			memo[i][j] += memo[i-1][j];
			
			if (regions[i][0] > regions[j][1]) {
				memo[i][i] += memo[i-1][j]+pow2[j-except];
				memo[i][i] %= mod;
			}
			else {
				memo[i][j] += memo[i-1][j];
				memo[i][j] %= mod;
				except++;
			}
		}
	}

	the memo here stores[current index][the previous index where the max right is]
	
	the idea behind this is first, base cases is for (all i) memo[i][i] = 1;
	
	then, when we move forwards to memo[i][j], there are 2 cases
	
	case 1) do not take this position. Then memo[i][j] += memo[i-1][j];
	
	case 2) do take this position. there are 2 subcases
		2.1) regions[i][0] > regions[j][1]. In this case, you have just created a new 
			connected region within this section. 
			memo[i][i] += memo[i-1][j]+pow2[j-except];
			
			First off, memo[i][i] += memo[i-1][j], because you are adding in this new element at position i,
				and previously the max was position j, but now it is at position i
			Next, you need to add +pow2[j-except];
				This is because pow2[j-except] is all the ways that memo[i-1][j] can be formed
					j-except denotes all of the regions, from 0 to j-1, where region j 
						has a greater rightmost boundary. Out of these choices, you have to
						create a subsection of them, which is done in 2^(j-except) ways
						
						because region j has the rightmost region, it is automatically included
						
					In all of these sequences, you need to add one extra section, because
					an extra section is created from memo[i][i]
					
		2.2) otherwise memo[i][j] += memo[i-1][j]; 
			This is because region j still has the rightmost x, so in this case you are
				adding in the current region i, but it will be added to memo[i][j]
				
			except++; 	This is because, as explained above, this region j has a greater rightmost
				region so it isn't included in count
				
		After all this, answer would be:
			long ans=0;
			for (int i=0; i<n; i++) {
				ans += memo[n-1][i];
				ans %= mod;
			}
				
		
	This dp has dimensions 10^5 by 10^5, which is MLE. Also n^2 algo so TLE.
		To remove MLE problem, make it one dimension
		
		
	long[] memo = new long[n];
		// cur
	
	for (int i=0; i<n; i++) memo[i] = 1;
	for (int i=1; i<n; i++) {
		int except=0;
		memo[i] += memo[i-1];		
		memo[i] += memo[i-1];
		memo[i] %= mod;
		for (int j=0; j<i; j++) {
			if (regions[i][0] > regions[j][1]) {
				memo[i] += pow2[j-except];
				memo[i] %= mod;
			}
			else {
				except++;
			}
		}
	}
	
	The base cases here are still the same, it is if you only take this one index, then it is one region
		for the loop, there again are 2 cases
		
		case 1) do not take this position
			Note that from the dp[i][j] array, you are adding dp[i-1][j] once in this case
				for every j. Therefore, dp[i] += dp[i-1] suffices
				
		case 2) take this position
			Notice that dp[i-1][j] will always be added once to the sum in this case
			Therefore, dp[i] += dp[i-1] suffices
			
			 then, the pow2 and except stuff are all the same
		
		After all this, answer would be:
			long ans=memo[n-1];
			
			
	This gets TLE because it is n^2. Speed up by noticing that
		Whenever you add powers of 2, they will be consecutive
			(2^0 + 2^1 + ... + 2^x)
			
		For memo[i], 
			These powers will go from 0 to (i-1-except)
			2^0 + 2^1 + ... + 2^(i-1-except) = 2^(i-except) - 1
		
		Now, just need to find what except is. Because each point only ranges from 
		1 to 2N, this can be done with a segment tree.
			for memo[i], do a query on regions[i][0] to tree.size, which will give
				how many previous regions have an endpoint greater than regions[i][0],
				which is except.
			
			Afterwards, you can update tree and set regions[i][1] to 1.
*/