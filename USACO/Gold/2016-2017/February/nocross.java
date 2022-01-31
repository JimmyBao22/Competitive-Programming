
import java.util.*;
import java.io.*;

public class nocross {

	// http://usaco.org/index.php?page=viewproblem2&cpid=718
	
	static int[] one;
	static int[] two;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("nocross.in"));
		PrintWriter out = new PrintWriter(new FileWriter("nocross.out"));

		int n = Integer.parseInt(in.readLine());
		one = new int[n];
		two = new int[n];
		for (int i=0; i<n; i++) {
			one[i] = Integer.parseInt(in.readLine());
		}
		for (int i=0; i<n; i++) {
			two[i] = Integer.parseInt(in.readLine());
		}
		
		int[][] memo = new int[n][n];
		
		for (int i=0; i<n; i++) Arrays.fill(memo[i], -1);
		
		int ans = dp(memo, 0, 0);
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static int dp(int[][] memo, int x, int y) {
		if (x>=memo.length || y>= memo.length) return 0;
		if (memo[x][y] != -1) return memo[x][y];
		
		int ans=0;
		// dont take for x
		ans = Math.max(ans, dp(memo, x, y+1));
		// don't take for y
		ans = Math.max(ans, dp(memo, x+1, y));
		
		if (Math.abs(one[x] - two[y]) <= 4) {
			// take!
			ans = Math.max(ans, dp(memo, x+1, y+1)+1);
		}
		
		return memo[x][y] = ans;
	}
}