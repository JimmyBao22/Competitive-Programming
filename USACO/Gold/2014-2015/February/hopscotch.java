
import java.util.*;
import java.io.*;

public class hopscotch {

	// http://usaco.org/index.php?page=viewproblem2&cpid=530
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("hopscotch.in"));
		PrintWriter out = new PrintWriter(new FileWriter("hopscotch.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		long mod = (long)(1e9+7);
		int[][] arr = new int[r][c];
		for (int i=0; i<r; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j=0; j<c; j++) arr[i][j] = Integer.parseInt(st.nextToken());
		}
		
		long[][] dp = new long[r][c];
		dp[0][0] = 1;
		
		for (int i=1; i<r; i++) {
			for (int j=1; j<c; j++) {
				// current is dp[i][j]
				
				for (int f=0; f<i; f++) {
					for (int t=0; t<j; t++) {
						if (arr[i][j] != arr[f][t]) {		// only jump to diff integer
							dp[i][j] += dp[f][t];
							dp[i][j]%=mod;
						}
					}
				}
				
			}
		}

		System.out.println(dp[r-1][c-1]);
		out.println(dp[r-1][c-1]);
		out.close();
	}
}