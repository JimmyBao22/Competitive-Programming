
import java.util.*;
import java.io.*;

public class hps {

	// http://usaco.org/index.php?page=viewproblem2&cpid=694
	
	static char[] s;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("hps.in"));
		PrintWriter out = new PrintWriter(new FileWriter("hps.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		s = new char[n];
		for (int i=0; i<n; i++) {
			s[i] = in.readLine().charAt(0);
		}
		
		int[][][] memo = new int[n][k+1][3];
			// n, k, character
			// 0 = H, 1 = S, 2 = P
		for (int i=0; i<n; i++) {
			for (int j=0; j<k+1; j++) { 
				Arrays.fill(memo[i][j], -1);
			}
		}
		
		int ans = Math.max(dp(memo, 0, 0, 0), Math.max(dp(memo, 0, 0, 1), dp(memo, 0, 0, 2)));
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static int dp(int[][][] memo, int n, int k, int c) {
		if (n >= memo.length) return 0;
		if (memo[n][k][c] != -1) return memo[n][k][c];
		
		// no change c
		int ans = dp(memo, n+1, k, c);
		if (c == 0 && s[n] == 'S') ans++;
		else if (c == 1 && s[n] == 'P') ans++;
		else if (c == 2 && s[n] == 'H') ans++;
		
		// change c
		if (k+1 < memo[0].length) {
			for (int i=0; i<3; i++) {
				if (i == c) continue;
				if (i == 0) {
					if (s[n] == 'S') ans = Math.max(ans, dp(memo, n+1, k+1, i) +1);
					else ans = Math.max(ans, dp(memo, n+1, k+1, i));
				} 
				else if (i == 1) {
					if (s[n] == 'P') ans = Math.max(ans, dp(memo, n+1, k+1, i) +1);
					else ans = Math.max(ans, dp(memo, n+1, k+1, i));
				} 
				else {
					if (s[n] == 'H') ans = Math.max(ans, dp(memo, n+1, k+1, i) +1);
					else ans = Math.max(ans, dp(memo, n+1, k+1, i));
				}
			}
		}
		
		return memo[n][k][c] = ans;
	}
}