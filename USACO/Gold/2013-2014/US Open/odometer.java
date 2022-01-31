
import java.util.*;
import java.io.*;

public class odometer {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=435
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("odometer.in"));
		PrintWriter out = new PrintWriter(new FileWriter("odometer.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		long x = Long.parseLong(st.nextToken());
		long y = Long.parseLong(st.nextToken());
		
		long ans = f(y) - f(x-1);	
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
		// f(x) is how many work from 1 to x
	public static long f(long x) {
		String xstring = x + "";
		int n = xstring.length();
		long ans=0;
		
		for (int t=1; t<n; t++) {
			long[][][] memo = new long[t][10][19];	
				// index, number you're keeping track of, count
			for (int i=0; i<t; i++) {
				for (int j=0; j<10; j++) {
					Arrays.fill(memo[i][j], -1);
				}
			}
			
			for (int i=1; i<10; i++) {
				// i is special
				ans += dp1(memo, 1, i, 1);			// pos 0 took
				ans += 8*dp1(memo, 1, i, 0); 		// pos 0 did not take
			}
			
			// i = 0
			ans += 9*dp1(memo, 1, 0, 0);			// pos 0 did not take
		}
		
		// t = n
		long[][][][] memo = new long[n][10][19][2];
		// index, number you're keeping track of, count, on=1/under=0
		for (int i=0; i<n; i++) {
			for (int j=0; j<10; j++) {
				for (int k=0; k<19; k++) {
					Arrays.fill(memo[i][j][k], -1);					
				}
			}
		}
		
		for (int j=0; j<10; j++) {
			// j is val
			for (int i=1; i<xstring.charAt(0)-'0'; i++) {		// take i
				if (i == j) {
					ans += dp2(xstring, memo, 1, j, 1, 0);					
				}
				else {
					ans += dp2(xstring, memo, 1, j, 0, 0);
				}
			}			
		}

		
		// take xstring.charAt(0)-'0'
		for (int i=0; i<10; i++) {		// i is val
			if (i == xstring.charAt(0) - '0') {
				ans += dp2(xstring, memo, 1, i, 1, 1);
			}
			else {
				ans += dp2(xstring, memo, 1, i, 0, 1);				
			}
		}
		
		// overlap
		// if even length and both same # characters, then it overcounts (ex. 3355)
		
		long overlap=0;
		for (int t=2; t<n; t+=2) {
			long[][][][] memo2 = new long[t][10][10][19];	
			// index, number you're keeping track of, second number keeping track of, count
			for (int i=0; i<t; i++) {
				for (int j=0; j<10; j++) {
					for (int k=0; k<10; k++) {
						Arrays.fill(memo2[i][j][k], -1);						
					}
				}
			}
			
			for (int j=0; j<10; j++) {
				// j is val
				for (int k=j+1; k<10; k++) {
					// k is second val

					if (j != 0) {		// take j
						overlap += dp3(memo2, 1, j, k, 1);
					}
					if (k != 0) {		// take k
						overlap += dp3(memo2, 1, j, k, 0);						
					}
				}
			}
		}
		
		if (n % 2 == 0) {
			// t = n
			long[][][][][] memo2 = new long[n][10][10][19][2];
			// index, number you're keeping track of, second number keeping track of, count, on=1/under=0
			for (int i=0; i<n; i++) {
				for (int j=0; j<10; j++) {
					for (int k=0; k<10; k++) {
						for (int g=0; g<19; g++) {
								Arrays.fill(memo2[i][j][k][g], -1);	
						}
					}
				}
			}
			
			for (int j=0; j<10; j++) {
				// j is val
				for (int k=j+1; k<10; k++) {
					// k is second val
					
					if (j != 0 && j<xstring.charAt(0) - '0') {		// take j
						overlap += dp4(xstring, memo2, 1, j, k, 1, 0);
					}
					if (k != 0 && k<xstring.charAt(0) - '0') {		// take k
						overlap += dp4(xstring, memo2, 1, j, k, 0, 0);						
					}
				}
			}
			
			// k is second val
			int k = xstring.charAt(0) - '0';
			for (int j=0; j<10; j++) {
				// j is val
				if (j == k) continue;
				
				// take k
				overlap += dp4(xstring, memo2, 1, j, k, 0, 1);
			}
		}
		
		return ans - overlap;
	}
		
	public static long dp4(String xstring, long[][][][][] memo2, int index, int val1, int val2, int count, int on) {
		if (index >= memo2.length) {
			if (count*2 == memo2.length) return 1;
			return 0;
		}
		if (memo2[index][val1][val2][count][on] != -1) return memo2[index][val1][val2][count][on];
		long ans=0;
		
		if (on == 0) {		// under
			// take val1
			ans += dp4(xstring, memo2, index+1, val1, val2, count+1, 0);
			
			// take val2
			ans += dp4(xstring, memo2, index+1, val1, val2, count, 0);			
		}
		else {				// on
			if (val1 < xstring.charAt(index) - '0') {		// take val1
				ans += dp4(xstring, memo2, index+1, val1, val2, count+1, 0);
			}
			else if (val1 == xstring.charAt(index) - '0') {		// take val1
				ans += dp4(xstring, memo2, index+1, val1, val2, count+1, 1);
			}
			
			if (val2 < xstring.charAt(index) - '0') {		// take val2
				ans += dp4(xstring, memo2, index+1, val1, val2, count, 0);
			}
			else if (val2 == xstring.charAt(index) - '0') {		// take val2
				ans += dp4(xstring, memo2, index+1, val1, val2, count, 1);
			}
		}
		
		return memo2[index][val1][val2][count][on] = ans;
	}
	
	public static long dp3(long[][][][] memo2, int index, int val1, int val2, int count) {
		if (index >= memo2.length) {
			if (count*2 == memo2.length) return 1;
			return 0;
		}
		if (memo2[index][val1][val2][count] != -1) return memo2[index][val1][val2][count];
		long ans=0;
		
		// take val1
		ans += dp3(memo2, index+1, val1, val2, count+1);
		
		// take val2
		ans += dp3(memo2, index+1, val1, val2, count);
		
		return memo2[index][val1][val2][count] = ans;
	}
	
	public static long dp2(String xstring, long[][][][] memo, int index, int val, int count, int on) {
		if (index >= memo.length) {
			if (count*2 >= memo.length) return 1;
			return 0;
		}
		if (memo[index][val][count][on] != -1) return memo[index][val][count][on];
		long ans = 0;
		
		if (on == 0) {			// under
			
			// take val
			ans += dp2(xstring, memo, index+1, val, count+1, 0);
			
			// don't take val
			ans += 9 * dp2(xstring, memo, index+1, val, count, 0);
		}
		else {					// on
			for (int j=0; j<xstring.charAt(index)-'0'; j++) {
				if (j == val) {
					ans += dp2(xstring, memo, index+1, val, count+1, 0);
				}
				else {
					ans += dp2(xstring, memo, index+1, val, count, 0);
				}	
			}
			
			// j = xstring.charAt(index)-'0'
			if (xstring.charAt(index)-'0' == val) {
				ans += dp2(xstring, memo, index+1, val, count+1, 1);
			}
			else {
				ans += dp2(xstring, memo, index+1, val, count, 1);				
			}
		}
		
		return memo[index][val][count][on] = ans;
	}
	
	public static long dp1(long[][][] memo, int index, int val, int count) {
		if (index >= memo.length) {
			if (count*2 >= memo.length) return 1;
			return 0;
		}
		if (memo[index][val][count] != -1) return memo[index][val][count];
		long ans = 0;
		
		// take val
		ans += dp1(memo, index+1, val, count+1);
		
		// don't take val
		ans += 9 * dp1(memo, index+1, val, count);
		
		return memo[index][val][count] = ans;
	}
}