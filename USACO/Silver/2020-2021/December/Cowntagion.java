
import java.util.*;
import java.io.*;

public class Cowntagion {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1062
	
	static int n;
	static ArrayList<Integer>[] g;
	static long moves;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Cowntagion"));

		n = Integer.parseInt(in.readLine());
		g = new ArrayList[n];
		for (int i=0; i<n; i++) g[i] = new ArrayList<>();
		
		for (int i=0; i<n-1; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			g[one].add(two);
			g[two].add(one);
		}
		
		moves=0;

		dfs(0, -1);
		
		System.out.println(moves);
	}
	
	public static void dfs(int cur, int p) {
		if (p != -1 && g[cur].size() == 1) {
			return;
		}
		int count=1;
		
		if (p == -1) {
			while (count <= g[cur].size()) {
				count *= 2;
				moves++;
			}
		}
		else {
			while (count <= g[cur].size()-1) {
				count *= 2;
				moves++;
			}
		}
		for (Integer i : g[cur]) {
			if (i != p) {
				dfs(i, cur);
				moves++;
			}
		}
	}
}