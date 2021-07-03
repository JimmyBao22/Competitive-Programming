
import java.util.*;
import java.io.*;

public class ErrichTacToe {

	// https://codeforces.com/contest/1450/problem/C1
	// https://codeforces.com/contest/1450/problem/C2
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ErrichTacToe"));

		int t = Integer.parseInt(in.readLine());
		while (t-->0) {
			int n = Integer.parseInt(in.readLine());
			char[][] arr = new char[n][];
			for (int i=0; i<n; i++) arr[i] = in.readLine().toCharArray();
			
			char[][][][] arr2 = new char[3][3][n][n];
			int[][] changecount = new int[3][3];
			for (int mod1=0; mod1 < 3; mod1++) {
				for (int mod2=0; mod2<3; mod2++) {
					if (mod1 == mod2) continue;
					for (int i=0; i<n; i++) {
						for (int j=0; j<n; j++) {
							arr2[mod1][mod2][i][j] = arr[i][j];
							if ((i + j)%3 == mod1) {
								// change to O
								if (arr2[mod1][mod2][i][j] == 'X') {
									arr2[mod1][mod2][i][j] = 'O';
									changecount[mod1][mod2]++;
								}
							}
							if ((i + j)%3 == mod2) {
								if (arr2[mod1][mod2][i][j] == 'O') {
									arr2[mod1][mod2][i][j] = 'X';
									changecount[mod1][mod2]++;
								}
							}
						}
					}
				}
			}
			
			int min=n*n;
			for (int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					if (i == j) continue;
					min = Math.min(min, changecount[i][j]);
				}
			}
			
			o: for (int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					if (i == j) continue;
					if (changecount[i][j] == min) {
						StringBuilder s = new StringBuilder();
						for (int x=0; x<n; x++) {
							for (int y=0; y<n; y++) {
								s.append(arr2[i][j][x][y]);
							}
							s.append("\n");
						}
						System.out.print(s);
						break o;
					}
				}
			}
		}
	}
}