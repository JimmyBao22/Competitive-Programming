
import java.util.*;
import java.io.*;

public class Ropes2 {

	// https://codeforces.com/edu/course/2/lesson/6/2/practice/contest/283932/problem/B
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Ropes"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long k = Integer.parseInt(st.nextToken());
		long[] arr = new long[n];
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(in.readLine());
		}
		double min = 0;
		double max = 1e8;
		for (int i=0; i<100; i++) {
			double middle = (min + max)/2.0;
			if (check(arr, middle, k)) {
				min = middle;
			}
			else max = middle;
		}
		System.out.println(min);
	}
	
	public static boolean check(long[] arr, double x, long k) {
		long count=0;
		for (Long a : arr) {
			count += (long)(a/x);
		}
		return count>=k;
	}

}
