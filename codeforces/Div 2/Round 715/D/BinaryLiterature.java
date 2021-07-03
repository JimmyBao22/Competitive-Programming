
import java.util.*;
import java.io.*;

public class BinaryLiterature {

	// https://codeforces.com/contest/1509/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new FileReader("BinaryLiterature"));

		int t = Integer.parseInt(in.readLine());
		o: while (t-->0) {
			int n = Integer.parseInt(in.readLine());
			char[][] s = new char[3][];
			for (int i=0; i<3; i++) {
				s[i] = in.readLine().toCharArray();
			}

			StringBuilder sb = new StringBuilder();
			ArrayList<Character > ans = new ArrayList<>();

			for (int j=0; j<3; j++) {
				for (int k=j+1; k<3; k++) {
					ArrayList<Integer> same = new ArrayList<>();
					for (int i=0; i<2*n; i++) {
						if (s[j][i] == s[k][i]) {
							same.add(i);
						}
					}
					if (same.size() >= n) {
						// good stuff
						int p1 = 0;
						for (int i=0; i<2*n; i++) {
							if (p1<same.size() && same.get(p1) == i) {
								ans.add(s[j][i]);
								sb.append(s[j][i]);
								p1++;
							}
							else {
								ans.add(s[j][i]);
								ans.add(s[k][i]);
								sb.append(s[j][i]);
								sb.append(s[k][i]);
							}
						}
						System.out.println(sb);
//						if (!check(ans, n, s)) {
//							System.out.print("NO ");
//							System.out.println(Arrays.toString(s[0]));
//							System.out.println(Arrays.toString(s[1]));
//							System.out.println(Arrays.toString(s[2]));
//							return;
//						}
						continue o;
					}
				}
			}
			
			int[][] color = new int[3][2];	// 0, 1
			for (int j=0; j<3; j++) {
				for (int i=0; i<2*n; i++) {
					if (s[j][i] == '1') {
						color[j][1]++;
					}
					else color[j][0]++;
				}
			}
			
			for (int j=0; j<3; j++) {
				for (int k=j+1; k<3; k++) {
					if (color[j][0] >= n && color[k][0] >= n) {
						int p1 = 0;
						for (int i=0; i<2*n; i++) {
							if (s[j][i] == '0') {
								while (p1 < 2*n && s[k][p1] != '0') {
									ans.add('1');
									sb.append('1');
									p1++;
								}
								ans.add('0');
								sb.append('0');
								p1++;
							}
							else {
								ans.add('1');
								sb.append('1');
							}
						}
						while (p1 < 2*n) {
							ans.add(s[k][p1]);
							sb.append(s[k][p1++]);
						}
						System.out.println(sb);
//						if (!check(ans, n, s)) {
//							System.out.print("NO ");
//							System.out.println(Arrays.toString(s[0]));
//							System.out.println(Arrays.toString(s[1]));
//							System.out.println(Arrays.toString(s[2]));
//							return;
//						}
						continue o;
					}
					else if (color[j][1] >= n && color[k][1] >= n) {
						int p1 = 0;
						for (int i=0; i<2*n; i++) {
							if (s[j][i] == '1') {
								while (p1 < 2*n && s[k][p1] != '1') {
									ans.add('0');
									sb.append('0');
									p1++;
								}
								ans.add('1');
								sb.append('1');
								p1++;
							}
							else {
								ans.add('0');
								sb.append('0');
							}
						}
						while (p1 < 2*n) {
							ans.add(s[k][p1]);
							sb.append(s[k][p1++]);
						}
						System.out.println(sb);
//						if (!check(ans, n, s)) {
//							System.out.print("NO ");
//							System.out.println(Arrays.toString(s[0]));
//							System.out.println(Arrays.toString(s[1]));
//							System.out.println(Arrays.toString(s[2]));
//							return;
//						}
						continue o;
					}
				}
			}
			
			for (int i=0; i<3*n; i++) {
				if ((int)(Math.random()*2) == 0) {
					System.out.print("0");
				}
				else {
					System.out.print("1");
				}
			}
			System.out.println();
		}
	}
	
	public static boolean check(ArrayList<Character > ans, int n, char[][] s) {
		int c=0;
		for (int i=0; i<3; i++) {
			int k = 0;
			for (int j=0; j<ans.size(); j++) {
				if (k < 2*n && ans.get(j) == s[i][k]) {
					k++;
				}
			}
			if (k == 2*(n)) c++;
		}
		return c >= 2;
	}
}