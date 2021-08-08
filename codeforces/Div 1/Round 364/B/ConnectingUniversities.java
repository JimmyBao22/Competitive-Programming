
import java.util.*;
import java.io.*;

public class ConnectingUniversities {

	// https://codeforces.com/problemset/problem/700/B
	
	static int n, k;
	static boolean[] special;
	static ArrayList<Integer>[] g;
	static long ans = 0;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ConnectingUniversities"));

		
		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		k *= 2;
		special = new boolean[n];
		g = new ArrayList[n];
		for (int i=0; i<n; i++) {
			g[i] = new ArrayList<>();
		}
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<k; i++) {
			int a = Integer.parseInt(st.nextToken())-1;
			special[a] = true;
		}
		for (int i=0; i<n-1; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			g[a].add(b);
			g[b].add(a);
		}
		
		dfs(0, 0);
		System.out.println(ans);
	}
	
	public static A dfs(int node, int p) {
		// always bring the nodes up as much as you can
		int left = k;	// # nodes above me that I did not use yet
		int unused = 0;	// # nodes below me that I did not use yet
		int seen = 0;	// # nodes below me
		for (Integer a : g[node]) {
			if (a != p) {
				A ret = dfs(a, node);
				left -= ret.seen;
				seen += ret.seen;
				ans += ret.cur;
				unused += ret.cur;
			}
		}
		if (special[node]) {
			unused++;
			left--;
			seen++;
		}
		
		return new A(seen, Math.min(left, unused));
	}
	
	static class A {
		long seen, cur;
		A (long a, long b) {
			seen = a;
			cur = b;
		}
	}
}