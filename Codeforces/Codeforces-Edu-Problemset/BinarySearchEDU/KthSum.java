
import java.util.*;
import java.io.*;

public class KthSum {

	// https://codeforces.com/edu/course/2/lesson/6/5/practice/contest/285084/problem/C
	
	static int n; static long k;
	static long[] a, b;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("KthSum"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Long.parseLong(st.nextToken()) - 1;
		
		a = new long[n]; b = new long[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) a[i] = Long.parseLong(st.nextToken());
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) b[i] = Long.parseLong(st.nextToken());
		
		Arrays.parallelSort(a); Arrays.parallelSort(b);
		
		long min = 2;
		long max = (long)(2e9);
		while (min<max) {
			long middle = (min+max + 1)/2;
			if (check(middle)) {
				min = middle;
			}
			else max = middle-1;
		}
		
		System.out.println(min);
		
	}
	
	public static boolean check(long mid) {
		long sum=0;
		for (int i=0; i<n; i++) {
			if (a[i] >= mid) break;
			sum += search(a[i], mid);
		}
		return sum <= k;
	}
	
		// find number of elements in b such that val+b[i] < x
	public static long search(long val, long x) {
		int min=-1;			//-1 for when no values work
		int max = n-1;
		while(min<max) {
			int middle = (min+max+1)/2;
			if (b[middle] + val < x) {
				min = middle;
			}
			else max = middle-1;
		}
		return min+1;
	}
}