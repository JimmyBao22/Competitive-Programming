
import java.util.*;
import java.io.*;

public class Ropes {

	// https://codeforces.com/edu/course/2/lesson/6/2/practice/contest/283932/problem/B
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Ropes"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long k = Integer.parseInt(st.nextToken());
		long[] arr = new long[n];
		for (int i=0; i<n; i++) {
			arr[i] = (long)(Integer.parseInt(in.readLine())*1e6);
		}
		long min=1;
		long max=(long)1e13;
		while (min < max) {
			long middle = min + (max-min+1)/2;
			if (check(arr,middle,k)) {
				min = middle;
			}
			else max = middle-1;
		}
		System.out.println((double)(min)/(double)(1e6));
	}
	
	public static boolean check(long[] arr, long x, long k) {
		long count=0;
		for (Long a : arr) {
			count += a/x;
		}
		return count>=k;
	}

}
