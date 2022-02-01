
import java.util.*;
import java.io.*;

public class kTree {
	
	// https://codeforces.com/contest/431/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("kTree"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());

		long sum=0;
		long mod = (long)1e9 +7;
		long[] dp1 = new long[n+1];
		long[] dp2 = new long[n+1];
		
		for (int i=1; i<=k && i<=n; i++) {
			dp1[i]=1;
		}
		sum += dp1[n];
		for (int j=1; j<d && j<=n; j++) {
			dp2[j] = 1;
		}
		sum -= dp2[n];
		
		for (int i=0; i<n; i++) {
			long[] dp1temp = new long[n+1];
			for (int j=1; j<=k; j++) {
				for (int z=1; z+j<=n; z++) {
					dp1temp[z+j] += dp1[z];
					dp1temp[z+j] %= mod;
				}
			}
			sum += dp1temp[n];
			sum %= mod;
			dp1 = dp1temp;
			
			long[] dp2temp = new long[n+1];
			for (int j=1; j<d; j++) {
				for (int z=1; z+j<=n; z++) {
					dp2temp[z+j] += dp2[z];
					dp2temp[z+j] %= mod;
				}
			}
			sum -= dp2temp[n];
			sum %= mod;
			dp2 = dp2temp;
		}
		sum %= mod;
		if (sum < 0) sum += mod;
		System.out.println(sum);
		
	}
}

// total - ones with all edges < d
