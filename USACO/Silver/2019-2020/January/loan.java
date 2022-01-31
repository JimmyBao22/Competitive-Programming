
import java.util.*;
import java.io.*;

public class loan {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=991
	
	static long n,m,k;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("loan.in"));
		PrintWriter out = new PrintWriter(new FileWriter("loan.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Long.parseLong(st.nextToken());
		k = Long.parseLong(st.nextToken());
		m = Long.parseLong(st.nextToken());

		// binary search on x
		long min=1;
		long max = (long)(1e18);
		while (min < max) {
			long middle = min + (max-min+1)/2;
			if (check(middle)) {
				min = middle;
			}
			else max = middle-1;
		}
				
		System.out.println(min);
		out.println(min);
		out.close();
	}
	
	public static boolean check(long mid) {
		long used=n/mid;
		long prevtake = used;
		for (long i=2; i<=k; i++) {
			long curtake = (n - used)/mid;
			if (curtake <= m) {
				// how much left
				return used + m * (k - i + 1) >= n;
			}			
			else if (curtake - prevtake == 0) {
				// curtake > (n - used)/mid;
				// used > n - (curtake - 1) * mid
				// long needed = n - curtake * mid - used + 1;
				// long moves = (needed + curtake - 1)/curtake;	// ceiling
				// long moves = (n - curtake*mid - used + curtake)/curtake
				long moves = (n - used)/curtake - mid + 1;
				i += moves-1;
				if (i > k) {
					moves -= (i-k);
				}
				
				used += moves * curtake;
			}
			else used += curtake;

			prevtake = curtake;
			if (used >= n) return true;
		}
		return used >= n;
	}
	
	public static long loanbrute() {
		long min=1;
		long max = (n+m-1)/m;
		while (min < max) {
			long middle = min + (max-min+1)/2;
			if (check2(middle)) {
				min = middle;
			}
			else max = middle-1;
		}
		return min;
	}
	
	public static boolean check2(long mid) {
		long used=n/mid;
		for (long i=2; i<=k; i++) {
			long curtake = (n - used)/mid;
			if (curtake <= m) {
				// how much left
				return used + m * (k - i + 1) >= n;
			}			
			else used += curtake;

			if (used >= n) return true;
		}
		return used >= n;
	}
}