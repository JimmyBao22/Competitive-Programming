
import java.util.*;
import java.io.*;

public class PathQueries {

	// https://codeforces.com/problemset/problem/1213/G
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("PathQueries"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		A[] arr = new A[n-1];
		for (int i=0; i<n-1; i++ ) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken());
			arr[i] = new A(a,b,c);
		}
		Arrays.parallelSort(arr);
		DSU dsu = new DSU(n);
		
		st = new StringTokenizer(in.readLine());
		B[] queries = new B[m];
		for (int i=0; i<m; i++) {
			queries[i] = new B(Integer.parseInt(st.nextToken()), i);
		}
		Arrays.parallelSort(queries);
		
		StringBuilder s = new StringBuilder();
		long[] answers = new long[m];
		long ans = 0;
		int p = 0;
		for (int i=0; i<m; i++) {
			while (p < n-1 && arr[p].w <= queries[i].a) {
				int parent1 = dsu.FindSet(arr[p].a);
				int parent2 = dsu.FindSet(arr[p].b);
				ans -= choose(dsu.size[parent1]);
				ans -= choose(dsu.size[parent2]);
				dsu.Union(parent1, parent2);
				ans += choose(dsu.size[dsu.FindSet(parent1)]);
				++p;
			}
			answers[queries[i].i] = ans;
		}
		
		for (int i=0; i<m; i++) s.append(answers[i] + " ");
		
		System.out.println(s);
	}
	
	public static long choose(int x) {
		return (long)(x) * (x-1)/2;
	}
	
	static class A implements Comparable<A> {
		int a, b, w;
		A (int a, int b, int w) {
			this.a = a;
			this.b = b;
			this.w = w;
		}
		public int compareTo(A o) {
			return w - o.w;
		}
	}
	
	static class B implements Comparable<B> {
		int a, i;
		B (int a, int i) {
			this.a = a;
			this.i = i;
		}
		public int compareTo(B o) {
			return a - o.a;
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