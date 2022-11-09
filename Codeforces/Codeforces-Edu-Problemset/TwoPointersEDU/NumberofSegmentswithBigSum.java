
import java.util.*;
import java.io.*;

public class NumberofSegmentswithBigSum {

	// https://codeforces.com/edu/course/2/lesson/9/2/practice/contest/307093/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SegmentwithSmallSum"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long s = Long.parseLong(st.nextToken());
		long[] arr = new long[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) arr[i] = Long.parseLong(st.nextToken());
		
		long ans=0;
		long sum=0;
		int left=0;
		for (int i=0; i<n; i++) {
			sum += arr[i];
			while (sum - arr[left] >= s) {
				sum -= arr[left];
				left++;
			}
			if (sum >= s) {
				ans += left - 0 + 1;
			}
		}
		
		System.out.println(ans);
	}
}