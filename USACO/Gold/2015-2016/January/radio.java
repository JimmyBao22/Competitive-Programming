
import java.util.*;
import java.io.*;

public class radio {

	// http://usaco.org/index.php?page=viewproblem2&cpid=598
	
	static long[] f;
	static long[] b;
	static char[] f_path;
	static char[] b_path;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("radio.in"));
		PrintWriter out = new PrintWriter(new FileWriter("radio.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		f = new long[2];
		b = new long[2];
		st = new StringTokenizer(in.readLine());
		f[0] = Integer.parseInt(st.nextToken());
		f[1] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(in.readLine());
		b[0] = Integer.parseInt(st.nextToken());
		b[1] = Integer.parseInt(st.nextToken());
		f_path = in.readLine().toCharArray();
		b_path = in.readLine().toCharArray();
		
		long[][] memo = new long[n][m];
		for (int i=0; i<n; i++) Arrays.fill(memo[i], -1);
		
		long ans = dp(memo, 0, 0);
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static long dp(long[][] memo, int n, int m) {
		if (n >= memo.length) {
			long ret = 0;
			for (int i=m; i<memo[0].length; ++i) {
				move(b_path[i], b);
				ret += distance();
			}
			for (int i=m; i<memo[0].length; i++) {		// reverse the moves so can use later
				moveReverse(b_path[i], b);
			}
			return ret;
		}
		else if (m >= memo[0].length) {
			long ret = 0;
			for (int i=n; i<memo.length; ++i) {
				move(f_path[i], f);
				ret += distance();
			}
			for (int i=n; i<memo.length; i++) {		// reverse the moves so can use later
				moveReverse(f_path[i], f);
			}
			return ret;
		}
		if (memo[n][m] != -1) return memo[n][m];
		
		long ans = Long.MAX_VALUE;
		
		// f move
		move(f_path[n], f);
		ans = Math.min(ans, dp(memo, n+1, m) + distance());
		moveReverse(f_path[n], f);
		
		// b move
		move(b_path[m], b);
		ans = Math.min(ans, dp(memo, n, m+1) + distance());
		moveReverse(b_path[m], b);
		
		// both move
		move(f_path[n], f);
		move(b_path[m], b);
		ans = Math.min(ans, dp(memo, n+1, m+1) + distance());
		moveReverse(f_path[n], f);
		moveReverse(b_path[m], b);
		
		return memo[n][m] = ans;
	}
	
	public static void print() {
		System.out.println("f: " + f[0] + " " + f[1]);
		System.out.println("b: " + b[0] + " " + b[1]);
	}
	
	public static void move(char c, long[] a) {
		if (c == 'N') {
			a[1]++;
		}
		if (c == 'S') {
			a[1]--;
		}
		if (c == 'E') {
			a[0]++;
		}
		if (c == 'W') {
			a[0]--;
		}
	}
	
	public static void moveReverse(char c, long[] a) {
		if (c == 'N') {
			a[1]--;
		}
		if (c == 'S') {
			a[1]++;
		}
		if (c == 'E') {
			a[0]--;
		}
		if (c == 'W') {
			a[0]++;
		}
	}
	
	public static long distance() {
		return (f[0]-b[0])*(f[0]-b[0]) + (f[1]-b[1])*(f[1]-b[1]);
	}
}