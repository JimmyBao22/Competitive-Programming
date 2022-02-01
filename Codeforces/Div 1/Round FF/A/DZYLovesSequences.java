
import java.util.*;
import java.io.*;

public class DZYLovesSequences {

	// https://codeforces.com/contest/446/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("DZYLovesSequences"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		int[] lengths = new int[n];
		lengths[0] = 1;
		int max=1;
		for (int i=1; i<n; i++) {
			if (arr[i] > arr[i-1]) lengths[i] = lengths[i-1] + 1;
			else lengths[i] = 1;
			max = Math.max(max, lengths[i]);
		}
		
		int[] reverselengths = new int[n];
		reverselengths[n-1] = 1;
		for (int i=n-2; i>=0; i--) {
			if (arr[i+1] > arr[i]) {
				reverselengths[i] = reverselengths[i+1] + 1;
			}
			else reverselengths[i] = 1;
			max = Math.max(max, reverselengths[i]);
		}
		
		for (int i=0; i<n; i++) {
			// change arr[i]
			if (i != 0) {
				max = Math.max(max, lengths[i-1] + 1);
			}
			if (i != n-1) {
				max = Math.max(max, reverselengths[i+1] + 1);
			}
			// connect bottom and top
			if (i != n-1 && i != 0 && arr[i+1] >= arr[i-1] + 2) {
				max = Math.max(max, lengths[i-1] + reverselengths[i+1] + 1);
			}
		}
		
		System.out.println(max);
	}
}