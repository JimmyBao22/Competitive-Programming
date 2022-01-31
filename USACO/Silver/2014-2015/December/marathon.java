
import java.util.*;
import java.io.*;

public class marathon {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=487

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("marathon.in"));
		PrintWriter out = new PrintWriter(new FileWriter("marathon.out"));

		int n = Integer.parseInt(in.readLine());
		int[][] arr = new int[n][2];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		
		int dist=0;
		for (int i=1; i<n; i++) {
			dist += dist(arr, i, i-1);
		}
		
		int mindist = dist;
		for (int i=1; i<n-1; i++) {
			mindist = Math.min(mindist, dist - dist(arr, i, i-1) - dist(arr, i+1, i)
					+ dist(arr, i+1, i-1));
		}

		System.out.println(mindist);
		out.println(mindist);
		out.close();

	}
	
	public static int dist(int[][] arr, int i, int j) {
		return Math.abs(arr[i][0] - arr[j][0]) + Math.abs(arr[i][1] - arr[j][1]);
	}
}