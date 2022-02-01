
import java.util.*;
import java.io.*;

public class MonopoleMagnets {

	// https://codeforces.com/contest/1345/problem/D
	
	static int n;
	static int m;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MonopoleMagnets"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());  
		m = Integer.parseInt(st.nextToken());  
		char[][] arr = new char[n][];
		boolean works = true;
		boolean black_in_every_row = true;
		for (int i = 0; i < n; i++) {
			arr[i] = in.readLine().toCharArray();
			boolean black = false;
			int prev_index_black = -1;
			for (int j = 0; j < m; j++) {
				if (arr[i][j] == '#') {
					if (prev_index_black == -1) prev_index_black = j;
					else {
						if (Math.abs(j - prev_index_black) > 1) {
							works = false;
							break;
						}
						else {
							prev_index_black = j;
						}
					}
					black = true;
					//break;
				}
			}
			if (!black) {
				// no black found
				black_in_every_row = false;
			}
		}
		
		boolean black_in_every_col = true;
		if (works) {
			for (int j = 0; j < m; ++j) {
				boolean black = false;
				int prev_index_black = -1;
				for (int i = 0; i < n; i++) {
					if (arr[i][j] == '#') {
						if (prev_index_black == -1) prev_index_black = i;
						else {
							if (Math.abs(i - prev_index_black) > 1) {
								works = false;
								break;
							}
							else {
								prev_index_black = i;
							}
						}
						black = true;
						//break;
					}
				}
				if (!black) {
					// no black found
					black_in_every_col = false;
				}
			}
		}
		if ((black_in_every_col && !black_in_every_row) || (!black_in_every_col && black_in_every_row)) {
			works = false;
		}
		int count = 0;
		HashSet<Integer> visited = new HashSet<>();
			// each one is {row * 10000 + col}
		if (works) {
			// dfs
			for (int i = 0 ; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (arr[i][j] == '#' && !visited.contains(i * 10000 + j)) {
						count++;
						visited = dfs(i, j, visited, arr);
					}
				}
			}
		}
		
		if (works) {
			System.out.println(count);
		}
		else System.out.println(-1);
	}
	
	public static HashSet<Integer> dfs(int row, int col, HashSet<Integer> visited, char[][] arr) {
		ArrayDeque<int[]> a = new ArrayDeque<>();
		a.add(new int[] {row, col});
		
		while (a.size() > 0) {
			int[] cur = a.poll();
			if (cur[0] >= n || cur[0] < 0 || cur[1] >= m || cur[1] < 0) {
				continue;
				
			}
			if (arr[cur[0]][cur[1]] == '.') continue;
			if (visited.contains(cur[0] * 10000 + cur[1])) {
				continue;
			}
			visited.add(cur[0] * 10000 + cur[1]);
			
			a.add(new int[] {cur[0] + 1, cur[1]});
			a.add(new int[] {cur[0] - 1, cur[1]});
			a.add(new int[] {cur[0], cur[1]+1});
			a.add(new int[] {cur[0], cur[1]-1});
		}
		return visited;
	}	
}
