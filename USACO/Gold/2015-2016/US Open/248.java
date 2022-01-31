
import java.util.*;
import java.io.*;

public class 248 {

	// http://usaco.org/index.php?page=viewproblem2&cpid=647
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("248.in"));
		PrintWriter out = new PrintWriter(new FileWriter("248.out"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n];
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(in.readLine());

		int[][] dp = new int[n][n];
			// [start][end]
		
		for (int i=0; i<n; i++) {
			Arrays.fill(dp[i], -1);
		}
		
		for (int i=0; i<n; i++) {
			dp[i][i] = arr[i];
		}
		
		for (int diff=1; diff<n; diff++) {
			for (int i=0; i+diff<n; i++) {
				int j = i+diff;
				for (int k=i; k<j; k++) {
					if (dp[i][k] != -1 && dp[i][k] == dp[k+1][j]) {
						dp[i][j] = Math.max(dp[i][j], dp[i][k]+1);
					}
				}
			}
		}
		
		int ans=0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				ans = Math.max(ans, dp[i][j]);
			}
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static void print(int[][] dp, int n) {
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				System.out.print(dp[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}

/*

	When you end on a number, it takes a sequence of consecutive numbers to create that number
		
		For example, for 4
						 1
						 1
						 1
						 2
						 
		it takes the sequence from indices [1,3] to create the number 3.
		
	The final number created will be from a consecutive sequence of the whole thing
	
	Therefore, create a dp array dp[n][n] where dp[i][j] is the max number 
		from i to j ONLY if you use every single number in the sequence [i][j] to create that number.
			(aka ur only left with that number after all operations are done)
			Otherwise, it is just -1
			
	For each dp[i][j], you can look over all sequences inside of it, and see if 
		the left side and the right side have the same number (dp[i][k] = dp[k+1][j]),
		then dp[i][j] can be that number +1
		
	Take example: 2 2 1 1 3 1
		The answer to this is 4, because 2 2 1 1 3 1 --> 2 2 2 3 1 --> 2 3 3 1 --> 2 4 1
		
	The finished grid:
	2 3 -1 -1 -1 -1 
	-1 2 -1 3 4 -1 
	-1 -1 1 2 -1 -1 
	-1 -1 -1 1 -1 -1 
	-1 -1 -1 -1 3 -1 
	-1 -1 -1 -1 -1 1 

	Explanation of this grid:
		dp[0][0] = 2, meaning from 0 to 0 you are left with a single number 2
		dp[0][1] = 3, meaning the from 0 to 1 you are left with a single number 3
		dp[1][3] = 3, meaning from 1 to 3 you are left with a single number 3
		dp[1][4] = 4, meaning from 1 to 4 you are left with a single number 4
		...
*/