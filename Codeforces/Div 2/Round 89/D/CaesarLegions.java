
import java.util.*;
import java.io.*;

public class CaesarLegions {

	// https://codeforces.com/contest/118/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("CaesarLegions"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n1 = Integer.parseInt(st.nextToken());
		int n2 = Integer.parseInt(st.nextToken());
		int k1 =Integer.parseInt(st.nextToken());
		int k2 = Integer.parseInt(st.nextToken());
		long mod = (long)1e8;
		
		long[][][][] dp = new long[n1+1][n2+1][k1+1][k2+1];
		dp[0][1][0][1] = 1;
		dp[1][0][1][0] = 1;
		
		for (int sum=2; sum<=n1+n2; sum++) {
			for (int first=0; first<=n1; first++) {
				int second = sum-first;
				if (second < 0 || second>n2) continue;
				
				// dp[first][second]
				// add to first
					// dp[first-1][second]
				for (int j=0; j<k1; j++) {  // j cannot be k1
					if (first>=1)dp[first][second][j+1][0] += dp[first-1][second][j][0];
					dp[first][second][j+1][0] %= mod;
				}
				for (int j=0; j<k2+1; j++) {  
					if (first>=1)dp[first][second][1][0] += dp[first-1][second][0][j];
					dp[first][second][1][0] %= mod;
				}
				
				// add to second
					// dp[first][second-1]
				for (int j=0; j<k2; j++) {  // j cannot be k2
					if (second>=1)dp[first][second][0][j+1] += dp[first][second-1][0][j];
					dp[first][second][0][j+1] %= mod;
				}
				for (int j=0; j<k1+1; j++) {  
					if (second>=1)dp[first][second][0][1] += dp[first][second-1][j][0];
					dp[first][second][0][1] %= mod;
				}
			}
		}
		
		long sum=0;
		for (int first=0; first<=n1; first++) {
			int second = n1+n2-first;
			if (second < 0 || second>n2) continue;
			for (int j=0; j<k2+1; j++) {
				for (int i=0; i<k1+1; i++) {
					sum += dp[first][second][i][j];
					sum %= mod;
				}
			}
		}
		
		sum %= mod;
		System.out.println(sum);
		
	}
}
