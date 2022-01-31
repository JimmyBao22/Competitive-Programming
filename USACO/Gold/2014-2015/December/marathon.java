
import java.util.*;
import java.io.*;

public class marathon {

	// http://usaco.org/index.php?page=viewproblem2&cpid=492
	
	static int[][] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("marathon.in"));
		PrintWriter out = new PrintWriter(new FileWriter("marathon.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		arr = new int[n][2];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		
		int[][][] memo = new int[n][n][k+1];
			// curpos, lastpos visited, number k used
		
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				Arrays.fill(memo[i][j], -1);
			}
		}
		
		int ans = dp(memo, 0, 0, 0);
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static int dp(int[][][] memo, int curpos, int lastpos, int k) {
		if (curpos >= memo.length-1) {
			return dist(arr[arr.length-1], arr[lastpos]);
		}
		if (memo[curpos][lastpos][k] != -1) return memo[curpos][lastpos][k];
		
		// go to this position, then continue
		int ans = dp(memo, curpos+1, curpos, k) + dist(arr[curpos], arr[lastpos]);
		
		// skip this pos
		if (k+1 < memo[0][0].length) ans = Math.min(ans, dp(memo, curpos+1, lastpos, k+1));
		
		return memo[curpos][lastpos][k] = ans;
	}
	
	public static int dist(int[] a, int[] b) {
		return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
	}
}