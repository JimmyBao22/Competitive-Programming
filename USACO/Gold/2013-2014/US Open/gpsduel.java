
import java.util.*;
import java.io.*;

public class gpsduel {

	// http://usaco.org/index.php?page=viewproblem2&cpid=434
	
	static int n;
	static ArrayList<ArrayList<Edge>> p = new ArrayList<>(), q = new ArrayList<>(), f = new ArrayList<>();
	static long[] distp, distq, distf;
	static int[] parentp, parentq, parentf;
	static long INF = (long)(1e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("gpsduel.in"));
		PrintWriter out = new PrintWriter(new FileWriter("gpsduel.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		distp = new long[n];
		distq = new long[n];
		distf = new long[n];
		parentp = new int[n];
		parentq = new int[n];
		parentf = new int[n];
		Arrays.fill(distp, INF);
		Arrays.fill(distq, INF);
		Arrays.fill(distf, INF);
		for (int i=0; i<n; i++) {
			p.add(new ArrayList<>());
			q.add(new ArrayList<>());
			f.add(new ArrayList<>());
		}
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			long pval = Integer.parseInt(st.nextToken());
			long qval = Integer.parseInt(st.nextToken());
			p.get(b).add(new Edge(a,pval));
			q.get(b).add(new Edge(a,qval));
			f.get(a).add(new Edge(b,2));
		}
		
		dijk_q(n-1);
		dijk_p(n-1);
		
		for (int i=0; i<n-1; i++) {	
				// at each node, see where the gps want to go and subtract from 
					// 2 at those edges
			for (int j=0; j<f.get(i).size(); j++) {	
				if (f.get(i).get(j).dest == parentp[i]) {
					f.get(i).get(j).val--;
				}
				if (f.get(i).get(j).dest == parentq[i]) {
					f.get(i).get(j).val--;
				}
			}
		}
		
		dijk_f(0);
		
		System.out.println(distf[n-1]);
		out.println(distf[n-1]);
		out.close();
		
	}
	
	public static void dijk_f(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[n];
		distf[start] = 0;
		parentf[start] = -1;
		pq.add(new Edge(start, 0));
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			int node = cur.dest;
			if (visited[node]) continue;
			visited[node] = true;
			for (Edge a : f.get(node)) {
				if (!visited[a.dest] && distf[a.dest] > cur.val + a.val) {
					distf[a.dest] = cur.val + a.val;
					parentf[a.dest] = node;
					pq.add(new Edge(a.dest, distf[a.dest]));
				}
			}
		}
	}
	
	public static void dijk_q(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[n];
		distq[start] = 0;
		parentq[start] = -1;
		pq.add(new Edge(start, 0));
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			int node = cur.dest;
			if (visited[node]) continue;
			visited[node] = true;
			for (Edge a : q.get(node)) {
				if (!visited[a.dest] && distq[a.dest] > cur.val + a.val) {
					distq[a.dest] = cur.val + a.val;
					parentq[a.dest] = node;
					pq.add(new Edge(a.dest, distq[a.dest]));
				}
			}
		}
	}
	
	public static void dijk_p(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[n];
		distp[start] = 0;
		parentp[start] = -1;
		pq.add(new Edge(start, 0));
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			int node = cur.dest;
			if (visited[node]) continue;
			visited[node] = true;
			for (Edge a : p.get(node)) {
				if (!visited[a.dest] && distp[a.dest] > cur.val + a.val) {
					distp[a.dest] = cur.val + a.val;
					parentp[a.dest] = node;
					pq.add(new Edge(a.dest, distp[a.dest]));
				}
			}
		}
	}
	
	static class Edge implements Comparable<Edge> {
		int dest;
		long val;
		Edge(int a, long b) {
			dest = a;
			val = b;
		}
		public int compareTo(Edge o) {
			return Long.compare(val, o.val);
		}
	}
}