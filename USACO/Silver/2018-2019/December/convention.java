
import java.util.*;
import java.io.*;

public class convention {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=858

	static int n, m, c;
	static int[] times;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("convention.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());  
		m = Integer.parseInt(st.nextToken());  
		c = Integer.parseInt(st.nextToken());  

		times = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i = 0; i < n; i++) times[i] = Integer.parseInt(st.nextToken()); 
		
		in.close();

		Arrays.sort(times);
		
		int lowLimit = 0;
			// lowest waiting time that could work
		
		int hiLimit = times[n-1]-times[0];
			// lowest waiting time that will 100% work right now
				// need to try to find faster 
		
		while (lowLimit < hiLimit) {
			int mid = (lowLimit + hiLimit)/2;
			
			if (busCows(mid)) {
				// this worked; don't need to go higher now
				hiLimit = mid;
			}
			else {
				// this time did not work
				lowLimit = mid + 1;
			}
		}
		
		int result = lowLimit; // limits should be same by now
		
		PrintWriter out = new PrintWriter(new FileWriter("convention.out"));
		System.out.println(result);
		out.println(result);
		out.close();
	}

	public static boolean busCows(int wMax) {
		int buses = 1;
		
		int onThisBus = 1;
		int busStart = times[0];

		for (int i=1; i<n; i++) {
			if (onThisBus >= c || times[i] - busStart > wMax) {
				buses++;
								
				onThisBus = 1;
				busStart = times[i];
			}
			else {
				onThisBus++;
			}
		}
		return buses <= m;	
	}
}

/*
	Assume maximum time limit, then see it it's possible
	
	for any possible max wait time, wMax, try:
		track time and capacity characteristics of bus one at a time
		loop through cows in order
			if its time is greater than wMax, or bus is full, then coint a new bus
			if count exceeds wMax, or number of buses, fail
		otherwise its success
		
		use binary search for limits
*/