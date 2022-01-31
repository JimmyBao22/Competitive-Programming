
import java.util.*;
import java.io.*;

public class closing {

	// http://usaco.org/index.php?page=viewproblem2&cpid=646
	
	static ArrayList<ArrayList<Integer>> g = new ArrayList<>();
	static boolean[] open;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("closing.in"));
		PrintWriter out = new PrintWriter(new FileWriter("closing.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		for (int i=0; i<n; i++) {
			g.add(new ArrayList<>());
		}
		open=new boolean[n];
		int count=0;
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			g.get(one).add(two);
			g.get(two).add(one);
		}
		
		int[] away = new int[n];
		for (int i=0; i<n; i++) {
			away[i] = Integer.parseInt(in.readLine())-1;
		}
		
		DSU s = new DSU(n);
		boolean[] ans = new boolean[n];
		for (int i=n-1; i>=0; i--) {
			int cur = away[i]; 		//add this node in
			count++;
			open[cur] = true;
			for (int j=0; j<g.get(cur).size(); j++) {
				if (open[g.get(cur).get(j)]) s.merge(cur, g.get(cur).get(j));
			}
			if (s.size[s.find(cur)] == count) {
				ans[i] = true;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<n; i++) {
			if (ans[i]) {
				sb.append("YES\n");
			}
			else {
				sb.append("NO\n");
			}
		}

		System.out.print(sb);
		out.print(sb);
		out.close();

	}
	
	static class DSU {
		int n;
		int[] parent;
		int[] size;
		DSU (int a) {
			n=a;
			parent =new int[n];
			size=new int[n];
			for (int i=0; i<n; i++) {
				parent[i] = i;
				size[i]= 1;
			}
		}
		
		public int find(int a) {
			if (a == parent[a]) return a;
			return parent[a] = find(parent[a]);
		}
		
		public void merge(int a, int b) {
			a = find(a);
			b = find(b);
			if (a==b) return;
			if (size[a]<size[b]) {
				parent[a] = b;
				size[b] += size[a];
			}
			else {
				parent[b] = a;
				size[a] += size[b];
			}
		}
	}
}