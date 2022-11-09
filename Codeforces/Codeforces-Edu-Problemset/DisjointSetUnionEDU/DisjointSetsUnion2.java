
import java.util.*;
import java.io.*;

public class DisjointSetsUnion2 {

	// https://codeforces.com/edu/course/2/lesson/7/1/practice/contest/289390/problem/B
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("DisjointSetsUnion2"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		dsu s = new dsu(n+1);
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			char c = st.nextToken().charAt(0);
			int one = Integer.parseInt(st.nextToken());
			if (c == 'u') {
				int two = Integer.parseInt(st.nextToken());
				s.Union(one,two);
			}
			else {
				int p = s.FindSet(one);
				System.out.println(s.min[p] + " " + s.max[p] + " " + s.size[p]);
			}
		}
	}
	
	static class dsu {
		int n;
		int[] parent;
		int[] size;
		int[] min;
		int[] max;
		
		dsu (int n) {
			this.n = n;
			parent = new int[n];
			size = new int[n];
			min = new int[n]; max = new int[n];
			for (int i=0; i<n; i++) {
				parent[i] = i; size[i] = 1;
				min[i] = i; max[i] = i;
			}
		}

		public int FindSet(int a) {
			if (a == parent[a]) return a;
			return parent[a] = FindSet(parent[a]);
		}
		
		public void Union(int a, int b) {
			a = FindSet(a);
			b = FindSet(b);
			if (a == b) { 	
				return;
			}
			
			if (size[a] < size[b]) {
				parent[a] = b;
				size[b] += size[a];
				max[b] = Math.max(max[b], max[a]);
				min[b] = Math.min(min[b], min[a]);
			}
			else {
				parent[b] = a;
				size[a] += size[b];
				max[a] = Math.max(max[a], max[b]);
				min[a] = Math.min(min[a], min[b]);
			}
		}
	}

}
