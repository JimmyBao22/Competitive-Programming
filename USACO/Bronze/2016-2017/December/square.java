
import java.util.*;
import java.io.*;

public class square {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=663

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("square.in"));
		
		int[] x = new int[4];
		int[] y = new int[4];
		
		for (int i = 0 ; i < 8; i++) {
			if (i % 2 == 0) {
				x[(int)(i/2)] = in.nextInt();
			}
			else if (i % 2 == 1) {
				y[(int)(i/2)] = in.nextInt();
			}
		}
		
		int maxx = 0;
		for (int i = 0; i < 4; i++) {
			//check each subtraction
			for (int j = 0; j < 4; j++) {
				if (Math.abs(x[i] - x[j]) > maxx) {
					maxx = Math.abs(x[i] - x[j]);
				}
			}
		}
		int maxy = 0;
		for (int i = 0; i < 4; i++) {
			//check each subtraction
			for (int j = 0; j < 4; j++) {
				if (Math.abs(y[i] - y[j]) > maxy) {
					maxy = Math.abs(y[i] - y[j]);
				}
			}
		}
		
		int result = (int) Math.pow((double)Math.max((double)maxx, (double)maxy), 2.0);
		
		PrintWriter out = new PrintWriter(new File("square.out"));
		System.out.println(result);
		out.println(result);
		out.close();
	}
}