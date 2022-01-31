
import java.util.*;
import java.io.*;

public class ccski {

	// http://usaco.org/index.php?page=viewproblem2&cpid=380
	
	static int n,m;
	static int[][] arr;
	static ArrayList<int[]> imp = new ArrayList<>();
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("ccski.in"));
		PrintWriter out = new PrintWriter(new FileWriter("ccski.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		arr = new int[n][m];
		for (int i=0; i<n; ++i) {
			st = new StringTokenizer(in.readLine());
			for (int j=0; j<m; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for (int i=0; i<n; ++i) {
			st = new StringTokenizer(in.readLine());
			for (int j=0; j<m; j++) {
				int cur = Integer.parseInt(st.nextToken());
				if (cur==1) {
					imp.add(new int[] {i,j});
				}
			}
		}
		
		if (imp.size()==0) {
			out.println(0);
			out.close();
			return;
		}
		
		int min=0;
		int max = (int)(1e9+2);
		while (min<max) {
			int middle = (min+max)/2;
			if (check(middle)) {
				max = middle;
			}
			else min = middle+1;
		}

		System.out.println(min);
		out.println(min);
		out.close();

	}
	
	public static boolean check(int mid) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		boolean[][] visited = new boolean[n][m];
		d.add(imp.get(0));
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			int x = cur[0]; int y = cur[1];
			if (visited[x][y]) continue;
			visited[x][y] = true;
			
			if (x-1>=0 && !visited[x-1][y] && Math.abs(arr[x-1][y] - arr[x][y]) <= mid) {
				d.add(new int[] {x-1,y});
			}
			if (x+1<n && !visited[x+1][y] && Math.abs(arr[x+1][y] - arr[x][y]) <= mid) {
				d.add(new int[] {x+1,y});
			}
			if (y-1>=0 && !visited[x][y-1] && Math.abs(arr[x][y-1] - arr[x][y]) <= mid) {
				d.add(new int[] {x,y-1});
			}
			if (y+1<m && !visited[x][y+1] && Math.abs(arr[x][y+1] - arr[x][y]) <= mid) {
				d.add(new int[] {x,y+1});
			}
		}
		for (int i=0; i<imp.size(); ++i) {
			if (!visited[imp.get(i)[0]][imp.get(i)[1]]) return false;
		}
		return true;
	}
}