
import java.util.*;
import java.io.*;

public class multimoo {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=836
	
	static int n;
	static int[][] arr;
	static boolean[][] visited;
	static int[] dx = {1,-1,0,0};
	static int[] dy = {0,0,1,-1};
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("multimoo.in"));
		PrintWriter out = new PrintWriter(new FileWriter("multimoo.out"));

		n = Integer.parseInt(in.readLine());
		arr = new int[n][n];
		visited = new boolean[n][n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			for (int j=0; j<n; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		HashMap<Integer, Integer> map = new HashMap<>();
		int maxone=0;
		int maxtwo=0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				if (!visited[i][j]) {
					int ret = bfs(i,j, arr[i][j], -1);
					maxone = Math.max(maxone, ret);
					map.put(arr[i][j], map.getOrDefault(arr[i][j], 0)+ret);
				}
			}
		}
		
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				if (!notvalid(i + 1, j) && arr[i][j] != arr[i+1][j] && map.get(arr[i][j]) + map.get(arr[i+1][j]) > maxtwo) {
					maxtwo = Math.max(maxtwo, bfs2(i, j, arr[i][j], arr[i + 1][j]));
				}
				if (!notvalid(i, j + 1) && arr[i][j] != arr[i][j+1] && map.get(arr[i][j]) + map.get(arr[i][j+1]) > maxtwo) {
					maxtwo = Math.max(maxtwo, bfs2(i, j, arr[i][j], arr[i][j + 1]));
				}
			}
		}
		
		//System.out.println(maxone + "\n" + maxtwo);
		out.println(maxone + "\n" + maxtwo);
		out.close();

	}
		
	public static int bfs2(int x, int y, int one, int two) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {x,y});
		int count=0;
		visited = new boolean[n][n];
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			if (notvalid(cur[0], cur[1])) continue;
			if (arr[cur[0]][cur[1]] != one && arr[cur[0]][cur[1]] != two) continue;
			if (visited[cur[0]][cur[1]]) continue;
			visited[cur[0]][cur[1]] = true;
			count++;
			
			for (int i=0; i<4; i++) {
				d.add(new int[] {cur[0] + dx[i], cur[1] + dy[i]});
			}
		}
		return count;
	}
	
	public static int bfs(int x, int y, int one, int two) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {x,y});
		int count=0;
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			if (notvalid(cur[0], cur[1])) continue;
			if (arr[cur[0]][cur[1]] != one && arr[cur[0]][cur[1]] != two) continue;
			if (visited[cur[0]][cur[1]]) continue;
			visited[cur[0]][cur[1]] = true;
			count++;
			
			for (int i=0; i<4; i++) {
				d.add(new int[] {cur[0] + dx[i], cur[1] + dy[i]});
			}
		}
		return count;
	}
	
	static boolean notvalid(int x, int y) {
		return x < 0 || x >= n || y < 0 || y >= n;
	}
}