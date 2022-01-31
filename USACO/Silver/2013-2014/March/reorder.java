
import java.util.*;
import java.io.*;

public class reorder {

	// http://usaco.org/index.php?page=viewproblem2&cpid=412

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("reorder.in"));

		int n = Integer.parseInt(in.readLine());  

		int[] before = new int[n];
		int[] after = new int[n];
		
		for (int i = 0; i < n; i++) {
			before[i] = Integer.parseInt(in.readLine());   
		}
		for (int i = 0; i < n; i++) {
			after[i] = Integer.parseInt(in.readLine());   
		}
		in.close();

		int cycles = 0;
		int maxLen = -1;
		
		boolean[] moved = new boolean[n];
		
		for (int start = 0; start < n; start++) {
			// start = BEFORE INDEX of cow moving to begin a cycle
			
			// skip cows that already moved in an earlier cycle
			if (moved[start]) continue;
			
			// find AFTER index of starting cow
			int i = find(after, before[start]);
			// cow moved to same place (aka didn't move at all)
			if (i == start) continue;
			
			cycles++;
			
			int len = 1; //length of cycle
			moved[start] = true; 
			
			// continue shifting cows until cycle completes
			
			//remember i is the after position calculated above
			while (i != start) {
				len++;
				moved[i] = true;
				
				// find AFTER index of each cow when it moves
				// this lets us see which cow is present
				//	in that spot before hand, and thus which is the next
				// cow that needs to move.
				
				i = find(after, before[i]);
			}
			
			maxLen = Math.max(len, maxLen);		
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("reorder.out"));
		System.out.println(cycles + " " + maxLen);
		out.println(cycles + " " + maxLen);
		out.close();

	}

	static int find(int[] arr, int target) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == target) return i;
		}
		return -1;
	}
}

/*
	O(n^2)
	
	
	faster:
		Every time we are finding something, we are looping through whole thing again.
		We can create a data structure that finds the values ahead of time
		
		Set up structure where for any ID #, we can find where in 2nd array
			index: ID # of cow that has moved
			value: position in second input array that cow moved to
			
		Ex. Look up table for fast search
			
			static int[] afterLocs; //after location
			
			static int findFast(int[] after, int target) {
				return afterLocs[target];
			}
			
			static void buildLookupTable(int[] after) {
				afterLocs = new int[after.length+1];
				// need extra since 0 unused
				
				for (int i = 0; i < after.length; i++) {
					int id = after[i];
					afterLocs[id] = i;
				}
				// index of after becomes value of afterLocs
				// value of after becomes index of afterLocs
				 
				ex. if it was A = 5 1 4 2 3; B = 2 5 3 1 4 
			 	    and you're trying to find where the 5 goes in B, in 
			 	    the after array it will just give you 1, but
			 	    you have to do the whole searching thing. Here
			 	    you create a new array afterLocs[0, 3, 0, 2, 4, 1]
			 	    and you know the 5 went to index 1. 
				 
			}
*/