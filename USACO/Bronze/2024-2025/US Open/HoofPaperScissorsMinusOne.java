import java.util.*;
import java.io.*;

public class HoofPaperScissorsMinusOne {   
    
    // https://usaco.org/index.php?page=viewproblem2&cpid=1515

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        char[][] a = new char[n][n];
        for (int i = 0; i < n; i++) {
            String line = in.readLine();
            for (int j = 0; j < line.length(); j++) {
                a[i][j] = line.charAt(j);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (a[j][i] == 'W') a[i][j] = 'L';
                else if (a[j][i] == 'L') a[i][j] = 'W';
                else a[i][j] = 'D';
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            int s1 = Integer.parseInt(st.nextToken())-1;
            int s2 = Integer.parseInt(st.nextToken())-1;

            // find any row for which s1 and s2 are the same
            int win = 0;
            for (int j = 0; j < n; j++) {
                if (a[j][s1] == 'W' && a[j][s2] == 'W') 
                    win++;
            }

            // the number of combos is n^2 - (n - win)^2
            sb.append(n * n - (n - win) * (n - win));
            sb.append("\n");
        }

		System.out.print(sb);
	}
}