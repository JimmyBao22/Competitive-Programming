
import java.util.*;
import java.io.*;

public class ApolloversusPan {

	// https://codeforces.com/contest/1466/problem/E
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("five"));

		int t = Integer.parseInt(in.readLine());
		while (t-->0) {
			int n = Integer.parseInt(in.readLine());
			StringTokenizer st = new StringTokenizer(in.readLine());
			long[] arr = new long[n];
			for (int i=0; i<n; i++) {
				arr[i] = Long.parseLong(st.nextToken());
			}
			
			int m = 61;
			long mod = (long)1e9+7;
			long[] pow2 = new long[m];
			pow2[0] = 1;
			for (int i=1; i<m; i++) {
				pow2[i] = pow2[i-1] * 2;
				pow2[i] %= mod;
			}
			
			int[] num = new int[61];
			for (int i=0; i<61; i++) {
				for (int j=0; j<n; j++) {
					if (((arr[j] >> i) & 1) >= 1) {
						num[i]++;
					}
				}
			}
			
			long[] ands = new long[n];
			for (int i=0; i<n; i++) {
				for (int j=0; j<m; j++) {
					if (((arr[i] >> j) & 1) >= 1) {
						ands[i] += pow2[j] * num[j];
						ands[i] %= mod;
					}
				}
			}
			
			long[] ors = new long[n];
			for (int i=0; i<n; i++) {
				for (int j=0; j<m; j++) {
					if (((arr[i] >> j) & 1) >= 1) {
						ors[i] += pow2[j] * n;
						ors[i] %= mod;
					}
					else {
						ors[i] += pow2[j] * num[j];
						ors[i] %= mod;
					}
				}
			}
			
			long ans=0;
			for (int i=0; i<n; i++) {
				ans += ors[i] * ands[i];
				ans %= mod;
			}
			
			System.out.println(ans);
			
		}
	}
}