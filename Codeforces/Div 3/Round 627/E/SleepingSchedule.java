
import java.util.*;
import java.io.*;

public class SleepingSchedule {
	
	// https://codeforces.com/contest/1324/problem/E
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SleepingSchedule.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());  
		int h = Integer.parseInt(st.nextToken());  
		int l = Integer.parseInt(st.nextToken());  
		int r = Integer.parseInt(st.nextToken());  
		
		st = new StringTokenizer(in.readLine());
		int[] hours = new int[n+1];
		for (int i = 1; i < n+1; i++) {
			hours[i] = hours[i-1] + Integer.parseInt(st.nextToken());  
		}
		
		int[][] dp = new int[n+1][n+1];
		for (int row = 0; row < n+1; row++) {
			Arrays.fill(dp[row], -1);
		}
		dp[0][0] = 0;
			// row represents the number of times he has slept
			// col will represent the number of times he has slept a_i - 1
				// instead of just a_i
			// value is number of good sleeping times
		
		for (int i = 1 ; i < n+1; i++) {
			for (int j = 0; j < n+1; j++) {
				if (j == 0) {
					// this means this turn has to be a_i and not a_i-1
					dp[i][j] = dp[i-1][j];
					if ((hours[i] - j) % h >= l && (hours[i] - j) % h <= r) {
						dp[i][j]++;
					}
				}
				else {
					// this turn can be a_i and a_i-1
					if (dp[i-1][j-1] != -1) {
						if (dp[i-1][j] != -1) {
							dp[i][j] = Math.max(dp[i-1][j-1], dp[i-1][j]);
						}
						else {
							dp[i][j] = dp[i-1][j-1];
						}
					}
					else break;
					if ((hours[i] - j) % h >= l && (hours[i] - j) % h <= r) {
						dp[i][j]++;
					}
				}
			}
		}
		
		int max = 0;
		for (int j = 0; j < n+1; j++) {
			max = Math.max(max, dp[n][j]);
		}
		System.out.println(max);

	}
	
	public static void printgrid(int[][] dp) {
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0 ; j < dp.length; j++) {
				System.out.print(dp[i][j]);
			}
			System.out.println();
		}	
	}
}