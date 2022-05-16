import java.util.*;
import java.io.*;
 
public class ForestQueriesII {
 
	// https://cses.fi/problemset/task/1739/
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ForestQueriesII"));
 
		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		char[][] arr = new char[n][];
		BIT2D bit = new BIT2D(n,n);
		for (int i=0; i<n; i++) {
			arr[i] = in.readLine().toCharArray();
			for (int j=0; j<n; j++) {
				if (arr[i][j] == '*') bit.set(i,j,1);
			}
		}
		
		StringBuilder s = new StringBuilder();
		for (int i=0; i<q; i++) {
			st = new StringTokenizer(in.readLine());
			int c=Integer.parseInt(st.nextToken());
			if (c == 2) {
				int one=Integer.parseInt(st.nextToken())-1;
				int two=Integer.parseInt(st.nextToken())-1;
				int three=Integer.parseInt(st.nextToken())-1;
				int four=Integer.parseInt(st.nextToken())-1;
				s.append(bit.sum(one,two,three,four));
				s.append("\n");
			}
			else {
				int one=Integer.parseInt(st.nextToken())-1;
				int two=Integer.parseInt(st.nextToken())-1;
				if (arr[one][two] == '.') {
					arr[one][two] = '*';
					bit.set(one, two, 1);
				}
				else {
					arr[one][two] = '.';
					bit.set(one, two, -1);
				}
			}
		}
		System.out.print(s);
	}
	
	static class BIT2D {
		int n,m;
		long[][] f;		// 1 base indexing
		BIT2D(int n, int m) {
			this.n = n; this.m = m; f = new long[n+1][m+1];
		}
		
		// sum from (i,j) to (0,0)
		long sum (int i, int j) {
			i++; j++;
			long ret=0;
			while (i>0) {
				int y=j;
				while (y>0) {
					ret += f[i][y];
					y -= y&-y;
				}
				i -= i&-i;
			}
			return ret;
		}
		
		// sum from (i1, j1) to (i2, j2)
		long sum(int i1, int j1, int i2, int j2) {
			return sum(i2, j2) - sum(i1-1, j2) - sum(i2, j1-1) + sum(i1-1, j1-1);
		}
		
		// add value to (i,j)
		void set(int i, int j, long val) {
			i++; j++;
			while (i<=n) {
				int y=j;
				while (y<=m) {
					f[i][y] += val;
					y += y&-y;
				}
				i += i&-i;
			}
		}
		
		// add value from (i1, j1) to (i2, j2) --> arr[i][j] = sum(i, j);
		void range_set(int i1, int j1, int i2, int j2, long val) {
			set(i1, j1, val);
			set(i1, j2+1, -val);
			set(i2+1, j1, -val);
			set(i2+1, j2+1, val);
		}
	}
}