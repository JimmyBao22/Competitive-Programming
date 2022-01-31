
import java.util.*;
import java.io.*;

public class diamond {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=643
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("diamond.in"));
		PrintWriter out = new PrintWriter(new FileWriter("diamond.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		if (n == 0) {
			System.out.println(0);
			out.println(0);
			out.close();
			return;
		}
		
		int[] arr = new int[n];
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(in.readLine());
		}
		Arrays.sort(arr);
		
		int[] max = new int[n+1];
		int secondpointer=0;
		for (int i=1; i<n; i++) {
			if (arr[i] - arr[0] > k) {
				secondpointer = i-1;
				break;
			}
		}
		max[0] = secondpointer+1;
		//sliding window
		for (int i=1; i<n; i++) {
			// i = firstpointer
			for (int j=secondpointer+1; j<n; j++) {
				if (arr[j] - arr[i] > k) {
					break;
				}
				else secondpointer=j;
			}
			max[i] = secondpointer-i+1;
		}
		
		int[] max2 = new int[n+1];
		int ans=0;
		for (int i=n-1; i>=0; i--) {
			max2[i] = Math.max(max2[i+1], max[i]);
			ans = Math.max(ans, max[i] + max2[i+max[i]]);
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}

}

// first sliding window to see how many it can get, if the min is that position
// then loop through and do greedy.