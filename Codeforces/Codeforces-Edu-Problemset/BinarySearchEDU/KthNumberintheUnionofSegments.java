
import java.util.*;
import java.io.*;

public class KthNumberintheUnionofSegments {

	// https://codeforces.com/edu/course/2/lesson/6/5/practice/contest/285084/problem/A
	
	static int n; static long k;
	static long[][] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("KthNumberintheUnionofSegments"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Long.parseLong(st.nextToken());
		arr = new long[n][2];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i][0] = Long.parseLong(st.nextToken()); 
			arr[i][1] = Long.parseLong(st.nextToken());
		}
		
		long min=(long)(-2e9);
		long max = (long)(2e9);
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
		for (int i=0; i<n; i++) {
			if (arr[i][0] >= x) continue;
			sum += Math.min(x-1-arr[i][0]+1, arr[i][1]-arr[i][0]+1);
		}
		return sum <= k;
	}
}

/*
	Problem: given n segments, from [li, li+1, ..., ri], create a large array
		which contains every element in sorted order.
		
	Ex input:
	3 4
	1 3 
	5 7
	2 6
	
	This means the final array would be 
	1 2 2 3 3 4 5 5 6 6 7
		And the answer is the element at index 4, or (3) (use 0 base indexing)
		
	
	Idea: 
	Binary search on the number. 
	Function count(x) = number of numbers < x
		
	Therefore, if count(x) <= k, then this number has index <= k. Otherwise,
		if count(x) > k, then it does not work.
		
		Find max(x) such that count(x) <= k
		
	count function loops through all lists O(n). For each list, if 
		li > x, then continue. Otherwise, add Math.min(x-1-li+1, ri-li+1) to the sum
	return sum
*/