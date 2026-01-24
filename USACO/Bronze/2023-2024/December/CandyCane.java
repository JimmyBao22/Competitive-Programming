
import java.io.*;
import java.util.*;

public class CandyCane {

	// https://usaco.org/index.php?page=viewproblem2&cpid=1347

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		// inputs
		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		long[] cows = new long[n];
		st = new StringTokenizer(in.readLine());
		for (int i = 0; i < n; i++) {
			cows[i] = Integer.parseInt(st.nextToken());
		}
		long[] candy = new long[m];
		st = new StringTokenizer(in.readLine());
		for (int i = 0; i < m; i++) {
			candy[i] = Integer.parseInt(st.nextToken());
		}
		
		// first cow grows exponentially. If any other cow behind the first cow
		// is able to eat, that implies the first cow ate their full height.
		// therefore doubling in height. They can only double their height
		// log_2 (10^9) ≈ 30 times.
		
		for (int j = 0; j < m; j++) {
			long bottom = 0;
			for (int i = 0; i < n && bottom != candy[j]; i++) {
				if (cows[i] > bottom) {
					long ate = Math.min(cows[i], candy[j]) - bottom;
					bottom += ate;
					cows[i] += ate;
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(cows[i]);
			sb.append("\n");
		}
		System.out.print(sb);
	}
}