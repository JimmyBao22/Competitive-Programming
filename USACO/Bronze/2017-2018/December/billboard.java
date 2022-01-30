import java.util.*;
import java.io.*;

public class billboard {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=759

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("billboard.in"));
		
		int[][] r = new int[3][4];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				r[i][j] = in.nextInt();
			}
		}
		
		in.close();
		
		//calculate areas of rects separately
		int totala = area(r[0][0], r[0][1], r[0][2], r[0][3]);
		// this is area of first rect (a)
		int xa1 = Math.max(r[0][0], r[2][0]);
		int ya1 = Math.max(r[0][1], r[2][1]);
		int xa2 = Math.min(r[0][2], r[2][2]);
		int ya2 = Math.min(r[0][3], r[2][3]);
		if (xa1 < xa2 && ya1 < ya2) {
			totala -= area(Math.max(r[0][0], r[2][0]), Math.max(r[0][1], r[2][1]), Math.min(r[0][2], r[2][2]), Math.min(r[0][3], r[2][3]));
		}
		
		int totalb = area(r[1][0], r[1][1], r[1][2], r[1][3]);
		int xb1 = Math.max(r[1][0], r[2][0]);
		int yb1 = Math.max(r[1][1], r[2][1]);
		int xb2 = Math.min(r[1][2], r[2][2]);
		int yb2 = Math.min(r[1][3], r[2][3]);
		if (xb1 < xb2 && yb1 < yb2) {
			totalb -= area(Math.max(r[1][0], r[2][0]), Math.max(r[1][1], r[2][1]), Math.min(r[1][2], r[2][2]), Math.min(r[1][3], r[2][3]));
		}
		
		int result = totala + totalb;
		
		
		PrintWriter out = new PrintWriter(new File("billboard.out"));
		System.out.println(result);
		out.println(result);
		out.close();
	}
	
	public static int area(int x1, int y1, int x2, int y2) {
		return Math.abs((x2-x1) * (y2-y1));
	}
}