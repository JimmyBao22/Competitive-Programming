
import java.util.*;
import java.io.*;

public class CountPaths {

	// https://codeforces.com/gym/102644/problem/D
	
	static long mod = (long)(1e9) + 7;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("CountPaths"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		long[][] arr = new long[n][n];
		long[][] ans = new long[n][n];
		for (int i=0; i<n; i++) {
			ans[i][i] = 1;  	// at 0 moves, only diagonals are 1's
		}
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			arr[a][b] = 1;	// beginning with 1 move
		}
		
		// arr[i][j] and ans[i][j] means number of paths from vertex i to j
		
		while (k > 0) {
			if (k%2 == 1) {
				ans = mult(ans, arr);
			}
			arr = mult(arr, arr);
			k >>= 1;
		}
		
		long sum=0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				sum += ans[i][j];
				sum %= mod;
			}
		}
		System.out.println(sum);
		
	}
	
	public static long[][] mult(long[][] a, long[][] b) {
		long[][] product = new long[a.length][b.length];
		for (int i=0; i<a.length; i++) {
			for (int j=0; j<a.length; j++) {
				for (int k=0; k<a.length; k++) {
					product[i][k] += a[i][j] * b[j][k];
					product[i][k] %= mod;
				}
			}
		}
		return product;
	}
}
