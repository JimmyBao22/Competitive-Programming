
import java.util.*;
import java.io.*;

public class jazz {

	// https://saco-evaluator.org.za/cms/sapo2015z/tasks/jazz/description
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SpaceJazz"));

		String s = in.readLine();
		int n = s.length();
		int[][] dp = new int[n][n];
		int INF = (int)(1e9);
		
		for (int i=0; i<n; i++) {
			Arrays.fill(dp[i], INF);
			dp[i][i] = 1;
		}
		for (int i=0; i+1<n; i++) {
			if (s.charAt(i) == s.charAt(i+1)) {
				dp[i][i+1] = 0;
			}
			else {
				dp[i][i+1] = 2;
			}
		}
		
		for (int l=2; l<n; l++) {
			for (int i=0; i+l<n; i++) {
				int r = i+l;
				if (s.charAt(i) == s.charAt(r)) {
					dp[i][r] = dp[i+1][r-1];
				}
				for (int k=i; k<r; k++) {
					dp[i][r] = Math.min(dp[i][r], dp[i][k] + dp[k+1][r]);
				}
			}
		}
		
		System.out.println(dp[0][n-1]);
	}
}