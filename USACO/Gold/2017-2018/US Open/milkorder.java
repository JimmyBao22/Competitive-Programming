
import java.util.*;
import java.io.*;

public class milkorder {

	// http://usaco.org/index.php?page=viewproblem2&cpid=838
	
	static ArrayList<ArrayList<Integer>> rules = new ArrayList<>();
	static ArrayList<ArrayList<Integer>> g;
	static int n;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("milkorder.in"));
		PrintWriter out = new PrintWriter(new FileWriter("milkorder.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		for (int i=0; i<m; i++) {
			ArrayList<Integer> cur = new ArrayList<>();
			st = new StringTokenizer(in.readLine());
			int num = Integer.parseInt(st.nextToken());
			while (num-->0) {
				cur.add(Integer.parseInt(st.nextToken())-1);
			}
			rules.add(cur);
		}
		
		int min=0;
		int max=m;
		while (min < max) {
			int middle = (min+max+1)/2;
			if (check(middle)) {
				min = middle;
			}
			else max = middle-1;
		}
		
		g = new ArrayList<>();
		ArrayList<ArrayList<Integer>> reverse = new ArrayList<>();
		for (int i=0; i<n; i++) {
			g.add(new ArrayList<>());
			reverse.add(new ArrayList<>());
		}
		boolean[] visited = new boolean[n];
		for (int i=0; i<min; i++) {
			for (int j=0; j<rules.get(i).size()-1; j++) {
				g.get(rules.get(i).get(j)).add(rules.get(i).get(j+1));
				reverse.get(rules.get(i).get(j+1)).add(rules.get(i).get(j));
			}
		}
		
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		
		for (int i=0; i<reverse.size(); i++) {
			if (reverse.get(i).size() == 0) pq.add(i);	// nothing comes before i
		}
		
		ArrayList<Integer> ans = new ArrayList<>();
		
		while (!pq.isEmpty()) {
			int cur = pq.poll();
			if (visited[cur]) continue;
			visited[cur]=true;
			ans.add(cur);
			o: for (Integer a : g.get(cur)) {
				if (!visited[a]) {
					for (Integer b : reverse.get(a)) {		
						// if any predecessors have not been visited, 
						// don't add this to pq
						if (!visited[b]) continue o;
					}
						// if all predecessors have been visited, add to pq
					pq.add(a);
				}
			}
		}
		
		StringBuilder s = new StringBuilder();

		for (int i=0; i<ans.size(); i++) {
			s.append(ans.get(i)+1 + " ");
		}
		
		s.deleteCharAt(s.length()-1);		// remove last space
		System.out.println(s);
		out.println(s);
		out.close();
	}
	
	static boolean works;
	
	public static boolean check(int num) {
		// check if satisfy first num rules
		// create a DAG from first num rules
		g = new ArrayList<>();
		for (int i=0; i<n; i++) g.add(new ArrayList<>());
		for (int i=0; i<num; i++) {
			for (int j=0; j<rules.get(i).size()-1; j++) {
				g.get(rules.get(i).get(j)).add(rules.get(i).get(j+1));
			}
		}
		// if there is a cycle, it is impossible --> return false
		// else true
		boolean[] open = new boolean[n];
		boolean[] visited = new boolean[n];
		works=true;
		for (int i=0; i<n; i++) {
			if (!visited[i]) {
				cycle(i, open, visited);
			}
		}
		return works;
	}
	
	public static void cycle (int node, boolean[] open, boolean[] visited) {
		if (open[node]) {
			works=false;	// cycle found
			return;
		}
		if (visited[node]) {
			return;
		}
		open[node] = true;
		visited[node] = true;
		for (Integer a : g.get(node)) {
			cycle(a, open, visited);
		}
		open[node] = false;
	}
}