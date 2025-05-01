import java.util.*;

public class ShortestBridge {

	public static void main(String[] args) {

	}
	
	// https://binarysearch.com/problems/Shortest-Bridge

	static int n, m, INF = (int)(1e9);
	static boolean[][] visited;
	static int[] dirx = new int[] {1, 0, -1, 0}, diry = new int[] {0, 1, 0, -1};
	static ArrayDeque<int[]> start;
	static int[][] dist;
	
	public static int solve(int[][] matrix) {
		n = matrix.length;
		m = matrix[0].length;
		visited = new boolean[n][m];
		start = new ArrayDeque<>();
		dist = new int[n][m];
		for (int i=0; i<n; i++) {
			Arrays.fill(dist[i], INF);
		}
		
		// find first island
		o: for (int i=0; i<n; i++ ) {
			for (int j=0; j<m; j++) {
				if (matrix[i][j] == 1) {
					bfs(i,j,matrix);
					break o;
				}
			}
		}
		
		// build bridge
		while (!start.isEmpty()) {
			int[] cur = start.poll();
			int x = cur[0]; int y = cur[1]; int c = cur[2];
			if (outbounds(x,y)) continue;
			if (visited[x][y]) continue;
			if (dist[x][y] <= c) continue;
			dist[x][y] = c;
			if (matrix[x][y] == 1) continue;
			
			for (int i=0; i<4; i++) {
				start.add(new int[] {x+dirx[i], y+diry[i], c+1});
			}
		}
		
		int ans = INF;
		for (int i=0; i<n; i++) {
			for (int j=0; j<m; j++) {
				if (matrix[i][j] == 1) {
					ans = Math.min(ans, dist[i][j]);
				}
			}
		}
		
		return ans;
    }
	
	public static void bfs(int x, int y, int[][] matrix) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {x,y});
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			x = cur[0]; y = cur[1];
			if (outbounds(x,y)) continue;
			if (matrix[x][y] == 0) {
				start.add(new int[] {x,y,0});
				continue;
			}
			if (visited[x][y]) continue;
			visited[x][y] = true;
			
			for (int i=0; i<4; i++) {
				d.add(new int[] {x+dirx[i], y+diry[i]});
			}
		}
	}
	
	public static boolean outbounds(int x, int y) {
		return x < 0 || y < 0 || x >= n || y >= m;
	}
}