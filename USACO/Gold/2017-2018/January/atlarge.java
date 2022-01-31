
import java.util.*;
import java.io.*;

public class atlarge {

	// http://usaco.org/index.php?page=viewproblem2&cpid=790
	
	static ArrayList<ArrayList<Integer>> g = new ArrayList<>();
	static ArrayList<Integer> leaves = new ArrayList<>();
	static int[] distance;
	static int n,k;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("atlarge.in"));
		PrintWriter out = new PrintWriter(new FileWriter("atlarge.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken())-1;
		distance = new int[n];
		
		for (int i=0; i<n; i++) g.add(new ArrayList<>());
		for (int i=0; i<n-1; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two= Integer.parseInt(st.nextToken())-1;
			g.get(one).add(two);
			g.get(two).add(one);
		}
		
			// already a leaf
		if (g.get(k).size() == 1) {
			System.out.println(1);
			out.println(1);
			out.close();
			return;
		}
		
		Arrays.fill(distance, (int)(1e9));
		
		bfs();
				
		//for (int i=0; i<n; i++) System.out.print(distance[i] + " ");
		
		boolean[] bad = new boolean[n];
		int needed=0;
		for (int i=0; i<leaves.size(); i++) {
			int lengthneeded = (distance[leaves.get(i)])/2;
			bfs2(leaves.get(i), lengthneeded, bad);
		}
		
		needed = bfs3(bad);

		System.out.println(needed);
		out.println(needed);
		out.close();
	}
	
	public static int bfs3(boolean[] bad) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
			// the og node
		d.add(new int[] {k,-1});
		boolean[] visited = new boolean[n];
		int count=0;
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			if (visited[cur[0]]) continue;
			visited[cur[0]] = true;
			if (bad[cur[0]]) {
				count++;
				continue;
			}
			for (Integer a : g.get(cur[0])) {
				if (a != cur[1]) {
					d.add(new int[] {a, cur[0]});
				}
			}
		}
		return count;
	}
	
	public static void bfs2(int node, int num, boolean[] bad) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {node, -1, 0});
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			if (bad[cur[0]]) return;
			bad[cur[0]] = true;
			if (cur[2] == num) continue;
			for (Integer a : g.get(cur[0])) {
				if (a == cur[1]) continue;
				d.add(new int[] {a, cur[0], cur[2]+1});
			}
		}
	}
	
	public static void bfs() {
			// find all leaves. Also update distance from node K
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {k, -1, 0});
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			int x = cur[0]; int parent = cur[1]; int val = cur[2];
			distance[x] = Math.min(distance[x], val);
			if (g.get(x).size() == 1) {
				leaves.add(x);
				continue;
			}
			for (Integer a : g.get(x)) {
				if (a!=parent) {
					d.add(new int[] {a,x, val+1});
				}
			}
		}
	}
}