
import java.util.*;
import java.io.*;

public class UniquePathsIII {

	public static void main(String[] args) {
		
		int[][] grid = {{0,1},{2,0}};
		
		grid = new int[][] {{1,0,0,0},{0,0,0,0},{0,0,2,-1}};
		
		UniquePathsIII a = new UniquePathsIII();
		System.out.println(a.uniquePathsIII(grid));
	}
	
	// https://leetcode.com/problems/unique-paths-iii/
	
	int n, m, bits, finalbit, startx, starty, ans;
	public int uniquePathsIII(int[][] grid) {
        n = grid.length;
        m = grid[0].length;
        bits = n*m;		// <= 20
        finalbit = 0;
        ans = 0;

        for (int i=0; i<n; i++) {
        	for (int j=0; j<m; j++) {
        		
        		if (grid[i][j] != -1) finalbit += (1 << (i * m + j));        		
        		if (grid[i][j] == 1) {
        			startx = i;
        			starty = j;
        		}
    		}
        }
                
        bfs(grid);
        
        return ans;
    }

	public void bfs(int[][] grid) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {startx, starty, (1 << (startx * m + starty))});
		
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			int x = cur[0]; int y = cur[1];
			int bitval = cur[2];
			
			if (outOfBounds(x, y)) continue;
			if (grid[x][y] == 2) {
				ans++;
				continue;
			}
			
			doOperation(grid, d, x+1, y, bitval);
			doOperation(grid, d, x-1, y, bitval);
			doOperation(grid, d, x, y+1, bitval);
			doOperation(grid, d, x, y-1, bitval);
		}
	}
	
	public void doOperation(int[][] grid, ArrayDeque<int[]> d, int x, int y, int bitval) {
		if (!outOfBounds(x, y) && grid[x][y] != -1 && 
				(bitval & (1 << (x * m + y))) == 0) {
			
			int curBitVal = bitval + (1 << (x * m + y));
			if (grid[x][y] == 2) {
				if (curBitVal == finalbit) {
					d.add(new int[] {x, y, curBitVal});
				}
			}
			else {
				d.add(new int[] {x, y, curBitVal});
			}
		}
	}
	
	public boolean outOfBounds(int x, int y) {
		return x < 0 || y < 0 || x >= n || y >= m;
	}
	
	// store visited location in bits
    // ex. first row --> first to mth bit, second row --> m+1th to 2mth bits, etc...	
}