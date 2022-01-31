
import java.util.*;
import java.io.*;

public class superbull {

	// http://usaco.org/index.php?page=viewproblem2&cpid=531
	
	static int n;
	static long[] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("superbull.in"));
		PrintWriter out = new PrintWriter(new FileWriter("superbull.out"));

		n = Integer.parseInt(in.readLine());
		arr = new long[n];
		for (int i=0; i<n; i++) {
			arr[i] = Long.parseLong(in.readLine());
		}

		long ans = MST(0);
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	public static long MST(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[n];
		pq.add(new Edge(start, 0));
		long minlength = 0;
		
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			if (visited[cur.destination]) continue;
			visited[cur.destination] = true;
			minlength += cur.length;
			
			for (int i=0; i<n; i++) {
				if (!visited[i]) {
					pq.add(new Edge(i, arr[cur.destination]^arr[i]));
				}
			}
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
			return Long.compare(o.length, length);
		}
	}
}