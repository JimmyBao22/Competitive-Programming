
import java.util.*;
import java.io.*;

public class moop {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1040
	
	static int n;
	static A[] arr;
	static ArrayList<Integer>[] g;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("moop.in"));
		PrintWriter out = new PrintWriter(new FileWriter("moop.out"));

		int n = Integer.parseInt(in.readLine());
		A[] arr = new A[n];
		g = new ArrayList[n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i] = new A(i, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			g[i] = new ArrayList<>();
		}
		
		Arrays.parallelSort(arr);
		
		TreeMap<Integer, Integer> map = new TreeMap<>();
		for (int i=0; i<n; i++) {
			Integer lower = map.floorKey(arr[i].y);
			if (lower != null) {
				while (lower != null) {
					g[i].add(map.get(lower));
					g[map.get(lower)].add(i);
					lower = map.floorKey(lower-1);
				}
			}
			else {
				map.put(arr[i].y, i);
			}
		}
		
		int count=0;
		boolean[] visited = new boolean[n];
		for (int i=0; i<n; i++) {
			if (!visited[i]) {
				count++;
				dfs(visited, i);
			}
		}
		
		System.out.println(count);
		out.println(count);
		out.close();

	}
	
	public static void dfs(boolean[] visited, int node) {
		if (visited[node]) return;
		visited[node] = true;
		for (Integer i : g[node]) dfs(visited, i);
	}
	
	static class A implements Comparable<A> {
		int index, x, y;
		A (int a, int b, int c) {
			index = a; x = b; y = c;
		}
		public int compareTo(A o) {
			if (x == o.x) return y - o.y;
			return x - o.x;
		}
	}
}