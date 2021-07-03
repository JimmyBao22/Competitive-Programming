
import java.util.*;
import java.io.*;

public class CupcakeDistribution {

	// https://mbit.mbhs.edu/archive/2019/varsity.pdf
	// https://codeforces.com/gym/102802/problem/G

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("CupcakeDistribution"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		double[] s = new double[n];
		double[] psum = new double[n];		// normal
		double[] psums = new double[n];		// squares
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			s[i] = Integer.parseInt(st.nextToken());
			if (i != 0) psum[i] = s[i] + psum[i-1];
			else psum[0] = s[0];
			if (i != 0) psums[i] = s[i] * s[i] + psums[i-1];
			else psums[0] = s[0] * s[0];
		}
		
		double INF = (long)(1e18);
		
		double[] dp = new double[n];
			// min way up to this index
		
		Arrays.fill(dp, INF);
		
		double val = psum[k-1]/(k);
		dp[k-1] = psums[k-1] + (val * val) * k - 2*val*(psum[k-1]);
		
		for (int j=k; j<n; j++) {
			
			// from 0
			val = psum[j]/(j+1);
			dp[j] = psums[j] + (val * val) * (j+1) - 2*val*(psum[j]);
			
			for (int i=k; i<=j-k+1; i++) {
				// dp[i-1] to dp[j]
				
				val = (psum[j] - psum[i-1])/(j - i + 1);
				dp[j] = Math.min(dp[j], dp[i-1] + psums[j] - psums[i-1] 
						+ (val * val) * (j - i + 1) - 2*val*(psum[j] - psum[i-1]));
			}
		}
		
		System.out.println((long)dp[n-1]);
	}
}


/*
			
//for (int g=i; g<=j; g++) sum += (s[g] - val)*(s[g] - val); 
//for (int g=i; g<=j; g++) sum += s[g]^2 - 2*s[g]*val + val^2 
//psums[j] - psums[i-1] + (val^2)(j - i + 1) - 2*val*(psum[j] - psum[i-1])
			
	
	below got AC with 1996ms
	
	int m = n/k+1;
		double[][] dp = new double[n][m];
			// index, times

		for (int i=0; i<n; i++) {
			Arrays.fill(dp[i], INF);
		}
		
		// dp[i][1]
		for (int i=k-1; i<n; i++) {
			double val = psum[i]/(i+1);
			dp[i][1] = psums[i] + (val * val) * (i + 1)
					- 2*val*(psum[i]);
		}
		
		for (int t=2; t<m; t++) {
			for (int j=t*k-1; j<n; j++) {
				for (int i=(t-1)*k; i<=j-k+1; i++) {
					// dp[i-1][t-1] to dp[j][t];
					
					double val = (psum[j] - psum[i-1])/(j - i + 1);
					dp[j][t] = Math.min(dp[j][t], dp[i-1][t-1] + 
							psums[j] - psums[i-1] + (val * val) * (j - i + 1)
							- 2*val*(psum[j] - psum[i-1]));
				}
			}
		}
		
		long ans=(long)INF;
		for (int j=1; j<m; j++) {
			ans = Math.min(ans, (long)dp[n-1][j]);
		}
		System.out.println(ans);					
*/