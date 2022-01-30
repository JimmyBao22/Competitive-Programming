import java.util.*;
import java.io.*;

public class mixmilk {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=855

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("mixmilk.in"));
		
		int[][] a = new int[3][2];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				a[i][j] = in.nextInt();
			}
		}
		
		in.close();
		
		for (int b = 0; b < 100; b++) {
			//loop will reiterate 100 times
			if (b % 3 == 0) {
				//a pours into b
				/*
				 	while (a[0][1] > 0 && a[1][1] < a[1][0]) {
						a[0][1]--;
						a[1][1]++;
					}
				*/
				int x = Math.min(a[0][1], a[1][0]-a[1][1]);
				a[0][1] -= x;
				a[1][1] += x;

			}
			if (b % 3 == 1) {
				//b pours into c
				/*
				 	while (a[1][1] > 0 && a[2][1] < a[2][0]) {
						a[1][1]--;
						a[2][1]++;
					}
				*/
				int x = Math.min(a[1][1], a[2][0]-a[2][1]);
				a[1][1] -= x;
				a[2][1] += x;
			}
			if (b % 3 == 2) {
				//c pours into a
				/*
				 	while (a[2][1] > 0 && a[0][1] < a[0][0]) {
						a[2][1]--;
						a[0][1]++;
					}
				*/
				int x = Math.min(a[2][1], a[0][0]-a[0][1]);
				a[0][1] += x;
				a[2][1] -= x;
			}
		}

		PrintWriter out = new PrintWriter(new File("mixmilk.out"));
		
		for (int i = 0; i < 3; i++) {
			System.out.println(a[i][1]);
			out.println(a[i][1]);
		}
		out.close();
	}
}