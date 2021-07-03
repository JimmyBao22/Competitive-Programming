
import java.util.*;
import java.io.*;

public class BeautifulGraph {

	// https://codeforces.com/contest/1093/problem/D
	
	static int n, m;
	static ArrayList<Integer>[] g;
	static int[] color;
	static long mod = 998244353;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("BeautifulGraph"));

		StringBuilder s = new StringBuilder();
		int t = Integer.parseInt(in.readLine());
		while (t --> 0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			g = new ArrayList[n];
			for (int i=0; i<n; i++) g[i] = new ArrayList<>();
			for (int i=0; i<m; i++) {
				st = new StringTokenizer(in.readLine());
				int one = Integer.parseInt(st.nextToken())-1;
				int two = Integer.parseInt(st.nextToken())-1;
				g[one].add(two);
				g[two].add(one);
			}
			
			color = new int[n];
			Arrays.fill(color, -1);
			boolean[] visited = new boolean[n];
			long ans = 1;
			for (int i=0; i<n; i++) {
				if (!visited[i]) {
					ans *= ways(i, visited);
					ans %= mod;
				}
			}
			
			s.append(ans);
			s.append("\n");
		}
		System.out.println(s);
	}
	
	static boolean works;
		
	public static long ways(int cur, boolean[] visited) {
		works=true;
		int[] ret = dfs(cur, visited, 0);
		if (!works) return 0;
		long ans=1;
		ans = pow(2, ret[0], mod) + pow(2, ret[1] - ret[0], mod);	// either color ret[0] with odd #'s, or ret[1] - ret[0] with odd #'s
		return ans % mod;
	}
	
	public static int[] dfs(int node, boolean[] visited, int c) {
		if (visited[node]) {
			if (c != color[node]) {
				works=false;
			}
			return new int[]{0,0};
		}
		visited[node] = true;
		color[node] = c;
		int ones=0;
		int size=0;
		for (Integer i : g[node]) {
			int[] cur = dfs(i, visited, c^1);
			ones += cur[0];
			size += cur[1];
		}
		if (c == 1) ones++;
		return new int[] {ones, size+1};
	}
	
	public static long pow(long a, long b, long m) {
    	long ans = 1;
    	while (b > 0) {
    		if (b%2 == 1) {
    			ans *= a;
    			ans %= m;
    		}
    		a *= a;
    		a %= m;
    		b >>= 1;
    	}
    	return ans;
    }
}