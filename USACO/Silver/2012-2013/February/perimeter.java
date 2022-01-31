
import java.util.*;
import java.io.*;

public class perimeter {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=243
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("perimeter.in"));

		int n = Integer.parseInt(in.readLine());  
		char[][] bales = new char[102][102]; 	
		
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(st.nextToken());  
			int y = Integer.parseInt(st.nextToken()); 
			bales[bales.length-y-1][x] = 'X';
			// this sets up all the bales
			
		}

		in.close();
		
		// recursion
		int result = count(0, 0, bales);

		PrintWriter out = new PrintWriter(new FileWriter("perimeter.out"));
		System.out.println(result);
		out.println(result);
		out.close();

	}

	public static int count(int x, int y, char[][] bales) {
		ArrayDeque<int[]> loop = new ArrayDeque<>();
		loop.push(new int[] {x, y});
		
		int count = 0;
		boolean[][] counted = new boolean[bales.length][bales[0].length]; 
			// keeps track of counted
		
		while(loop.size() > 0) {
			
			int[] a = loop.pop();
			x = a[0];
			y = a[1];
			
			if (x < 0 || x >= bales.length || y < 0 || y >= bales[0].length) continue;
			if (counted[x][y]) continue;
			if (bales[x][y] == 'X') continue;
			
			counted[x][y] = true;
			
			if (x != bales.length-1 && bales[x+1][y] == 'X') count++;
			if (x != 0 && bales[x-1][y] == 'X') count++;
			if (y != bales[0].length-1 && bales[x][y+1] == 'X') count++;
			if (y != 0 && bales[x][y-1] == 'X') count++;
			
			loop.push(new int[] {x+1,y});
			loop.push(new int[] {x-1,y});
			loop.push(new int[] {x,y+1});
			loop.push(new int[] {x,y-1});
		}	
		return count;
	}	
	
}

/*

	My thinking
		8
		5 3
		5 4
		8 4
		5 5
		6 3
		7 3
		7 4
		6 5
		
			......
			.XX...
			.X XX.
			.XXX..
			......
			
			Make the field a 100x100 grid.
			Loop through the field in a floodfill manner
				Mark the ones already visited --> continue
				If outside boundary --> continue
				If it is an X --> continue
				
				If it is touching an X directly top left bottom right --> +1 to count
				continue deque

*/