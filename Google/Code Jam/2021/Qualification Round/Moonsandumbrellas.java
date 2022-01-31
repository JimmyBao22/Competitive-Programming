
import java.util.*;
import java.io.*;

public class Moonsandumbrellas {

	// https://codingcompetitions.withgoogle.com/codejam/round/000000000043580a/00000000006d1145
	
	static int x, y, n;
	static String s;
	static int[][] memo;
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Moonsandumbrellas"));

		int t = Integer.parseInt(in.readLine());
		for (int tt = 1; tt < t + 1; tt++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			s = st.nextToken();
			n = s.length();
			memo = new int[n][2];
			for (int i=0; i<n; i++) Arrays.fill(memo[i], -1);
			
			int ans=0;
			if (s.charAt(0) == 'C') {
				ans = dp(1, 0);
			}
			else if (s.charAt(0) == 'J') {
				ans = dp(1, 1);
			}
			else {
				ans = Math.min(dp(1, 0), dp(1, 1));
			}
			
			System.out.println("Case #" + tt + ": " + ans);
		}
	}

	public static int dp(int index, int lasttype) {
		if (index >= n) {
			return 0;
		}
		if (memo[index][lasttype] != -1) return memo[index][lasttype];
		int ans=(int)(1e9);
		
		if (s.charAt(index) == 'C') {
			if (lasttype == 0) {
				ans = Math.min(ans, dp(index+1, 0));
			}
			else {
				ans = Math.min(ans, dp(index+1, 0) + y);
			}
		}
		else if (s.charAt(index) == 'J') {
			if (lasttype == 0) {
				ans = Math.min(ans, dp(index+1, 1) + x);
			}
			else {
				ans = Math.min(ans, dp(index+1, 1));
			}
		}
		else {
			if (lasttype == 0) {
				ans = Math.min(ans, Math.min(dp(index+1, 0), dp(index+1, 1) + x));
			}
			else {
				ans = Math.min(ans, Math.min(dp(index+1, 0) + y, dp(index+1, 1)));
			}
		}
		
		return memo[index][lasttype] = ans;
	}
}