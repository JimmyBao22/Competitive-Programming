
import java.util.*;
import java.io.*;

public class distant {

	// http://usaco.org/index.php?page=viewproblem2&cpid=191
	
	static char[][] board;
	static long[][][][] dist;
	static long INF = (long)(1e18);
	static int n;
	static long a, b;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("distant.in"));
		PrintWriter out = new PrintWriter(new FileWriter("distant.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		a = Integer.parseInt(st.nextToken());
		b = Integer.parseInt(st.nextToken());
		dist = new long[n][n][n][n];
			// starting point, then the current point --> distance between those points
		board = new char[n][];
		for (int i=0; i<n; i++) {
			board[i] = in.readLine().toCharArray();
		}
		
		long max = 0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				d(i,j);
				for (int k=0; k<n; k++) {
					for (int z=0; z<n; z++) {
						max = Math.max(max, dist[i][j][k][z]);
					}
				}
			}
		}
		
		System.out.println(max);
		out.println(max);
		out.close();

	}
	
	public static void d(int x, int y) {
		boolean[][] visited = new boolean[n][n];
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		pq.add(new Edge(x,y,0));
		for (int i=0; i<n; ++i) {
			Arrays.fill(dist[x][y][i], INF);
		}
		dist[x][y][x][y] = 0;
		
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			if (cur.x<0 ||cur.y<0 || cur.x>=n || cur.y >= n) continue;
			if (visited[cur.x][cur.y]) continue;
			visited[cur.x][cur.y] = true;
			
				// cur.x+1
			if (cur.x+1 <n) {
				long curval = cur.val;
				if (board[cur.x][cur.y] != board[cur.x+1][cur.y]) curval += b;
				else curval += a;
				if (curval < dist[x][y][cur.x+1][cur.y]) {
					dist[x][y][cur.x+1][cur.y] = curval;
					pq.add(new Edge(cur.x+1, cur.y, curval));
				}
			}
			
			// cur.x-1
			if (cur.x-1>=0) {
				long curval = cur.val;
				if (board[cur.x][cur.y] != board[cur.x-1][cur.y]) curval += b;
				else curval += a;
				if (curval < dist[x][y][cur.x-1][cur.y]) {
					dist[x][y][cur.x-1][cur.y] = curval;
					pq.add(new Edge(cur.x-1, cur.y, curval));
				}
			}
			
			// cur.y+1
			if (cur.y+1 <n) {
				long curval = cur.val;
				if (board[cur.x][cur.y] != board[cur.x][cur.y+1]) curval += b;
				else curval += a;
				if (curval < dist[x][y][cur.x][cur.y+1]) {
					dist[x][y][cur.x][cur.y+1] = curval;
					pq.add(new Edge(cur.x, cur.y+1, curval));
				}
			}
			
			// cur.y-1
			if (cur.y-1>=0) {
				long curval = cur.val;
				if (board[cur.x][cur.y] != board[cur.x][cur.y-1]) curval += b;
				else curval += a;
				if (curval < dist[x][y][cur.x][cur.y-1]) {
					dist[x][y][cur.x][cur.y-1] = curval;
					pq.add(new Edge(cur.x, cur.y-1, curval));
				}
			}
		}
	}
	
	static class Edge implements Comparable<Edge> {
		int x;
		int y;
		long val;
		Edge (int a, int b, long c) {
			x = a; y=b; val = c;
		}
		public int compareTo(Edge o) {
			return Long.compare(val, o.val);
		}
	}
}