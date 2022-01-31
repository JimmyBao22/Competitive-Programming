
import java.util.*;
import java.io.*;

public class crosswords {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=488
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("crosswords.in"));
		PrintWriter out = new PrintWriter(new FileWriter("crosswords.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		char[][] arr = new char[n][];
		for (int i=0; i<n; i++) arr[i] = in.readLine().toCharArray();
		
		boolean[][] work = new boolean[n][m];
		
		int count=0;
		// going to right
		for (int i=0; i<n-2; i++) {
			for (int j=0; j<m; j++) {
				if (arr[i][j] == '.' && (i-1 < 0 || arr[i-1][j] == '#')) {
					if (arr[i+1][j] == '.' && arr[i+2][j] == '.') {
						work[i][j] = true;
						count++;
					}
				}
			}
		}
		
		// going down
		for (int i=0; i<n; i++) {
			for (int j=0; j<m-2; j++) {
				if (work[i][j]) continue;
				if (arr[i][j] == '.' && (j-1 < 0 || arr[i][j-1] == '#')) {
					if (arr[i][j+1] == '.' && arr[i][j+2] == '.') {
						work[i][j] = true;
						count++;
					}
				}
			}
		}
		
		StringBuilder s = new StringBuilder();
		s.append(count); s.append("\n");
		for (int i=0; i<n; i++) {
			for (int j=0; j<m; j++) {
				if (work[i][j]) {
					s.append(i+1 + " " + (j+1));
					s.append("\n");
				}
			}
		}
		
		System.out.print(s);
		out.print(s);
		out.close();
	}
}