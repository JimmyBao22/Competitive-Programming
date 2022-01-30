import java.util.*;
import java.io.*;

public class Vacations {	
	
	// http://codeforces.com/problemset/problem/698/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Vacations.in"));

		int n = Integer.parseInt(in.readLine());  
		StringTokenizer st = new StringTokenizer(in.readLine());
		int[] rules = new int[n];
		for (int i = 0; i < n; i++) {
			rules[i] = Integer.parseInt(st.nextToken());  
		}
		
		int[][] box = new int[3][n];
			// row 1 = rest
			// row 2 = contest
			// row 3 = vacation
		
		for (int i = 0; i < n; i++) {
			if (rules[i] == 0) {
				// you can only rest
				if (i == 0) {
					box[0][i] = 1;
				}
				else {
					box[0][i] = 1+Math.min(box[0][i-1], Math.min(box[1][i-1], box[2][i-1]));
				}
				box[1][i] = Integer.MAX_VALUE;
				box[2][i] = Integer.MAX_VALUE;
			}
			else if (rules[i] == 1) {
				// you can do contest or rest
				if (i == 0) {
					box[0][i] = 1;
					box[1][i] = 0; 
				}
				else {
					box[0][i] = 1+Math.min(box[0][i-1], Math.min(box[1][i-1], box[2][i-1]));
					box[1][i] = Math.min(box[0][i-1], box[2][i-1]);

				}
				box[2][i] = Integer.MAX_VALUE;
			}
			else if (rules[i] == 2) {
				// you can do vacation or rest
				if (i == 0) {
					box[0][i] = 1;
					box[2][i] = 0; 
				}
				else {
					box[0][i] = 1+Math.min(box[0][i-1], Math.min(box[1][i-1], box[2][i-1]));
					box[2][i] = Math.min(box[0][i-1], box[1][i-1]);

				}
				box[1][i] = Integer.MAX_VALUE;
			}
			else if (rules[i] == 3) {
				// you can do anything
				if (i == 0) {
					box[0][i] = 1;
					box[1][i] = 0;
					box[2][i] = 0;
				}
				else {
					box[0][i] = 1+Math.min(box[0][i-1], Math.min(box[1][i-1], box[2][i-1]));
					box[1][i] = Math.min(box[0][i-1], box[2][i-1]);
					box[2][i] = Math.min(box[0][i-1], box[1][i-1]);

				}
			}
			
		}
		
		int result = Math.min(Math.min(box[0][n-1], box[1][n-1]), box[2][n-1]);
		System.out.println(result);
	}
}

/*

	Create a box for the days and use recursion based on previous day.
	
	input:
	4
	1 3 2 0
	
	Day: 1 2 3 4
	r	 1 1 1 2
	c    0 1 ∞ ∞
	v	 ∞ 0 1 ∞
	
	First day can do contest/rest
	we are trying to find minimum, so if we cannot do it, then say infinity
	
	Do minimum every day of the previous activities
	
	https://youtu.be/RgCn_C1rZXs


*/
