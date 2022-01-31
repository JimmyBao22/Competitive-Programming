
import java.util.*;
import java.io.*;

public class rblock {

	// http://usaco.org/index.php?page=viewproblem2&cpid=398
	
	static ArrayList<ArrayList<Edge>> g = new ArrayList<>();
	static int n;
	static ArrayList<long[]> dist = new ArrayList<>();
	static int[] parent;
	static long INF = (long)(1e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("rblock.in"));
		PrintWriter out = new PrintWriter(new FileWriter("rblock.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		parent = new int[n];
		for (int i=0; i<n; i++) g.add(new ArrayList<>());
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			long c = Long.parseLong(st.nextToken());
			g.get(a).add(new Edge(b,c));
			g.get(b).add(new Edge(a,c));
		}
		
		dijkstra(0);
		long orig = dist.get(0)[n-1];
		long maxdiff=0;
		ArrayList<Integer> path = backtrack(n-1);
		for (int i=1; i<path.size(); i++) {
			int curnode = path.get(i);
			int d = path.get(i-1);
			int index=0;
			for (int j=0; j<g.get(curnode).size(); j++) {
				if (g.get(curnode).get(j).dest == d) {
					g.get(curnode).get(j).val *= 2;
					index=j;
					break;
				}
			}
			dijkstra(0);
			long a = dist.get(i)[n-1];
			maxdiff = Math.max(maxdiff, a-orig);
			g.get(curnode).get(index).val /= 2;
		}
		
		System.out.println(maxdiff);
		out.println(maxdiff);
		out.close();

	}
	
	public static ArrayList<Integer> backtrack(int end) {
		ArrayList<Integer> path = new ArrayList<>();
		while (end!=-1) {
			path.add(end);
			end = parent[end];
		}
		return path;
	}
	
	public static void dijkstra(int start) {
		boolean[] visited = new boolean[n];
		parent[start] = -1;
		long[] d = new long[n];
		Arrays.fill(d, INF);
		d[start] = 0;
		for (int i=0; i<n; i++) {
			long smallestval=INF;
			int smallestindex=0;
			for (int j=0; j<n; j++) {
				if (!visited[j] && d[j] < smallestval) {
					smallestval = d[j];
					smallestindex = j;
				}
			}
			
			for (Edge a : g.get(smallestindex)) {
				if (!visited[a.dest] && d[a.dest] > smallestval + a.val) {
					d[a.dest] = smallestval + a.val;
					parent[a.dest] = smallestindex;
				}
			}
			visited[smallestindex] = true;
		}
		dist.add(d);
	}
	
	static class Edge {
		int dest;
		long val;
		Edge (int a, long b) {
			dest = a; val  =b;
		}
	}
}