
import java.util.*;
import java.io.*;

public class RectangularPasture {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1063
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("RectangularPasture"));

		int n = Integer.parseInt(in.readLine());
		int[][] arr = new int[n][2];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		Arrays.parallelSort(arr, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[0] - b[0];
			}
		});
		
		long[][] above = new long[n][n];
		long[][] below = new long[n][n];
		
		for (int i=0; i<n; i++) {

			// going to left
			for (int j=i-1; j>=0; j--) {
				if (j+1 < n) above[i][j] = above[i][j+1];
				if (arr[j][1] > arr[i][1]) above[i][j]++;
				
				if (j + 1 < n) below[i][j] = below[i][j+1];
				if (arr[j][1] < arr[i][1]) below[i][j]++;
			}
			
			// going to right
			for (int j=i+1; j<n; j++) {
				if (j - 1 >= 0) above[i][j] = above[i][j-1];
				if (arr[j][1] > arr[i][1]) above[i][j]++;
				
				if (j - 1 >= 0) below[i][j] = below[i][j-1];
				if (arr[j][1] < arr[i][1]) below[i][j]++;
			}
		}
		
		
		long ans=1;
		for (int i=0; i<n; i++) {
			for (int j=i; j<n; j++) {
				
				long countabove=0;
				long countbelow=0;

				if (arr[i][1] > arr[j][1]) {
					countabove = above[i][j];
					countbelow = below[j][i];
				}
				else {
					countabove = above[j][i];
					countbelow = below[i][j];
				}
								
				countabove++;
				countbelow++;
				ans += (countabove * countbelow);
				
			}
		}
		
		System.out.println(ans);
		
	}
}