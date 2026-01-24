
import java.io.*;
import java.util.*;

public class Majority {

	// https://usaco.org/index.php?page=viewproblem2&cpid=1371

	public static void main(String[] args) throws IOException, FileNotFoundException {
		Scanner in = new Scanner(System.in);
		//      BufferedReader in = new BufferedReader(new FileReader("Majority.in"));
		//		PrintWriter out = new PrintWriter(new FileWriter("Majority.out"));

		int t = in.nextInt();
		while (t-->0) {
			int n = in.nextInt();
			int[] cows = new int[n];
			for (int i = 0; i < n; i++) {
				cows[i] = in.nextInt();
			}
			
			// out of any triple, if 2 of them are the same, that pair
			// can be expanded to the whole group.
			
			// Note: edge case is if n = 2, which is why we shouldnt check
			// cows[i+1] == cows[i+2]
			boolean[] works = new boolean[n + 1];
			for (int i = 0; i+1 < n; i++) {
				if (cows[i] == cows[i+1]) works[cows[i]] = true;
				if (i + 2 < n && cows[i] == cows[i+2]) works[cows[i]] = true;
			}
			
			boolean noneWork = true;
			for (int i = 1; i <= n; i++) {
				if (works[i]) {
					if (noneWork) {
						System.out.print(i);						
						noneWork = false;
					} else {
						System.out.print(" " + i);
					}
				}
			}
			
			if (noneWork) {
				System.out.print("-1");
			}
			
			System.out.println();
		}
	}
}