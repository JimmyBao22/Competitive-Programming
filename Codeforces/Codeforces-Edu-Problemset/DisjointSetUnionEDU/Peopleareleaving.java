
import java.util.*;
import java.io.*;

public class Peopleareleaving {

	// https://codeforces.com/edu/course/2/lesson/7/2/practice/contest/289391/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Peopleareleaving"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		dsu d = new dsu(n+1);
		StringBuilder s = new StringBuilder();
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			char c = st.nextToken().charAt(0);
			int one = Integer.parseInt(st.nextToken())-1;
			if (c == '?') {
				int p = d.FindSet(one);
				if (d.max[p] == n) {
					s.append(-1 + "\n");
				}
				else {
					s.append(d.max[p]+1 + "\n");
				}
			}
			else {
				d.Union(one, one+1);
			}
		}
		System.out.println(s);
	}
	
	static class dsu {
		int n;
		int[] parent;
		int[] max;
		int[] size;
		
		dsu (int n) {
			this.n = n;
			parent = new int[n];
			max = new int[n];
			size = new int[n];
			for (int i=0; i<n; i++) {parent[i] = i; max[i] = i; size[i] = 1;}
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
	create a dsu. Because all the numbers of people are 0 to n-1 (0 base indexing),
		if someone leaves, then the next person is the closest person on its right that 
		has not left yet
	Therefore, in dsu, when node x is removed, just make an edge going from node
		x to node x+1, because this means when you now check node x, it will go to node x+1
		so, parent[x] = x+1. Though, this is too slow. Therefore, note that in a group where
		
	parent[i] = i+1, parent[i+1] = i+2, ... parent[x] = x+1, parent[x+1] = x+1,
	
		the max in this group will be the number that is returned. Therefore, simply keep
			a maximum array, and the max[parent[i]] will be the number that should be returned.


*/