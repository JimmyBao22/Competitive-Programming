
import java.util.*;
import java.io.*;

public class Deque {

	// https://atcoder.jp/contests/dp/tasks/dp_l
	
	static int n;
	static long[] arr;
	static long[][][] memo;
	static long INF = (long)(1e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Deque"));

		n = Integer.parseInt(in.readLine());
		arr = new long[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			arr[i] = Long.parseLong(st.nextToken());
		}
		
		memo = new long[n][n][2];
			// left, right, whose turn (0 = taro)
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				Arrays.fill(memo[i][j], -1);
			}
		}
		
		long ans = dp(0, n-1, 0);
		
		System.out.println(ans);
	}
	
	public static long dp(int left, int right, int turn) {
		if (right < left) {
			return 0;
		}
		if (memo[left][right][turn] != -1) return memo[left][right][turn];
		
		long ans;
		if (turn == 0) {
								// take left					// take right
			ans = Math.max(dp(left+1, right, 1) + arr[left], dp(left, right-1, 1) + arr[right]);
		}
		else {
								// take left					// take right
			ans = Math.min(dp(left+1, right, 0) - arr[left], dp(left, right-1, 0) - arr[right]);
		}
		
		return memo[left][right][turn] = ans;
	}
}
