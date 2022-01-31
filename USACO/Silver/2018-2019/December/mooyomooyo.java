
import java.util.*;
import java.io.*;

public class mooyomooyo {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=860
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("mooyomooyo.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());  
		int k = Integer.parseInt(st.nextToken());  

		char[][] bales = new char[n+1][];
		// ensure empty row on top, to make some subfunctions easier
		bales[0] = "0000000000".toCharArray();
		for (int r = 1; r <= n; r++) {
			bales[r] = in.readLine().toCharArray();  
		}
		in.close();
		
		boolean go = true;
		while (go) {
			// assume we can stop after this sweep
			go = false;
			HashSet<Integer> counted = new HashSet<>();
			// visited tracker
			// 	  element value = row # * 10 + column # (representing a unique cell)
			
			for (int r = 1; r <= n; r++) {
				for (int c = 0; c < 10; c++) {
					if (bales[r][c] != '0' && !counted.contains(r*10+c)) {
						int region = count(r, c, bales, counted);
						// finds how big region is
						
						// if any region gets zeroed out due to having a large enough size, 
							// do so and make sure to keep going later
						if (region >= k) {
							zeroOut(r, c, bales); // this row and column has stuff in it that is not 0
							go = true;
						}
					}
				}
			}
			if (go) gravity(bales);
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("mooyomooyo.out"));
		for (int r = 1; r <= n; r++) {
			String result = new String(bales[r]);
			System.out.println(result);
			out.println(result);
		}
		out.close();
	}

	public static void gravity(char[][] grid) {
		for (int c = 0; c < 10; c++) {
			int setter = grid.length-1; // bottom row
			while (grid[setter][c] != '0') setter--;
			
			// find lowest bale that will fall onto 0
			int getter = setter - 1;
			while (getter >= 0 && grid[getter][c] == '0') getter--;
			
			// move cells downward in a bottom up patter
			while (getter >= 0) {
				grid[setter][c] = grid[getter][c];
				grid[getter][c] = '0';
				
				setter--;
				
				// get next bale that will fall
				getter--;
				while (getter >= 0 && grid[getter][c] == '0') getter--;
				
			}
		}
	}
	
	public static void zeroOut(int r, int c, char[][] grid) {
		ArrayDeque<int[]> toVisit = new ArrayDeque<>();
		toVisit.push(new int[] {r, c});
		// we want to zero out the region next to and including r,c
		
		char target = grid[r][c];	
		
		while(toVisit.size() > 0) {
			int[] x = toVisit.pop();	// removes the beginning one
			r = x[0];
			c = x[1];
			
			// don't go out of bounds
			if (r < 0 || r >= grid.length || c < 0 || c >= 10) continue;
			if (grid[r][c] != target) continue;
			
			grid[r][c] = '0'; 
			
			toVisit.push(new int[] {r+1, c}); // puts this element {r+1, c} at the head of the deque
			toVisit.push(new int[] {r-1, c});
			toVisit.push(new int[] {r, c+1});
			toVisit.push(new int[] {r, c-1});
		}
	}
	
	public static int count(int r, int c, char[][] grid, HashSet<Integer> counted) {
		ArrayDeque<int[]> toVisit = new ArrayDeque<>();
		toVisit.push(new int[] {r,c});
		
		char target = grid[r][c];
		
		int count = 0;
		
		while (toVisit.size() > 0) {
			int[] x = toVisit.pop();
			r = x[0];
			c = x[1];
			
			if (r < 0 || r >= grid.length || c < 0 || c >= 10) continue;
			if (counted.contains(r*10 + c) || grid[r][c] != target) continue;
			
			counted.add(r*10+c);
			count++;
			
			toVisit.push(new int[] {r+1,c});
			toVisit.push(new int[] {r-1,c});
			toVisit.push(new int[] {r,c+1});
			toVisit.push(new int[] {r,c-1});
		}	
		return count;
	}	
}

/*
	grid size 1000
	
	assume k>1;
	
	flood 1000 cells to get counts, 
		flood size k spaces to eliminate k of them
		allow gravity to drop blocks (assume 1000 steps)
		
		repeat up to 1000/k

	(2000+k) * 1000/k = 2 mil / k + 1000
		if k = 2 --> 1 mil 
		good
		
	let gravity work, for each column:
		find lowest 0 in the column, set 2 row index pointers there, one is the 'setter' one is the 'retriever'
		move setter up to lowest 0
		move retriever up to next non-zero
			if none, stop with this column
			if there is, move retriever's value to setter's position, then move both row pointers up by 1
			
		entire process loops over each cell once
*/