
import java.util.*;
import java.io.*;

public class cowmbat {

	// http://usaco.org/index.php?page=viewproblem2&cpid=971
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cowmbat.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cowmbat.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		long INF = (long)(1e18);
		
		if (k > n) {
			out.print(0);
			out.close();
			return;
		}
		
		String orig = in.readLine();
		long[][] grid = new long[m][m];
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j=0; j<m; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		FW(grid);
				
		long[][] precomp = new long[m][n + 1];
			// character, change over orig cost
			// 1 base indexing
		
		for (int i=0; i<m; i++) {
			for (int j=0; j<n; j++) {
				precomp[i][j+1] = grid[orig.charAt(j) - 'a'][i] + precomp[i][j];
			}
		}
		
		long[][] dp = new long[n][m];
			// index, character
		
		long[] min = new long[n];
		Arrays.fill(min, INF);
		
		for (int i=0; i<m; i++) {
			dp[k-1][i] = precomp[i][k];
			min[k-1] = Math.min(min[k-1], dp[k-1][i]);
		}
		
		for (int i=k; i<n; i++) {
			for (int j=0; j<m; j++) {
				// dp[i][j]
				dp[i][j] = dp[i-1][j] + grid[orig.charAt(i) - 'a'][j];
				dp[i][j] = Math.min(dp[i][j], min[i-k] + precomp[j][i+1] - precomp[j][i - k + 1]);
				min[i] = Math.min(min[i], dp[i][j]);
			}
		}
				
		System.out.println(min[n-1]);
		out.println(min[n-1]);
		out.close();
	}
	
	public static void FW (long[][] grid) {
		int m = grid.length;
		for (int k=0; k<m; k++) {
			for (int i=0; i<m; i++) {
				for (int j=0; j<m; j++) {
					grid[i][j] = Math.min(grid[i][j], grid[i][k] + grid[k][j]);
				}
			}
		}
	}
	
	public static void print(long[][] grid) {
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[0].length; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}