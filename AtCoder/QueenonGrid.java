
import java.util.*;
import java.io.*;

public class QueenonGrid {

	// https://atcoder.jp/contests/abc183/tasks/abc183_e
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("QueenonGrid"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		char[][] arr = new char[n][];
		for (int i=0; i<n; i++) arr[i] = in.readLine().toCharArray();
		
		if (arr[0][0] == '#') {
			System.out.println(0);
			return;
		}
		
		long mod = (long)(1e9+7);
			// 0=row; 1=col; 2=diag
		long[][][] over = new long[n][m][3];
		long[][] count = new long[n][m];
		count[0][0] = 1;
		for (int i=0; i<n; i++) {
			for (int j=0; j<m; j++) {
				if (arr[i][j] == '#') continue;
				if (i+1<n && arr[i+1][j] != '#') {
					count[i+1][j] += count[i][j] + over[i][j][0];
					count[i+1][j] %= mod;
					if ((i+2<n && arr[i+2][j] != '#') || (j+1<m && arr[i+1][j+1] != '#')) {
						over[i+1][j][0] += count[i][j];
						over[i+1][j][0] += over[i][j][0];
						over[i+1][j][0] %= mod;
					}
				}
				if (j+1<m && arr[i][j+1] != '#') {
					count[i][j+1] += count[i][j] + over[i][j][1];
					count[i][j+1] %= mod;
					if ((j+2 < m && arr[i][j+2] != '#') || (i+1<n && arr[i+1][j+1] != '#')) {
						over[i][j+1][1] += count[i][j];
						over[i][j+1][1] += over[i][j][1];
						over[i][j+1][1] %= mod;
					}
				}
				
				if (i+1<n && j+1<m && arr[i+1][j+1] != '#') {
					count[i+1][j+1] += count[i][j] + over[i][j][2];
					count[i+1][j+1] %= mod;
					if (i+2<n && j+2<m && arr[i+2][j+2] != '#') {
						over[i+1][j+1][2] += count[i][j];
						over[i+1][j+1][2] += over[i][j][2];
						over[i+1][j+1][2] %= mod;
					}
				}
			}
		}
		
		System.out.println(count[n-1][m-1]);
	}
}