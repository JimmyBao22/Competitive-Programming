
import java.util.*;
import java.io.*;

public class pogocow {

	// http://usaco.org/index.php?page=viewproblem2&cpid=345
	
	static long[][][] memo;
	static A[] arr;
	static int n;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("pogocow.in"));
		PrintWriter out = new PrintWriter(new FileWriter("pogocow.out"));

		n = Integer.parseInt(in.readLine());
		arr = new A[n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i] = new A(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		Arrays.parallelSort(arr);
		
		memo = new long[n][n][2];
			// node, last node, left (0) or right (1)
		
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) Arrays.fill(memo[i][j], -1);
		}
				
		long ans=0;
		for (int i=0; i<n; i++) {			
			ans = Math.max(ans, Math.max(dp(i, i, 0), dp(i,i,1)));
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}

	public static long dp(int index, int lastindex, int dir) {
		if (index < 0 || index >= n) return 0;
		if (memo[index][lastindex][dir] != -1) return memo[index][lastindex][dir];
		
		long ans=arr[index].val;
		if (dir == 0) {
			int min=0;
			int max = index-1; 
			while (min<max) {
				int middle = (min + max+1)/2;
				if (Math.abs(arr[middle].pos - arr[index].pos) < Math.abs(arr[index].pos - arr[lastindex].pos)) {
					max = middle-1;
				}
				else min = middle;
			}
			int minpos=-1;
			for (int i=max; i>=0; i--) {
				if (minpos != -1 && Math.abs(arr[i].pos - arr[minpos].pos) >= Math.abs(arr[minpos].pos - arr[index].pos)) break;
					// if a minpos exists, and the distance from arr[i] to arr[minpos] >= the 
						//distance from arr[minpos] to arr[index], it is always more beneficial
						// to go from arr[index] to arr[minpos] to arr[i] instead of 
						// straight from arr[index] to arr[i]
				
				if (Math.abs(arr[i].pos - arr[index].pos) >= Math.abs(arr[index].pos - arr[lastindex].pos)) {
					if (minpos == -1) minpos = i;
					ans = Math.max(ans, dp(i, index, dir) + arr[index].val);					
				}
			}
		}
		else {
			int min=index+1;
			int max = n-1;
			while (min<max) {
				int middle = (min + max)/2;
				if (Math.abs(arr[middle].pos - arr[index].pos) < Math.abs(arr[index].pos - arr[lastindex].pos)) {
					min = middle+1;
				}
				else max = middle;
			}
			int minpos = -1;
			for (int i=min; i<n; i++) {
				if (minpos != -1 && Math.abs(arr[i].pos - arr[minpos].pos) >= Math.abs(arr[minpos].pos - arr[index].pos)) break;
					// same as above
				
				if (Math.abs(arr[i].pos - arr[index].pos) >= Math.abs(arr[index].pos - arr[lastindex].pos)) {
					if (minpos == -1) minpos = i;
					ans = Math.max(ans, dp(i, index, dir) + arr[index].val);					
				}
			}
		}
		
		return memo[index][lastindex][dir] = ans;
	}
	
	static class A implements Comparable<A> {
		int pos; long val;
		public A (int a, long b) {
			pos = a; val = b;
		}
		public int compareTo(A o) {
			return pos - o.pos;
		}
		void print() {
			System.out.println(pos + " " + val);
		}
	}
}