
import java.util.*;
import java.io.*;

public class lifeguards {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=784

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("lifeguards.in"));
		
		int n = in.nextInt();
		int[][] shifts = new int[n][2];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 2; j++) {
				shifts[i][j] = in.nextInt();
			}
		}
		
		in.close();
		
		boolean[] check = new boolean[1000];
		int max = 0;
		for (int i = 0; i < n; i++) {
			// n times cuz each time you have one lifeguard you're leaving out
			// i will be the lifeguard left out
			
			for (int j = 0; j < n; j++) {
				if (j == i) continue;
				for (int k = 0; k <= shifts[j][1]-shifts[j][0]-1; k++) {
					check[shifts[j][0]+k] = true;
				}
			}
			
			//now check for amount
			int amount = 0;
			for (int j = 0; j < check.length; j++) {
				if (check[j]) amount++;
			}
			
			if (amount > max) {
				max = amount;
			}
			
			for (int j = 0; j < check.length; j++) {
				check[j] = false;
				//reset check
			}
		}
		
		PrintWriter out = new PrintWriter(new File("lifeguards.out"));
		System.out.println(max);
		out.println(max);
		out.close();
	}
}