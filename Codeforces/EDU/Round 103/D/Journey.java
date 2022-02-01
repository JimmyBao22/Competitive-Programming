
import java.util.*;
import java.io.*;

public class Journey {

	// https://codeforces.com/problemset/problem/1476/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Journey"));

		int t = Integer.parseInt(in.readLine());
		while (t-->0) {
			int n = Integer.parseInt(in.readLine());
			char[] arr =in.readLine().toCharArray();
		
			int[][] left = new int[n+1][2];
				// index, flip
			int[][] right = new int[n+1][2];
				// index, flip
		
			left[0][0] = left[0][1] = 1;
			for (int i=1; i<=n; i++) {
				left[i][0] = left[i][1] = 1;
				if (arr[i-1] == 'R') {
					left[i][1] += left[i-1][0];
				}
				else {
					left[i][0] += left[i-1][1];
				}
			}
			right[n][0] = right[n][1] = 1;
			for (int i=n-1; i>=0; i--) {
				right[i][0] = right[i][1] = 1;
				if (arr[i] == 'R') {
					right[i][0] += right[i+1][1];
				}
				else {
					right[i][1] += right[i+1][0];
				}
			}
			
			StringBuilder s = new StringBuilder();
			for (int i=0; i<=n; i++) {
				s.append(right[i][0] + left[i][0] - 1);
				s.append(" ");
			}
			System.out.println(s);
		}
	}
}