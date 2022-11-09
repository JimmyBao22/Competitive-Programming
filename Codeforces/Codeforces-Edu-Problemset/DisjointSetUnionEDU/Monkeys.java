
import java.util.*;
import java.io.*;

public class Monkeys {

	// https://codeforces.com/edu/course/2/lesson/7/1/practice/contest/289390/problem/E
	
	static int[] ans;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Monkeys"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[][] all = new int[n][2];
		for (int i=0; i<n; ++i) {
			st = new StringTokenizer(in.readLine());
			all[i][0] = Integer.parseInt(st.nextToken())-1;
			all[i][1] = Integer.parseInt(st.nextToken())-1;
		}
		int[][] special = new int[m][2];
		HashMap<Integer, HashSet<Integer>> special_map = new HashMap<>();
			// node, hand
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			if (special_map.containsKey(one)) {
				special_map.get(one).add(two);
			}
			else {
				HashSet<Integer> c = new HashSet<Integer>();
				c.add(two);
				special_map.put(one, c);
			}
			special[i][0] = one;
			special[i][1] = two;
		}
		
		ans = new int[n];
		Arrays.fill(ans, (int)(1e9));
		
		dsu d = new dsu(n);
		for (int i=0; i<n; i++) {
			if (!special_map.containsKey(i)) {
				if (all[i][0] >= 0) {
					d.Union(i, all[i][0]);
				}
				if (all[i][1] >=0 ) {
					d.Union(i, all[i][1]);
				}
			}
			else if (!special_map.get(i).contains(0)) {
				if (all[i][0] >= 0) {
					d.Union(i, all[i][0]);
				}
			}
			else if (!special_map.get(i).contains(1)) {
				if (all[i][1] >=0 ) {
					d.Union(i, all[i][1]);
				}
			}
		}
		
		for (int i=0; i<n; i++) {
			if (d.FindSet(0) == d.FindSet(i)) {
				ans[i] = -1;
				d.children.get(0).remove(i);
			}
		}
		
			// add back edges
		for (int i=m-1; i>=0; i--) {
			boolean good = false;
			int node1 = special[i][0];
			int node2 = all[special[i][0]][special[i][1]];
				// draw edge between node1 and node2
			if (node1 == 0 || d.FindSet(0) != d.FindSet(node1)) good = true;
				// this means this node is 0 OR
					//nodes 0 and this edge are not currently connected
			if (node2 == 0 || d.FindSet(0) != d.FindSet(node2)) good = true;
			
			d.Union(node1, node2);
			if (good && d.FindSet(0) == d.FindSet(node1)) {
				// now, after operation, it got connected!
				int parent = d.FindSet(node1);
				for (Integer c : d.children.get(parent)) {
					if (ans[c] == (int)(1e9)) { 	
						ans[c] = i;				// becomes good at this turn
					}
				}
				d.children.get(parent).clear();
			}
		}
		ans[0] = -1;
		StringBuilder s = new StringBuilder();
		for (int i=0; i<n; i++) {
			if (ans[i] == (int)(1e9)) s.append(-1 + "\n");
			else s.append(ans[i] + "\n");
		}
		System.out.println(s);	
	}
	
	static class dsu {
		int n;
		int[] parent;
		int[] size;
		ArrayList<HashSet<Integer>> children;
		
		dsu (int n) {
			this.n = n;
			parent = new int[n];
			size = new int[n];
			children = new ArrayList<>();
			for (int i=0; i<n; i++) {
				parent[i] = i; size[i] = 1; 
				children.add(new HashSet<>());
				children.get(i).add(i);
			}
		}

		public int FindSet(int a) {
			if (a == parent[a]) return a;
			return parent[a] = FindSet(parent[a]);
		}
		
		public void Union(int a, int b) {
			a = FindSet(a);
			b = FindSet(b);
			if (a == b) return;
			
			if (children.get(a).size() < children.get(b).size()) {
				parent[a] = b;
				size[b] += size[a];
				// add children of a to b
				for (Integer i : children.get(a)) {
					if (ans[i] != (int)(1e9)) continue;
					children.get(b).add(i);
				}
			}
			else {
				parent[b] = a;
				size[a] += size[b];
				// add children of b to a
				for (Integer i : children.get(b)) {
					if (ans[i] != (int)(1e9)) continue;
					children.get(a).add(i);
				}
			}
		}
	}
}
/*

	first take input, the create ans array (filled with infinty), 
		go through all the nodes that stay in the graph the whole way through (never removed), 
		and combine those in the dsu. Then, for all nodes already connected with node 0, 
		just set ans[i] = -1. 
	
	From there, go through all operations backwards, so this way you are
	adding in nodes instead of removing.
	
	Boolean good just means if something was not connected to node 0 before,
		then ur going to do union and then check if something is connected to node 0 now
			after operation
	IF it got connected, then find parent and loop through all children of parent, 
		and if ans[i] = INF, then update ans[i]
		At the end of this loop, I know that all ans[i] != INF no matter what, so
			I can just do "d.children.get(parent).clear();" so I never
			check these same nodes again.

*/