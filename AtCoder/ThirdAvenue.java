
import java.util.*;
import java.io.*;

public class ThirdAvenue {

	// https://atcoder.jp/contests/abc184/tasks/abc184_e
	
	static int n,m;
	static char[][] arr;
	static ArrayList<int[]>[] map;
	static int[][] steps;
	static int INF = (int)(1e9);
	static boolean[] visited = new boolean[26];		// only visit each teleporter once max
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ThirdAvenue"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		arr = new char[n][];
		map = new ArrayList[26];
		int x=0;
		int y=0;
		int fx=0;
		int fy=0;
		steps = new int[n][m];
		for (int i=0; i<26; i++) map[i] = new ArrayList<>();
		for (int i=0; i<n; i++) {
			arr[i] = in.readLine().toCharArray();
			for (int j=0; j<m; j++) {
				if (arr[i][j] != '.' && arr[i][j] != '#' && arr[i][j] != 'S' &&
						arr[i][j] != 'G') {
					map[arr[i][j] - 'a'].add(new int[] {i,j});
				}
				if (arr[i][j] == 'S') {
					x=i;
					y=j;
				}
				if (arr[i][j] == 'G') {
					fx=i; fy = j;
				}
				steps[i][j] = INF;
			}
		}
		
		bfs(x,y);
		
		if (steps[fx][fy] >= INF) {
			System.out.println(-1);
		}
		else {
			System.out.println(steps[fx][fy]);
		}
	}
	
	public static void bfs(int x, int y) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {x,y, 0});
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			x = cur[0]; y = cur[1]; int count = cur[2];
			if (x < 0 || x >= n || y<0 || y>=m) {
				continue;
			}
			if (arr[x][y] == '#') continue;
			if (steps[x][y] <= count) continue;
			steps[x][y] = count;
			if (arr[x][y] == 'G') {
				return;
			}
			
			if (arr[x][y] - 'a' >= 0 && arr[x][y] - 'a' < 26 && !visited[arr[x][y] - 'a']) {
				for (int j=0; j<map[arr[x][y]-'a'].size(); j++) {
					d.add(new int[] {map[arr[x][y]-'a'].get(j)[0], map[arr[x][y]-'a'].get(j)[1], count+1});
				}
				visited[arr[x][y] - 'a'] = true;
			}
			
			d.add(new int[] {x+1, y, count+1});
			d.add(new int[] {x-1, y, count+1});
			d.add(new int[] {x, y+1, count+1});
			d.add(new int[] {x, y-1, count+1});
		}
	}
}