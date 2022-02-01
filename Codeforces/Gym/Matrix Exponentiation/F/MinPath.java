
import java.util.*;
import java.io.*;

public class MinPath {

	// https://codeforces.com/gym/102644/problem/F
	
	static long max = (long)(3e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MinPath"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		long[][] arr = new long[n][n];
		long[][] prod = new long[n][n];
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				arr[i][j] = max;
				prod[i][j] = max;
			}
		}
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			long c = Integer.parseInt(st.nextToken());
			arr[a][b] = c;
		}

		for (int i=0; i<n; i++) prod[i][i] = 0;
		while (k > 0) {
			if (k%2 == 1) {
				prod = mult(prod, arr);
			}
			arr = mult(arr, arr);
			k >>= 1;
		}
		
		long ans=max;
		for (int i=0; i<prod.length; i++) {
			for (int j=0; j<prod.length; j++) {
				ans = Math.min(ans, prod[i][j]);
			}
		}
		
		if (ans >= max/2) {
			System.out.println("IMPOSSIBLE");
		}
		else System.out.println(ans);
		
	}
	
	public static long[][] mult(long[][] a, long[][] b) {
		long[][] product = new long[a.length][b.length];
		for (int i=0; i<product.length; i++) {
			for (int j=0; j<product.length; j++) {
				product[i][j] = max;
			}
		}
		for (int i=0; i<a.length; i++) {
			for (int j=0; j<a.length; j++) {
				for (int k=0; k<a.length; k++) {
					product[i][k] = Math.min(product[i][k], a[i][j] + b[j][k]);
				}
			}
		}
		return product;
	}
}