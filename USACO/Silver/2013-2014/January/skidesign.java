
import java.util.*;
import java.io.*;

public class skidesign {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=376
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("skidesign.in"));
		PrintWriter out = new PrintWriter(new FileWriter("skidesign.out"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n];
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(in.readLine());
		
		Arrays.parallelSort(arr);
		
		long minans = (long)(1e18);
		for (int val=arr[0]; val <= arr[n-1] - 17; val++) {
			long cur=0;
			int j=0;
			for (; j<n; j++) {
				if (arr[j] < val) cur += (val - arr[j]) * (val - arr[j]);
				else break;
			}
			for (; j<n; j++) {
				if (Math.abs(arr[j] - val) > 17) {
					cur += Math.abs(arr[j] - val - 17) * Math.abs(arr[j] - val - 17);
				}
			}
			minans = Math.min(minans, cur);
		}
		if (minans == (long)(1e18)) minans=0;
		
		System.out.println(minans);
		out.println(minans);
		out.close();
	}
}