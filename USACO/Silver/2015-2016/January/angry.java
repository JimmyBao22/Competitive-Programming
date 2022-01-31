
import java.util.*;
import java.io.*;

public class angry {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=594
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("angry.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		int[] places = new int[n];
		int max=0;
		for (int i=0; i<n; i++) {
			places[i] = Integer.parseInt(in.readLine());
			max = Math.max(max, places[i]);
		}
		Arrays.parallelSort(places);
		int low = 0;
		int high = max;
		while (low < high) {
			int middle = (low + high) / 2;
			if (works(places, middle, k)) {
				high = middle;
			}
			else low = middle+1;
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("angry.out"));
		System.out.println(low);
		out.println(low);
		out.close();

	}
	
	public static boolean works(int[] places, int R, int k) {
		int lastexplosion = places[0] + R;
		int numexplosions=1;
		for (int i=1; i<places.length; i++) {
			if (lastexplosion + R >= places[i]) continue;
			else {
				lastexplosion = places[i] + R;
				numexplosions++;
			}
		}
		return numexplosions <= k;
	}
}