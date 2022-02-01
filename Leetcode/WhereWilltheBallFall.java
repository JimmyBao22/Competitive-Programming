
import java.util.*;
import java.io.*;

public class WhereWilltheBallFall {

	public static void main(String[] args) {
		int[][] grid = {{1,1,1,-1,-1},{1,1,1,-1,-1},{-1,-1,-1,1,1},{1,1,1,1,-1},{-1,-1,-1,-1,-1}};
		System.out.println(Arrays.toString(findBall(grid)));
	}

	// https://leetcode.com/contest/weekly-contest-221/problems/where-will-the-ball-fall/
	
	static int n,m;
	
	public static int[] findBall(int[][] grid) {
		n = grid.length; m = grid[0].length;
        
		int[] ans = new int[m];
		for (int i=1; i<m; i++) {
			ans[i] = dfs(0, i, grid);
		}
        
        return ans;
    }
	
	public static int dfs(int x, int y, int[][] grid) {
		ArrayDeque<A> d = new ArrayDeque<>();
		d.add(new A(x,y,0));
		boolean[][][] visited = new boolean[n][m][4];
		while (!d.isEmpty()) {
			A cur = d.poll();
			if (cur.x == n && cur.num == 0) return cur.y;
			if (cur.x < 0 || cur.y < 0 || cur.x >= n || cur.y >= m) continue;
			if (visited[cur.x][cur.y][cur.num]) continue;
			visited[cur.x][cur.y][cur.num] = true;
			
			cur.print();
			
			if (grid[cur.x][cur.y] == 1) {
				if (cur.num == 0) {
					d.add(new A(cur.x,cur.y,1));
				}
				else if (cur.num == 3) {
					d.add(new A(cur.x,cur.y,2));
				}
			}
			else {
				if (cur.num == 0) {
					d.add(new A(cur.x,cur.y,3));
				}
				else if (cur.num == 1) {
					d.add(new A(cur.x,cur.y,2));
				}
			}
			
			if (cur.num == 1) {
				d.add(new A(cur.x,cur.y+1, 3));
			}
			else if (cur.num == 2) {
				d.add(new A(cur.x+1,cur.y, 0));
			}
			else if (cur.num == 3) {
				d.add(new A(cur.x,cur.y-1, 1));
			}
			
		}
		
		return -1;
	}
	
		// 0 = top, 1 = right, 2 = down, 3 = left
	static class A {
		int x, y, num;
		A (int a, int b, int c) {
			x = a; y = b; num = c;
		}
		void print() {
			System.out.println(x + " " + y + " " + num);
		}
	}	
}