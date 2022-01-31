
import java.util.*;
import java.io.*;

public class moocrypt {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=545
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("moocrypt.in"));
		PrintWriter out = new PrintWriter(new FileWriter("moocrypt.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		char[][] arr = new char[n][];
		for (int i=0; i<n; i++) arr[i] = in.readLine().toCharArray();
		
		int max=0;
		for (int first=0; first<26; first++) {
			for (int sec = 0; sec<26; sec++) {
				if (first == sec) continue;
				char f = (char)(first + 'A');
				if (f == 'M') continue;
				char s = (char)(sec + 'A');
				if (s == 'O') continue;
				int count=0;
				for (int i=0; i<n; i++) {
					for (int j=0; j<m; j++) {
						if (arr[i][j] != f) continue;
						
						// right
						if (i + 2 < n && arr[i+1][j] == s && arr[i+2][j] == s) count++;
						
						// left
						if (i - 2 >= 0 && arr[i-1][j] == s && arr[i-2][j] == s) count++;
						
						// down
						if (j + 2 < m && arr[i][j+1] == s && arr[i][j+2] == s) count++;
						
						// up
						if (j - 2 >= 0 && arr[i][j-1] == s && arr[i][j-2] == s) count++;
						
						// diag dr
						if (i + 2 < n && j + 2 < m && arr[i+1][j+1] == s && arr[i+2][j+2] == s) count++;
						
						// diag dl
						if (i + 2 < n && j - 2 >= 0 && arr[i+1][j-1] == s && arr[i+2][j-2] == s) count++;
						
						// diag ur
						if (i - 2 >= 0 && j + 2 < m && arr[i-1][j+1] == s && arr[i-2][j+2] == s) count++;
						
						// diag ul
						if (i - 2 >= 0 && j - 2 >= 0 && arr[i-1][j-1] == s && arr[i-2][j-2] == s) count++;
					}
				}
				max = Math.max(max, count);
			}
		}
		
		System.out.println(max);
		out.println(max);
		out.close();
	}
}