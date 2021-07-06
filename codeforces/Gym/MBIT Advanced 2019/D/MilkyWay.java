
import java.util.*;
import java.io.*;

public class MilkyWay {

	// https://codeforces.com/gym/102802/problem/D
	// https://mbit.mbhs.edu/archive/2019/varsity.pdf
	
	static int INF = (int)(1e9);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MilkyWay"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(in.readLine());
		int a = Integer.parseInt(st.nextToken())-1;
		int b = Integer.parseInt(st.nextToken())-1;
		int[] p = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) p[i] = Integer.parseInt(st.nextToken());
		ArrayList<Edge>[] g = new ArrayList[n];
		for (int i=0; i<n; i++) g[i] = new ArrayList<>();
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			int three = Integer.parseInt(st.nextToken());
			g[one].add(new Edge(two, three));
			g[two].add(new Edge(one, three));
		}
		
		int[][] memo = new int[n][c+1];
		for (int i=0; i<n; i++) Arrays.fill(memo[i], INF);
		
		ArrayDeque<int[]> d = new ArrayDeque<>();
			// int node, int capacity, int count
		d.add(new int[] {a, 0, 0});
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			int node = cur[0]; int cap = cur[1]; int days = cur[2];
			if (memo[node][cap] <= days) continue;
			memo[node][cap] = days;
			
			while (cap <= c) {
				for (Edge j : g[node]) {
					if (cap >= j.dist) {
						d.add(new int[] {j.to, cap - j.dist, days+1});
					}
				}
				cap += p[node];
				days++;
			}
			if (cap > c) {
				cap = c;
				for (Edge j : g[node]) {
					if (cap >= j.dist) {
						d.add(new int[] {j.to, cap - j.dist, days+1});
					}
				}
			}
		}
				
		int min=INF;
		for (int i=0; i<=c; i++) min = Math.min(min, memo[b][i]);
		System.out.println(min);
	}
	
	static class Edge {
		int to; int dist;
		Edge (int a, int b) {
			to = a; dist = b;
		}
	}
}