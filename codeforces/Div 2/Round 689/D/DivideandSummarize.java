
import java.util.*;
import java.io.*;

public class DivideandSummarize {

	// https://codeforces.com/contest/1461/problem/D
	
	static long[] arr, psums;
	static int n;
	static HashSet<Long> sums;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("five"));

		int t = Integer.parseInt(in.readLine());
		StringBuilder s = new StringBuilder();
		while (t-->0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			int q = Integer.parseInt(st.nextToken());
			arr = new long[n];
			st = new StringTokenizer(in.readLine());
			psums = new long[n];
			for (int i=0; i<n; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			Arrays.parallelSort(arr);
			psums[0] = arr[0];
			for (int i=1; i<n; i++) {
				psums[i] = psums[i-1] + arr[i];
			}
			
			sums = new HashSet<>();
			find(0,n-1);
			
			for (int i=0; i<q; i++) {
				long c = Integer.parseInt(in.readLine());
				if (sums.contains(c)) s.append("Yes");
				else s.append("No");
				s.append("\n");
			}
		}
		System.out.print(s);
	}
	
	public static void find(int l, int r) {
		if (l == 0) {
			sums.add(psums[r]);
		}
		else {
			sums.add(psums[r] - psums[l-1]);
		}
		
		if (arr[l] == arr[r]) return;
		
		long mid = (arr[l] + arr[r])/2;
		int min=l;
		int max=r;
		while (min<max) {
			int middle = (min+max+1)/2;
			if (arr[middle] <= mid) {
				min = middle;
			}
			else max = middle-1;
		}
		
		// l to min, min + 1 to r
		find(l, min);
		find(min+1, r);
	}
}