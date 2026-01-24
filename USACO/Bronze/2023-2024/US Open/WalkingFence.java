
import java.util.*;
import java.io.*;

public class WalkingFence {
	
	// https://usaco.org/index.php?page=viewproblem2&cpid=1420
	
	private static final int MAX_COORD = 1001;

	public static void main(String[] args) throws IOException, FileNotFoundException {
		Scanner in = new Scanner(System.in);
		
		// input
		int n = in.nextInt();
		int p = in.nextInt();
		int[][] fences = new int[p][2];
		for (int i = 0; i < p; i++) {
			fences[i][0] = in.nextInt();
			fences[i][1] = in.nextInt();
		}
		int[][] cows = new int[n][4];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 4; j++) {
				cows[i][j] = in.nextInt();
			}
		}
		
		// calculate distance to a base point (fences[0])
		// simulate walk around the perimeter 
		int[][] ldist = new int[MAX_COORD][MAX_COORD];
		int x = fences[0][0];
		int y = fences[0][1];
		
		// 0 = up, 1 = right, 2 = down, 3 = left
		int dir = getDir(fences, 0, p);
		
		if (dir == 0) {
			y++;
		} else if (dir == 1) {
			x++;
		} else if (dir == 2) {
			y--;
		} else {
			x--;
		}
		
		int dist = 1;
		int fenceIndex = 1;
		while (x != fences[0][0] || y != fences[0][1]) {
			ldist[x][y] = dist;
			if (x == fences[fenceIndex][0] && y == fences[fenceIndex][1]) {
				dir = getDir(fences, fenceIndex, p);
				fenceIndex = (fenceIndex + 1) % p;
			}
			
			if (dir == 0) {
				y++;
			} else if (dir == 1) {
				x++;
			} else if (dir == 2) {
				y--;
			} else {
				x--;
			}
			
			dist++;
		}
		
		// perform distance calculation
		// either going left or going right
		for (int i = 0; i < n; i++) {
			int x1 = cows[i][0];
			int y1 = cows[i][1];
			int x2 = cows[i][2];
			int y2 = cows[i][3];
			
			int curDist = Math.abs(ldist[x1][y1] - ldist[x2][y2]);
			curDist = Math.min(curDist, dist - curDist);
			
			System.out.println(curDist);
		}
	}
	
	private static int getDir(int[][] fences, int index, int p) {
		if (fences[(index+1)%p][0] == fences[index][0]) {
			if (fences[(index+1)%p][1] > fences[index][1]) {
				return 0;
			} else {
				return 2;
			}
		} else if (fences[(index+1)%p][0] > fences[index][0]) {
			return 1;
		} else {
			return 3;
		}
	}
}