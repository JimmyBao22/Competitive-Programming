
import java.util.*;
import java.io.*;

public class fortmoo {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=600
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("fortmoo.in"));
		PrintWriter out = new PrintWriter(new FileWriter("fortmoo.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		char[][] grid = new char[n][];
		for (int i=0; i<n; i++) {
			grid[i] = in.readLine().toCharArray();
		}
		
		int[][] hor = new int[n][m];
		int[][] vert = new int[n][m];
		for (int i=0; i<n; i++) {
			if (grid[i][0] != 'X') hor[i][0] = 1;
		}
		for (int i=0; i<n; i++) {
			for (int j=1; j<m; j++) {
				if (grid[i][j] != 'X') hor[i][j] = hor[i][j-1] + 1;
			}
		}
		
		for (int j=0; j<m; j++) {
			if (grid[0][j] != 'X') vert[0][j] = 1;
		}
		for (int i=1; i<n; i++) {
			for (int j=0; j<m; j++) {
				if (grid[i][j] != 'X') vert[i][j] = vert[i-1][j] + 1;
			}
		}
		
//		print(hor);
//		print(vert);
		
		// O(n^4) passes...
		
		int ans=0;
		for (int i=n-1; i>=0; i--) {
			for (int j=m-1; j>=0; j--) {
				int left = hor[i][j];
				for (int k=0; k<left; k++ ) {
					int up = Math.min(vert[i][j], vert[i][j-k]);
					for (int h=up-1; h>=0; h--) {
						if (hor[i-h][j] >= k) {
							ans = Math.max(ans, (k+1)*(h+1));
							// System.out.println(i + " " + j + " " + k + " " + h + " " + ans);
							break;
						}
					}
				}
			}
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	static void print(int[][] arr) {
		for (int i=0; i<arr.length; i++) {
			System.out.println(Arrays.toString(arr[i]));
		}
		System.out.println();
	}
}