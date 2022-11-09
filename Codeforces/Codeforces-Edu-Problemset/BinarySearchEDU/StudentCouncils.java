
import java.util.*;
import java.io.*;

public class StudentCouncils {

	// https://codeforces.com/edu/course/2/lesson/6/2/practice/contest/283932/problem/G
	
	static long[] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("StudentCouncils"));

		long k = Integer.parseInt(in.readLine());
		int n = Integer.parseInt(in.readLine());
		arr = new long[n];
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(in.readLine());
		}
		
		long min=0;
		long max = (long)(1e17);
		while (min<max) {
			long middle = min  + (max-min+1)/2;
			if (check(middle, k)) {
				min=middle;
			}
			else max = middle-1;
		}
		
		System.out.println(min);

	}
	
	public static boolean check(long mid, long k) {
		long cur=0;
		for (int i=0; i<arr.length; i++) {
			cur += Math.min(arr[i], mid);
		}
		return cur >= mid*k;
	}

}
