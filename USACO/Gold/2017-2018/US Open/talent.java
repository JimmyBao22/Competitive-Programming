
import java.util.*;
import java.io.*;

public class talent {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=839
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("talent.in"));
		PrintWriter out = new PrintWriter(new FileWriter("talent.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int w = Integer.parseInt(st.nextToken());
		int[][] arr = new int[n][2];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		
		// binary search on ratio -->
		// check if sum t / sum w >= x --> sum (t - xw) >= 0
		
		double min=0;
		double max = (double)(1e5);
		for (int i=0; i<100; i++) {
			double middle = (min + max)/2;
			if (check(middle, arr, n, w)) {
				min = middle;
			}
			else max = middle;
		}

		int ans = (int)(1000 * min);
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	public static boolean check(double mid, int[][] arr, int n, int w) {
		A[] copy = new A[n];
		for (int i=0; i<n; i++) {
			copy[i] = new A(arr[i][1] - mid * arr[i][0], i);
		}
		Arrays.parallelSort(copy);
		
		int totalweight=0;
		double sum=0;
		// always can take those with sum >= 0
		int i=0;
		for (; i<n; i++) {
			if (copy[i].val < 0) break;
			sum += copy[i].val;
			totalweight += arr[copy[i].index][0];
		}

		if (totalweight >= w) {
			return sum >= 0;
		}
		if (i == 0) return false;
		
		double[][] dp = new double[n][w+1];
		for (int j=0; j<n; j++) {
			Arrays.fill(dp[j], -(int)(1e9));
		}
		
		dp[i-1][totalweight] = sum;
		for (; i<n; i++) {
			
			// don't take this pos
			for (int j=0; j<=w; j++) dp[i][j] = dp[i-1][j];
			
				// take this pos
			int curweight = arr[copy[i].index][0];
			for (int j=w+curweight; j>=curweight; j--) {
				// come from dp[i-1][j-curweight]
				dp[i][Math.min(j,w)] = Math.max(dp[i][Math.min(j,w)], dp[i-1][j-curweight] + copy[i].val);
			}
		}
		
		return dp[n-1][w] >= 0;
	}
	
	static class A implements Comparable<A> {
		double val; int index;
		A (double a, int b) {
			val = a; index = b;
		}
		public int compareTo(A o) {
			return Double.compare(o.val, val);
		}
	}
}