
import java.util.*;
import java.io.*;

public class cowrouteII {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=508
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cowroute.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cowroute.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int a = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());
		int[] cost = new int[n];
		ArrayList<ArrayList<Integer>> graphs = new ArrayList<>();
		for (int i=0; i<n; i++) graphs.add(new ArrayList<>());
		
		if (a == b) {
			out.println(0);
			out.close();
			return;
		}
		
		int mincost = Integer.MAX_VALUE;
		ArrayList<Integer> can_start = new ArrayList<>();		// contains a
		ArrayList<Integer> can_end = new ArrayList<>();		// contains b
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			cost[i] = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			boolean afound=false;
			boolean bfound=false;
			st = new StringTokenizer(in.readLine());
			for (int j=0; j<c; ++j) {
				int cur = Integer.parseInt(st.nextToken());
				graphs.get(i).add(cur);
				if (cur == a) {
					afound=true;
					can_start.add(i);
				}
				else if (cur == b) {
					can_end.add(i);
					if (afound) bfound=true;
				}
			}
			if (bfound) mincost = Math.min(mincost, cost[i]);
		}

		for (Integer i : can_start) {
			int j=0;
			for (; j<graphs.get(i).size(); j++) if (graphs.get(i).get(j) == a) break;

			for (; j<graphs.get(i).size(); j++) {
				// exit first route at graphs.get(i).get(j)
				for (Integer k : can_end) {
					for (int t=0; t<graphs.get(k).size(); t++) {
						if (graphs.get(k).get(t).equals(graphs.get(i).get(j))) {
							mincost = Math.min(mincost, cost[i] + cost[k]);
							break;
						}
						if (graphs.get(k).get(t) == b) break;
					}
				}
			}
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