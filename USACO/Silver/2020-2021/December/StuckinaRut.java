
import java.util.*;
import java.io.*;

public class StuckinaRut {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1064
	
	static ArrayList<Integer>[] g;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("StuckinaRut"));

		int n = Integer.parseInt(in.readLine());
		A[] arr = new A[n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			char c = st.nextToken().charAt(0);
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken());
			arr[i] = new A(c,one,two);
		}
		
		ArrayList<Edge> all = new ArrayList<>();
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				// find stuff that stops cow i
				if (j == i) continue;
				if (arr[i].c != arr[j].c) {
					
					if (arr[i].c == 'N') {
						if (arr[j].x < arr[i].x && arr[j].y > arr[i].y) {
							
							// time
							int timefori = arr[j].y - arr[i].y;
							int timeforj = arr[i].x - arr[j].x;

							if (timefori > timeforj && timefori < arr[i].stoptime) {
								// intersects
								all.add(new Edge(i, j, timefori, arr[i].x, arr[j].y));	
							}
						}
					}
					else {
						if (arr[j].x > arr[i].x && arr[j].y < arr[i].y) {
							
							int timefori = arr[j].x - arr[i].x;
							int timeforj = arr[i].y - arr[j].y;
							
							if (timefori > timeforj && timefori < arr[i].stoptime) {
								// intersects
								all.add(new Edge(i, j, timefori, arr[j].x, arr[i].y));
							}
						}
					}
				}
			}
		}
		
		Collections.sort(all);
		
		for (int i=0; i<all.size(); i++) {
			Edge cur = all.get(i);
			if (arr[cur.one].stopindex != -1) continue;
			
			if (arr[cur.two].stopx < cur.x || arr[cur.two].stopy < cur.y) {
				continue;
			}
			
			// works
			arr[cur.one].stopx = cur.x;
			arr[cur.one].stopy = cur.y;
			arr[cur.one].stopindex = cur.two;
			arr[cur.one].stoptime = cur.time;
			
		}
		
		g = new ArrayList[n];
		for (int i=0; i<n; i++) g[i] = new ArrayList<>();
		
		for (int i=0; i<n; i++) {
			if (arr[i].stopindex != -1) {
				g[i].add(arr[i].stopindex);
			}
		}
		
		int[] ans = new int[n];
		boolean[] visited = new boolean[n];
		
		for (int i=0; i<n; i++) {
			dfs(ans, visited, i, 0);
		}
		
		for (int i=0; i<n; i++) {
			System.out.println(ans[i]);
		}
	}
	
	public static void dfs(int[] ans, boolean[] visited, int cur, int depth) {
		if (visited[cur]) return;
		if (depth > 0) ans[cur]++;
		for (Integer i : g[cur]) {
			dfs(ans ,visited, i, depth+1);
		}
	}
	
	static class Edge implements Comparable<Edge> {
		int one, two, time;
		int x, y;
		public Edge (int a, int b, int c, int d, int e) {
			one= a; two = b; time = c;
			x = d; y = e;
		}
		public int compareTo (Edge o) {
			return time - o.time;
		}
	}
	
	static class A {
		char c; int x,y;
		int stopx, stopy;
		int stopindex; int stoptime;
		A (char a, int b, int d) {
			c = a; x = b; y = d;
			stopindex = -1;
			stoptime = stopx = stopy = Integer.MAX_VALUE;
		}
	}
}