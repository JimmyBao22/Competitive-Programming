
import java.util.*;
import java.io.*;

public class clocktree {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1016
	
	static int n;
	static ArrayList<Integer>[] g;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("clocktree.in"));
		PrintWriter out = new PrintWriter(new FileWriter("clocktree.out"));

		n = Integer.parseInt(in.readLine());
		int[] clock = new int[n];
		g = new ArrayList[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			clock[i] = Integer.parseInt(st.nextToken())%12;
			g[i] = new ArrayList<>();
		}
		
		for (int i=0; i<n-1; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			g[one].add(two);
			g[two].add(one);
		}
		
		int ans=0;
		
		for (int i=0; i<n; i++) {
			int[] clockcopy = new int[n];
			for (int j=0; j<n; j++) clockcopy[j] = clock[j];
			dfs(i, -1, clockcopy);
			
			if (clockcopy[i]%12 == 0 || clockcopy[i] == 1) ans++;
		}

		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static void dfs(int node, int parent, int[] clock) {
		for (int i=0; i<g[node].size(); i++) {
			if (g[node].get(i) != parent) {
				
				add(g[node].get(i), 1, clock);		// go to node g[node].get(i)
				
				dfs(g[node].get(i), node, clock);
				
				add(node, 1, clock);		// come back to this clock
				
				
				// fix node i's clock, currently i am on node's clock
				int needed = 12 - clock[g[node].get(i)];
				add(g[node].get(i), needed, clock);
				add(node, needed, clock);
			}
		}
	}
	
	public static void add(int i, int val, int[] clock) {
		clock[i] += val; if (clock[i] < 0) clock[i] += 12; if (clock[i] >= 12) clock[i] -= 12;
	}
}