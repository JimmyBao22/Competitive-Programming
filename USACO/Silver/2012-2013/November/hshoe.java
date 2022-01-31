
import java.util.*;
import java.io.*;

public class hshoe {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=189
	
	static int n;
	static char[][] grid;
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("hshoe.in"));

		n = Integer.parseInt(in.readLine());  
		
		grid = new char[n][];
		for (int i = 0; i < n; i++) {
			grid[i] = in.readLine().toCharArray();
		}  

		in.close();

		int result = explore(0,0,0,0);
		// explore from cell [0,0] with 0 open and 0 close rn to start
		
		PrintWriter out = new PrintWriter(new FileWriter("hshoe.out"));
		System.out.println(result);
		out.println(result); 
		out.close();

	}
	
	// return the max length of path that results in the same # of parenthesis of both types
		// satisfying the condition
	public static int explore(int r, int c, int opens, int closes) {
		boolean[][] visited = new boolean[n][n];
			// default = false
		
		Deque<Cell> stack = new ArrayDeque<>();
		
		stack.push( new Cell(r, c, opens, closes) );
		
		int maxlength = 0;
		
		while (stack.size() > 0) {
			Cell cur = stack.pop();
			if (cur.recurred) {
				// all paths have been 'recursively' explored from this cell
					// so unvisit it so other paths can go through this
				visited[cur.r][cur.c] = false;	
			}
			else {

				if (cur.r < 0 || cur.r >= n || cur.c < 0 || cur.c >= n) {
					continue;
					// abandon this cell and move on to other ones if this is out of bounds
				}
								
				if (cur.opens == cur.closes && cur.opens > 0) {
					maxlength = Math.max(maxlength, cur.closes + cur.opens);
					continue;
					// cur.closes and cur.opens is the number BEFORE this cell; no use for this cell
				}
				
				if (visited[cur.r][cur.c]) continue;
				
				// can't visit ( after a )
				if (grid[cur.r][cur.c] == '(' && cur.closes > 0) continue;
										
				visited[cur.r][cur.c] = true;
				cur.recurred = true;
				stack.push(cur);
				
				int newOpens = cur.opens;
				int newCloses = cur.closes;
				
				if (grid[cur.r][cur.c] == '(') newOpens++;
				else newCloses++;
				
				// recur in 4 directions
				stack.push(new Cell(cur.r, cur.c-1, newOpens, newCloses));
				stack.push(new Cell(cur.r, cur.c+1, newOpens, newCloses));
				stack.push(new Cell(cur.r-1, cur.c, newOpens, newCloses));
				stack.push(new Cell(cur.r+1, cur.c, newOpens, newCloses));
			}
		}
		
		return maxlength;
	}

	// cell we are exploring, part of a longer path
	// takes the place of a recursive function call
	static class Cell {
		int r, c;
		int opens, closes;	// # of opening & closing parentehsis in path BEFORE this cell
		boolean recurred; 	// have we made recursive calls for this cell already
		
		Cell(int r, int c, int op, int cl) {
			this.r = r;
			this.c = c;
			opens = op;
			closes = cl;
			// recurred set to false by default
		}	
	}
}

/*
	recursive outline:
		call a recursive 'explore' function on upper left corner
		explore function:
			keep track of # of open/closed so far
			check boundaries against next space
			check other rule - can't go back to open after any are closed
			if # close == # of open
				return this length
			if this space already visited, end this branch
				
			mark this space as 'visited'
			recursively explore the neighboring spaces
	
		big difference from floodfill
			UNMARK spaces we try so we can try again from a different direction
	
*/