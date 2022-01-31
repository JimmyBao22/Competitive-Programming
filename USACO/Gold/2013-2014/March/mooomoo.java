
import java.util.*;
import java.io.*;

public class mooomoo {

	// http://usaco.org/index.php?page=viewproblem2&cpid=417
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("mooomoo.in"));
		PrintWriter out = new PrintWriter(new FileWriter("mooomoo.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());
		int[] arr = new int[b];
		for (int i=0; i<b; i++) {
			arr[i] = Integer.parseInt(in.readLine());
		}
		
		Arrays.parallelSort(arr);
		
		int[] dp = new int[(int)(1e5+1)];
		Arrays.fill(dp, (int)(1e9));
		dp[0] = 0;
		for (int i=1; i<dp.length; i++) {
			for (int j=0; j<b; j++) {
				if (i - arr[j] < 0) break;
				dp[i] = Math.min(dp[i], dp[i-arr[j]]+1);
			}
		}
		
		int[] vals = new int[n];
		for (int i=0; i<n; i++) vals[i] = Integer.parseInt(in.readLine());
		
		int cur=0;
		int cows=0;
		for (int i=0; i<n; i++) {
			int val = vals[i];
			if (val == 0) {
				cur=0;
				continue;
			}
			int val2 = val - cur;
			cows += dp[val2];
			cur = val - 1;
		}

		System.out.println(cows);
		out.println(cows);
		out.close();
	}
}