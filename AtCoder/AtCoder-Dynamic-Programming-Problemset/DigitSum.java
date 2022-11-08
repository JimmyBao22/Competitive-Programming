
import java.util.*;
import java.io.*;

public class DigitSum {

	// https://atcoder.jp/contests/dp/tasks/dp_s
	
	static String k;
	static int d, n;
	static long[][][] memo;
	static long mod = (long)(1e9+7);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("DigitSum"));

		k = in.readLine();
		n = k.length();
		d = Integer.parseInt(in.readLine());

		memo = new long[n][d+1][2];
			// index, sum, on max (1) or under (0)
		
		for (int i=0; i<n; i++) {
			for (int j=0; j<=d; j++) Arrays.fill(memo[i][j], -1);
		}
		
		long ans = 0;
		for (int i=0; i<k.charAt(0) - '0'; i++) {
			ans += dp(1, i%d, 0);
			ans %= mod;
		}
		ans += dp(1, (k.charAt(0) - '0')%d, 1);
		System.out.println((ans%mod-1+mod)%mod);		// subtract 1 bc can't be 0
	}
	
	public static long dp(int index, int sum, int under) {
		if (index >= n) {
			if (sum % d == 0) return 1;
			else return 0;
		}
		
		if (memo[index][sum][under] != -1) return memo[index][sum][under];
		long ans=0;
		
		if (under == 0) {
			// take any here
			for (int i=0; i<=9; ++i) {
				ans += dp(index+1, (sum+i)%d, 0);
				ans %= mod;
			}
		}
		else {
			for (int i=0; i<k.charAt(index)-'0'; i++) {
				ans += dp(index+1, (sum + i)%d, 0);
				ans %= mod;
			}
			ans += dp(index+1, (sum + k.charAt(index)-'0')%d, 1);
			ans %= mod;
		}
		
		return memo[index][sum][under] = ans;
	}
}
