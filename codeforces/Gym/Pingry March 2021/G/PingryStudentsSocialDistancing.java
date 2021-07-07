
import java.util.*;
import java.io.*;

public class PingryStudentsSocialDistancing {

	// https://codeforces.com/gym/310127/problem/G
	
	static int n, k;
	static long[][][] memo;
	static long[][] arr;
	static long INF = (long)(1e12);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("PingryStudentsSocialDistancing"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		arr = new long[n][2];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.parallelSort(arr, new Comparator<long[]>() {
			public int compare(long[] a, long[] b) {
				return Long.compare(a[0], b[0]);
			}
		});
		
		memo = new long[n][n][k];
		for (int i=0; i<n; i++ ) {
			for (int j=0; j<n; j++) {
				Arrays.fill(memo[i][j], -1);
			}
			
		}
		
		long ans = -INF;
		for (int i=0; i<n; i++) {
			ans = Math.max(ans, dp(i+1, i, 1));
		}
		System.out.println(ans);
	}
	
	public static long dp(int index, int lasttook, int took) {
		if (took >= k) {
			return 0;
		}
		if (index >= n) return -INF;
		if (memo[index][lasttook][took] != -1) return memo[index][lasttook][took];
		
		long ans=0;
		
		// dont take
		ans = Math.max(ans, dp(index+1, lasttook, took));
		
		// take
		ans = Math.max(ans, dp(index+1, index, took+1) + dist(index, lasttook));
		
		return memo[index][lasttook][took] = ans;
	}
	
	public static long dist(int a, int b) {
		return Math.abs(arr[a][0] - arr[b][0]) + Math.abs(arr[a][1] - arr[b][1]);
	}

	public static void sort(int[] a) {
		shuffle(a);
		Arrays.sort(a);
	}

	public static void shuffle(int[] a) {
		Random get = new Random();
		for (int i = 0; i < a.length; i++) {
			int r = get.nextInt(a.length);
			int temp = a[i];
			a[i] = a[r];
			a[r] = temp;
		}
	}
}