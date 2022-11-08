
import java.util.*;
import java.io.*;

public class Tower {

	static int n;
	static long[][] arr;
	static long[][] memo;
	static long INF = (long)(1e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Tower"));

		n = Integer.parseInt(in.readLine());
		arr = new long[n][3];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
			arr[i][2] = Integer.parseInt(st.nextToken());
		}
		Arrays.parallelSort(arr, new Comparator<long[]> () {
			public int compare(long[] a, long[] b) {
				return Long.compare(b[0] + b[1], a[0] + a[1]);		// larger sum first
			}
		});
		memo = new long[n][(int)(1e4+1)];
			// index, weight that matters --- max weight that can be taken
		
		for (int i=0; i<n; i++) Arrays.fill(memo[i], -1);
		
		long ans = 0;
		for (int i=0; i<n; i++) {
			ans = Math.max(ans, dp(i+1, (int)arr[i][1]) + arr[i][2]);
		}
		System.out.println(ans);
	}
	
	public static long dp(int index, int weight) {
									// weight is max weight that can be taken at this point
		if (weight < 0) return -INF;
		if (index >= n) return 0;
		if (index != 0 && memo[index][weight] != -1) return memo[index][weight];
		long ans=0;
		
		// don't take
		ans = Math.max(ans, dp(index+1, weight));
		
		// take
		ans = Math.max(ans, dp(index+1, (int)Math.min(arr[index][1], weight - arr[index][0])) + arr[index][2]);
		
		return memo[index][weight] = ans;
	}
}

/*
	Sort by larger si + wi first
	
	Consider 2 boxes, A and B, with A on top of B. In this case, it is most optimal for
		A to be on top of B if s_B > w_A and w_B > s_A
		Adding these together gives s_B + w_B > s_A + w_A
			Therefore, sort by larger si + wi first
*/