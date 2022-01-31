
import java.util.*;
import java.io.*;

public class lazy {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=413
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("lazy.in"));
		PrintWriter out = new PrintWriter(new FileWriter("lazy.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		a[] arr = new a[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			arr[i] = new a(b,c);
		}

		Arrays.parallelSort(arr);
		
		int[] pref = new int[n+1];
		for (int i=0; i<n; i++) {
			pref[i+1] = pref[i] + arr[i].g;
		}
		
		int max=0;
		for (int i=0; i<=1000000; i++) {
			int left = left(arr, i, k);
			int right = right(arr, i, k);
			max = Math.max(max, pref[right+1] - pref[left]);
		}
		
		System.out.println(max);
		out.println(max);
		out.close();

	}
	
	public static int left(a[] arr, int pos, int k) {
		int needed = pos-k;
		int min=0;
		int max = arr.length-1;
		while (min < max) {
			int middle = (min + max)/2;
			if (arr[middle].x == needed) {
				min = middle;
				max = middle;
			}
			else if (arr[middle].x < needed) {
				min = middle+1;
			}
			else {
				max = middle;
			}
		}
		return min;
	}
	
	public static int right(a[] arr, int pos, int k) {
		int needed = pos+k;
		int min=0;
		int max = arr.length-1;
		while (min < max) {
			int middle = (min + max+1)/2;
			if (arr[middle].x == needed) {
				min = middle;
				max = middle;
			}
			else if (arr[middle].x < needed) {
				min = middle;
			}
			else {
				max = middle-1;
			}
		}
		return min;
	}
	
	static class a implements Comparable<a> {
		int g;
		int x;
		a(int b, int c) {g =b; x = c;}
		
		public int compareTo(a other) {
			return x - other.x;
		}
	}
}