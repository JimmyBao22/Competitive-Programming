
import java.util.*;
import java.io.*;

public class Checity {

	// https://codeforces.com/edu/course/2/lesson/9/3/practice/contest/307094/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Checity"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long r = Long.parseLong(st.nextToken());
		st = new StringTokenizer(in.readLine());
		long[] arr = new long[n];
		for (int i=0; i<n; i++) arr[i] = Long.parseLong(st.nextToken());
		
		long ans = 0;
		int left=0;
		for (int i=0; i<n; i++) {
			while (left+1 < n && arr[i] - arr[left+1] > r) {
				left++;
			}
			if (arr[i] - arr[left] > r) ans += left - 0 + 1;
		}
		System.out.println(ans);
	}
}
