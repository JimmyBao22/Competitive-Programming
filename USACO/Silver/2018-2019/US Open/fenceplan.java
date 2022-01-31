
import java.util.*;
import java.io.*;

public class fenceplan {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=944
	
	static ArrayList<Integer>[] g;
	static int n, m;
	static A[] arr;
	static boolean[] visited;
	static long[] ret;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("fenceplan.in"));
		PrintWriter out = new PrintWriter(new FileWriter("fenceplan.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		arr = new A[n];
		g = new ArrayList[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i] = new A(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i);
			g[i] = new ArrayList<>();
		}
		
		for (int i=0; i<m; i++ ) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			g[one].add(two);
			g[two].add(one);
		}
		
		visited = new boolean[n];
		long ans = Long.MAX_VALUE;
		for (int i=0; i<n; i++) {
			if (!visited[i]) {
				ret = new long[4];
					// minx, miny, maxx, maxy
				ret[0] = ret[1] = (long)(1e18);
				ret[2] = ret[3] = -(long)(1e18);
				dfs(i);
				ans = Math.min(ans, 2*(ret[3] - ret[1]) + 2*(ret[2] - ret[0]));
			}
		}

		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	static void dfs(int node) {
		if (visited[node]) return;
		visited[node] = true;
		ret[0] = Math.min(ret[0], arr[node].x);
		ret[1] = Math.min(ret[1], arr[node].y);
		ret[2] = Math.max(ret[2], arr[node].x);
		ret[3] = Math.max(ret[3], arr[node].y);
		for (Integer a : g[node]) {
			dfs(a);
		}
	}
	
	static class A {
		int x, y, index;
		A (int a, int b, int i) {
			x=a; y=b; index = i;
		}
	}
}