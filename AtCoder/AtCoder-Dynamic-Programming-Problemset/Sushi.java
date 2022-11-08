
import java.util.*;
import java.io.*;

public class Sushi {

	// https://atcoder.jp/contests/dp/tasks/dp_j
	
	static int n;
	static int ones=0, twos=0, threes=0;
	static double[][][] memo;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Sushi"));

		n = Integer.parseInt(in.readLine());
		int[] arr = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			if (arr[i] == 1) ones++;
			else if (arr[i] == 2) twos++;
			else if (arr[i] == 3) threes++;
		}
		
		memo = new double[n+1][n+1][n+1];
			// memo[i][j][k] = expected number of times to get here
		
		for (int i=0; i<=n; i++) {
			for (int j=0; j <= n; j++) {
				Arrays.fill(memo[i][j], -1);
			}
		}
		
		double ans = dp(ones, twos, threes);
		
		System.out.println(ans);
	}
	
	public static double dp(int one, int two, int three) {
		if (one == 0 && two == 0 && three == 0) return 0;
		if (memo[one][two][three] != -1) return memo[one][two][three];
		
		double ans = n/(double)(one+two+three);
						// number of steps before reaching a number
		
		if (one > 0) {
			ans += ((double)(one)/(one+two+three) * dp(one - 1 , two, three));
						// probability you take a 1
		}
		if (two > 0) {
			ans += ((double)(two)/(one+two+three) * dp(one + 1, two - 1, three));
						// probability you take a 2
		}
		if (three > 0) {
			ans += ((double)(three)/(one+two+three) * dp(one, two + 1, three - 1));
						// probability you take a 3
		}
		
		return memo[one][two][three] = ans;
	}
}