
import java.util.*;
import java.io.*;

public class hps {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=691
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("hps.in"));
		PrintWriter out = new PrintWriter(new FileWriter("hps.out"));

		int n = Integer.parseInt(in.readLine());
		char[] arr = new char[n];
		// h, s, p
		int[] beg = new int[3];
		int[] end = new int[3];
		for (int i=0; i<n; i++) {
			String cur = (in.readLine());
			arr[i] = cur.charAt(0);
			if (arr[i] == 'H') end[0]++;
			else if (arr[i] == 'S') end[1]++;
			else end[2]++;
		}
		
		int max = Math.max(end[0], Math.max(end[1], end[2]));
		for (int i=0; i<n; i++) {
			if (arr[i] == 'H') {
				end[0]--;
				beg[0]++;
			}
			else if (arr[i] == 'S') {
				end[1]--;
				beg[1]++;
			}
			else {
				end[2]--;
				beg[2]++;
			}
			int ans = Math.max(end[0], Math.max(end[1], end[2]));
			ans += Math.max(beg[0], Math.max(beg[1], beg[2]));
			max = Math.max(max, ans);
		}
		
		System.out.println(max);
		out.println(max);
		out.close();

	}
}

// h > s > p > h