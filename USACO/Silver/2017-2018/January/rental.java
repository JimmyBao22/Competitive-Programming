
import java.util.*;
import java.io.*;

public class rental {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=787
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("rental.in"));
		PrintWriter out = new PrintWriter(new FileWriter("rental.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int r = Integer.parseInt(st.nextToken());

		long[] cows = new long[n];
		for (int i=0; i<n; ++i) cows[i] = Integer.parseInt(in.readLine());
		
		Arrays.parallelSort(cows);
		
		long[] cowsum = new long[n];
		for (int i=0; i<n; i++) {
			if (i == 0) cowsum[i] = cows[i];
			else {
				cowsum[i] = cowsum[i-1] + cows[i];
			}
		}
		
		long[][] stores = new long[m][2];
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			stores[i][0] = Integer.parseInt(st.nextToken());
			stores[i][1] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(stores, new Comparator<long[]>() {
			public int compare(long[] a, long[] b) {
				return Long.compare(b[1], a[1]);
		    }
		});		// sort max firsts
		
		long[] storesnum = new long[m];
		long[] storesmoney = new long[m];
		for (int i=0; i<m; i++) {
			if (i == 0) {
				storesnum[0] = stores[0][0];
				storesmoney[0] = stores[0][0] * stores[0][1];
			}
			else {
				storesnum[i] = storesnum[i-1] + stores[i][0];
				storesmoney[i] = storesmoney[i-1] + stores[i][0] * stores[i][1];
			}
		}
		
		long[] neighbors = new long[r];
		long[] neighborsum = new long[r+1];
		for (int i=0; i<r; i++) {
			neighbors[i] = Integer.parseInt(in.readLine());
		}
		
		Arrays.sort(neighbors);	
		for (int i=r-1; i>=0; i--) {
			neighborsum[r-i] = neighborsum[r-i-1] + neighbors[i];
		}
		
		long max=0;
		for (int i=0; i<=n; i++) {
			// take i number of cows for neighbor, take n-i for store
			if (i>r) continue;
			
			long cur = neighborsum[i];
			long milk = 0;
			if (i != 0) {
				milk = cowsum[n-1] - cowsum[i-1];
			}
			else {
				milk = cowsum[n-1];
			}
			
			int minindex = 0;
			int maxindex = m-1;
			while (minindex < maxindex) {
				int middle = (minindex + maxindex)/2;
				if (storesnum[middle] == milk) {
					minindex = middle;
					maxindex = middle;
				}
				else if (storesnum[middle] > milk) {
					maxindex = middle;
				}
				else {
					minindex = middle+1;
				}
			}
			
			if (storesnum[minindex] == milk) {
				cur += storesmoney[minindex];
			}
			else {
				// storesnum[minindex] > milk
				long needed = milk;
				if (minindex>=1) {
					cur += storesmoney[minindex-1];
					needed = milk - storesnum[minindex-1];
				}
				if (minindex == m-1 && needed>stores[minindex][0]) {
					needed = stores[minindex][0];
				}
				cur += needed*stores[minindex][1];
			}
			
			max = Math.max(max, cur);
		}
		
		System.out.println(max);
		out.println(max);
		in.close();
		out.close();
	}
}