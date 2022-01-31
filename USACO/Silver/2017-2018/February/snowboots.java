
import java.util.*;
import java.io.*;

public class snowboots {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=811
	
	static Boot[] boots;
	static int n;
	static long[] f;
	static int[][] memo;
	static int INF;
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("snowboots.in"));
		PrintWriter out = new PrintWriter(new FileWriter("snowboots.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());
		f = new long[n];
		st = new StringTokenizer(in.readLine());
		memo = new int[n][b];
		for (int i=0; i<n; i++) {
			f[i] = Long.parseLong(st.nextToken());
			Arrays.fill(memo[i], -1);
		}
		
		INF = (int)(2e9+7);
		boots = new Boot[b];
		
		for (int i=0; i<b; i++) {
			st = new StringTokenizer(in.readLine());
			long x = Long.parseLong(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			boots[i] = new Boot(x,y);
		}
		
		int result = dp(0,0);
		
		System.out.println(result);
		out.println(result);
		out.close();

	}
	
	public static int dp (int f_index, int b_index) {
		if (f_index == n-1) return 0;
		if (b_index >= boots.length) return INF;
		if (memo[f_index][b_index] != -1) return memo[f_index][b_index];
		
		if (boots[b_index].depth < f[f_index]) {
			return memo[f_index][b_index] = dp(f_index, b_index+1)+1;   // move to next boot
		}
		
		// boots[b_index].print();
		
		int min = INF;
		for (int i=f_index+1; i<=f_index+boots[b_index].dist && i<n; i++) {
			if (f[i] <= boots[b_index].depth) {
				min = Math.min(min, dp(i, b_index+1)+1);   // move on to next boot
				min = Math.min(min, dp(i, b_index));		// stay on this boot
			}
		}
		// waste this boot
		min = Math.min(min, dp(f_index, b_index+1)+1);
		
		return memo[f_index][b_index] = min;
	}
	
	static class Boot {
		long depth;
		int dist;
		Boot (long a, int b) {
			depth = a;
			dist = b;
		}
		
		public void print() {
			System.out.println(depth + " " + dist);
		}
	}
}