
import java.util.*;
import java.io.*;

public class Grouping {

	// https://atcoder.jp/contests/dp/tasks/dp_u
	
	static int n;
	static long[][] a;
	static long[] dp;
	static long INF = (long)(1e18);
	static long[] precomp_sum;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Grouping"));

		n = Integer.parseInt(in.readLine());
		a = new long[n][n];		
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			for (int j=0; j<n; ++j) {
				a[i][j] = Long.parseLong(st.nextToken());
			}
		}
		
		precomp_sum = new long[(1 << n)];
			// what is sum from a of the bit k
		for (int k=1; k<(1<<n); k++) {
			ArrayList<Integer> added = new ArrayList<>();
			for (int i=0; i<n; i++) {
				if (((k >> i) & 1) == 1) {
					added.add(i);
				}
			}
			for (int i=0; i<added.size(); i++) {
				for (int j=i+1; j<added.size(); j++) {
					precomp_sum[k] += a[added.get(i)][added.get(j)];
				}
			}
		}
		
		dp = new long[(1 << n)];
			// dp[i] --> binary of i represents stuff already used
				// dp[i] = max value with stuff used/not used
				
		for (int i=0; i<(1<<n); i++) {
			for (int k=i; k>0; k = (k-1)&i) {		// all subsets
				// take the subset k at this turn
				dp[i] = Math.max(dp[i], dp[i ^ k] + precomp_sum[k]);
			}
		}
		
		System.out.println(dp[dp.length-1]);
	}
}


/*

	this recursive dp works, idk why

//		for (int i=0; i<(1<<n); i++) {
//			ArrayList<Integer> not_included = new ArrayList<>();
//			for (int j=0; j<n; j++) {
//				if (((i >> j) & 1) == 0) not_included.add(j);
//			}
//			dp(i, 0, not_included, 0);
//		}

	public static void dp(int bit, int index, ArrayList<Integer> not_included, int groupbit) {
				// bit value, index for not_included, indices that aren't included,
					// current group bit that is being added
		
		if (index == not_included.size()) {
			memo[bit+groupbit] = Math.max(memo[bit+groupbit], memo[bit] + precomp_sum[groupbit]);
			return;
		}
		
		// put the bit in this group
		dp(bit, index+1, not_included, groupbit);
		
		// don't put the bit in this group
		dp(bit, index+1, not_included, groupbit + (1 << not_included.get(index)));
	}

*/
