
import java.util.*;
import java.io.*;

public class recording {

	// http://usaco.org/index.php?page=viewproblem2&cpid=381
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("recording.in"));
		PrintWriter out = new PrintWriter(new FileWriter("recording.out"));

		int n = Integer.parseInt(in.readLine());
		int[][] arr = new int[n][2];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.parallelSort(arr, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[0]-b[0];
			}
		});
		
		long[][] dp = new long[n][n];
		
		for (int i=0; i<n; i++) dp[i][i] = 1;
		
		for (int i=1; i<n; i++) {
			for (int j=0; j<i; j++) {
				// dp[j][i]
				// dp[i][j]
				
				// first show i, second show j
				
				dp[i][j]=2;
				dp[j][i]=2;
				for (int k=0; k<i; k++) {
					if (k!=j && arr[k][1] > arr[i][0]) {
						continue;
					}
					dp[j][i] = Math.max(dp[j][i], dp[j][k]+1);
					dp[i][j] = Math.max(dp[i][j], dp[k][j]+1);
					//print(dp);
				}
			}
		}
		
		long ans=1;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				ans = Math.max(ans, dp[i][j]);
			}
		}

		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static void print(int[][] a) {
		for (int i=0; i<a.length; i++) {
			for (int j=0; j<a[i].length; j++) {
				System.out.print(a[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
}