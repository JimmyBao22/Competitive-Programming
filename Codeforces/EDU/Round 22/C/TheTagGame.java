
import java.util.*;
import java.io.*;

public class TheTagGame {

	// https://codeforces.com/contest/813/problem/C
	static ArrayList<ArrayList<Integer>> g = new ArrayList<>();
	static HashMap<Integer, Integer> distance = new HashMap<>();
	static int n;
	static int x;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("TheTagGame"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken())-1;
		for (int i=0; i<n; i++) {
			ArrayList<Integer> cur= new ArrayList<>();
			g.add(cur);
		}
		
		for (int i=0; i<n-1; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			g.get(a).add(b);
			g.get(b).add(a);
		}
		
		dfs();
		
		int m = distance.get(x);
		int mid = (m - 1)/2;
		int maxnode = dfs2(mid);
		
		HashSet<Integer> visited = new HashSet<>();
		int maxseclength = dfs3(maxnode, 0, visited);
		
		System.out.println(2*mid + 2*(m-2*mid+maxseclength));
	}
	
	// maximum depth of a subtree of node node (going downwards)
	public static int dfs3 (int node, int length, HashSet<Integer> visited) {
		if (visited.contains(node)) return Integer.MIN_VALUE;
		visited.add(node);
		if (g.get(node).size() == 1) {
			if (node == 0) {
				return Integer.MIN_VALUE;
			}
			else {
				return length;
			}
		}
		int max=0;
		for (int i=0; i<g.get(node).size(); i++) {
			if (distance.containsKey(g.get(node).get(i)) && distance.containsKey(node) && distance.get(g.get(node).get(i)) < distance.get(node)) continue;
			max = Math.max(max, dfs3(g.get(node).get(i), length+1, visited));
		}
		return max;
	}
	
	// dfs to previous node. from x, closest one distance mid away from x going upwards
	public static int dfs2 (int mid) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		HashSet<Integer> visited = new HashSet<>();
		d.add(new int[] {x, 0});
		while (d.size() > 0) {
			int[] cur = d.poll();
			if (cur[0] < 0 || cur[1] >= n) continue;
			if (visited.contains(cur[0])) continue;
			visited.add(cur[0]);
			if (cur[1] == mid) return cur[0];
			if (cur[1] > mid) continue;
			for (int i=0; i<g.get(cur[0]).size(); i++) {
				if (!distance.containsKey(g.get(cur[0]).get(i))) continue;
				if (distance.get(g.get(cur[0]).get(i)) >= distance.get(cur[0])) continue;
					// go to node with smaller distance
				d.add(new int[] {g.get(cur[0]).get(i), cur[1]+1});
			}
		}
		return -1;
	}
	
	// dfs to find x. also update distance
	public static void dfs () {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		HashSet<Integer> visited = new HashSet<>();
		d.add(new int[] {0, 0});
		while (d.size() > 0) {
			int[] cur = d.poll();
			if (cur[0] < 0 || cur[1] >= n) continue;
			if (visited.contains(cur[0])) continue;
			visited.add(cur[0]);
			distance.put(cur[0], cur[1]);
			if (cur[0] == x) break;
			for (int i=0; i<g.get(cur[0]).size(); i++) {
				d.add(new int[] {g.get(cur[0]).get(i), cur[1]+1});
			}
		}
	}
}