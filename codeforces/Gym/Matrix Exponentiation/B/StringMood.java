
import java.util.*;
import java.io.*;

public class StringMood {

	// https://codeforces.com/gym/102644/problem/B
	
	static long mod = (long)1e9 + 7;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("StringMood"));

		long n = Long.parseLong(in.readLine());
		
		long[][] change = {{19, 7}, {6, 20}};
		long[][] ans = new long[2][2];
		ans[0][0] = 1; 
		ans[1][1] = 1;
		while (n > 0) {
			if (n%2 == 1) {
				ans = multiply(ans, change);
			}
			change = multiply(change, change);
			n >>= 1;
		}
		System.out.println(ans[0][0]%mod);
		
	}
	
	public static long[][] multiply (long[][] a, long[][] b) {
		long[][] product = new long[2][2];
		for (int i=0; i<2; i++) {
			for (int j=0; j<2; j++) {
				for (int k=0; k<2; k++) {
					product[i][k] += a[i][j] * b[j][k];
					product[i][k] %= mod;
				}
			}
		}
		return product;
	}
}