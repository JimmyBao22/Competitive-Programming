
import java.util.*;
import java.io.*;

public class decorate {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=432
	
	static int n,m;
	static HashSet<Integer>[] g;
	static boolean[] visited;
	static int[] type;
	static int[] ret;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("decorate.in"));
		PrintWriter out = new PrintWriter(new FileWriter("decorate.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		g = new HashSet[n];
		visited = new boolean[n];
		type = new int[n];
		ret = new int[2];

		for (int i=0; i<n; i++) {
			g[i] = new HashSet<Integer>();
			type[i] = -1;
		}
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			g[one].add(two);
			g[two].add(one);
		}
		
		int ans = 0;
		for (int i=0; i<n; i++) {
			if (!visited[i]) {
				int ret = find(i);
				if (ret == -1) {
					out.println(-1);
					out.close();
					return;
				}
				ans += ret;
			}
		}
				
		//System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	static boolean work;
	public static int find(int index) {

		work = true;
		ret[0] = ret[1] = 0;
		dfs(index, 0);
		
		if (!work) return -1;
		
		return Math.max(ret[0], ret[1]);
	}
		
	public static void dfs(int index, int curtype) {
		visited[index] = true;
		if (type[index] != -1 && type[index] != curtype) {
			work=false;
			return;
		}
		if (type[index] != -1) return;
		type[index] = curtype;
		ret[curtype]++;
		for (Integer a : g[index]) {
			dfs(a, curtype^1);
			if (!work) return;
		}
	}
}	