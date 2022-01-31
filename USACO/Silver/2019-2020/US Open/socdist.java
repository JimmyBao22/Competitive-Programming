
import java.util.*;
import java.io.*;
import java.lang.*;

public class socdist {

	// http://www.usaco.org/index.php?page=viewproblem&cpid=1026
	
	static int n;
	static int m;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("socdist.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());  
	    m = Integer.parseInt(st.nextToken());  

		long max = 0;
		long min = (long)1e18;
		TreeMap<Long, Long> coords = new TreeMap<>();
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(in.readLine());
			long start = Long.parseLong(st.nextToken());
			long end = Long.parseLong(st.nextToken());
			max = Math.max(max, end);
			min = Math.min(min, start);
			coords.put(start, end);
		}

		in.close();
		
		long least = 1;
		long most = (long)((max-min+1) / (n-1));
		
		while (least < most) {
			long middle = (most + least+1)/2;
			
			if (works(middle, coords)) least = middle;
			else most = middle - 1;
		}

		PrintWriter out = new PrintWriter(new FileWriter("socdist.out"));
		System.out.println(least);
		out.println(least);
		out.close();

	}
	
	public static boolean works (long sep, TreeMap<Long, Long> coords) {
		long lastindex= Integer.MIN_VALUE;
		int numcows = 0;
		for (Long a : coords.keySet()) {
			long k = sep + lastindex - a;
			if (k < 0) k=0;
			numcows += (int)Math.ceil((coords.get(a) - (a+k) + 1) / (double)sep);
			lastindex = a+k+((int)Math.ceil((coords.get(a) - (a+k) + 1) / (double)sep) - 1)*(sep);
		}
		return numcows >= n;
	}
}