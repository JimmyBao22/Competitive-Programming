import java.util.*;
import java.io.*;

public class baseball {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=359
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("baseball.in"));

		int n = Integer.parseInt(in.readLine());  
		
		int[] xs = new int[n];
		for (int i = 0; i < n; i++) {
			xs[i] = Integer.parseInt(in.readLine());  
		}
		in.close();

		Arrays.sort(xs);
		
		int result = 0;

		for (int i = 0; i < n; i++) {
			for (int j = i+1; j < n; j++) {
				
				int dist1 = xs[j] - xs[i];
				
				int maxdist = xs[j] + 2 * dist1;
				int mindist = xs[j] + dist1;
				// for third cow
				
				int kLow = Arrays.binarySearch(xs, mindist);
				int kHigh = Arrays.binarySearch(xs, maxdist);
								
				/* 
				 	ex. 	if we do not find it
					value	15 16 	19 		25
					indexes	 5	6 	 7		 8
						
						//  https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#binarySearch(int[],%20int)
						search for 14 -->
							insertion point = 5
							return (-5-1 = -6)
							
							RV = -IP - 1;
							IP = -RV - 1;
				*/
				
				if (kLow < 0) kLow = -kLow - 1;
				if (kHigh < 0) kHigh = -kHigh - 1 - 1;
				
				// ex if we are searching for 22 in the array above
				// 		it will return 8 but we do not want the 8th number (25)
				
				result += kHigh - kLow +1;
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("baseball.out"));
		System.out.println(result);
		out.println(result);
		out.close();

	}

}

/*
	find earliest possible third cow that works (index); find last (index)
		do index - index + 1
		
	use binary search to find lower bound and upper bound, each in logn time
	
	O(n^2logn) time (first two cows still takes n^2, third cow takes logn)
*/