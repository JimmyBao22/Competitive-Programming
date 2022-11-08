
import java.util.*;
import java.io.*;

public class Frog1 {

	// https://atcoder.jp/contests/dp/tasks/dp_a
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Frog1"));

		int n = Integer.parseInt(in.readLine()); 
		int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(in.readLine()); 
		for (int i=0; i<n; i++ ) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		int[] dp = new int[n];
		if (n > 1) dp[1] = Math.abs(arr[1] - arr[0]);
		for (int i=2; i<n; i++) {
			dp[i] = Math.min(dp[i-1] + Math.abs(arr[i] - arr[i-1]), dp[i-2] + 
					Math.abs(arr[i] - arr[i-2]));
		}
		
		System.out.println(dp[n-1]);
	}
}