
import java.util.*;
import java.io.*;

public class dirtraverse {

	// http://usaco.org/index.php?page=viewproblem2&cpid=814
	
	static ArrayList<ArrayList<Integer>> g = new ArrayList<>();
	static String[] arr;
	static long[] leaves;
	static long min;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("dirtraverse.in"));
		PrintWriter out = new PrintWriter(new FileWriter("dirtraverse.out"));

		int n = Integer.parseInt(in.readLine());
		arr = new String[n];
		leaves = new long[n];
		for (int i=0; i<n; i++) g.add(new ArrayList<>());
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i] = st.nextToken();
			int k = Integer.parseInt(st.nextToken());
			for (int j=0; j<k; j++) {
				int a = Integer.parseInt(st.nextToken())-1;
				g.get(i).add(a);
				g.get(a).add(i);
			}
		}
		
		find_leaves(0,-1);
		long c = 0;
		for (int i=0; i<g.get(0).size(); i++) {
			c += dfs1(g.get(0).get(i), 0);
		}
		min = c;
		
		//print();
		for (int i=0; i<g.get(0).size(); i++) {
			dfs2(g.get(0).get(i), 0, c);
		}
		
		System.out.println(min);
		out.println(min);
		out.close();
	}
	
	public static void dfs2(int node, int parent, long curval) {
		if (parent != -1 && g.get(node).size() == 1) return;
		curval = curval + (leaves[0]-leaves[node])*(3) - leaves[node]*((long)arr[node].length()+1);
		min = Math.min(min, curval);
		for (int i=0; i<g.get(node).size(); i++) {
			if (g.get(node).get(i) == parent) continue;
			dfs2(g.get(node).get(i), node, curval);
		}
	}
	
	public static long dfs1(int node, int parent) {
		if (parent != -1 && g.get(node).size() == 1) {
			return arr[node].length();
		}
		long sum=0;
		for (int i=0; i<g.get(node).size(); ++i) {
			if (g.get(node).get(i) == parent) continue;
			sum += dfs1(g.get(node).get(i), node);
		}
		return sum + leaves[node]*(arr[node].length()+1);
	}
	
	public static long find_leaves(int node, int parent) {
		if (parent != -1 && g.get(node).size() == 1) return 1;
		long c=0;
		for (int i=0; i<g.get(node).size(); ++i) {
			if (g.get(node).get(i) == parent) continue;
			c += find_leaves(g.get(node).get(i), node);
		}
		return leaves[node] = c;
	}
	
	public static void print() {
		for (int i=0; i<arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
		for (int i=0; i<leaves.length; i++) {
			System.out.print(leaves[i] + " ");
		}
		System.out.println();
	}
}

/*
		start in one folder, and do a dfs. Then, 
			find the other stuff through do 
			-(some*leaves) + (letters*leaves)
		O(2n) = O(n) algorithm
		
*/