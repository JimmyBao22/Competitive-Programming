
import java.util.*;
import java.io.*;

public class ATwistyMovement {
 
	// https://codeforces.com/contest/933/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ATwistyMovement"));

		int n = Integer.parseInt(in.readLine());
		StringTokenizer st = new StringTokenizer(in.readLine());
		int[] arr = new int[n];
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		int[][] ones = new int[n][n];
		int[][] twos = new int[n][n];
		
		int ans=1;
		for (int i=0; i<n; i++) {
			if (arr[i] == 1) ones[i][i] = 1;
			else twos[i][i] = 1;
		}
		for (int diff=1; diff<n; diff++) {
			for (int i=0; i+diff<n; i++) {
				int j=i+diff;
				if (arr[i] == 1) {
					ones[i][j] = ones[i+1][j] + 1;
					twos[i][j] = twos[i+1][j];
				}
				else {
					ones[i][j] = ones[i+1][j];
					twos[i][j] = twos[i+1][j] + 1;
				}
				ans = Math.max(ans, Math.max(ones[i][j], twos[i][j]));
			}
		}
		
		int[][] subsforwards = new int[n][n];
		int[][] subsbackwards = new int[n][n];
		for (int i=0; i<n; i++) {
			subsforwards[i][i] = 1;
			subsbackwards[i][i] = 1;
		}

		for (int diff=1; diff<n; diff++) {
			for (int i=0; i+diff<n; i++) {
				int j = i+diff;
				
				subsforwards[i][j] = Math.max(subsforwards[i+1][j], subsforwards[i][j-1]);				
				if (subsforwards[i][j-1] == ones[i][j-1] && arr[j] == 1) {
					// can take a 1 on right
					subsforwards[i][j] = Math.max(subsforwards[i][j], subsforwards[i][j-1] + 1);
				}
				if (subsforwards[i+1][j] == twos[i+1][j] && arr[i] == 2) {
					// can take a 2 on left
					subsforwards[i][j] = Math.max(subsforwards[i][j], subsforwards[i+1][j] + 1);
				}
			
				// take 1 from left
				if (arr[i] == 1) {
					subsforwards[i][j] = Math.max(subsforwards[i][j], subsforwards[i+1][j] + 1);
				}
				
				// take 2 from right
				if (arr[j] == 2) {
					subsforwards[i][j] = Math.max(subsforwards[i][j], subsforwards[i][j-1] + 1);
				}
				
				ans = Math.max(ans, subsforwards[i][j]);
				
				subsbackwards[i][j] = Math.max(subsbackwards[i+1][j], subsbackwards[i][j-1]);
				if (subsbackwards[i+1][j] == ones[i+1][j] && arr[i] == 1) {
					// can take 1 on left
					subsbackwards[i][j] = Math.max(subsbackwards[i][j], subsbackwards[i+1][j] + 1);
				}
				if (subsbackwards[i][j-1] == twos[i][j-1] && arr[j] == 2) {
					// can take 2 on right
					subsbackwards[i][j] = Math.max(subsbackwards[i][j], subsbackwards[i][j-1] + 1);
				}
				
				// take 1 from right
				if (arr[j] == 1) {
					subsbackwards[i][j] = Math.max(subsbackwards[i][j], subsbackwards[i][j-1] + 1);
				}
				
				// take 2 from left
				if (arr[i] == 2) {
					subsbackwards[i][j] = Math.max(subsbackwards[i][j], subsbackwards[i+1][j] + 1);
				}
				
				ans = Math.max(ans, subsbackwards[i][j]);
			}
		}
				
		for (int i=0; i<n; i++) {
			for (int j=i; j<n; j++) {
				// reverse [i, j]
				
				// subsequence on [0, i-1], then 2's onwards
				if (i-1 >= 0) ans = Math.max(ans, subsforwards[0][i-1] + twos[i][n-1]);
				
				// subsequence [j+1, n-1]
				if (j+1 < n) ans = Math.max(ans, ones[0][j] + subsforwards[j+1][n-1]);
				
				// subsequence i,j
				if (i-1 >= 0 && j+1 < n) ans = Math.max(ans, ones[0][i-1] + subsbackwards[i][j] + twos[j+1][n-1]);
				else if (i-1 >= 0) ans = Math.max(ans, ones[0][i-1] + subsbackwards[i][j]);
				else if (j+1 < n) ans = Math.max(ans, subsbackwards[i][j] + twos[j+1][n-1]);
			}
		}
		
		System.out.println(ans);
	}
}