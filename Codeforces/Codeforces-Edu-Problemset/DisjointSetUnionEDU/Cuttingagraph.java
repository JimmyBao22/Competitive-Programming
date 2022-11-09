
import java.util.*;
import java.io.*;

public class Cuttingagraph {

	// https://codeforces.com/edu/course/2/lesson/7/1/practice/contest/289390/problem/D
	
	static int n,m,k;
	static int[][] edges;
	static op[] operations;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Cuttingagraph"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		edges = new int[m][2];
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			edges[i][0] = Integer.parseInt(st.nextToken())-1;
			edges[i][1]= Integer.parseInt(st.nextToken())-1;
		}
		operations = new op[k];
		for (int i=0; i<k; i++) {
			st = new StringTokenizer(in.readLine());
			char c = st.nextToken().charAt(0);
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			operations[i] = new op(c,one,two);
		}
		
		dsu d = new dsu(n);
		ArrayList<String> ans = new ArrayList<>();
		for (int i=k-1; i>=0; i--) {
			op cur = operations[i];
			if (cur.type == 'a') {		// ask
				if (d.FindSet(cur.one) == d.FindSet(cur.two)) {
					// same tree
					ans.add("YES");
				}
				else ans.add("NO");
			}
			else {
				// union
				d.Union(cur.one, cur.two);
			}
		}
		for (int i=ans.size()-1; i>=0; i--) {
			System.out.println(ans.get(i));
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
	
	static class op {
		char type; int one; int two;
		op(char a, int b, int c) {
			type = a; one = b; two = c;
		}
	}
}

/*

	whenever there are remove operations, go backwards when using dsu
	Therefore, you can actually build the tree rather than take apart the tree

*/