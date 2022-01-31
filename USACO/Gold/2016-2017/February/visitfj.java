
import java.util.*;
import java.io.*;

public class visitfj {

	// http://usaco.org/index.php?page=viewproblem2&cpid=717
	
	static int n;
	static long t;
	static long[][] grid;
	static long INF = (long)(1e18);
	static long[][][] memo;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("visitfj.in"));
		PrintWriter out = new PrintWriter(new FileWriter("visitfj.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());
		grid = new long[n][n];
		memo = new long[n][n][3];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j=0; j<n; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
				Arrays.fill(memo[i][j], INF);				
			}
		}
		
		dijkstras();
		
		long min=INF;
		for (int i=0; i<3; i++) {
			min = Math.min(min, memo[n-1][n-1][i]);
		}
		
		//System.out.println(min);
		out.println(min);
		out.close();

	}
	
	static int[] dx = new int[] {1,-1,0,0};
	static int[] dy = new int[] {0,0,1,-1};
	
	public static void dijkstras() {
		PriorityQueue<long[]> pq = new PriorityQueue<>(new Comparator<long[]>() {
			public int compare(long[] a, long[] b) {
				return Long.compare(a[3], b[3]);
			}
		});
		
		boolean[][][] visited = new boolean[n][n][3];
		
		pq.add(new long[] {0,0,0,0});
		while (!pq.isEmpty()) {
			long[] cur = pq.poll();
			int x = (int)cur[0]; int y = (int)cur[1]; int turn = (int)cur[2]; long val = cur[3];
			if (x<0 || x>=n || y<0 || y>=n) continue;
			if (turn == 3) {
				val += grid[x][y];
				turn=0;
			}
			if (visited[x][y][turn]) continue;
			if (memo[x][y][turn] <= val) continue;
			visited[x][y][turn] = true;
			memo[x][y][turn] = val;
			
			for (int i=0; i<4; i++) {
				int cx = x+dx[i];
				int cy = y+dy[i];
				pq.add(new long[] {cx,cy,turn+1,val+t});
			}
		}
	}
}