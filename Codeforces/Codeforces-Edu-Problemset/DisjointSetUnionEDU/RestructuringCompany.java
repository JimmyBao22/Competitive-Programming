
import java.util.*;
import java.io.*;

public class RestructuringCompany {

	// https://codeforces.com/edu/course/2/lesson/7/2/practice/contest/289391/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("RestructuringCompany"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		dsu d = new dsu(n+1);
		dsu next = new dsu(n+1);		// arr[i] = finds index of next element right of 
											// i that is not on the same team
		StringBuilder s = new StringBuilder();
		for (int i=0; i<q; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken())-1;
			int three = Integer.parseInt(st.nextToken())-1;
			if (one == 1) {		// merge 2
				d.Union(two, three);
				if (three == two+1 || two == three+1) {		// consecutive
					next.Union(two, three);
				}
			}
			else if (one == 2) {		// merge segment
				for (int j=two+1; j<=three; j++) {
					j = next.max[next.FindSet(j)];
					if (j>three) j = three;
					d.Union(two, j);
					next.Union(j, j+1);
				}
			}
			else {			// check if one and two same
				if (d.FindSet(two) == d.FindSet(three)) {
					s.append("YES" + "\n");
				}
				else s.append("NO" + "\n");
			}
		}
		System.out.println(s);
	}
	
	static class dsu {
		int n;
		int[] parent;
		int[] size;
		int[] max;
		
		dsu (int n) {
			this.n = n;
			parent = new int[n];
			size = new int[n];
			max = new int[n];
			for (int i=0; i<n; i++) {parent[i] = i; size[i] = 1; max[i] = i;}
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
				max[b] = Math.max(max[b], max[a]);
			}
			else {
				parent[b] = a;
				size[a] += size[b];
				max[a] = Math.max(max[a], max[b]);
			}
		}
	}
}

/*

	Create two dsu
		The first one (d), will control which teams are merged together and which are not
			(standard dsu)
		The second one (next), will control which consecutive teams are all merged together
			For example if 1,2,3,5 are merged together, next will make 1,2,3 merged 
			because they are consecutive.
		 
		When operation 2 is done, keep using the next.max[i] to find the next value for j to merge
			This is because if you have a lot of consecutive people on the same team, for example 
				1,2,3,
				going through all of them will be too slow, so just go through the rightmost ones
					(in this case only visit 3 if 1 or 2 is called on)
*/