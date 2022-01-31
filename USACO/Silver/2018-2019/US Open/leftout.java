
import java.util.*;
import java.io.*;

public class leftout {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=942
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("leftout.in"));
		PrintWriter out = new PrintWriter(new FileWriter("leftout.out"));

		int n = Integer.parseInt(in.readLine());
		char[][] arr = new char[n][];
		for (int i=0; i<n; i++) arr[i] = in.readLine().toCharArray();

		char want = arr[0][0];
		while (true) {
			boolean did_something=false;
			for (int i=0; i<n; i++) {
				int c=0;
				for (int j=0; j<n; j++) {
					if (arr[i][j] != want) c++;
				}
				if (c > n/2) {
					for (int j=0; j<n; j++) {
						if (arr[i][j] == 'R') arr[i][j] = 'L';
						else arr[i][j] = 'R';
					}
					did_something=true;
				}
			}
			for (int i=0; i<n; i++) {
				int c=0;
				for (int j=0; j<n; j++) {
					if (arr[j][i] != want) c++;
				}
				if (c > n/2) {
					for (int j=0; j<n; j++) {
						if (arr[j][i] == 'R') arr[j][i] = 'L';
						else arr[j][i] = 'R';
					}
					did_something=true;
				}
			}
			if (!did_something) break;
		}
		
		int r=0; int l=0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				if (arr[i][j] == 'R') r++;
				else l++;
			}
		}
		
		int goodx=0;
		int goody=0;
		if (r == 1) {
			for (int i=0; i<n; i++) {
				for (int j=0; j<n; j++) {
					if (arr[i][j] == 'R') {
						goodx=i; goody=j; break;
					}
				}
			}
		}
		if (l == 1) {
			for (int i=0; i<n; i++) {
				for (int j=0; j<n; j++) {
					if (arr[i][j] == 'L') {
						goodx=i; goody=j; break;
					}
				}
			}
		}
		
		if (r == 1 || l == 1) {
			System.out.println(goodx+1 + " " + (goody+1));
			out.println(goodx+1 + " " + (goody+1));
		}
		else out.println(-1);
		out.close();
	}
}