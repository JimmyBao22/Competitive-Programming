
import java.util.*;
import java.io.*;

public class ConnectingTwoBarns {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1159
	
	static int n, m;
	static ArrayList<Integer>[] g, components;
	static DSU s;
	static long[] distance_from_beg, distance_from_end;
	static long INF = (long)(1e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//      BufferedReader in = new BufferedReader(new FileReader("ConnectingTwoBarns.in"));
		//		PrintWriter out = new PrintWriter(new FileWriter("ConnectingTwoBarns.out"));

		int t = Integer.parseInt(in.readLine());
		while (t --> 0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			g = new ArrayList[n];
			components = new ArrayList[n];
			for (int i=0; i<n; i++) {
				g[i] = new ArrayList<>();
				components[i] = new ArrayList<>();
			}
			s = new DSU(n);
			for (int i=0; i<m; i++) {
				st = new StringTokenizer(in.readLine());
				int a = Integer.parseInt(st.nextToken())-1;
				int b = Integer.parseInt(st.nextToken())-1;
				g[a].add(b);
				g[b].add(a);
				s.Union(a, b);
			}
			
			for (int i=0; i<n; i++) {
				int p = s.FindSet(i);
				components[p].add(i);		// only top parents have component size != 0
			}
			
			distance_from_beg = new long[n];
			distance_from_end = new long[n];
			Arrays.fill(distance_from_beg, INF);
			Arrays.fill(distance_from_end, INF);
			
			// calc distance from 0 to everything
			int pointer = 0;
			int p0 = s.FindSet(0);
			distance_from_beg[p0] = 0;
			for (int i=1; i<n; i++) {
				int pcur = s.FindSet(i);
				if (p0 != pcur) {
					while (pointer+1 < components[p0].size() && dist(i,components[p0].get(pointer+1)) < dist(i,components[p0].get(pointer))) {
						pointer++;
					}
					distance_from_beg[pcur] = Math.min(distance_from_beg[pcur], dist(i, components[p0].get(pointer)));
				}
			}
			
			// calc distance from n-1 to everything
			pointer = 0;
			int pn1 = s.FindSet(n-1);
			distance_from_end[pn1] = 0;
			for (int i=1; i<n; i++) {
				int pcur = s.FindSet(i);
				if (pn1 != pcur) {
					while (pointer+1 < components[pn1].size() && dist(i,components[pn1].get(pointer+1)) < dist(i,components[pn1].get(pointer))) {
						pointer++;
					}
					distance_from_end[pcur] = Math.min(distance_from_end[pcur], dist(i, components[pn1].get(pointer)));
				}
			}
			
			long ans = distance_from_end[p0];		// go straight from component 0 to component n-1
			
			// go from component 0 to component i then component i to component n-1
			for (int i=1; i<n-1; i++) {
				// go to this transition component
				int pcur = s.FindSet(i);
				ans = Math.min(ans, distance_from_beg[pcur] + distance_from_end[pcur]);
			}

			System.out.println(ans);
		}

		//		out.println();
		//		out.close();
	}
	
	public static long dist(long a, long b) {
		return (a - b) * (a - b);
	}

	static class DSU {
		int n;
		int[] parent;
		int[] size;
		
		DSU (int n) {
			this.n = n;
			parent = new int[n];
			size = new int[n];
			for (int i=0; i<n; i++) {parent[i] = i; size[i] = 1;}
		}

		public int FindSet(int a) {
			if (a == parent[a]) return a;
			return parent[a] = FindSet(parent[a]);
		}
		
		public void Union(int a, int b) {
			a = FindSet(a);
			b = FindSet(b);
			if (a == b) return;
			
			if (size[a] < size[b]) {
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