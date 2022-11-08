
import java.util.*;
import java.io.*;

public class Confluence {

	// https://atcoder.jp/contests/abc183/tasks/abc183_f
	
	static int n;
	static int[] c;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Confluence"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		c = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) c[i] = Integer.parseInt(st.nextToken());
		
		dsu s = new dsu(n);
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<q; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken());
			int three = Integer.parseInt(st.nextToken());
			if (one == 1) {
				two--; three--;
				s.Union(two, three);
			}
			else {
				two--;
				int par = s.FindSet(two);
				if (s.map[par].containsKey(three)) {
					sb.append(s.map[par].get(three));
				}
				else sb.append(0);
				sb.append("\n");
			}
		}
		System.out.print(sb);
	}
	
	static class dsu {
		int n;
		int[] parent;
		HashMap<Integer, Integer>[] map;
		
		dsu (int n) {
			this.n = n;
			parent = new int[n];
			map = new HashMap[n];
			for (int i=0; i<n; i++) {
				parent[i] = i;
				map[i] = new HashMap<>();
				map[i].put(c[i], 1);
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
			
			if (map[a].size() < map[b].size()) {
				parent[a] = b;
				for (Integer x : map[a].keySet()) {
					map[b].put(x, map[b].getOrDefault(x, 0) + map[a].get(x));
				}
			}
			else {
				parent[b] = a;
				for (Integer x : map[b].keySet()) {
					map[a].put(x, map[a].getOrDefault(x, 0) + map[b].get(x));
				}
			}
		}
	}
}