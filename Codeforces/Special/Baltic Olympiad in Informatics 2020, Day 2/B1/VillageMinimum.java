
import java.util.*;
import java.io.*;

public class VillageMinimum {

	// https://codeforces.com/contest/1387/problem/B1
	
	static int n;
	static ArrayList<Integer>[] g;
	static boolean[] taken;
	static int[] ans;
	static long minans = 0;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("VillageMinimum"));

		int n = Integer.parseInt(in.readLine());
		g = new ArrayList[n];
		for (int i=0; i<n; i++) {
			g[i] = new ArrayList<>();
		}
		for (int i=0; i<n-1; i++ ) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			a--; b--;
			g[a].add(b);
			g[b].add(a);
		}
		
		taken = new boolean[n];
		ans = new int[n];
		Arrays.fill(ans, -1);
		
		dfs(0,0);
		
		if (!taken[0]) {
			minans += 2;
			int node = 0;
			for (int i=0; i<n; i++) {
				if (ans[i] == -1) node = i;
			}
			
			if (node == 0) {
				int goes=0;
				for (int i=0; i<n; i++) {
					if (ans[i] == g[0].get(0)) goes = i;
				}
				ans[0] = g[0].get(0);
				ans[goes] = 0;
			}
			else {
				ans[node] = ans[0];
				ans[0] = node;
			}
		}
		
		StringBuilder s = new StringBuilder();
		s.append(minans + "\n");
		for (int i=0; i<n; i++) {
			s.append(ans[i]+1 + " ");
		}
		System.out.println(s);
	}
	
	public static void dfs(int node, int p) {
		ArrayList<Integer> allcur = new ArrayList<>();
		for (Integer a : g[node]) {
			if (a != p) {
				dfs(a, node);
				if (!taken[a]) allcur.add(a);
			}
		}
		
		if (allcur.size() == 0) return;
		
		minans += 2*allcur.size();
		for (int i=0; i<allcur.size()-1; i++) {
			ans[allcur.get(i)] = allcur.get(i+1);
		}
		ans[allcur.get(allcur.size()-1)] = node;
		ans[node] = allcur.get(0);
		taken[node] = true;
	}
}