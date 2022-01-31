
import java.util.*;
import java.io.*;

public class lazy {

	// http://usaco.org/index.php?page=viewproblem2&cpid=416
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("lazy.in"));
		PrintWriter out = new PrintWriter(new FileWriter("lazy.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[][] beg = new int[n][n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j=0; j<n; j++) {
				beg[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int[][] changed = new int[1000][1000];
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				int newx = i - j + n + 1;
				int newy = i + j + 1;
				changed[newx][newy] = beg[i][j];
			}
		}
		n = changed.length;
		
		int[][] psum = new int[n][n];
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				psum[i][j] = changed[i][j];
				if (i-1>=0) psum[i][j] += psum[i-1][j];
				if (j-1>=0) psum[i][j] += psum[i][j-1];
				if (i-1>=0 && j-1>=0) psum[i][j] -= psum[i-1][j-1];
			}
		}
		
		int ans=0;
		k = k*2+1;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				int cur = psum[i][j];
				if (i-k>=0) cur -= psum[i-k][j];
				if (j-k >= 0) cur -= psum[i][j-k];
				if (i-k>=0 && j-k>=0) cur += psum[i-k][j-k];
				ans = Math.max(ans, cur);
			}
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
}

/*
	Think about rotating it so you can get normal square
	Then shift it so every coordinate is positive.
		Elements will be in every other square over a big area
		Normal prefix sums over 2k and 2n instead of k and n
*/