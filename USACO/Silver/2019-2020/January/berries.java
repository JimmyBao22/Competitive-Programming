
import java.util.*;
import java.io.*;

public class berries {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=990
	
	static int n,k;
	static int[] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("berries.in"));
		PrintWriter out = new PrintWriter(new FileWriter("berries.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		arr = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		Arrays.parallelSort(arr);
		System.out.println(Arrays.toString(arr));
		
		int max=0;
		for (int j=1; j<=1000; j++) {		// j is max # berries in a single basket
			int min = j;
			int[] copy = new int[n];
			for (int i=0; i<n; i++) {
				copy[i] = arr[i];
			}

			int needed = k/2;
			int val = 0;
			
			for (int i=n-1; i>=0; i--) {
				if (needed == -k/2) break;
				int amount = copy[i]/min;
				copy[i] -= amount * min;
				if (needed - amount >= 0) needed -= amount;
				else {
					int temp = amount;
					if (needed > 0) amount -= needed;
					needed -= temp;
					if (needed < -k/2) {
						int over = (-k/2 - needed);
						amount = Math.max(0, amount-over);
						needed = -k/2;
					}
					val += amount * min;
				}
			}
			
			if (needed > -k/2) {	// still can put more stuff in
				Arrays.parallelSort(copy);
				for (int i=n-1; i>=0; i--) {		
						// just put biggest ones, because
							// everything over min has already been taken
					
					if (needed <= 0) val += copy[i];
					
					needed--;
					if (needed == -k/2) break;
				}
			}
			max = Math.max(max, val);
		}
		
		System.out.println(max);
		out.println(max);
		out.close();
	}
}