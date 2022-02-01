
import java.util.*;
import java.io.*;

public class TeamBuilding {

	// https://codeforces.com/contest/1316/problem/E
	
	static int n, p, k;
	static A[] a;
	static long[][] s, memo;
	static long INF = (long)(1e15);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("TeamBuilding"));

		int t = 1;
		while (t-- > 0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			p = Integer.parseInt(st.nextToken());
			k = Integer.parseInt(st.nextToken());
			st = new StringTokenizer(in.readLine());
			a = new A[n];
			for (int i=0; i<n; i++) a[i] = new A(Integer.parseInt(st.nextToken()), i);
			Arrays.parallelSort(a);
			
			s = new long[n][p];
			for (int i=0; i<n; i++) {
				st = new StringTokenizer(in.readLine());
				for (int j=0; j<p; j++) {
					s[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			memo = new long[n][1 << p];
			for (int i=0; i<n; i++) {
				Arrays.fill(memo[i], -1);
			}
			
			long ans = dp(0, 0, 0);
			
			System.out.println(ans);
		}
	}
	
	public static long dp(int index, int mask, int count) {
		if (index >= n) {
			if (mask == (1 << p) - 1) {
				return 0;
			}
			return -INF;
		}
		if (memo[index][mask] != -1) return memo[index][mask];

		// dont take
		long ans = 0;
		if (index + 1 - count > k) ans = dp(index+1, mask, count);
		else ans = dp(index+1, mask, count) + a[index].val;
		
		// take
		for (int i=0; i<p; i++) {
			if (((mask >> i)&1) == 0) {
				ans = Math.max(ans, s[a[index].index][i] + dp(index+1, mask + (1 << i), count+1));	
			}
		}
		
		return memo[index][mask] =  ans;
	}
	
	static class A implements Comparable<A> {
		long val; int index;
		A (long a, int b) {
			val = a; index = b;
		}
		public int compareTo(A o) {
			return Long.compare(o.val, val);
		}
	}
}