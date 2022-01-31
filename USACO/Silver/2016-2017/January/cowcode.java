
import java.util.*;
import java.io.*;

public class cowcode {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=692
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cowcode.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cowcode.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		String s = st.nextToken();
		int m = s.length();
		long n = Long.parseLong(st.nextToken());
		
		if (n < m) {
			out.println(s.charAt((int)n-1));
			out.close();
			return;
		}
		
		long[] doubles = new long[61];
		doubles[1] = m;
		int index=1;
		for (int i=2; i<n; i++ ) {
			doubles[i] = doubles[i-1] * 2;
			if (doubles[i] >= n) {
				index = i;
				break;
			}
		}
		
		for (; index >= 2; index--) {
				// if it came from the first part of previous block, don't need to rotate
			if (n <= doubles[index-1]) {
				
			}
			else {
				n -= doubles[index-1];
				n--;		// previous block rotated, so subtract 1
				if (n == 0) n += doubles[index-1];
			}
		}

		System.out.println(s.charAt((int)n-1));
		out.println(s.charAt((int)n-1));
		out.close();
	}
}