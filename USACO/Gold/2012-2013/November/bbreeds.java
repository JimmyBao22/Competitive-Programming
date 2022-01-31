
import java.util.*;
import java.io.*;

public class bbreeds {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=192
	
	static String s;
	static int n;
	static int[] psum;
	static int mod = 2012;
	static int[][] memo;
		// index, how many for H
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("bbreeds.in"));
		PrintWriter out = new PrintWriter(new FileWriter("bbreeds.out"));

		s = in.readLine();
		n = s.length();

		psum = new int[n];
		memo = new int[n][501];
		for (int i=0; i<n; i++) Arrays.fill(memo[i], -1);
		
		if (s.charAt(0) == ')') {
			out.println(0);
			out.close();
			return;
		}
		
		psum[0] = 1;
		for (int i=1; i<n; i++) {
			psum[i] = psum[i-1];
			if (s.charAt(i) == '(') psum[i]++;
			else psum[i]--;
		}
				
		if (psum[n-1] != 0) {
			out.println(0);
			out.close();
			return;
		}
		
		int ans = dp(0, 0);
		ans %= mod;

		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static int dp(int index, int H) {
		if (index >= n) {
			if (H == 0) return 1;
			else return 0;
		}
		if (H < 0 || (index != 0 && psum[index-1] - H < 0)) return 0;
		
		if (memo[index][H] != -1) return memo[index][H];
		
		int ans=0;
		
		if (s.charAt(index) == '(') {
			ans += dp(index+1, H+1);
			ans += dp(index + 1, H);
			ans %= mod;
		}
		else {
			ans += dp(index+1, H-1);
			ans += dp(index+1, H);
			ans %= mod;
		}
		
		return memo[index][H] = ans;
		
	}
}