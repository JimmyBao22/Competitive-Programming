
import java.util.*;
import java.io.*;

public class Hardproblem {

	// https://codeforces.com/contest/706/problem/C
	
	static int n;
	static long[] cost;
	static String[] arr, rev;
	static long INF = (long)(1e18);
	static long[][] memo;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Hardproblem"));

		n = Integer.parseInt(in.readLine());  
		cost = new long[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) cost[i] = Long.parseLong(st.nextToken());
		
		arr = new String[n];
		rev = new String[n];
		for (int i=0; i<n; i++) {
			arr[i] = in.readLine();
			rev[i] = reverse(arr[i]);
		}
		
		memo = new long[n][2];
			// first is use normal
			// second is use not normal
			// holds the cost
		
		for (int i=0; i<n; i++) {
			Arrays.fill(memo[i], -1);
		}
		
		long ans = Math.min(dp(1, 0), dp(1, 1) + cost[0]);
		if (ans == INF) {
			System.out.println(-1);
		}
		else System.out.println(ans);
	}
	
	public static long dp(int index, int a) {
		if (index >= n) return 0;
		if (memo[index][a] != -1) return memo[index][a];
		long val = INF;
		
		if (a == 0) {
			if (arr[index-1].compareTo(arr[index]) <= 0) {
				val = Math.min(val, dp(index+1, 0));
			}
			if (arr[index-1].compareTo(rev[index]) <= 0) {
				val = Math.min(val, dp(index+1, 1) + cost[index]);
			}
		}
		else {
			if (rev[index-1].compareTo(arr[index]) <= 0) {
				val = Math.min(val, dp(index+1, 0));
			}
			if (rev[index-1].compareTo(rev[index]) <= 0) {
				val = Math.min(val, dp(index+1, 1) + cost[index]);
			}
		}
		
		return memo[index][a] = val;
	}
	
	public static String reverse(String x) {
		char[] rev = new char[x.length()];
		for (int i=0; i<x.length(); i++) {
			rev[i] = x.charAt(x.length() - i - 1);
		}
		return String.valueOf(rev);
	}
}