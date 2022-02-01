
import java.util.*;
import java.io.*;

public class LogoTurtle {

	// https://codeforces.com/contest/132/problem/C
	
	static String s;
	static int n, m, l, start, INF = (int)(1e9);
	static int[][][][] memo;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("LogoTurtle"));

		int t = 1;
		while (t-- > 0) {
			s = in.readLine();
			n = s.length();
			m = Integer.parseInt(in.readLine());
			start = 2*n;
			l = 4*n;
			
			memo = new int[4*n][n][m+1][2];
				// position (start = 2*n), index, turned how many, facing left or right

			for (int i=0; i<l; i++) {
				for (int j=0; j<n; j++) {
					for (int k=0; k<m+1; k++) {
						Arrays.fill(memo[i][j][k], -1);						
					}
				}
			}
			
			System.out.println(dp(start, 0, 0, 1));
			
		}
	}
	
	public static int dp(int pos, int index, int turn, int right) {
		if (index >= n) {
			if ((m - turn)%2 == 0) return Math.abs(pos - start);		
				// even number of turns left (can change the same one multiple times w/o affecting ans
			else return -INF;
		}
		if (memo[pos][index][turn][right] != -1) return memo[pos][index][turn][right];
		int ans=-INF;
		
		// don't change
		if (s.charAt(index) == 'T') {
			ans = Math.max(ans, dp(pos, index+1, turn, right^1));
		}
		else {
			if (right == 1) {
				ans = Math.max(ans, dp(pos+1, index+1, turn, right));
			}
			else {
				ans = Math.max(ans, dp(pos-1, index+1, turn, right));
			}
		}
		
		// change
		if (turn < m) {
			if (s.charAt(index) == 'T') {
				if (right == 1) {
					ans = Math.max(ans, dp(pos+1, index+1, turn+1, right));
				}
				else {
					ans = Math.max(ans, dp(pos-1, index+1, turn+1, right));
				}
			}
			else {
				ans = Math.max(ans, dp(pos, index+1, turn+1, right^1));
			}
		}
		
		return memo[pos][index][turn][right] = ans;
	}
}