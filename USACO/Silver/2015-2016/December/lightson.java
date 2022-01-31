
import java.util.*;
import java.io.*;

public class lightson {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=570
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("lightson.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		boolean[][] lights = new boolean[n][n];
		lights[0][0] = true;
		HashMap<Integer, HashMap<Integer, ArrayList<int[]>>> switches = new HashMap<>();
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			if (switches.containsKey(x)) {
				if (switches.get(x).containsKey(y)) {
					switches.get(x).get(y).add(new int[] {a,b});
				}
				else {
					ArrayList<int[]> cur = new ArrayList<>();
					cur.add(new int[] {a,b});
					switches.get(x).put(y, cur);
				}
			}
			else {
				HashMap<Integer, ArrayList<int[]>> cur = new HashMap<>();
				ArrayList<int[]> cur2 = new ArrayList<>();
				cur2.add(new int[] {a,b});
				cur.put(y, cur2);
				switches.put(x, cur);
			}
		}

		boolean didsomething=true;
		while (didsomething) {
			didsomething=dfs(lights, switches);
		}
		
		int result = 0;
		
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				if (lights[i][j]) result++;
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("lightson.out"));
		System.out.println(result);
		out.println(result);
		out.close();

	}
	
	public static boolean dfs(boolean[][] lights, HashMap<Integer, HashMap<Integer, ArrayList<int[]>>> switches) {
		Deque<int[]> pos = new ArrayDeque<>();
		pos.add(new int[] {0,0});
		boolean[][] visited = new boolean[lights.length][lights.length];
		int n = lights.length;
		boolean did_something=false;
		while (pos.size() > 0) {
			int[] cur = pos.poll();
			if (cur[0] < 0 || cur[0]  >= n || cur[1] < 0 || cur[1] >= n) continue;
			if (!lights[cur[0]][cur[1]]) continue; // lights not on
			if (visited[cur[0]][cur[1]]) continue;
			visited[cur[0]][cur[1]] = true;
			
			// turn on all the switches, if there are any
			if (switches.containsKey(cur[0])) {
				if (switches.get(cur[0]).containsKey(cur[1])) {
					ArrayList<int[]> now = switches.get(cur[0]).get(cur[1]);
					for (int i=0; i<now.size(); i++) {
						if (!lights[now.get(i)[0]][now.get(i)[1]]) {
							lights[now.get(i)[0]][now.get(i)[1]] = true; // turn on
							did_something=true;
						}
					}
				}
			}
			
			pos.add(new int[] {cur[0]+1, cur[1]});
			pos.add(new int[] {cur[0]-1, cur[1]});
			pos.add(new int[] {cur[0], cur[1]+1});
			pos.add(new int[] {cur[0], cur[1]-1});
		}
		
		return did_something;
	}
}