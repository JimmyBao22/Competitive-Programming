
import java.util.*;
import java.io.*;

public class angry {

	// http://usaco.org/index.php?page=viewproblem2&cpid=597

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("angry.in"));
		PrintWriter out = new PrintWriter(new FileWriter("angry.out"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n];
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(in.readLine());
		}
		Arrays.parallelSort(arr);
		
		int[] forwards = new int[n];
		int[] backwards = new int[n];
		int lastindex = 0;
		for (int i=1; i<n; i++) {
			if (arr[i] - arr[lastindex] <= forwards[i-1]) {
				forwards[i] = forwards[i-1];
			}
			else {
				forwards[i] = Math.max(forwards[i-1]+1, arr[i] - arr[i-1]);
				lastindex = i-1;
			}
		}
		
		lastindex = n-1;
		for (int i=n-2; i>=0; i--) {
			if (arr[lastindex] - arr[i] <= backwards[i+1]) {
				backwards[i] = backwards[i+1];
			}
			else {
				backwards[i] = Math.max(backwards[i+1]+1, arr[i+1] - arr[i]);
				lastindex = i+1;
			}
		}
		
		double ans=(double)(5e8);
		
		int i=0;
		int j = n-1;
		while (i<=j) {
			ans = Math.min(ans, Math.max(Math.max(forwards[i], backwards[j])+1, 
					(arr[j] - arr[i])/2.0));
			if (j-1>=0 && i+1<n && forwards[i+1] < backwards[j-1]) {
				i++;
			}
			else j--;
		}
		
		System.out.printf("%.1f", ans);
		out.printf("%.1f", ans);
		out.close();

	}
}


/*
	build two arrays
	forwards[i] --> minimum radius needed if you launched a cow at position arr[i]
						going to the left
	backwards[i] --> minimum radius needed if you launched a cow at position arr[i]
						going to the right
								
	end while loop is for --> you want to detonate in the middle of i and j, and 
		get everything inside with one bomb. Then use forwards[i] and backwards[j] to
			find needed to get everything on left of i and right of j

*/