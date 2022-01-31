
import java.util.*;
import java.io.*;

public class cowdance {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=690
	
	static int n;
	static int[] cows; // performance lengths of each cow
	static int tMax;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cowdance.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());  
		tMax = Integer.parseInt(st.nextToken());  
		cows = new int[n];
		
		for (int i = 0; i < n; i++) {
			cows[i] = Integer.parseInt(in.readLine());  
		}
		in.close();

		int kMin = 1;
		int kMax = n;
		
		while (kMin < kMax) {
			int kMid = (kMin + kMax) / 2;
			if (checkStageSize(kMid)) {
				// kMid works; stage never needs to be bigger
				kMax = kMid;
			}
			else {
				// kMid fails; stage needs to be bigger
				kMin = kMid + 1;
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("cowdance.out"));
		System.out.println(kMin);
		out.println(kMin);
		out.close();
	}

	public static boolean checkStageSize(int k) {
		PriorityQueue<Integer> finishTimes = new PriorityQueue<>();
		
		// add first k cows to the queue
		for (int i = 0; i < k; i++) {
			// start: 0
			// end: whenever they finish (already in array)
			finishTimes.add(cows[i]);  
		}
		// add remaining cows to queue, making room each time
		for ( int i = k; i < n; i++) {
			// make room on stage
			int t = finishTimes.poll(); 
				// start: when the next cow gets out
			
			t += cows[i];	// finish time for cow i
			if (t > tMax) return false;
			finishTimes.add(t);
		}
		return true;
	}
}