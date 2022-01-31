
import java.util.*;
import java.io.*;

public class cowjog {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=489
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cowjog.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cowjog.out"));

		int n = Integer.parseInt(in.readLine());
		int[][] arr = new int[n][2];		
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		
		int count=1;
		int last = arr[n-1][1];
		for (int i=n-2; i>=0; i--) {
			if (arr[i][1] <= last) {
				last = arr[i][1];
				count++;
			}
		}

		System.out.println(count);
		out.println(count);
		out.close();
	}
}