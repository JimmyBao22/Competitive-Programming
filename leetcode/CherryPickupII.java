
import java.util.*;
import java.io.*;

public class CherryPickupII {

	// https://leetcode.com/problems/cherry-pickup-ii/
	
	public static void main(String[] args) {

	}
	
	int n, m;
	int[][][] memo;
	int[] dir = new int[] {-1, 0, 1};
	int INF = (int)(1e9);
	public int cherryPickup(int[][] grid) {
        n = grid.length;
        m = grid[0].length;
        memo = new int[n][m][m];
        	// x coord, first one y, second one y = max

        for (int i=0; i<n; i++) {
        	for (int j=0; j<m; j++) {
        		Arrays.fill(memo[i][j], -INF);
        	}
        }
        
        memo[0][0][m-1] = grid[0][0] + grid[0][m-1];
        for (int row=1; row<n; row++) {
        	for (int j1=0; j1<m; j1++) {
        		for (int j2=0; j2<m; j2++) {
        			
        			for (int i=0; i<3; i++) {
        				for (int j=0; j<3; j++) {
        					int prevy1 = j1 - dir[i];
        					int prevy2 = j2 - dir[j];
        					if (!outbounds(row-1, prevy1) && !outbounds(row-1, prevy2) && memo[row-1][prevy1][prevy2] != -INF) {
        						if (j1 == j2) {
        							memo[row][j1][j2] = Math.max(memo[row][j1][j2], memo[row-1][prevy1][prevy2] + grid[row][j1]);
        						}
        						else {
        							memo[row][j1][j2] = Math.max(memo[row][j1][j2], memo[row-1][prevy1][prevy2] + grid[row][j1] + grid[row][j2]);
        						}
        					}
        				}
        			}
        			
        		}
        	}
        }
        
        int ans = 0;
        for (int i=0; i<m; i++) {
        	for (int j=0; j<m; j++) {
        		ans = Math.max(ans, memo[n-1][i][j]);
        	}
        }
        return ans;
    }

	public boolean outbounds(int x, int y) {
		return x < 0 || y < 0|| x >= n || y >= m;
	}
	
	// NOTE: Both dijkstras and bfs --> TLE, even in c++
	
	public void dijkstras(int[][] grid) {
		PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return b[3] - a[3];			// largest first;
			}
		});
		
		pq.add(new int[] {0, 0, m-1, grid[0][0] + grid[0][m-1]});
		while (!pq.isEmpty()) {
			int[] cur = pq.poll();
			int x = cur[0], y1 = cur[1], y2 = cur[2];
			if (memo[x][y1][y2] > cur[3]) continue;
			memo[x][y1][y2] = cur[3];
			if (x == n-1) continue;
			
			for (int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					int newy1 = y1 + dir[i];
					int newy2 = y2 + dir[j];
					if (newy1 == newy2) {
//						if (!outbounds(x+1, newy1) && !outbounds(x+1, newy2)) {
//							pq.add(new int[] {x+1, newy1, newy2, cur[3] + grid[x+1][newy1]});
//						}
						continue;
					}
					if (!outbounds(x+1, newy1) && !outbounds(x+1, newy2)) {
						pq.add(new int[] {x+1, newy1, newy2, cur[3] + grid[x+1][newy1] + grid[x+1][newy2]});
					}
				}
			}
		}
	}
	
 	public void bfs(int[][] grid) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {0, 0, m-1, grid[0][0] + grid[0][m-1]});
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			int x = cur[0], y1 = cur[1], y2 = cur[2];
			if (memo[x][y1][y2] > cur[3]) continue;
			memo[x][y1][y2] = cur[3];
			if (x == n-1) continue;
			
			for (int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					int newy1 = y1 + dir[i];
					int newy2 = y2 + dir[j];
					if (newy1 == newy2) {
						if (!outbounds(x+1, newy1) && !outbounds(x+1, newy2)) {
							d.add(new int[] {x+1, newy1, newy2, cur[3] + grid[x+1][newy1]});
						}
						continue;
					}
					if (!outbounds(x+1, newy1) && !outbounds(x+1, newy2)) {
						d.add(new int[] {x+1, newy1, newy2, cur[3] + grid[x+1][newy1] + grid[x+1][newy2]});
					}
				}
			}
		}
	}
}