
import java.util.*;
import java.io.*;

public class Knapsack1 {

	// https://atcoder.jp/contests/dp/tasks/dp_d
	
	static long[] v;
	static int[] weights;
	static long[][] memo;	
		// index, weight
	static long INF = (long)(1e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Knapsack1"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int w = Integer.parseInt(st.nextToken());
		weights = new int[n];
		v = new long[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			weights[i] = Integer.parseInt(st.nextToken());
			v[i] = Integer.parseInt(st.nextToken());
		}
		
		memo = new long[n][w+1];
		for (int i=0; i<n; i++) {
			Arrays.fill(memo[i], -1);
		}
		
		long ans = dp(0,0);
		System.out.println(ans);
	}

	public static long dp(int index, int weight) {
		if (weight >= memo[0].length) {
			return -INF;		// doesn't work
		}
		if (index >= memo.length) {
			return 0;
		}
		if (memo[index][weight] != -1) return memo[index][weight];
		
		long ans=0;
		
		// take
		ans = Math.max(ans, dp(index+1, weight + weights[index]) + v[index]);
		
		// don't take
		ans = Math.max(ans, dp(index+1, weight));
		
		return memo[index][weight] = ans;
	}
}