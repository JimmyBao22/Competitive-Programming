import java.util.*;
import java.io.*;

public class gymnastics {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=963

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("gymnastics.in"));
		
		int k = in.nextInt();
		int n = in.nextInt();
		
		int[][] sessions = new int[k][n];
		
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < n; j++) {
				sessions[i][j] = in.nextInt();
			}
		}
		in.close();
		
		int count = 0; // this will count the number of pairs that work
		boolean left  = true; 
		boolean right = true;
		for (int i = 1; i < n; i++) {
			for (int j = i+1; j <= n; j++) {
				// this will checke every pair
				// pair will be i, j
				left = true;
				right = true;
				for (int m = 0; m < k; m++) {
					// loop through every training session
					int indexi = 0;
					for (int a = 0; a < n; a++) {
						if (sessions[m][a] == i) {
							indexi = a;
						}
					}
					int indexj = 0;
					for (int a = 0; a < n; a++) {
						if (sessions[m][a] == j) {
							indexj = a;
						}
					}
					
					if (indexi>indexj) right = false;
					if (indexi < indexj) left = false;
				}
				
				if (left || right) count++;
			}
		}

		PrintWriter out = new PrintWriter(new File("gymnastics.out"));
		System.out.println(count);
		out.println(count);
		out.close();
	}
}