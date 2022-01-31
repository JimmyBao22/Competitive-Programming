
import java.util.*;
import java.io.*;

public class mootube {

	// http://usaco.org/index.php?page=viewproblem2&cpid=789
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("mootube.in"));
		PrintWriter out = new PrintWriter(new FileWriter("mootube.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		int[][] graph = new int[n-1][3];
		
		for (int i=0; i<n-1; i++) {
			st = new StringTokenizer(in.readLine());
			graph[i][0] = Integer.parseInt(st.nextToken())-1;
			graph[i][1] = Integer.parseInt(st.nextToken())-1;
			graph[i][2] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(graph, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return b[2] - a[2];			// largest first
			}
		});
		
		A[] arr = new A[q];
		for (int i=0; i<q; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken())-1;
			arr[i] = new A (one, two, i);
		}
		
		Arrays.parallelSort(arr);
		
		DSU s = new DSU(n);
		int[] ans = new int[q];
		int graphpointer=0;
		for (int i=0; i<arr.length; i++) {
			while (graphpointer<n-1 && graph[graphpointer][2] >= arr[i].k) {
				// create node
				s.join(graph[graphpointer][0], graph[graphpointer][1]);
				graphpointer++;
			}
			ans[arr[i].index] = s.size[s.find(arr[i].v)];
		}
		
		for (int i=0; i<ans.length; i++) {
			System.out.println(ans[i]-1);
			out.println(ans[i]-1);
		}
		
		out.close();
	}
	
	static class A implements Comparable<A> {
		int k;
		int v;
		int index;
		A (int a, int b, int c) {
			k=a; v=b; index=c;
		}
		public int compareTo(A o) {
			return o.k - k;			// biggest first
		}
	}
	
	static class DSU {
		int n;
		int[] parent;
		int[] size;
		DSU(int a) {
			n = a;
			parent = new int[n];
			size = new int[n];
			for (int i=0; i<n; i++) {
				parent[i] = i;
				size[i] = 1;
			}
		}
		
		public int find (int a) {
			if (a == parent[a]) return a;
			return parent[a] = find(parent[a]);
		}
		
		public void join (int a, int b) {
			a = find(a);
			b = find(b);
			if (size[a]<size[b]) {
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