
import java.util.*;
import java.io.*;

public class irrigation {

	// http://usaco.org/index.php?page=viewproblem2&cpid=415
	
	static int n;
	static long c;
	static long[][] points;
	static long INF = (long)(1e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("irrigation.in"));
		PrintWriter out = new PrintWriter(new FileWriter("irrigation.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		points = new long[n][2];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			points[i][0] = Integer.parseInt(st.nextToken());
			points[i][1] = Integer.parseInt(st.nextToken());
		}
		
		long ans = MST(0);
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	public static long MST(int start) {
		boolean[] visited = new boolean[n];
		long[] dist = new long[n];
		Arrays.fill(dist, INF);
		dist[start] = 0;
		long minlength = 0;
		
		while (true) {
			// find the smallest one
			int smallest=-1;
			long minval=INF;
			for (int j=0; j<n; j++) {
				if (!visited[j] && dist[j]<minval) {
					minval = dist[j];
					smallest = j;
				}
			}
			if (smallest == -1) break;
			minlength += minval;
			
			for (int i=0; i<n; ++i) {
				if (visited[i]) continue;
				long d = dist(points[smallest], points[i]);
				if (d < c || dist[i] < d) continue;
				dist[i] = d;
			}
			visited[smallest] = true;
		}
		for (int i=0; i<n; i++) {
			if (!visited[i]) return -1;
		}
		return minlength;
	}
	
	static class Edge implements Comparable<Edge> {
		int destination;
		long length;
		Edge(int a , long b) {
			destination = a;
			length = b;
		}
		
		public int compareTo(Edge o) {
			return Long.compare(length, o.length);
		}
	}
	
	static long dist(long[] a, long[] b) {
		return (a[0] - b[0])*(a[0] - b[0]) + (a[1] - b[1])*(a[1] - b[1]);
	}
}