
import java.util.*;
import java.io.*;

public class Frog2 {

	// https://atcoder.jp/contests/dp/tasks/dp_b
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Frog2"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[] arr = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; ++i) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		int[] dp = new int[n];
		Arrays.fill(dp, (int)(1e9));
		dp[0] = 0;
		for (int i=0; i<n; i++) {
			for (int j=i+1; j<n && j<=i+k; j++) {
				dp[j] = Math.min(dp[j], dp[i] + Math.abs(arr[i] - arr[j]));
			}
		}
		
		System.out.println(dp[n-1]);
	}
}