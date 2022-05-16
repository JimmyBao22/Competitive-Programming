
import java.util.*;
import java.io.*;

public class MinimizingCoins {

	// https://cses.fi/problemset/task/1634
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MinimizingCoins"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());  
		int x = Integer.parseInt(st.nextToken());  
		int[] arr = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());  
		
		int[] dp = new int[x+1];
		Arrays.fill(dp, (int)(1e9));
		dp[0] = 0;
		for (int i=1; i<=x; i++) {
			for (Integer j : arr) {
				if (i - j < 0) continue;
				dp[i] = Math.min(dp[i], dp[i-j]+1);
			}
		}
		if (dp[x] == (int)(1e9)) System.out.println(-1);
		else System.out.println(dp[x]);
	}
}