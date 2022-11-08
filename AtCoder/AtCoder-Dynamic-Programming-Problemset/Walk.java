
import java.util.*;
import java.io.*;

public class Walk {

	// https://atcoder.jp/contests/dp/tasks/dp_r
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Walk"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long k = Long.parseLong(st.nextToken());
		long mod = (long)(1e9+7);
		
		long[][] arr = new long[n][n];
		long[][] ans = new long[n][n];
		for (int i=0; i<n; i++) {
			ans[i][i] = 1;		// identity matrix
			
			st = new StringTokenizer(in.readLine());
			for (int j=0; j<n; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		while (k > 0) {
			if (k%2 == 1) {
				ans = multiply(n, ans, arr, mod);
			}
			arr = multiply(n, arr, arr, mod);
			k >>= 1;
		}
		
		long answer=0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				answer += ans[i][j];
				answer %= mod;
			}
		}
		System.out.println(answer);
	}
	
	public static long[][] multiply (int n, long[][] a, long[][] b, long mod) {
		long[][] product = new long[n][n];
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				for (int k=0; k<n; k++) {
					product[i][k] += a[i][j] * b[j][k];
					product[i][k] %= mod;
				}
			}
		}
		return product;
	}
}

// matrix exponentation