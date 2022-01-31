
import java.util.*;
import java.io.*;

public class palpath {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=548
	
	static int n;
	static char[][] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("palpath.in"));
		PrintWriter out = new PrintWriter(new FileWriter("palpath.out"));

		n = Integer.parseInt(in.readLine());
		arr = new char[n][];
		for (int i=0; i<n; i++) arr[i] = in.readLine().toCharArray();
		
		long ans = bfs();
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static long bfs() {
		ArrayDeque<A> d = new ArrayDeque<>();
		d.add(new A(0,0,arr[0][0] + ""));
		HashSet<String>[][] set = new HashSet[n][n];
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) set[i][j] = new HashSet<>();
		}
		while (!d.isEmpty()) {
			A cur = d.poll();
			
			if (cur.x + cur.y >= n) {
				if (arr[cur.x][cur.y] != cur.s.charAt(2*n - 2 - (cur.x + cur.y))) continue;
			}
			if (set[cur.x][cur.y].contains(cur.s)) continue;
			set[cur.x][cur.y].add(cur.s);
			
				// only have to add to string when length < n, because other half
					// needs to be same. If always add, will TLE
			if (!notvalid(cur.x+1, cur.y)) {
				if (cur.x + cur.y + 1 < n) d.add(new A(cur.x+1, cur.y, cur.s + arr[cur.x+1][cur.y]));
				else d.add(new A(cur.x+1, cur.y, cur.s));
			}
			if (!notvalid(cur.x, cur.y+1)) {
				if (cur.x + cur.y + 1 < n) d.add(new A(cur.x, cur.y+1, cur.s + arr[cur.x][cur.y+1]));
				else d.add(new A(cur.x, cur.y+1, cur.s));
			}
			
		}
		return set[n-1][n-1].size();
	}
	
	static boolean notvalid(int x, int y) {
		return x < 0 || x >= n || y < 0 || y >= n;
	}
	
	static class A {
		int x; int y; String s;
		A (int a, int b, String s) {
			x=a; y=b; this.s = s;
		}
	}
}