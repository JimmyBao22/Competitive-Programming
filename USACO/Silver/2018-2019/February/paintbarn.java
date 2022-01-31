
import java.util.*;
import java.io.*;

public class paintbarn {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=919
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("paintbarn.in"));
		PrintWriter out = new PrintWriter(new FileWriter("paintbarn.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		int[][] arr = new int[1001][1002];
		
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			
			for (int j=x1; j<x2; j++) {
				arr[j][y1]++;
				arr[j][y2]--;
			}
		}
		
		int count=0;
		for (int i=0; i<arr.length; i++) {
			for (int j=0; j<arr[0].length; j++) {
				if (j == 0) {
					if (arr[i][j] == k) count++;
				}
				else {
					arr[i][j] += arr[i][j-1];
					if (arr[i][j] == k) count++;
				}
			}
		}
		
		System.out.println(count);
		out.println(count);
		out.close();

	}
	
	public static void print(int[][] arr) {
		for (int i=0; i<arr.length; i++) {
			for (int j=0; j<arr[0].length; j++) {
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
}