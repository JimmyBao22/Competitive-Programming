
import java.util.*;
import java.io.*;

public class cowjog {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=496
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cowjog.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cowjog.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long t = Integer.parseInt(st.nextToken());

		long[][] arr = new long[n][2];
		for (int i=0; i<n; i++ ) {
			st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		
		TreeMap<Long, ArrayList<Integer>> alltimes = new TreeMap<>();
		long[] times = new long[n];
		for (int i=0; i<n; i++) {
			times[i] = arr[i][0] + arr[i][1] * t;
			if (!alltimes.containsKey(times[i])) alltimes.put(times[i], new ArrayList<>());
			alltimes.get(times[i]).add(i);
		}
		
		int p=0;
		for (Long a : alltimes.keySet()) {
			for (int i=alltimes.get(a).size()-1; i>=0; i--) {
				times[alltimes.get(a).get(i)] = p;
				p++;
			}
		}
		
		TreeSet<Long> set = new TreeSet<>();
		for (int i=n-1; i>=0; i--) {				// LIS backwards
			Long higher = set.ceiling(times[i]);
			if (higher != null) set.remove(higher);
			set.add(times[i]);
		}
		
		int ans=set.size();
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
}

/*

	LIS backwards.
	
	ex. 
	0 1 --> 3
	1 2 --> 7
	2 3 --> 11
	3 2 --> 9
	6 1 --> 9
	
	here, you can put the 3,7,11 together. Then, you can't put the 11 with the 9, so 
		for the 9 you have to start a new sequence. 
		Same with the second 9 - you can't put the other 9, or the 11, with it, so you
			have to start a new sequence
			
	Therefore, do LIS backwards. Except, instead of only strictly increasing, there can be
		numbers with same value so in reality it is a longest non-decreasing sequence
		
		Deal with this by using the map to make every single time different.
*/
