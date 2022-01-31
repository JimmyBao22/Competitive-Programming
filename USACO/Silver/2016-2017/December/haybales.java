
import java.util.*;
import java.io.*;

public class haybales {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=666
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("haybales.in"));
		PrintWriter out = new PrintWriter(new FileWriter("haybales.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		
		long[] arr = new long[n];
		st = new StringTokenizer(in.readLine());
		long max=0;
		for (int i=0; i<n; ++i ) {
			arr[i] = Long.parseLong(st.nextToken());
			max = Math.max(max, arr[i]);
		}
		
		Arrays.parallelSort(arr);
		
		//binary search each query
		for (int i=0; i<q; i++) {
			st = new StringTokenizer(in.readLine());
			long a = Long.parseLong(st.nextToken());
			long b = Long.parseLong(st.nextToken());
			
			int first=binsearchright(a, arr);
			int sec=binsearchleft(b, arr);
			int val=sec-first+1;
			if (b > max && a > max) val = 0;

			System.out.println(val);
			out.println(val);
		}
		
		out.close();
	}
	
	public static int binsearchright(long val, long[] arr) {
		int min=0;
		int max = arr.length-1;
		while (min < max) {
			int middle = (min + max)/2;
			if (arr[middle] == val) return middle;
			else if (arr[middle] > val) {
				max = middle;
			}
			else min = middle+1;
		}
		return min;
	}
	public static int binsearchleft(long val, long[] arr) {
		int min=0;
		int max = arr.length-1;
		while (min < max) {
			int middle = (min + max+1)/2;
			if (arr[middle] == val) return middle;
			else if (arr[middle] > val) {
				max = middle-1;
			}
			else min = middle;
		}
		return min;
	}
}