
import java.util.*;
import java.io.*;

public class cownav {

	// http://usaco.org/index.php?page=viewproblem2&cpid=695
	
	static int n;
	static char[][] arr;
	static long[][][][][][] memo;
	static int INF = (int)(1e9);
	static int[][] dir = new int[][] {{-1,0}, {0,1}, {1,0}, {0,-1}};
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cownav.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cownav.out"));

		n = Integer.parseInt(in.readLine());
		arr = new char[n][];
		for (int i=0; i<n; i++) {
			arr[i] = in.readLine().toCharArray();
		}
		
		// start at n-1,0, go to 0,n-1
		memo = new long[n][n][n][n][4][4];
			// 0 = north, 1 = east, 2 = south, 3 = west
			// [firstx][firsty][secondx][secondy][firstdir][seconddir]
		
		for (int i=0; i<n; i++) {
			for (int a=0; a<n; a++) {
				for (int b=0; b<n; b++) {
					for (int c=0; c<n; c++) {
						for (int d=0;d<4;d++) {
							Arrays.fill(memo[i][a][b][c][d], INF);
						}
					}
				}
			}
		}
		
		bfs();
		
		long ans=INF;
		for (int i=0;i<4;i++) {
			for (int j=0; j<4; j++) {
				ans = Math.min(ans, memo[0][n-1][0][n-1][i][j]);
			}
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static void bfs() {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {n-1,0,n-1,0,0,1,0});		
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			int firstx = cur[0]; int firsty = cur[1]; int secondx = cur[2]; int secondy = cur[3];
			int firstdir = cur[4]; int seconddir = cur[5]; int val = cur[6];
			if (firstx<0 || firsty<0 || firstx>=n || firsty>=n) continue;
			if (secondx<0 || secondy<0 || secondx>=n || secondy>=n) continue;
			if (arr[firstx][firsty] == 'H') continue;
			if (arr[secondx][secondy] == 'H') continue;
			
			// all good
			if (firstx == 0 && firsty == n-1 && secondx == 0 && secondy == n-1) {
				memo[firstx][firsty][secondx][secondy][firstdir][seconddir] = Math.min(memo[firstx][firsty][secondx][secondy][firstdir][seconddir], val);
				continue;
			}
			
			// already seen
			if (memo[firstx][firsty][secondx][secondy][firstdir][seconddir] <= val) {
				continue;
			}
			
			memo[firstx][firsty][secondx][secondy][firstdir][seconddir] = val;
			
			// first good
			if (firstx == 0 && firsty == n-1) {
				// move second one
				secondx += dir[seconddir][0];
				secondy += dir[seconddir][1];
				boolean bad=false;
				if (secondx<0 || secondy<0 || secondx>=n || secondy>=n || arr[secondx][secondy] == 'H') {
					secondx -= dir[seconddir][0];		// reset
					secondy -= dir[seconddir][1];
					bad=true;
				}
				d.add(new int[] {firstx,firsty, secondx, secondy, firstdir, seconddir, val+1});
				if (!bad) {
					secondx -= dir[seconddir][0];
					secondy -= dir[seconddir][1];
				}
				// all turns
				for (int i=1; i<=3; i++) {
					if (i==1||i==3) d.add(new int[] {firstx,firsty, secondx, secondy, (firstdir+i)%4, (seconddir+i)%4, val+1});
					else d.add(new int[] {firstx,firsty, secondx, secondy, (firstdir+i)%4, (seconddir+i)%4, val+2});
				}
			}
			else if (secondx == 0 && secondy == n-1) {			// second good
				// move first one
				firstx += dir[firstdir][0];
				firsty += dir[firstdir][1];
				boolean bad = false;
				if (firstx<0 || firsty<0 || firstx>=n || firsty>=n || arr[firstx][firsty] == 'H') {
					firstx -= dir[firstdir][0];
					firsty -= dir[firstdir][1];
					bad=true;
				}
				d.add(new int[] {firstx,firsty, secondx, secondy, firstdir, seconddir, val+1});
				if (!bad ) {
					firstx -= dir[firstdir][0];
					firsty -= dir[firstdir][1];
				}
				// all turns
				for (int i=1; i<=3; i++) {
					if (i==1||i==3) d.add(new int[] {firstx,firsty, secondx, secondy, (firstdir+i)%4, (seconddir+i)%4, val+1});
					else d.add(new int[] {firstx,firsty, secondx, secondy, (firstdir+i)%4, (seconddir+i)%4, val+2});
				}
			}
			else {
				// move both!
				
				// move forwards
				boolean badone=false;
				boolean badtwo=false;
				
				firstx += dir[firstdir][0];
				firsty += dir[firstdir][1];
				if (firstx<0 || firsty<0 || firstx>=n || firsty>=n || arr[firstx][firsty] == 'H') {
					firstx -= dir[firstdir][0];
					firsty -= dir[firstdir][1];
					badone=true;
				}
				secondx += dir[seconddir][0];
				secondy += dir[seconddir][1];
				if (secondx<0 || secondy<0 || secondx>=n || secondy>=n || arr[secondx][secondy] == 'H') {
					secondx -= dir[seconddir][0];
					secondy -= dir[seconddir][1];
					badtwo=true;
				}
				d.add(new int[] {firstx,firsty, secondx, secondy, firstdir, seconddir, val+1});
				if (!badone) {
					firstx -= dir[firstdir][0];
					firsty -= dir[firstdir][1];
				}
				if (!badtwo) {
					secondx -= dir[seconddir][0];
					secondy -= dir[seconddir][1];
				}
				// all turns
				for (int i=1; i<=3; i++) {
					if (i==1||i==3) d.add(new int[] {firstx,firsty, secondx, secondy, (firstdir+i)%4, (seconddir+i)%4, val+1});
					else d.add(new int[] {firstx,firsty, secondx, secondy, (firstdir+i)%4, (seconddir+i)%4, val+2});
				}
			}
		}
	}
}