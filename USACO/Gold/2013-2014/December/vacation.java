
import java.util.*;
import java.io.*;

public class vacation {

	// http://usaco.org/index.php?page=viewproblem2&cpid=362
	
	static ArrayList<ArrayList<Edge>> g = new ArrayList<>();
	static long[][] dist; 	
	static int n, m,k,q;
	static long INF = (long)(1e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("vacation.in"));
		PrintWriter out = new PrintWriter(new FileWriter("vacation.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken())-1;
		q = Integer.parseInt(st.nextToken());
		for (int i=0; i<n; i++) g.add(new ArrayList<>());
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			long three = Integer.parseInt(st.nextToken());
			g.get(one).add(new Edge(two,three));
		}

		HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
		for (int i=0; i<q; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			
			if (map.containsKey(one)) {
				map.get(one).put(two, map.get(one).getOrDefault(two, 0)+1);
			}
			else {
				HashMap<Integer, Integer> c = new HashMap<>();
				c.put(two,1);
				map.put(one, c);
			}
		}
		
		long ans=0;
		int numwork=0;
		for (int i=0; i<n; i++) {
			
			dist = new long[n][2];
			dijkstras(i);
			
			for (Integer two : map.get(i).keySet()) {
				if (dist[two][1] == INF) {
					
				}
				else {
					ans += dist[two][1]*(long)map.get(i).get(two);
					numwork+=map.get(i).get(two);
				}
			}
		}
		
		System.out.println(numwork);
		System.out.println(ans);
		out.println(numwork);
		out.println(ans);
		out.close();

	}
	
	public static void dijkstras(int start) {
		boolean[][] visited = new boolean[n][2];
		for (int i=0; i<n; i++) {
			Arrays.fill(dist[i], INF);			
		}
		if (start<=k) dist[start][1] = 0;
		else dist[start][0] = 0;
		
		while (true) {
			// find the smallest one
			int[] smallest=new int[] {-1,-1};
			long minval=INF;
			for (int j=0; j<n; j++) {
				if (!visited[j][0] && dist[j][0]<minval) {
					minval = dist[j][0];
					smallest = new int[] {j,0};
				}
				if (!visited[j][1] && dist[j][1]<minval) {
					minval = dist[j][1];
					smallest = new int[] {j,1};
				}
			}
			if (smallest[0] == -1) break;
			
			for (Edge a : g.get(smallest[0])) {
				if (smallest[1] == 1) {
					if (!visited[a.destination][1] && dist[a.destination][1] > minval + a.length) {
						dist[a.destination][1] = minval + a.length;
					}
				}
				else {
					if (a.destination<=k) {
						if (!visited[a.destination][1] && dist[a.destination][1] > minval + a.length) {
							dist[a.destination][1] = minval + a.length;
						}
					}
					else {
						if (!visited[a.destination][0] && dist[a.destination][0] > minval + a.length) {
							dist[a.destination][0] = minval + a.length;
						}
					}
				}			
			}
			visited[smallest[0]][smallest[1]] = true;
		}
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
}