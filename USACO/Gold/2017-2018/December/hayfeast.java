
import java.util.*;
import java.io.*;

public class hayfeast {

	// http://usaco.org/index.php?page=viewproblem2&cpid=767
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("hayfeast.in"));
		PrintWriter out = new PrintWriter(new FileWriter("hayfeast.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long m = Long.parseLong(st.nextToken());

		Food[] arr = new Food[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i] = new Food(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken()));
		}
		
		int min=1;
		int max = (int)(1e9);
		while (min < max) {
			int middle = min + (max - min)/2;
			if (check(arr, middle, n, m)) {
				max = middle;
			}
			else min = middle+1;
		}
		
		System.out.println(min);
		out.println(min);
		out.close();

	}
	
	public static boolean check(Food[] arr, int val, int n, long m) {
		long cursum =0;
		for (int i=0; i<n; i++) {
			if (arr[i].spice > val) {
				if (cursum >= m) return true;
				cursum=0;
			}
			else cursum += arr[i].flavor;
		}
		return cursum >= m;
	}
	
	static class Food {
		long flavor;
		long spice;
		Food (long a, long b) {
			flavor = a;
			spice = b;
		}
	}
}