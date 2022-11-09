
import java.util.*;
import java.io.*;

public class MultiplicationTable {

	// https://codeforces.com/edu/course/2/lesson/6/5/practice/contest/285084/problem/B
	
	static long n; static long k;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MultiplicationTable"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Long.parseLong(st.nextToken())-1;	// make it 0 base indexing
		
		long min=1;
		long max = n*n;
		while (min<max) {
			long middle = (min+max+1)/2;
			if (count(middle)) {
				min = middle;
			}
			else max = middle-1;
		}
		
		System.out.println(min);
		
	}
	
	public static boolean count(long x) {
		long sum=0;
		for (long i=0; i<n; i++) {
			if ((i+1) >= x) continue;
			long t = Math.min((x-1)/(i+1), n);
			sum += t;
		}
		return sum <= k;
	}
}