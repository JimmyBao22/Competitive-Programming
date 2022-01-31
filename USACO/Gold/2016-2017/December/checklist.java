
import java.util.*;
import java.io.*;

public class checklist {

	// http://usaco.org/index.php?page=viewproblem2&cpid=670
	
	static long[][] harr;
	static long[][] garr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("checklist.in"));
		PrintWriter out = new PrintWriter(new FileWriter("checklist.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int h = Integer.parseInt(st.nextToken());
		int g = Integer.parseInt(st.nextToken());
		harr = new long[h][2];
		garr = new long[g][2];
		for (int i=0; i<h; i++) {
			st = new StringTokenizer(in.readLine());
			harr[i][0] = Integer.parseInt(st.nextToken());
			harr[i][1] = Integer.parseInt(st.nextToken());
		}
		for (int i=0; i<g; i++) {
			st = new StringTokenizer(in.readLine());
			garr[i][0] = Integer.parseInt(st.nextToken());
			garr[i][1] = Integer.parseInt(st.nextToken());
		}
		
		long[][][] memo = new long[h][g][2];
			// h, g, pos
		
		for (int i=0; i<h; i++) {
			for (int j=0; j<g; j++) {
				Arrays.fill(memo[i][j], -1);
			}
		}
		
		long ans = dp(memo, 1, 0, 0);
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static long dp(long[][][] memo, int h, int g, int pos) {
		if (h>=memo.length) {
			// want to end on H, but still have G left
			return (long)(1e18);
		}
		if (g>=memo[0].length) {
			long res=distance(garr[garr.length-1], harr[h]);
			for (int i=h+1; i<harr.length; i++) {
				res += distance(harr[i], harr[i-1]);
			}
			return res;
		}
		if (memo[h][g][pos] != -1) return memo[h][g][pos];
		
		long res=0;
		if (pos == 0) {
			// go to h
			res = dp(memo, h+1, g, 0) + distance(harr[h-1], harr[h]);
			
			// go to g
			res = Math.min(res, dp(memo, h, g+1, 1) + distance(harr[h-1], garr[g]));
		}
		else {
			// go to h
			res = dp(memo, h+1, g, 0) + distance(harr[h], garr[g-1]);
			 
			// go to g
			res = Math.min(res, dp(memo, h, g+1, 1) + distance(garr[g-1], garr[g]));
		}
		
		return memo[h][g][pos] = res;
	}
	
	public static long distance (long[] a, long[] b) {
		return (a[0] - b[0])*(a[0] - b[0]) + (a[1] - b[1])*(a[1] - b[1]);
	}
}