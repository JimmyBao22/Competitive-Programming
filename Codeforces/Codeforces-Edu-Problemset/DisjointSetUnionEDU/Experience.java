
import java.util.*;
import java.io.*;

public class Experience {

	// https://codeforces.com/edu/course/2/lesson/7/1/practice/contest/289390/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Experience"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		dsu s = new dsu(n+1);
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			char c = st.nextToken().charAt(0);
			int one = Integer.parseInt(st.nextToken());
			if (c == 'g') {			// get
				int[] ret = s.FindSet(one);
				System.out.println(s.xp[ret[0]] + ret[1]);
				continue;
			}
			int two = Integer.parseInt(st.nextToken());
			if (c == 'j') {			// join
				s.Union(one, two);
			}
			else {				// add
				s.xp[s.FindSet(one)[0]] += two;
			}
		}
	}
	
	static class dsu {
		int n;
		int[] parent;
		int[] size;
		int[] xp;
		
		dsu (int n) {
			this.n = n;
			parent = new int[n];
			size = new int[n];
			xp = new int[n];
			for (int i=0; i<n; i++) {parent[i] = i; size[i] = 1;}
		}

			// {par, xp}
		public int[] FindSet(int a) {
			if (a == parent[a]) return new int[] {a,0};
			int[] ret = FindSet(parent[a]);
			parent[a] = ret[0];
			xp[a] += ret[1];
			return new int[] {parent[a], xp[a]};
		}
		
		public void Union(int a, int b) {
			int[] aa = FindSet(a);
			int[] bb = FindSet(b);
			a = aa[0];
			b = bb[0];
			if (a == b) { 
				return;
			}
			if (size[a] < size[b]) {
				parent[a] = b;
				size[b] += size[a];
				xp[a] -= xp[b];
			}
			else {
				parent[b] = a;
				size[a] += size[b];
				xp[b] -= xp[a];
			}
		}
		
		void print() {
			for (int i=0; i<n; i++) System.out.print(xp[i] + " ");
			System.out.println();
		}
	}
}

/*
	Every time you join two teams, everyone gets the xp that whole team gets, but the xp
		in each team before you join the teams does not add together
		
	Therefore, everytime you join two teams, you lower team needs to subtract out the 
		xp of the top team, so when you call get, where you take xp[this team] + xp[parent],
		the subtracted out value with cancel.
		
	in FindParent, the ret[] array keeps track of the sum of all xp in this path of the
		tree to the root excluding the root.
		
		
	Ex. 
	5 20
	add 3 24
	get 1
	add 2 29
	join 4 5
	join 3 1
	join 3 4
	get 4
	get 4
	add 3 18
	get 2
	join 3 1
	get 1
	get 3
	get 1
	get 5
	join 2 5
	join 5 3
	join 3 5
	join 5 4
	get 5
	
	0, 0, 0, 0, 0, 0 
		
		add 24 to index 3
	0, 0, 0, 24, 0, 0 
		
		add 29 to index 2
	0, 0, 29, 24, 0, 0 
	
		join indices 4,5
	0, 0, 29, 24, 0, 0 

		join indices 3,1
			you need to subtract 24 from index 1. This is because, now, if you want to find
			xp of 1, you will print out xp[parent[1]] + ret[1]
				parent[1] = 3, and xp[3] = 24. Meanwhile, ret[1] = -24, so it will leave
				xp of 1 = 0
	0, -24, 29, 24, 0, 0 
	
		join indices 3,4
	0, -24, 29, 24, -24, 0 
		
		add 18 to index 3
	0, -24, 29, 42, -24, 0 
	
		join indices 3,1. Already joined so nothing happens
	0, -24, 29, 42, -24, -24
	
		join indices 2,5
	0, -24, -13, 42, -24, -24
	
		For other join operations, the indices are already joined so nothing happens
*/