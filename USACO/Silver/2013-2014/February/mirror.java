
import java.util.*;
import java.io.*;

public class mirror {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=394
	
	static int n,m;
	static Cell[][] arr;
	static char[][] s;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("mirror.in"));
		PrintWriter out = new PrintWriter(new FileWriter("mirror.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		arr = new Cell[n][m];
		s = new char[n][];
		for (int i=0; i<n; i++) {
			s[i] = in.readLine().toCharArray();
			for (int j=0; j<m; j++) arr[i][j] = new Cell();
		}
		
		int ans=1;
		
		for (int j=0; j<m; j++) {
				// top
			if (!arr[0][j].a[0]) {
				ans = Math.max(ans, bfs(0,j,0));
			}
			
				// bottom
			if (!arr[n-1][j].a[1]) {
				ans = Math.max(ans, bfs(n-1,j,1));
			}
		}
		
		for (int i=0; i<n; i++) {
				// left
			if (!arr[i][0].a[2]) {
				ans = Math.max(ans, bfs(i,0,2));
			}
			
				// right
			if (!arr[i][m-1].a[3]) {
				ans = Math.max(ans, bfs(i, m-1, 3));
			}
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	static int bfs(int i, int j, int k) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {i,j,k});
		int count=1;
		while (!d.isEmpty()) {
			int[] c = d.poll();
			int x = c[0]; int y = c[1]; int z = c[2];
			if (notvalid(x,y)) continue;
			if (arr[x][y].a[z]) continue;
			arr[x][y].a[z] = true;
			
			// go to another part of the cell
			if (z == 0) {
				if (s[x][y] == '/') d.add(new int[] {x,y,2});
				else if (s[x][y] == '\\') d.add(new int[] {x,y,3});
			}
			else if (z == 1) {
				if (s[x][y] == '/') d.add(new int[] {x,y,3});
				else if (s[x][y] == '\\') d.add(new int[] {x,y,2});
			}
			else if (z == 2) {
				if (s[x][y] == '/') d.add(new int[] {x,y,0});
				else if (s[x][y] == '\\') d.add(new int[] {x,y,1});
			}
			else {
				if (s[x][y] == '/') d.add(new int[] {x,y,1});
				else if (s[x][y] == '\\') d.add(new int[] {x,y,0});
			}
			
			// move to new cell
			if (z == 0) {
				if (!notvalid(x-1,y) && !arr[x-1][y].a[1]) {
					count++;
					d.add(new int[] {x-1, y, 1});
				}
			}
			else if (z == 1) {
				if (!notvalid(x+1, y) && !arr[x+1][y].a[0]) {
					count++;
					d.add(new int[] {x+1, y, 0});
				}
			}
			else if (z == 2) {
				if (!notvalid(x, y-1) && !arr[x][y-1].a[3]) {
					count++;
					d.add(new int[] {x, y-1, 3});
				}
			}
			else {
				if (!notvalid(x, y+1) && !arr[x][y+1].a[2]) {
					count++;
					d.add(new int[] {x, y+1, 2});
				}
			}
		}
		return count;
	}
	
	static boolean notvalid(int x, int y) {
		return (x < 0 || y < 0 || x >= n || y >= m);
	}

	static class Cell {
		boolean[] a = new boolean[4];
			// up, down, left, right
	}
}