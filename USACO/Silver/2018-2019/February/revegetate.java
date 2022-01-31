
import java.util.*;
import java.io.*;

public class revegetate {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=920
	
	static ArrayList<ArrayList<Edge>> g = new ArrayList<>();
	static int count;
	static boolean[] visited;
	static int n,m;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("revegetate.in"));
		PrintWriter out = new PrintWriter(new FileWriter("revegetate.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		count=0;
		for (int i=0; i<n; i++) {
			g.add(new ArrayList<>());
		}
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			char c = st.nextToken().charAt(0);
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			g.get(a).add(new Edge(c,a,b));
			g.get(b).add(new Edge(c,b,a));
		}
		
		boolean check = check();
		if (!check) {					// invalid
			out.println(0);
			out.close();
			return;
		}
		
		visited = new boolean[n];
		for (int i=0; i<n; i++) {
			if (!visited[i]) {
				count++;
				dfs(i);
			}
		}
		
		//System.out.println(count);
		StringBuilder s = new StringBuilder();
		s.append(1);
		for (int i=0; i<count; i++) {
			s.append(0);
		}
		//System.out.println(s);
		out.println(s);
		out.close();
	}
	
	public static void dfs(int curnode) {
		if (visited[curnode]) return;
		visited[curnode] = true;
		for (Edge i : g.get(curnode)) {
			dfs(i.to);
		}
	}
	
	static boolean works;
	public static boolean check() {
		works = true;
		HashSet<Integer> c1 = new HashSet<>();
		HashSet<Integer> c2 = new HashSet<>();
		boolean[] visited2 = new boolean[n];
		for (int i=0; i<n; i++) {
			if (!visited2[i]) {
				c1.add(i);
				dfs2(i, 0, c1, c2, visited2);
			}
		}
	
		return works;
	}
	
	public static void dfs2(int curnode, int type, HashSet<Integer> c1, HashSet<Integer> c2, boolean[] visited2) {
		if (visited2[curnode]) return;
		visited2[curnode] = true;
		for (Edge i : g.get(curnode)) {
			if (i.type == 'S') {
				if (type == 0) {
					if (c2.contains(i.to)) {
						works=false;
						return;
					}
					c1.add(i.to);
				}
				else {
					if (c1.contains(i.to)) {
						works=false;
						return;
					}
					c2.add(i.to);
				}
				
				dfs2(i.to, type, c1, c2, visited2);
			}
			else {
				if (type == 0) {
					if (c1.contains(i.to)) {
						works=false;
						return;
					}
					c2.add(i.to);
				}
				else {
					if (c2.contains(i.to)) {
						works=false;
						return;
					}
					c1.add(i.to);
				}
				
				dfs2(i.to, type^1, c1, c2, visited2);
			}
		}
	}
	
	static class Edge {
		int from, to;
		char type;
		Edge (char a, int b, int c) {
			type = a; from = b; to = c;
		}
	}
}