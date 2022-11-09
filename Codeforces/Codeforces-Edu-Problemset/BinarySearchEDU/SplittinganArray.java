
import java.util.*;
import java.io.*;

public class SplittinganArray {

	// https://codeforces.com/edu/course/2/lesson/6/3/practice/contest/285083/problem/B
	
	static long[] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SplittinganArray"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		arr = new long[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		long min=1;
		long max = (long)(1e18);
		while (min<max) {
			long middle = min +(max-min)/2;
			if (check(middle,k)) {
				max = middle;
			}
			else min = middle+1;
		}
		
		System.out.println(min);
		
	}
	
	public static boolean check(long mid, int k) {
		int sections=0;
		long cursum=0;
		for (int i=0; i<arr.length; i++) {
			if (cursum + arr[i] > mid) {
				cursum=arr[i];
				if (cursum>mid) return false;
				sections++;
			}
			else {
				cursum += arr[i];
			}
		}
		if (cursum>0) sections++;
		return sections<=k;
	}
	
}