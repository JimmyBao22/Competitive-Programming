
import java.util.*;
import java.io.*;

public class TheTreasureofTheSegments {

	// https://codeforces.com/contest/1462/problem/F
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("TheTreasureofTheSegments"));

		int t = Integer.parseInt(in.readLine());
		while (t-->0) {
			int n = Integer.parseInt(in.readLine());
			int[][] forwards = new int[n][2];
			int[][] backwards = new int[n][2];
			for (int i=0; i<n; i++) {
				StringTokenizer st = new StringTokenizer(in.readLine());
				forwards[i][0] = Integer.parseInt(st.nextToken());
				forwards[i][1] = Integer.parseInt(st.nextToken());
				backwards[i][0] = forwards[i][0];
				backwards[i][1] = forwards[i][1];
			}
			
			Arrays.parallelSort(forwards, new Comparator<int[]>() {
				public int compare(int[] a, int[] b) {
					return a[0] - b[0];
				}
			});
			
			Arrays.parallelSort(backwards, new Comparator<int[]>() {
				public int compare(int[] a, int[] b) {
					return a[1] - b[1];
				}
			});
			
			int minval=n;
			for (int i=0; i<n; i++) {
				int min=-1;
				int max=n-1;
				while (min<max) {
					int middle = (min+max+1)/2;
					if (backwards[middle][1] < forwards[i][0]) {
						min = middle;
					}
					else max = middle-1;
				}
				int front = min+1;
				min=0;
				max=n;
				while (min<max) {
					int middle = (min+max)/2;
					if (forwards[middle][0] > forwards[i][1]) {
						max = middle;
					}
					else min = middle+1;
				}
				int back= n-min;
				minval = Math.min(minval, Math.max(front + back, 0));
				
			}
			
			System.out.println(minval);
			
		}
	}
}