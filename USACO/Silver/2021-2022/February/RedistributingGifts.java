
import java.util.*;
import java.io.*;

public class RedistributingGifts {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1206
	
	static ArrayList<Integer>[] g;
	static boolean[] visited;
	static HashSet<Integer>[] reachable;
		// i can reach all values in reachable[i] 
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//      BufferedReader in = new BufferedReader(new FileReader("RedistributingGifts.in"));
		//		PrintWriter out = new PrintWriter(new FileWriter("RedistributingGifts.out"));

		int n = Integer.parseInt(in.readLine());
		int[][] arr = new int[n][n];
		g = new ArrayList[n];
		reachable = new HashSet[n];
		for (int i=0; i<n; i++) {
			g[i] = new ArrayList<>();
			reachable[i] = new HashSet<>();
			StringTokenizer st = new StringTokenizer(in.readLine());
			boolean found = false;
			for (int j=0; j<n; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken())-1;
				if (arr[i][j] == i) found = true;
				if (!found) {
					g[i].add(arr[i][j]);
				}
			}
		}
		
		for (int i=0; i<n; i++) {
			visited = new boolean[n];
			dfs(i, i);
		}
		
		int[] ans = new int[n];
		Arrays.fill(ans, -1);
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<n; i++) {
			int k = 0;
			while (true) {
				if (arr[i][k] == i) break;
				
				// can it reach this value
				if (reachable[arr[i][k]].contains(i)) {
					// YES, can reach this value
					ans[i] = arr[i][k]+1;
					break;
				}
				k++;
			}
			if (ans[i] == -1) ans[i] = i+1;
			sb.append(ans[i]);
			sb.append("\n");
		}

		System.out.print(sb);
		//		out.println();
		//		out.close();
	}
	
	public static void dfs(int node, int original) {
		if (visited[node]) return;
		visited[node] = true;
		reachable[original].add(node);
		for (Integer i : g[node]) {
			dfs(i, original);
		}
	}
}