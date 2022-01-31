
import java.util.*;
import java.io.*;

public class cowroute {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=507
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cowroute.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cowroute.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int a = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());
		
		int mincost = Integer.MAX_VALUE;
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			int cost = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			boolean afound=false;
			boolean bfound=false;
			st = new StringTokenizer(in.readLine());
			for (int j=0; j<c; ++j) {
				int cur = Integer.parseInt(st.nextToken());
				if (cur == a) afound=true;
				else if (cur == b && afound) bfound=true;
			}
			if (bfound) mincost = Math.min(mincost, cost);
		}

		if (mincost == Integer.MAX_VALUE) {
			out.println(-1);
			out.close();
			return;
		}
		System.out.println(mincost);
		out.println(mincost);
		out.close();
	}
}