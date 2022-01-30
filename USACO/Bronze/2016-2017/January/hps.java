import java.util.*;
import java.io.*;

public class hps {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=688

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("hps.in"));
		
		int n = in.nextInt();
		
		int[][] hps = new int[n][2];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 2; j++) {
				hps[i][j] = in.nextInt();
			}
		}
		
		in.close();

		// there are 6 variations. 
		/*
			  1 --> 1 = r, 2 = p, 3 = s; 1>3>2>1
			  2 --> 1 = r, 3 = p, 2 = s; 1>2>3>1
			  3 --> 2 = r, 1 = p, 3 = s; 1>2>3>1
			  note:2 and 3 are same
			  4 --> 2 = r, 3 = p, 1 = s; 1>3>2>1
			  note:1 and 4 r same
			  5 --> 3 = r, 2 = p, 1 = s; 1>2>3>1
			  note:same as 2
			  6 --> 3 = r, 1 = p, 2 = s; 1>3>2>1
			  note:same as 1
			  therefore we only need to check 2 cases.
			  either 1>3>2>1 OR 1>2>3>1
		 */
		
		int count1 = 0;
		int count2 = 0;

		for (int i = 0; i < n; i++) {
			if (hps[i][0] == 1) {
				if (hps[i][1] == 3) count1++;
				if (hps[i][1] == 2) count2++;
			}
			else if (hps[i][0] == 2) {
				if (hps[i][1] == 1) count1++;
				if (hps[i][1] == 3) count2++;
			}
			else if (hps[i][0] == 3) {
				if (hps[i][1] == 2) count1++;
				if (hps[i][1] == 1) count2++;
			}
		}
				
		int result = Math.max(count1, count2);
		PrintWriter out = new PrintWriter(new File("hps.out"));
		System.out.println(result);
		out.println(result);
		out.close();
	}
}