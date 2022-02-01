
import java.util.*;
import java.io.*;

public class DayattheBeach {

	// https://codeforces.com/contest/599/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("DayattheBeach"));

		int n = Integer.parseInt(in.readLine());

		int[] arr = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		int[] max = new int[n];
		max[0] = arr[0];
		for (int i=1; i<n; i++) {
			max[i] = Math.max(max[i-1], arr[i]);
		}
		
		int[] min = new int[n];
		min[n-1] = arr[n-1];
		for (int i=n-2; i>= 0; i--) {
			min[i] = Math.min(min[i+1], arr[i]);
		}
		int count=0;
		
		for (int i=0; i<n-1; i++) {
			if (max[i] <= min[i+1]) count++;		// partition here
		}
		count++;
		
		System.out.println(count);
	
	}

}