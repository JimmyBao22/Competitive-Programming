
import java.util.*;
import java.io.*;

public class cowroute {

	// http://usaco.org/index.php?page=viewproblem2&cpid=512
	
	static ArrayList<ArrayList<Edge>> g = new ArrayList<>();
	static D[][] dist;
	static Edge[][] parent;
	static int a,b,n;
	static long INF = (long)(1e18);
	static long[] costs;
	static int MaxN=1005;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cowroute.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cowroute.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		a = Integer.parseInt(st.nextToken())-1;
		b = Integer.parseInt(st.nextToken())-1;
		n = Integer.parseInt(st.nextToken());

		for (int i=0; i<1001; i++) g.add(new ArrayList<>());
		
		costs = new long[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			costs[i] = Long.parseLong(st.nextToken());
			int num = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			for (int j=1; j<num; j++) {
				int two = Integer.parseInt(st.nextToken())-1;
				g.get(one).add(new Edge(two, i, costs[i]));
				one=two;
			}
		}
		
		dist = new D[MaxN][n];
		parent = new Edge[MaxN][n];
		
		dijkstras(a);
				
		long time=INF;
		long count=INF;
		for (int i=0; i<n; i++) time = Math.min(time, dist[b][i].cost);
				
		if (time == INF) {
			System.out.println("-1 -1");
			out.println("-1 -1");
			out.close();
			return;
		}
		
		for (int i=0; i<n; i++) {
			if (dist[b][i].cost == time) {
				count = Math.min(count, dist[b][i].length);
				//count = Math.min(count, Backtrack(b,i)-1);
			}
		}
		
		System.out.println(time + " " + count);
		out.println(time + " " + count);
		out.close();

	}
	
	public static int Backtrack(int dest, int val) {
		int count=0;
		Edge c = new Edge(dest, val);
		while (c.destination != -1) {
			count++;
			c = parent[c.destination][c.type];
		}
		return count;
	}
	
	public static void dijkstras(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[][] visited = new boolean[MaxN][n];
		for (int i=0; i<MaxN; i++) {
			Arrays.fill(dist[i], new D(INF, 0));	
			if (i<n) parent[start][i] = new Edge(-1,-1);
		}
		
		for (Edge i : g.get(start)) {
			pq.add(new Edge(i.destination, i.type, i.cost, 1));
			dist[i.destination][i.type] = new D(i.cost, 1);
			parent[i.destination][i.type] = new Edge(start, i.type);
			dist[start][i.type] = new D(0,0);
			visited[start][i.type] = true;
		}
		
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			int node = cur.destination;
			if (visited[node][cur.type]) continue;
			visited[node][cur.type] = true;
			
			for (Edge i : g.get(node)) {
				if (visited[i.destination][i.type]) continue;
				
				if (i.type == cur.type) {
					if (cur.cost < dist[i.destination][i.type].cost) {
						dist[i.destination][i.type] = new D(cur.cost, cur.length+1);
						parent[i.destination][i.type] = new Edge(node, cur.type);
						pq.add(new Edge(i.destination, i.type, dist[i.destination][i.type].cost, cur.length+1));
					}
					else if (cur.cost == dist[i.destination][i.type].cost &&
							cur.length<dist[i.destination][i.type].length) {
						
						dist[i.destination][i.type] = new D(cur.cost, cur.length+1);
						parent[i.destination][i.type] = new Edge(node, cur.type);
						pq.add(new Edge(i.destination, i.type, dist[i.destination][i.type].cost, cur.length+1));
						
					}
				}
				else {
					if (cur.cost + i.cost < dist[i.destination][i.type].cost) {
						dist[i.destination][i.type] = new D(cur.cost + i.cost, cur.length+1);
						parent[i.destination][i.type] = new Edge(node, cur.type);
						pq.add(new Edge(i.destination, i.type, dist[i.destination][i.type].cost, cur.length+1));
					}
					else if (cur.cost + i.cost == dist[i.destination][i.type].cost && 
							cur.length<dist[i.destination][i.type].length) {
						
						dist[i.destination][i.type] = new D(cur.cost + i.cost, cur.length+1);
						parent[i.destination][i.type] = new Edge(node, cur.type);
						pq.add(new Edge(i.destination, i.type, dist[i.destination][i.type].cost, cur.length+1));
						
					}
				}		
			}
		}
	}
	
	static class D {
		long cost;
		int length;
		D (long a, int b) {
			cost = a; length = b;
		}
	}
	
	static class Edge implements Comparable<Edge> {
		int destination;
		int type;
		long cost;
		int length;
		Edge(int a , int b, long c) {
			destination = a;
			type = b;
			cost=c;
		}
		Edge(int a , int b, long c, int d) {
			destination = a;
			type = b;
			cost=c;
			length = d;
		}
		Edge (int a, int b) {
			destination = a; type = b;
		}
		
		public int compareTo(Edge o) {
			if (cost == o.cost) return length-o.length;
			return Long.compare(cost,o.cost);
		}
		
		void print() {
			System.out.println(destination + " " + type + " " + cost);
		}
	}
}