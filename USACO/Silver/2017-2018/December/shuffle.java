
import java.util.*;
import java.io.*;

public class shuffle {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=764
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("shuffle.in"));

		int n = Integer.parseInt(in.readLine());  
		int[] dest = new int[n+1];
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i = 1; i < n+1; i++) {
			dest[i] = Integer.parseInt(st.nextToken());  

		}
		
		in.close();

		boolean[] known = new boolean[n+1];
		// true; This position has been checked before, and therefore doesn't need a cycle check or counting 
			// (already counted)
		// false: this position still uncertain
			// check it
		
		int result = 0;
		for (int start = 1; start < n+1; start++) {
			// set visited for this traversal specifically
			HashSet<Integer> visited = new HashSet<>();
			
			// search for either a cycle, or a previously traversed position
			int i = start;
			while (!visited.contains(i) && !known[i]) {
				visited.add(i);
				i = dest[i]; //next step in shuffle
			}
			
			if (!known[i]) {
				//	i has been visited twice but was not known;
					// we just discovered it is part of a cycle
					// (because as seen above, visited contains i)
				
				int cycleStart = i;
				//count everything in cycle
				do {
					result++;
					i = dest[i];
				} while(i != cycleStart);
				// this is basically doing it the first time
					// then doing the while loop so that
					// it will actually run
			}
			// else : if i was already known BEFORE this traversal, no spaces
			//		before it in this traversal can be in a cycle - they would've already been visited as i was
			//		first becoming known
				// 		possible that there are no spaces before i
				//		because we tried to start a traversal at a known position
			//		therefore we've gotten everything we need from this traversal
			
			for (int b4 : visited) {
				known[b4] = true;
			}
		
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("shuffle.out"));
		System.out.println(result);
		out.println(result);
		out.close();
	}
}