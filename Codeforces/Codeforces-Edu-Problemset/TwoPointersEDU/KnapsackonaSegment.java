
import java.util.*;
import java.io.*;

public class KnapsackonaSegment {

	// https://codeforces.com/edu/course/2/lesson/9/3/practice/contest/307094/problem/E
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("KnapsackonaSegment"));
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long s = Long.parseLong(st.nextToken());
		long[] w = new long[n];
		long[] c = new long[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			w[i] = Long.parseLong(st.nextToken());
		}
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			c[i] = Long.parseLong(st.nextToken());
		}
		
		long[] psum = new long[n];
		for (int i=0; i<n; i++) {
			if (i == 0) psum[i] = c[i];
			else psum[i] = psum[i-1] + c[i];
		}
		
		long ans=0;
		long weight=0;
		int left=0;
		for (int i=0; i<n; i++) {
			weight += w[i];
			while (weight > s) {
				weight -= w[left];
				left++;
			}
			
			if (left == 0) {
				ans = Math.max(ans, psum[i]);
			}
			else {
				ans = Math.max(ans, psum[i] - psum[left-1]);
			}
		}
		
		System.out.println(ans);
	}
}
