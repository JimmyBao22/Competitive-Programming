
import java.util.*;
import java.io.*;

public class ClassyNumbers {

	// https://codeforces.com/gym/335765/problem/A
	
	static long[][][][] memo;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ClassyNumbers"));
				
		StringBuilder s = new StringBuilder();
		int t = Integer.parseInt(in.readLine());
		while (t-- > 0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			long l = Long.parseLong(st.nextToken());
			long r = Long.parseLong(st.nextToken());
			int n = (r + "").length();
			memo = new long[n+1][n][4][2];
				// length, index, count, exact/under
			
			for (int k=0; k<n+1; k++) {
				for (int i=0; i<n; i++) {
					for (int j=0; j<memo[0][0].length; j++) {
						Arrays.fill(memo[k][i][j], -1);
					}
				}
			}
			
			long ret1 = calc(r);
			
			for (int k=0; k<n+1; k++) {
				for (int i=0; i<n; i++) {
					for (int j=0; j<memo[0][0].length; j++) {
						Arrays.fill(memo[k][i][j], -1);
					}
				}
			}
			
			long ret2 = calc(l-1);
			
			s.append(ret1 - ret2);
			s.append("\n");
			
		}
		
		System.out.print(s);

	}
	
	public static long calc(long x) {
		if (x == 0) return 0;
		String xx = x + "";
		int n = xx.length();
		long ans = 0;
		for (int m=1; m<n; m++) {	// number of digits
			// first digit cannot be 0
			ans += 9 * dp(m, 1, 1, 0, xx);
		}
		
		// m = n
		for (int i=1; i<xx.charAt(0) - '0'; i++) {		// first digit cannot be 0
			ans += dp(n, 1, 1, 0, xx);	
		}
		
		// m = n, i = xx.charAt(0) - '0'
		ans += dp(n, 1, 1, 1, xx);
		
		return ans;
	}
	
	public static long dp(int n, int i, int count, int exact, String x) {
		if (i >= n) return 1;
		if (memo[n][i][count][exact] != -1) return memo[n][i][count][exact];
		
		long ans = 0;
		
		if (exact == 1) {
			// keep exact
			if (x.charAt(i) == '0') {
				ans += dp(n, i+1, count, 1, x);
			}
			else if (count < 3) {
				ans += dp(n, i+1, count+1, 1, x);
			}
			
			// do not keep exact
			for (int j=0; j<x.charAt(i) - '0'; j++) {
				if (j == 0) {
					ans += dp(n, i+1, count, 0, x);
				}
				else if (count < 3) {
					ans += dp(n, i+1, count+1, 0, x);
				}
			}
		}
		else {
			for (int j=0; j<=9; j++) {
				if (j == 0) {
					ans += dp(n, i+1, count, 0, x);
				}
				else if (count < 3) {
					ans += dp(n, i+1, count+1, 0, x);
				}
			}
		}
		
		return memo[n][i][count][exact] = ans;
	}
}