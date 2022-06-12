
import java.util.*;
import java.io.*;

public class Acowdemia {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1136
	
	static int n;
	static long k, l;
	static int[] c;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//      BufferedReader in = new BufferedReader(new FileReader("Acowdemia.in"));
		//		PrintWriter out = new PrintWriter(new FileWriter("Acowdemia.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Long.parseLong(st.nextToken());
		l = Long.parseLong(st.nextToken());
		c = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			c[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.parallelSort(c);

		long max = n;
		long min = 0;
		while (min < max) {
			long middle = (min + max + 1)/2;
			if (check(middle)) {
				min = middle;
			}
			else max = middle - 1;
		}

		System.out.println(max);
		//		out.println();
		//		out.close();

	}
	
	public static boolean check(long count) {
		if (count - c[n-(int)count] > k) return false;
			// if it will take more than k surveys, then it is false
		long needed = 0;
		for (int i=n-1; i>=n-count; i--) {
			if (c[i] < count) needed += count - c[i];
		}
		return needed <= k * l;
	}
}