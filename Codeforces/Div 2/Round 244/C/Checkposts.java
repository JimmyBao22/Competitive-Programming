
import java.util.*;
import java.io.*;

public class Checkposts {
	
	static ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
	static ArrayList<ArrayList<Integer>> reverse = new ArrayList<>();
	static boolean[] visited;
	static ArrayDeque<Integer> stack = new ArrayDeque<>();
	static ArrayList<ArrayList<Integer>> SCC = new ArrayList<>();
		// scores all of the components
	
	// https://codeforces.com/contest/427/problem/C
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Checkposts"));

		int n = Integer.parseInt(in.readLine()); 	// number of vertices
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		HashMap<Integer, Integer> costs = new HashMap<>();
		for (int i=0; i<n; i++) {
			costs.put(i, Integer.parseInt(st.nextToken()));
		}
		
		int m = Integer.parseInt(in.readLine()); 	// number of edges

		visited = new boolean[n];
		for (int i=0; i<n; i++) {
			adj.add(new ArrayList<>());
			reverse.add(new ArrayList<>());
		}
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			adj.get(a).add(b);
			reverse.get(b).add(a);
		}
		
		SCC(n, m);
		
		long mod = (long)1e9 + 7;
		long ways=1;
		long ans=0;
		for (int i=0; i<SCC.size(); i++) {
			int min = Integer.MAX_VALUE;
			long countmin=1;
			for (int j=0; j<SCC.get(i).size(); j++) {
				if (costs.get(SCC.get(i).get(j)) < min) {
					min = costs.get(SCC.get(i).get(j));
					countmin=1;
				}
				else if (costs.get(SCC.get(i).get(j)) == min) {
					countmin++;
				}
			}
			ans += min;
			ways *= countmin %mod;
			ways %= mod;
		}
		System.out.println(ans + " " + ways);
	}
	
	public static void SCC(int n, int m) {
		for (int i=0; i<n; i++) {
			if (!visited[i]) fillstack(i);
		}
		
		Arrays.fill(visited, false);
		
		while (!stack.isEmpty()) {
			int cur = stack.pop();
			if (!visited[cur]) {
				ArrayList<Integer> curarr = new ArrayList<>();
				dfsRev(cur, curarr);
				SCC.add(curarr);
			}
		}
	}
	
	public static void fillstack(int cur) {
		visited[cur] = true;
		for (Integer i : adj.get(cur)) {
			if (!visited[i]) fillstack(i);
		}
		stack.push(cur);
	}
	
	public static void dfsRev(int cur, ArrayList<Integer> curarr) {
		visited[cur] = true;
		curarr.add(cur);
		for (Integer i : reverse.get(cur)) {
			if (!visited[i]) dfsRev(i, curarr);
		}
	}
}
