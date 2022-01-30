import java.util.*;
import java.io.*;

public class billboard2 {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=759
	// another solution to billboard
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("billboard.in"));
		
		int[][] r = new int[2][4];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				r[i][j] = in.nextInt();
			}
		}
		
		in.close();

		boolean[][] x = new boolean[2001][2001]; 
		// coordinate system. 0,0 will be 1000,1000 here
		// 0 1 2 3 ... 1999 2000
		
		for (int i = 0; i < r[0][2] - r[0][0]; i++) {
			for (int j = 0; j < r[0][3] - r[0][1]; j++) {
				x[1000+r[0][0]+i][1000+r[0][1]+j] = true;
				// this sets first billboard to be true
			}
		}
		
		for (int i = 0; i < r[1][2] - r[1][0]; i++) {
			for (int j = 0; j < r[1][3] - r[1][1]; j++) {
				x[1000+r[1][0]+i][1000+r[1][1]+j] = false;
				// this sets second billboard to be false
			}
		}

		boolean f;
		int k = 0;
		outerloop:
		for (int i = 0; i < r[0][2] - r[0][0]; i++) {
			f = true;
			// this loops through the rows in the first billboard
			// check each row, starting from the left. 
			for (int j = 0; j < r[0][3] - r[0][1]; j++) {
				if (x[1000+r[0][0]+i][1000+r[0][1]+j]) {
					//f = false;
					break outerloop;
				}
			}
			if (f) k++;
		}
		
		outerloop:
			for (int i = r[0][2] - r[0][0]-1; i >=0; i--) {
				f = true;
				// start at right
				for (int j = 0; j < r[0][3] - r[0][1]; j++) {
					if (x[1000+r[0][0]+i][1000+r[0][1]+j]) {
						f = false;
						break outerloop;
					}
				}
				if (f) k++;
			}
		
		
		int l = 0;
		outerloop:
		for (int i = 0; i < r[0][3] - r[0][1]; i++) {
			// this loops through the columns in the first billboard
			// check each col, starting from the left. 
			f = true;
			for (int j = 0; j < r[0][2] - r[0][0]; j++) {
				if (x[1000+r[0][0]+j][1000+r[0][1]+i]) {
					f = false;
					break outerloop;
				}
			}
			if (f) l++;
		}
		outerloop:
		for (int i = r[0][3] - r[0][1]-1; i >=0; i--) {
			// this loops through the columns in the first billboard
			// check each col, starting from the left. 
			f = true;
			for (int j = 0; j < r[0][2] - r[0][0]; j++) {
				if (x[1000+r[0][0]+j][1000+r[0][1]+i]) {
					f = false;
					break outerloop;
				}
			}
			if (f) l++;
		}
		
		int result = 0;

		if (r[1][0]<=r[0][0] && r[1][1]<=r[0][1] && r[1][2]>=r[0][2] && r[1][3]>=r[0][3]) {
			result = 0;
			//it covers all
		}
		else {
			result = (r[0][2]-r[0][0]-k)*(r[0][3]-r[0][1]-l);
		}
		
		PrintWriter out = new PrintWriter(new File("billboard.out"));
		System.out.println(result);
		out.println(result);
		out.close();
	}
}