
import java.util.*;
import java.io.*;

public class hopscotch {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=528
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("hopscotch.in"));
		PrintWriter out = new PrintWriter(new FileWriter("hopscotch.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		char[][] arr = new char[n][];
		for (int i=0; i<n; i++) {
			arr[i] = in.readLine().toCharArray();
		}
		
		long[][] dp = new long[n][m];
		dp[0][0] = 1;
		for (int i=1; i<n; i++) {
			for (int j=1; j<m; j++) {
				for (int k=0; k<i; k++) {
					for (int t=0; t<j; t++) {
						if (arr[i][j] != arr[k][t]) dp[i][j] += dp[k][t];
					}
				}
			}
		}
		
		System.out.println(dp[n-1][m-1]);
		out.println(dp[n-1][m-1]);
		out.close();
	}
}