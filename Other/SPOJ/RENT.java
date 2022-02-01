
import java.util.*;
import java.io.*;

public class RENT {

	// https://www.spoj.com/problems/RENT/cstart=20
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("RENT"));

		int t = Integer.parseInt(in.readLine());
		while (t --> 0) {
			int n = Integer.parseInt(in.readLine());
			v[] arr = new v[n];
			for (int i=0; i<n; i++) {
				StringTokenizer st = new StringTokenizer(in.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				arr[i] = new v(a,b,c);
			}
			Arrays.parallelSort(arr);
			
			int[] dp = new int[n];
			dp[0] = arr[0].val;
			for (int i=1; i<n; i++) {
				int index = find(arr, arr[i].start, i);
				if (index == -1) {
					dp[i] = Math.max(dp[i-1], arr[i].val);
				} else {
					dp[i] = Math.max(dp[i-1], dp[index] + arr[i].val);
				}
			}
			System.out.println(dp[n-1]);
		}
	}
	
	public static int find(v[] arr, int start, int i) {
		int min=-1;
		int max=i;
		while (min < max) {
			int middle = (min + max + 1)/2;
			if (middle == -1) return -1;
			if (arr[middle].start + arr[middle].dur <= start) {
				min = middle;
			} else {
				max = middle - 1;
			}
		}
		return min;
	}
	
	static class v implements Comparable<v> {
		int start;
		int dur;
		int val;
		v(int a, int b, int c) {
			start =a;
			dur = b;
			val = c;
		}
		
		public int compareTo(v o) {
			return start+dur - (o.start + o.dur);
		}
	}
}