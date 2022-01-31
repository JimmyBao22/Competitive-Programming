
import java.util.*;
import java.io.*;

public class StableWall {

	// https://codingcompetitions.withgoogle.com/kickstart/round/000000000019ff43/00000000003379bb
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("StableWall"));

		int t = Integer.parseInt(in.readLine());
		for (int j = 1; j < t + 1; j++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int r = Integer.parseInt(st.nextToken()); 
			int c = Integer.parseInt(st.nextToken());  
			char[][] arr = new char[r+1][];
			for (int i=0; i<r; i++) {
				arr[i] = in.readLine().toCharArray();
			}
			arr[r] = new char[c];
			for (int i=0; i<c; i++) {
				arr[r][i] = ' ';
			}
			boolean[][] check = new boolean[r+1][c];
			for (int i=0; i<c; i++) {
				// last row fill w/ true
				check[r][i] = true;
			}
			
			String f = "";
			boolean works = true;
			int num_passed = 0;
			while (true) {
				boolean did_something = false;
				for (int i=1; i<r+1; i++) {
					for (int k=0; k<c; k++) {
						if (check[i][k] && !check[i-1][k]) {
							if (dfs(i-1, k, arr, check)) {
								num_passed += filldfs(i-1, k, arr, check);
								did_something = true;
								f+= arr[i-1][k];
							}
						}
					}
				}
				if (!did_something) {
					works = false;
					break;
				}
				if (num_passed == r*c) {
					break;
				}
			}
			
			if (works) {
				System.out.println("Case #" + j + ": " + f);
			}
			else {
				System.out.println("Case #" + j + ": -1");
			}
		}
	}
	
	public static boolean dfs(int row, int col, char[][] array, boolean[][] check) {
		boolean works = true;
		boolean[][] visited = new boolean[array.length][array[0].length];
		char good = array[row][col];
		
		ArrayDeque<int[]> a = new ArrayDeque<>();
		a.add(new int[] {row, col});
		while (a.size()>0) {
			int[] cur = a.poll();
			int x = cur[0];
			int y = cur[1];
			
			if (x >= array.length-1 || x < 0 || y >= array[0].length || y <0) continue;
			if (array[x][y] != good) continue;
			if (visited[x][y]) continue;
			visited[x][y] = true;
			
			if (array[x+1][y] != good && !check[x+1][y]) {
				works = false;
				break;
			}
			
			a.add(new int[] {x+1, y});
			a.add(new int[] {x-1, y});
			a.add(new int[] {x, y+1});
			a.add(new int[] {x, y-1});
		}
		return works;
	}
	public static int filldfs(int row, int col, char[][] array, boolean[][] check) {
		boolean[][] visited = new boolean[array.length][array[0].length];
		char good = array[row][col];
		int num = 0;
		
		ArrayDeque<int[]> a = new ArrayDeque<>();
		a.add(new int[] {row, col});
		while (a.size()>0) {
			int[] cur = a.poll();
			int x = cur[0];
			int y = cur[1];
			
			if (x >= array.length-1 || x < 0 || y >= array[0].length || y <0) continue;
			if (array[x][y] != good) continue;
			if (visited[x][y]) continue;
			visited[x][y] = true;
			check[x][y] = true;
			num++;
			a.add(new int[] {x+1, y});
			a.add(new int[] {x-1, y});
			a.add(new int[] {x, y+1});
			a.add(new int[] {x, y-1});
		}
		return num;
	}
}