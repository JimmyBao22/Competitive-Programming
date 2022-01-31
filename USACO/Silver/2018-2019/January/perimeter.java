
import java.util.*;
import java.io.*;

public class perimeter {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=895
	
	static HashSet<Integer> visited;
		// 1000*r + c
	static char[][] board;
	static TreeMap<Integer, TreeSet<Integer>> area_perim;
		// area --> perim
	static int n;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("perimeter.in"));
		PrintWriter out = new PrintWriter(new FileWriter("perimeter.out"));

		n = Integer.parseInt(in.readLine());
		visited = new HashSet<>();
		
		board = new char[n][];
		for (int i=0; i<n; i++) {
			board[i] = in.readLine().toCharArray();
		}
		
		area_perim = new TreeMap<>();
		
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				if (board[i][j] == '#' && !visited.contains(1000*i + j)) {
					dfs(i,j);
				}
			}
		}
		
		int largestarea = 0;
		int smallestperim =0;
		if (area_perim.size() > 0) {
			largestarea = area_perim.lastKey();
			smallestperim = area_perim.get(largestarea).first();
		}
		
		System.out.println(largestarea + " " + smallestperim);
		out.println(largestarea + " " + smallestperim);
		out.close();

	}
	
	public static void dfs(int a, int b) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {a,b});
		int countarea=0;
		int countperim=0;
		while (d.size() > 0) {
			int[] cur = d.poll();
			int x = cur[0];
			int y = cur[1];
			if (x >= n || x < 0 || y < 0 || y>= n) continue;
			if (board[x][y] == '.') continue;
			if (visited.contains(1000*x + y)) continue;
			visited.add(1000*x + y);
			countarea++;
			
			// count perimeter
			if (x+1 >= n || board[x+1][y] == '.') countperim++;
			if (x-1 < 0 || board[x-1][y] == '.') countperim++;
			if (y+1 >= n || board[x][y+1] == '.') countperim++;
			if (y-1 < 0 || board[x][y-1] == '.') countperim++;

			d.add(new int[] {x+1, y});
			d.add(new int[] {x-1, y});
			d.add(new int[] {x, y+1});
			d.add(new int[] {x, y-1});
		}
		if (area_perim.containsKey(countarea)) {
			area_perim.get(countarea).add(countperim);
		}
		else {
			TreeSet<Integer> c = new TreeSet<>();
			c.add(countperim);
			area_perim.put(countarea, c);
		}
	}
}