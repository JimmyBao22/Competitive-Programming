import java.util.*;
import java.io.*;

public class relay {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=241
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("relay.in"));

		int n = Integer.parseInt(in.readLine());  
		int[] F = new int[n];
		for (int i = 0; i < n; i++) {
			F[i] = Integer.parseInt(in.readLine());
		}

		in.close();


		int result = 0;
		
		for (int i = 1; i <= n; i++) {
			Set<Integer> visited = new HashSet<Integer>();
			int curr = i;
			while (curr != 0 && !visited.contains(curr)) {
				visited.add(curr);
				curr = F[curr-1];
			}
			
			if (curr == 0) result++;
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("relay.out"));
		System.out.println(result);
		out.println(result);
		out.close();

	}

}

// possible solutions:

/* 
  1.
  Kind of slow, worst case is O(n^2) i think
  you loop through every cow and then look at their path. 
  could be counting some loops multiple times
  
  		for (int i = 1; i <= n; i++) {
			Set<Integer> visited = new HashSet<Integer>();
			int curr = i;
			while (curr != 0 && !visited.contains(curr)) {
				visited.add(curr);
				curr = F[curr-1];
			}
			
			if (curr == 0) result++;
		}
		*/
	
/*
 	2.
 	faster
 	check cows 1 by 1, and continue relaying until encounter 0, or found it to be loopy
 		if its loopy, then mark it as loop
 		next time we check we will see if it passes this cow, 
 		and we know this cow is already loopy
 		we know the whole thing is loopy
 
 	- could use 2 hashsets (one for loopy, one for nonloopy)
 	- could use an array with 3 values (+1, 0, or -1 for loopy, nonloopy, unknown)
 
 	if we reached 0 OR NONLOOPY cow --> go back through all cows visited from this
 	starting cow and mark NONLOOPY, add 1 to count of nonloopy cows
 	
 	if we reached a cow already visited from this starting point OR a known loopy cow -->
 		go back through all cows visited from start and mark LOOPY
 
 	
 */
