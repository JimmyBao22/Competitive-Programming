
import java.util.*;
import java.io.*;

public class YearoftheCow {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1111
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//      BufferedReader in = new BufferedReader(new FileReader("a.in"));
		//		PrintWriter out = new PrintWriter(new FileWriter("a.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		long[] arr = new long[n];
		for (int i=0; i<n; i++) {
			arr[i] = Long.parseLong(in.readLine());
		}
		
		Arrays.parallelSort(arr);
		
		// if it is in the same thing, then combine
		ArrayList<Long> newarr = new ArrayList<>();
		newarr.add(arr[0]);
		for (int i=1; i<n; i++) {
			if (arr[i-1] / 12 != arr[i] / 12) {
				newarr.add(arr[i]);
			}
		}
		
		n = newarr.size();
		arr = new long[n];
		for (int i=0; i<n; i++) arr[i] = newarr.get(i);
		
		long[] diff = new long[n-1];
		long[] forwards = new long[n];
		long[] backwards1 = new long[n];
		long[] backwards2 = new long[n];
		for (int i=1; i<n; i++) {
			diff[i-1] = arr[i] - arr[i-1];
		}
		
		for (int i=0; i<n; i++) {
			long div = arr[i] / 12;
			backwards1[i] = arr[i] - div * 12;
			if (i == 0) backwards2[i] = arr[i];
			else backwards2[i] = backwards1[i];
			forwards[i] = (div+1) * 12 - arr[i];
		}
		
		long curtotal1 = backwards1[0] + forwards[n-1];
		long curtotal2 = backwards2[0] + forwards[n-1];
		for (int i=0; i<n-1; i++) {
			curtotal1 += diff[i];
			curtotal2 += diff[i];
		}
		
		long[] costs1 = new long[n-1];
		long[] costs2 = new long[n-1];
		for (int i=0; i<n-1; i++) {
			costs1[i] = diff[i] - forwards[i] - backwards1[i+1];
			costs2[i] = diff[i] - forwards[i] - backwards2[i+1];
		}
		
		Arrays.parallelSort(costs1);
		Arrays.parallelSort(costs2);
		// want to go greatest cost to least cost
		
		// have the start be one of the groups; create k groups (don't need to jump
			// back to start because you are waiting to go back to start already)
		for (int i=0; i<k-1; i++) {
			if (n-i-2 >= 0) curtotal2 -= costs2[n-i-2];
		}
		
		// create k-1 groups bc need 1 jump to teleport to start
		for (int i=0; i<k-2; i++) {		
			if (n-i-2 >= 0) curtotal1 -= costs1[n-i-2];
		}
		
		System.out.println(Math.min(curtotal1, curtotal2));
		//		out.println();
		//		out.close();
	}
}