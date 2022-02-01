
import java.util.*;
import java.io.*;

public class CircumferenceofaTree {

	// https://codeforces.com/gym/102694/problem/A
	
	static ArrayList<Integer>[] g;
	static int n;
	static int[] dist;
	static boolean[] visited;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("CircumferenceofaTree"));

		n = Integer.parseInt(in.readLine());
		g = new ArrayList[n];
		for (int i=0; i<n; i++) {
			g[i] = new ArrayList<>();
		}
		
		for (int i=0; i<n-1; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			g[one].add(two);
			g[two].add(one);
		}
		
		dist = new int[n];
		visited = new boolean[n];
		dfs(0, -1, 0);
		int max=0; int maxindex=0;
		for (int i=0; i<n; i++) {
			if (dist[i] > max) {
				max = dist[i];
				maxindex = i;
			}
		}
		
		dist = new int[n];
		visited = new boolean[n];
		dfs(maxindex, -1, 0);
		max=0;
		for (int i=0; i<n; i++) {
			max = Math.max(max, dist[i]);
		}
		
		System.out.println(max * 3);
	}
	
	public static void dfs(int node, int p, int d) {
		if (visited[node]) return;
		visited[node] = true;
		dist[node] = d;
		for (Integer i : g[node]) {
			if (i != p) dfs(i, node, d+1);
		}
	}
}