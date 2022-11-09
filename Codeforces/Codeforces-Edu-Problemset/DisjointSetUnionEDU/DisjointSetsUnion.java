
import java.util.*;
import java.io.*;

public class DisjointSetsUnion {

	// https://codeforces.com/edu/course/2/lesson/7/1/practice/contest/289390/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("DisjointSetsUnion"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		dsu s = new dsu(n+1);
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			char c = st.nextToken().charAt(0);
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken());
			if (c == 'u') {
				s.Union(one, two);
			}
			else {
				if (s.FindSet(one) == s.FindSet(two)) {
					System.out.println("YES");
				}
				else System.out.println("NO");
			}
		}
	}

	static class dsu {
		int n;
		int[] parent;
		int[] size;
		
		dsu (int n) {
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
			if (a == b) { 	
				return;
			}
			
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