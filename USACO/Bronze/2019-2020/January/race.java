
import java.util.*;
import java.io.*;

public class race {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=989
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("race.in"));
		PrintWriter out = new PrintWriter(new FileWriter("race.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int k = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());
		int max = (int)(1e5);
		
		int[] x = new int[n];
		for (int i=0; i<n; i++) x[i] = Integer.parseInt(in.readLine());
		
		ArrayList<Long> forwards = new ArrayList<>();
		forwards.add(0l);
		for (int i=1; i<max; i++) {
			forwards.add(forwards.get(i-1) + i);
			if (forwards.get(i-1) + i > k) break;
		}
		
		for (int i=0; i<n; i++) {
			int min = k;
			for (int j=2; j<forwards.size(); j++) {
				if (forwards.get(j) > k && j>x[i]) break;
				// j moves to get to forwards.get(j);
				if (j < x[i]) {
					min = Math.min(min, j + (int)(k - forwards.get(j) + j - 1) / j);
				}
				else {
					// j-1 to x[i] + 1
					if (forwards.get(j) + (int)(j - 1 + x[i] + 1) * (j - 1 - x[i]) / 2 > k) break;
					// add from j - 1 to x[i]
					int a = (int) (k - (int)(j - 1 + x[i]) * (j - 1 - x[i] + 1) / 2 - forwards.get(j) + j - 1)/j;
					min = Math.min(min, j + a + j - 1 - x[i] + 1);
				}
			}
			
			System.out.println(min);
			out.println(min);
		}
			
		out.close();
	}
}