
import java.util.*;
import java.io.*;

public class Portals {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1138
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Portals"));

		int n = Integer.parseInt(in.readLine());
		A[] arr = new A[n];
		DSU dsu = new DSU(2*n);
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken())-1;
			int e = Integer.parseInt(st.nextToken())-1;
			arr[i] = new A(a,b,c,d,e);
			dsu.Union(b, c);
			dsu.Union(d, e);
		}
		
		Arrays.parallelSort(arr);
		
		int ans = 0;
		for (int i=0; i<n; i++) {
			int p1 = dsu.FindSet(arr[i].a);
			int p2 = dsu.FindSet(arr[i].c);
			if (p1 != p2 && (dsu.size[p1] > 2 || dsu.size[p2] > 2)) {
				dsu.Union(p1, p2);
				ans += arr[i].cost;
			}
		}
		System.out.println(ans);
	}
	
	static class A implements Comparable<A> {
		int cost, a, b, c, d;
		A (int a, int b, int c, int d, int e) {
			cost = a;
			this.a = b;
			this.b = c;
			this.c = d;
			this.d = e;
		}
		
		public int compareTo(A o) {
			return cost - o.cost;
		}
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