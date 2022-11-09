
import java.util.*;
import java.io.*;

public class CowsinStalls {

	// https://codeforces.com/edu/course/2/lesson/6/3/practice/contest/285083/problem/C
	
	static int[] arr;
	static int n,k;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("CowsinStalls"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		arr = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		long min=0;
		long max = (long)(1e10);
		while (min<max) {
			long middle = min+(max-min+1)/2;
			if (check(middle)) {
				min=middle;
			}
			else max = middle-1;
		}
		
		System.out.println(min);
		
	}
	
	public static boolean check(long mid) {
		int lastpos = arr[0];
		int count=1;
		while (count<k) {
			int next = nextpos(lastpos+mid);
			if (next==-1) break;
			lastpos=arr[next];
			count++;
		}
		return count>=k;
	}
	
	public static int nextpos(long needed) {
		int min=0;
		int max=n-1;
		if (needed>arr[n-1]) return -1;
		while (min<max) {
			int middle=(min+max)/2;
			if (arr[middle]<needed) {
				min = middle+1;
			}
			else max=middle;
		}
		return min;
	}
	
}