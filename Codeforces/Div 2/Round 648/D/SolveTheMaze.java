
import java.util.*;
import java.io.*;

public class SolveTheMaze {

	// https://codeforces.com/contest/1365/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SolveTheMaze"));
		
		int t = Integer.parseInt(in.readLine());  
		for (int j=0; j<t; j++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken());  
			int m = Integer.parseInt(st.nextToken());  
			char[][] arr = new char[n][];
			for (int i=0; i<n; i++) {
				arr[i] = in.readLine().toCharArray();
			}
			for (int i=0; i<n; i++) {
				for (int k=0; k<m; k++) {
					if (arr[i][k] == 'B') {
						if (i-1 >=0) {
							if (arr[i-1][k] == '.') arr[i-1][k] = '#';
						}
						if (k-1 >=0) {
							if (arr[i][k-1] == '.') arr[i][k-1] = '#';
						}
						if (i+1 < n) {
							if (arr[i+1][k] == '.') arr[i+1][k] = '#';
						}
						if (k+1 < m) {
							if (arr[i][k+1] == '.') arr[i][k+1] = '#';
						}
					}
				}
			}
			
			HashMap<Integer, HashMap<Integer, Character>> visited = new HashMap<>();
			// row, col=letter (B or G)
			
			boolean works = true;
			outerloop: for (int i=0; i<n; i++) {
				for (int k=0; k<m; k++) { 
					if (arr[i][k] == 'G') {
						if (visited.containsKey(i) && visited.get(i).containsKey(k)) {
							if (visited.get(i).get(k) == 'B') {
								// this path was used by B, and B was not able to get to
									// destination
								works = false;
								break outerloop;
							}
						}
						else if (!dfs(i, k, arr, visited)) {
							works = false;
							break outerloop;
						}
					}
					if (arr[i][k] == 'B') {
						if (visited.containsKey(i) && visited.get(i).containsKey(k)) {
							if (visited.get(i).get(k) == 'G') {
								// this path was used by G, and G was able to get to
									// destination
								works = false;
								break outerloop;
							}
						}
						else if (dfs(i, k, arr, visited)) {
							works = false;
							break outerloop;
						}
					}
				}
			}
			
			if (works) System.out.println("Yes");
			else System.out.println("No");
		}
	}
	
	public static boolean dfs(int row, int col, char[][] grid, HashMap<Integer, HashMap<Integer, Character>> visited) {
		ArrayDeque<int[]> a = new ArrayDeque<>();
		a.add(new int[] {row, col});
		char curchar = grid[row][col];

		HashMap<Integer, HashSet<Integer>> curvisited = new HashMap<>();
		// row, col
		
		while (a.size() > 0) {
			int[] x = a.poll();
			int r = x[0];
			int c = x[1];
						
			if (r >= grid.length || c >= grid[0].length || r < 0 || c < 0) continue;
			
			if (grid[r][c] == '#') continue;
			
			if (r == grid.length-1 && c == grid[0].length-1) return true;

			if (visited.containsKey(r)) {
				if (visited.get(r).containsKey(c)) {
				//	continue;
				}
				else {
					visited.get(r).put(c, curchar);
				}
			}
			else {
				HashMap<Integer, Character> cur = new HashMap<>();
				cur.put(c, curchar);
				visited.put(r, cur);
			}
			
			if (curvisited.containsKey(r)) {
				if (curvisited.get(r).contains(c)) continue;
				else {
					curvisited.get(r).add(c);
				}
			}
			else {
				HashSet<Integer> cur = new HashSet<>();
				cur.add(c);
				curvisited.put(r, cur);
			}
			
			a.add(new int[] {r+1, c});
			a.add(new int[] {r-1, c});
			a.add(new int[] {r, c+1});
			a.add(new int[] {r, c-1});
		}
		return false;
	}
}