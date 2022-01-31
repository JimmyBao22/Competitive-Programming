
import java.util.*;
import java.io.*;

public class meeting {

	// http://usaco.org/index.php?page=viewproblem2&cpid=513
	
	static int n,m;
	static ArrayList<ArrayList<Integer>> g = new ArrayList<>();
	static HashMap<Integer, HashMap<Integer, Integer>> bmap = new HashMap<>(), emap = new HashMap<>();
			// first node --> second node --> time
	static boolean[][] bessie, elsie;
		// node, times to get to this node
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("meeting.in"));
		PrintWriter out = new PrintWriter(new FileWriter("meeting.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		for (int i=0; i<n; i++) g.add(new ArrayList<>());
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			int tone = Integer.parseInt(st.nextToken());
			int ttwo = Integer.parseInt(st.nextToken());
			g.get(one).add(two);
			if (bmap.containsKey(one)) {
				bmap.get(one).put(two, tone);
			}
			else {
				HashMap<Integer, Integer> cur = new HashMap<>();
				cur.put(two, tone);
				bmap.put(one, cur);
			}
			if (emap.containsKey(one)) {
				emap.get(one).put(two, ttwo);
			}
			else {
				HashMap<Integer, Integer> cur = new HashMap<>();
				cur.put(two, ttwo);
				emap.put(one, cur);
			}
		}
		
		bessie = new boolean[n][10001];
		elsie = new boolean[n][10001];
		
		bessie[0][0] = elsie[0][0] = true;
		for (int i=0; i<n; i++) {
			for (int j=0; j<10001; j++) {
				if (!bessie[i][j] && !elsie[i][j]) continue;
				for (int c=0; c<g.get(i).size(); c++) {
					int to = g.get(i).get(c);
					// from node i to node to
					if (bessie[i][j]) {
						int time = j + bmap.get(i).get(to);
						if (time < 10001) {
							bessie[to][time] = true;
						}
					}
					if (elsie[i][j]) {
						int time = j + emap.get(i).get(to);
						if (time < 10001) {
							elsie[to][time] = true;
						}
					}
				}
			}
		}
		
		int ans=-1;
		for (int i=0; i<10001; i++) {
			if (bessie[n-1][i] && elsie[n-1][i]) {
				ans = i;
				break;
			}
		}
		
		if (ans == -1) {
			System.out.println("IMPOSSIBLE");
			out.println("IMPOSSIBLE");
		}
		else {
			System.out.println(ans);
			out.println(ans);	
		}
		out.close();
	}
}