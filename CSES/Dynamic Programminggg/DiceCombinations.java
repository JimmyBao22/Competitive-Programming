
import java.util.*;
import java.io.*;

public class DiceCombinations {

	// https://cses.fi/problemset/task/1633
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("DiceCombinations"));

		int n = Integer.parseInt(in.readLine());  
		int[] dp = new int[n+1];
		dp[0] = 1;
		int mod = (int)(1e9+7);
		
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= 6 && i-j >= 0; j++) {
				dp[i] += dp[i-j];
				dp[i] %= mod;
			}
		}
		System.out.println(dp[n]);
	}
}