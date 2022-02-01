
import java.util.*;
import java.io.*;

public class RobotTurtles {

	// https://open.kattis.com/problems/robotturtles
	
	static int n = 8, INF = (int)(1e9), endx, endy;
	static char[][] arr;
	static int[][][] dist;
	static Edge[][][] parent;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ABKnapsack"));

		arr = new char[n][];
		for (int i=0; i<n; i++) {
			arr[i] = in.readLine().toCharArray();
			for (int j=0; j<n; j++) {
				if (arr[i][j] == 'D') {
					endx = i; endy = j;
				}
			}
		}

		dist = new int[n][n][4];
			// x, y, dir
				// 0 = north, 1 = east, 2 = south, 3 = west
		parent = new Edge[n][n][4];
		
		dijkstras();
		
		int min = INF;
		int dir = 0;
		for (int i=0; i<4; i++) {
			if (dist[endx][endy][i] < min) {
				min = dist[endx][endy][i];
				dir = i;
			}
		}
		
		if (min == INF) {
			System.out.println("no solution");
			return;
		}
		
		ArrayList<Character> path = new ArrayList<>();
		while (parent[endx][endy][dir].x != -1) {
			Edge prev = parent[endx][endy][dir];
			
			if (Math.abs(prev.x - endx) + Math.abs(prev.y - endy) == 1) {
				path.add('F');
				if (arr[endx][endy] == 'I') {
					path.add('X');
				}
			}
			else if ((prev.dir+1)%4 == dir) {
				path.add('R');
			}
			else {
				path.add('L');
			}
			
			endx = prev.x;
			endy = prev.y;
			dir = prev.dir;
		}

		StringBuilder s = new StringBuilder();
		for (int i=path.size()-1; i>=0; i--) {
			s.append(path.get(i));
		}
		System.out.print(s);
	}
	
	public static void dijkstras() {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		
		boolean[][][] visited = new boolean[n][n][4];
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				Arrays.fill(dist[i][j], INF);
			}
		}

		parent[n-1][0][1] = new Edge(-1, -1, -1);
		pq.add(new Edge (n-1, 0, 1, 0));
		
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			int x = cur.x, y = cur.y, dir = cur.dir, count = cur.length;
			if (outbounds(x, y)) continue;
			if (visited[x][y][dir]) continue;
			visited[x][y][dir] = true;
			
			// go forwards
			int addx = 0;
			int addy = 0;
			if (dir == 0) addx = -1;
			else if (dir == 1) addy = 1;
			else if (dir == 2) addx = 1;
			else addy = -1;
			
			x += addx; y += addy;
			if (!outbounds(x, y) && !visited[x][y][dir]) {
				if (arr[x][y] == 'I') {
					if (count + 2 < dist[x][y][dir]) {
						dist[x][y][dir] = count+2;
						parent[x][y][dir] = new Edge(x - addx, y - addy, dir);
						pq.add(new Edge (x, y, dir, count+2));
					}
				}
				else {
					if (count + 1 < dist[x][y][dir]) {
						dist[x][y][dir] = count+1;
						parent[x][y][dir] = new Edge(x - addx, y - addy, dir);
						pq.add(new Edge (x, y, dir, count+1));
					}
				}
			}
			x -= addx; y -= addy;
			
			// turn R
			if (!visited[x][y][(dir+1)%4] && count + 1 < dist[x][y][(dir+1)%4]) {
				dist[x][y][(dir+1)%4] = count+1;
				parent[x][y][(dir+1)%4] = new Edge(x, y, dir);
				pq.add(new Edge (x, y, (dir+1)%4, count+1));
			}
			
			// turn L
			if (!visited[x][y][((dir-1)%4+4)%4] && count + 1 < dist[x][y][((dir-1)%4+4)%4]) {
				dist[x][y][((dir-1)%4+4)%4] = count+1;
				parent[x][y][((dir-1)%4+4)%4] = new Edge(x, y, dir);
				pq.add(new Edge (x, y, ((dir-1)%4+4)%4, count+1));
			}
		}
	}
	
	public static boolean outbounds(int x, int y) {
		return x < 0 || y < 0 || x >= n || y >= n || arr[x][y] == 'C';
	}
	
	static class Edge implements Comparable<Edge> {
		int x, y, dir, length;
		Edge (int a, int b, int c, int d) {
			x = a; y = b; dir = c; length = d;
		}
		Edge (int a, int b, int c) {
			x = a; y = b; dir = c;
		}
		public int compareTo(Edge o) {
			return length - o.length;
		}
	}
}