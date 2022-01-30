
import java.util.*;
import java.io.*;

public class Woodcutters {
	
	// https://codeforces.com/problemset/problem/545/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Woodcutters.in"));

		int n = Integer.parseInt(in.readLine());  

		long[] treecoord = new long[n];
		long[] length = new long[n];
		
		for (int i = 0 ; i < n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			long x = Long.parseLong(st.nextToken());  
			treecoord[i] = x;
			length[i] = Long.parseLong(st.nextToken());
		}
		
		int[] stay = new int[n];
		int[] left = new int[n];
		int[] right = new int[n];
		
		// = # of fallen trees
		stay[0] = 0;
		left[0] = 1;
		right[0] = 1;
		
		for (int i = 1; i < n; i++) {
			stay[i] = Math.max(stay[i-1], left[i-1]);
			right[i] = Math.max(stay[i-1], left[i-1])+1;
			if (treecoord[i] > treecoord[i-1] + length[i-1]) {
				stay[i] = Math.max(stay[i], right[i-1]);
				right[i] = Math.max(right[i], right[i-1]+1);
			}
			
			// fall to the left
			if (treecoord[i] - length[i] > treecoord[i-1]) {
				left[i] = Math.max(stay[i-1], left[i-1])+1;
			}
			if (treecoord[i] - length[i] > treecoord[i-1] + length[i-1]) {
				left[i] = Math.max(left[i], right[i-1] + 1);
			}		
		}

		System.out.println(Math.max(left[n-1], Math.max(stay[n-1], right[n-1])));
	}
}