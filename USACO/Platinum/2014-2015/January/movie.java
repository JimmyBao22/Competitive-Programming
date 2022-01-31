
import java.util.*;
import java.io.*;

public class movie {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=515
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("movie.in"));
		PrintWriter out = new PrintWriter(new FileWriter("movie.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int L = Integer.parseInt(st.nextToken());
		long[] duration = new long[n];
		TreeSet<Long>[] times = new TreeSet[n];
		for (int i=0; i<n; ++i) {
			st = new StringTokenizer(in.readLine());
			duration[i] = Integer.parseInt(st.nextToken());
			times[i] = new TreeSet<>();
			int c = Integer.parseInt(st.nextToken());
			while (c-->0) {
				times[i].add((long)Integer.parseInt(st.nextToken()));
			}
		}
		
		long[] dp = new long[(1 << n)];
			// taken = max time
		Arrays.fill(dp, -1);
		dp[0] = 0;
		for (int i=0; i<dp.length; i++) {
			for (int j=0; j<n; j++) {
				if (((i >> j) & 1) == 1) {
					if (dp[i - (1 << j)] != -1) {
						Long lower = times[j].floor(dp[i - (1 << j)]);
						if (lower != null && lower + duration[j] > dp[i]) {
							dp[i] = lower + duration[j];
						}
					}
				}
			}
		}
		
		int ans=Integer.MAX_VALUE;
		for (int i=0; i<dp.length; i++) {
			if (dp[i] >= L) {
				ans = Math.min(ans, Integer.bitCount(i));
			}
		}
		
		if (ans == Integer.MAX_VALUE) {
			System.out.println(-1);
			out.println(-1);
		}
		else {
			System.out.println(ans);
			out.println(ans);
		}
		out.close();
	}
}