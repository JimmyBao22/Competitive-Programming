
import java.util.*;
import java.io.*;

public class cowart {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=414
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cowart.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cowart.out"));

		int n = Integer.parseInt(in.readLine());
		char[][] arr = new char[n][];
		for (int i=0; i<n; i++) {
			arr[i] = in.readLine().toCharArray();
		}
		
		boolean[][] visited1 = new boolean[n][n];
		int count=0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				if (!visited1[i][j]) {
					dfs1(visited1, arr, n, i, j);
					count++;
				}
			}
		}
		
		boolean[][] visited2 = new boolean[n][n];
		int count2=0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				if (!visited2[i][j]) {
					dfs2(visited2, arr, n, i, j);
					count2++;
				}
			}
		}

		System.out.println(count + " " + count2);
		out.println(count + " " + count2);
		out.close();
	}
	
	public static void dfs2(boolean[][] visited2, char[][] arr, int n, int i, int j) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {i,j});
		while (d.size() > 0) {
			int[] c = d.poll();
			int x = c[0];
			int y = c[1];
			if (x<0 || y <0 || x>= n || y>= n) continue;
			if (visited2[x][y]) continue;
			if (arr[i][j] == 'G' || arr[i][j] == 'R') {
				if (arr[x][y] == 'B') continue;
			}
			else if (arr[i][j] == 'B' && arr[x][y] != 'B') continue;
			visited2[x][y] = true;
			d.add(new int[] {x+1, y});
			d.add(new int[] {x-1, y});
			d.add(new int[] {x, y+1});
			d.add(new int[] {x, y-1});
		}
	}
	
	public static void dfs1(boolean[][] visited1, char[][] arr, int n, int i, int j) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {i,j});
		while (d.size() > 0) {
			int[] c = d.poll();
			int x = c[0];
			int y = c[1];
			if (x<0 || y <0 || x>= n || y>= n) continue;
			if (visited1[x][y]) continue;
			if (arr[x][y] != arr[i][j]) continue;
			visited1[x][y] = true;
			d.add(new int[] {x+1, y});
			d.add(new int[] {x-1, y});
			d.add(new int[] {x, y+1});
			d.add(new int[] {x, y-1});
		}
	}	
}