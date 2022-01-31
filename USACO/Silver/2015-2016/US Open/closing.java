
import java.util.*;
import java.io.*;

public class closing {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=644
	
	static ArrayList<ArrayList<Integer>> g = new ArrayList<>();
	static TreeSet<Integer> notdeleted;
	static boolean ans;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("closing.in"));
		PrintWriter out = new PrintWriter(new FileWriter("closing.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		notdeleted = new TreeSet<>();
		for (int i=0; i<n; i++) {
			ArrayList<Integer> cur = new ArrayList<>();
			g.add(cur);
			notdeleted.add(i);
		}
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			g.get(a).add(b);
			g.get(b).add(a);
		}
		
		for (int i=0; i<n; i++) {
			HashSet<Integer> visited = new HashSet<>();
			works(notdeleted.first(), visited);
			ans = (visited.size() == notdeleted.size());
			if (ans) {
				System.out.println("YES");
				out.println("YES");
			}
			else {
				System.out.println("NO");
				out.println("NO");
			}
			
			int curdelete = Integer.parseInt(in.readLine())-1;
			notdeleted.remove(curdelete);
		}
		
		out.close();
	}
	
	public static void works(int curpos, HashSet<Integer> visited) {
		if (!notdeleted.contains(curpos)) return;	// doenst contain
		if (visited.contains(curpos)) return;
		visited.add(curpos);
		for (int i=0; i<g.get(curpos).size(); i++) {
			works(g.get(curpos).get(i), visited);
		}
	}
}