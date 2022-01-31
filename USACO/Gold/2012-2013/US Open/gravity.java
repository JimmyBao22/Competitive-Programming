
import java.util.*;
import java.io.*;

public class gravity {

	// http://usaco.org/index.php?page=viewproblem2&cpid=282
	
	static int n, m;
	static char[][] arr;
	static int[] start, end;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("gravity.in"));
		PrintWriter out = new PrintWriter(new FileWriter("gravity.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		arr = new char[n][];
		for (int i=0; i<n; i++) {
			arr[i] = in.readLine().toCharArray();
		}
		
		start=new int[2];
		end=new int[2];
		for (int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				if (arr[i][j] == 'C') {
					start[0] = i;
					start[1] = j;
				}
				if (arr[i][j] == 'D') {
					end[0] = i;
					end[1] = j;
				}
			}
		}
		
		int ans = find();
		
		if (ans>=1e9) {
			System.out.println(-1);
			out.println(-1);
		}
		else {
			System.out.println(ans);
			out.println(ans);
		}
		out.close();

	}
	
	public static int find() {
		int[][] ans = new int[n][m];
		for (int i=0; i<n; i++) {
			Arrays.fill(ans[i], (int) (1e9));
		}
		
		int min = (int)1e9;
		
		ArrayDeque<int[]> a = new ArrayDeque<>();
		a.add(new int[] {start[0], start[1], 0, 0});
			// x, y, gravity, number of flips so far
		
		while (!a.isEmpty()) {
			int[] cur = a.poll();
			int x= cur[0]; int y = cur[1]; int g = cur[2]; int count=cur[3];
			if (x<0 || x>=n || y<0 || y>=m) continue;
			if (arr[x][y] == '#') continue;
			if (ans[x][y] <= count) continue;
			
			if (x == end[0] && y == end[1]) {
				// found
				min = Math.min(min, count);
				continue;
			}
			
			if (g == 0 && x==n-1) {			//fall down
				continue;
			}
			else if (g==0 && arr[x+1][y] != '#') {
				a.add(new int[] {x+1,y,0, count});
				continue;
			}
			if (g == 1 && x==0) {
				continue;
			}
			else if (g == 1 && arr[x-1][y] != '#') {
				a.add(new int[] {x-1,y,1, count});
				continue;
			}
			
			ans[x][y] = count;
			// no gravity
			a.add(new int[] {x,y+1,g, count});
			a.add(new int[] {x,y-1,g, count});
			
			// gravity
			if (g == 0) {
				a.add(new int[] {x-1, y, 1, count+1});
			}
			else {
				a.add(new int[] {x+1, y, 0, count+1});
			}
		}
		
		return min;
	}
}