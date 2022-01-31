
import java.util.*;
import java.io.*;

public class gates {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=596
	
	static int n;
	static String s;
	static boolean[][] grid;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("gates.in"));
		PrintWriter out = new PrintWriter(new FileWriter("gates.out"));

		n = Integer.parseInt(in.readLine());
		s = in.readLine();
		grid = new boolean[4101][4101];
		// grid[i][j] --> if i+j even, then its like a coordinate point. 
			// Otherwise, it is an edge between 2 unit blocks. Therefore, 
			// when you walk past need to cover up both
		
		int curx = 2010;
		int cury = 2010;
		
		for (int i=0; i<n; i++) {
			if (s.charAt(i) == 'N') {
				grid[curx+1][cury] = true;
				grid[curx+2][cury] = true;
				curx += 2;
			}
			else if (s.charAt(i) == 'S') {
				grid[curx-1][cury] = true;
				grid[curx-2][cury] = true;
				curx -= 2;
			}
			else if (s.charAt(i) == 'E') {
				grid[curx][cury+1] = true;
				grid[curx][cury+2] = true;
				cury += 2;
			}
			else {
				grid[curx][cury-1] = true;
				grid[curx][cury-2] = true;
				cury -= 2;
			}
		}
		
		int regions=0;
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[0].length; j++) {
				if (!grid[i][j]) {
					regions++;
					bfs(i,j);
				}
			}
		}
		
		System.out.println(regions-1);
		out.println(regions-1);
		out.close();
	}
	
	public static void bfs(int i, int j) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {i,j});
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			i = cur[0]; j = cur[1];
			if (notvalid(i,j)) continue;
			if (grid[i][j]) continue;
			grid[i][j] = true;
			
			d.add(new int[] {i+1, j});
			d.add(new int[] {i-1, j});
			d.add(new int[] {i, j+1});
			d.add(new int[] {i, j-1});
		}
	}
	
	public static boolean notvalid(int i, int j) {
		return i < 0 || j < 0 || i >= grid.length || j >= grid[0].length;
	}
}