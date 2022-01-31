
import java.util.*;
import java.io.*;

public class lasers {

	// http://usaco.org/index.php?page=viewproblem2&cpid=671
	
	static int n;
	static int[] start;
	static int[] end;
	static HashMap<Integer, HashMap<Integer, Integer>> x, y;
	static int INF=(int)(1e9);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("lasers.in"));
		PrintWriter out = new PrintWriter(new FileWriter("lasers.out"));

		start = new int[2];
		end = new int[2];
		x = new HashMap<>();	// x = y = num of transitions
		y = new HashMap<>();	// y = x = num of transitions
		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		start[0] = Integer.parseInt(st.nextToken());
		start[1] = Integer.parseInt(st.nextToken());
		end[0] = Integer.parseInt(st.nextToken());
		end[1] = Integer.parseInt(st.nextToken());
		
		HashMap<Integer, Integer> c = new HashMap<Integer, Integer>();
		c.put(start[1], INF);
		x.put(start[0], c);
		c = new HashMap<Integer, Integer>();
		c.put(start[0], INF);
		y.put(start[1], c);
		c = new HashMap<Integer, Integer>();
		c.put(end[1], INF);
		x.put(end[0], c);
		c = new HashMap<Integer, Integer>();
		c.put(end[0], INF);
		y.put(end[1], c);
		
		
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken());
			if (x.containsKey(one)) {
				x.get(one).put(two, INF);
			}
			else {
				c = new HashMap<Integer, Integer>();
				c.put(two, INF);
				x.put(one, c);
			}
			if (y.containsKey(two)) {
				y.get(two).put(one, INF);
			}
			else {
				c = new HashMap<Integer, Integer>();
				c.put(one, INF);
				y.put(two, c);
			}
		}
		
		bfs();
		int ans = x.get(end[0]).get(end[1]);
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static void bfs() {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		// 0 = north, 1 = south, 2 = east, 3 = west
		d.add(new int[] {start[0], start[1], 0, 0});
		d.add(new int[] {start[0], start[1], 0, 1});
		d.add(new int[] {start[0], start[1], 0, 2});
		d.add(new int[] {start[0], start[1], 0, 3});
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			int xpoint = cur[0]; int ypoint = cur[1]; int val = cur[2]; int dir = cur[3];
			if (x.get(xpoint).get(ypoint) <= val) {
				continue;
			}
			x.get(xpoint).put(ypoint, val);
			y.get(ypoint).put(xpoint, val);
			
			// move
			if (end[0] == xpoint)  { // same x
				if (end[1] > ypoint && dir == 0) {
					x.get(end[0]).put(end[1], Math.min(x.get(end[0]).get(end[1]), val));
					y.get(end[1]).put(end[0], Math.min(x.get(end[0]).get(end[1]), val));
					continue;
				}
				else if (end[1] < ypoint && dir == 1) {
					x.get(end[0]).put(end[1], Math.min(x.get(end[0]).get(end[1]), val));
					y.get(end[1]).put(end[0], Math.min(x.get(end[0]).get(end[1]), val));
					continue;
				}
			}
			if (end[1] == ypoint) {
				if (end[0] > xpoint && dir == 2) {
					x.get(end[0]).put(end[1], Math.min(x.get(end[0]).get(end[1]), val));
					y.get(end[1]).put(end[0], Math.min(x.get(end[0]).get(end[1]), val));
					continue;
				}
				else if (end[0] < xpoint && dir == 3) {
					x.get(end[0]).put(end[1], Math.min(x.get(end[0]).get(end[1]), val));
					y.get(end[1]).put(end[0], Math.min(x.get(end[0]).get(end[1]), val));
					continue;
				}
			}
			
			// move to new vertex
			if ((dir == 0 || dir == 1) && x.get(xpoint).size()>0) {
				for (Integer a : x.get(xpoint).keySet()) {
					if (x.get(xpoint).get(a)<=val) continue;
					if (dir == 0 && a>ypoint) {
						d.add(new int[] {xpoint, a, val+1, 2});
						d.add(new int[] {xpoint, a, val+1, 3});
					}
					else if (dir == 1 && a<ypoint) {
						d.add(new int[] {xpoint, a, val+1, 2});
						d.add(new int[] {xpoint, a, val+1, 3});
					}
				}
			}
			else if ((dir == 2 || dir == 3) && y.get(ypoint).size()>0) {
				for (Integer a : y.get(ypoint).keySet()) {
					if (y.get(ypoint).get(a)<=val) continue;
					if (dir == 2 && a>xpoint) {
						d.add(new int[] {a, ypoint, val+1, 0});
						d.add(new int[] {a, ypoint, val+1, 1});
					}
					else if (dir == 3 && a<xpoint) {
						d.add(new int[] {a, ypoint, val+1, 0});
						d.add(new int[] {a, ypoint, val+1, 1});
					}
				}
			}
		}
	}
}