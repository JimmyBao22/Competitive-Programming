
import java.util.*;
import java.io.*;

public class pairup {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=738
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("pairup.in"));
		PrintWriter out = new PrintWriter(new FileWriter("pairup.out"));

		int n = Integer.parseInt(in.readLine());
		
		ArrayList<long[]> arr = new ArrayList<>();
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			long x = Long.parseLong(st.nextToken());
			long y = Long.parseLong(st.nextToken());
			arr.add(new long[] {x, y});
		}
		Collections.sort(arr, new Comparator<long[]>() {
			public int compare(long[] a, long[] b) {
				return Long.compare(a[1], b[1]);
		    }
	    });

		long max=0;
		int firstpointer=0;
		long usedfirst=0;
		int lastpointer = arr.size()-1;
		long usedsec=0;
		while (firstpointer <= lastpointer) {
			if (arr.get(firstpointer)[0] - usedfirst <= arr.get(lastpointer)[0] - usedsec) {
				if (arr.get(firstpointer)[0] - usedfirst != 0) max = Math.max(max, arr.get(firstpointer)[1] + arr.get(lastpointer)[1]);
				usedsec += (arr.get(firstpointer)[0] - usedfirst);
				firstpointer++;
				usedfirst=0;
			}
			else {
				if (arr.get(lastpointer)[0] - usedsec != 0) max = Math.max(max, arr.get(firstpointer)[1] + arr.get(lastpointer)[1]);
				usedfirst += (arr.get(lastpointer)[0] - usedsec);
				lastpointer--;
				usedsec=0;
			}
		}
		
		System.out.println(max);
		out.println(max);
		out.close();
	}
}