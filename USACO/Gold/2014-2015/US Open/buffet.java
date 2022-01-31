
import java.util.*;
import java.io.*;

public class buffet {

	// http://usaco.org/index.php?page=viewproblem2&cpid=551
	
	static ArrayList<HashSet<Integer>> g = new ArrayList<>();
	static long[][] dist;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("buffet.in"));
		PrintWriter out = new PrintWriter(new FileWriter("buffet.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int e = Integer.parseInt(st.nextToken());
		
		for (int i=0; i<n; i++) g.add(new HashSet<>());
		
		A[] qsorted = new A[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			long one = Integer.parseInt(st.nextToken());
			qsorted[i] = new A(i, one);
			int two = Integer.parseInt(st.nextToken());
			for (int j=0; j<two; j++) {
				int three = Integer.parseInt(st.nextToken())-1;
				g.get(i).add(three);
				g.get(three).add(i);
			}
		}
		
		Arrays.parallelSort(qsorted);
		
		dist = new long[n][n];
			// dist[i][j] = number of edges between i and j

		for (int i=0; i<n; i++) {
			Arrays.fill(dist[i], (long)(1e9));
		}
		for (int i=0; i<n; i++) {
			bfs(i);
		}
		
		long[] dp = new long[n];
		// max energy ending at this node
		
			// loop from smallest val to largest
				// because once you take a large val, you cannot take anything smaller
		for (int i=0; i<n; i++) {
			long max=qsorted[i].val;
			for (int j=0; j<i; j++) {
				//System.out.println(dp[j] + qsorted[i].val - e*(dist[qsorted[i].i][qsorted[j].i]));
				if (dist[qsorted[i].i][qsorted[j].i] >= (long)(1e9)) continue;
				max = Math.max(max, dp[j] + qsorted[i].val - e*(dist[qsorted[i].i][qsorted[j].i]));
			}
			dp[i] = max;
		}
		
		long ans=0;
		for (int i=0; i<n; i++) ans = Math.max(ans, dp[i]);
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	public static void bfs(int i) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {i, 0});
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			if (dist[i][cur[0]] <= cur[1]) continue;
			dist[i][cur[0]] = cur[1];
			for (Integer a : g.get(cur[0])) {
				d.add(new int[] {a, cur[1]+1});
			}
		}
	}
	
	static class A implements Comparable<A> {
		int i; long val;
		A(int a, long b) {
			i = a; val = b;
		}
		public int compareTo(A o ) {
			return Long.compare(val, o.val);
		}
	}
}

/*

	increasing order of energy
	only need dp[node] because any previous has less of energy
	dp[node] = energy
	
	// loop over previous nodes before this one
	for (int i=0; i<node; i++) {
		max (	
		calculate max energy of the previous node (i) + q[node] -
		e(distance between the nodes)
		)
	}
	
	distance between nodes bfs
	
	answer = max (dp(node))

*/