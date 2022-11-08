
import java.util.*;
import java.io.*;

public class Matching {

	// https://atcoder.jp/contests/dp/tasks/dp_o
	
	static int n;
	static int[][] grid;
	static long[] memo;
	static long mod = (long)(1e9+7);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Matching"));

		n = Integer.parseInt(in.readLine());
		grid = new int[n][n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			for (int j=0; j<n; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		memo = new long[(1 << (n+1)) + 1];
			// loop over rows
			// binary representation represents what has been taken in columns
		
		Arrays.fill(memo, -1);
		
		System.out.println(dp(0));
	}
	
	public static long dp(int binary) {
		int index = Integer.bitCount(binary);
		if (index >= n) return 1;
		if (memo[binary] != -1) return memo[binary];
		long ans=0;
		
		for (int i=0; i<n; i++) {
			if (((binary >> i) & 1) == 1) continue;		// already taken at this pos
			else {
				if (grid[index][i] == 1) { 		// can take this position
					ans += dp(binary + (1 << i));
					if (ans > mod) ans -= mod;
				}
			}
		}
		
		return memo[binary] = ans;
	}
}
