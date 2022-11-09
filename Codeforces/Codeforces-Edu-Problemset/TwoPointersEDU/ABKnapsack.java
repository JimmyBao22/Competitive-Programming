
import java.util.*;
import java.io.*;

public class ABKnapsack {

	// https://codeforces.com/edu/course/2/lesson/9/3/practice/contest/307094/problem/H
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ABKnapsack"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		long s = Long.parseLong(st.nextToken());
		long A = Long.parseLong(st.nextToken());
		long B = Long.parseLong(st.nextToken());
		long[] a = new long[n];
		long[] b = new long[m];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			a[i] = Long.parseLong(st.nextToken());
		}
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<m; i++) {
			b[i] = Long.parseLong(st.nextToken());
		}
		
		Arrays.parallelSort(a);
		Arrays.parallelSort(b);
		
		long ans=0;
		int bpointer = 0;
		long asum=0;
		long bsum=0;
		
		// only b
		long take = s / B;
		for (int i=m-1; i>=Math.max(0, m-take); i--) {
			bsum += b[i];
		}
		bpointer = (int)Math.max(0, m-take);
		ans = Math.max(ans, bsum + asum);
		
		for (int i=n-1; i>=0; i--) {
			asum += a[i];
			long cur = A * (n - i);
			if (cur > s) break;
			long left = s - cur;
			take = left / B;
			
			while (m - bpointer > take) {
				bsum -= b[bpointer];
				bpointer++;
			}
			
			ans = Math.max(ans, bsum + asum);
		}
		
		System.out.println(ans);
	}
}