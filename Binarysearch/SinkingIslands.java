
import java.util.*;
import java.io.*;

public class SinkingIslands {

	public static void main(String[] args) throws IOException, FileNotFoundException {
		// BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SinkingIslands"));

		int[][] board = {
		         {1, 0, 0, 0},
		         {0, 1, 1, 0},
		         {0, 0, 0, 0}
		         };
		
		solve(board);
		
		for (int i=0; i<board.length; i++) {
			System.out.println(Arrays.toString(board[i]));
		}
	}
	
	// https://binarysearch.com/problems/Sinking-Islands
	
	static boolean[][] visited, visited2;
    static int n, m;
    static int[] dirx = new int[] {0, 1, 0, -1};
    static int[] diry = new int[] {1, 0, -1, 0};
    public static int[][] solve(int[][] board) {
        n = board.length;
        m = board[0].length;
        visited = new boolean[n][m];
        visited2 = new boolean[n][m];
        for (int i=0; i<n; i++) {
        	for (int j=0; j<m; j++) {
        		if (board[i][j] != 0 && !visited[i][j]) {
        			boolean ret = floodfill(i, j, board, visited, false);
        			if (ret) {
        				floodfill(i, j, board, visited2, true);
        			}
        		}
        	}
        }
        return board;
    }
    
    public static boolean floodfill(int x, int y, int[][] board, boolean[][] visited, boolean change) {
    	ArrayDeque<int[]> d = new ArrayDeque<>();
    	d.add(new int[] {x,y});
    	boolean ret = true;
    	while (!d.isEmpty()) {
    		int[] cur = d.poll();
    		x = cur[0]; y = cur[1];
    		if (outbounds(x,y)) continue;
    		if (board[x][y] == 0) continue;
    		if (visited[x][y]) continue;
    		visited[x][y] = true;
    		if (change) {
    			board[x][y] = 0;
    		}
    		
    		if (x == 0 || y == 0 || x == n-1 || y == m-1) {
    			ret = false;
    		}
    		
    		for (int i=0; i<4; i++) {
    			d.add(new int[] {x+dirx[i], y + diry[i]});
    		}
    	}
    	return ret;
    }
    
    public static boolean outbounds(int x, int y) {
    	return x < 0 || y < 0 || x >= n || y >= m;
    }
}