
import java.util.*;
import java.io.*;

public class Subtree {

	// https://atcoder.jp/contests/dp/tasks/dp_v
	
	static int n;
	static long mod;
	static ArrayList<Integer>[] g;
	static ArrayList<Long>[] prefixes, suffixes;
	static long[] down, up;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Subtree"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		mod = Integer.parseInt(st.nextToken());
		g = new ArrayList[n];
		prefixes = new ArrayList[n];
		suffixes = new ArrayList[n];
		for (int i=0; i<n; i++) {
			g[i] = new ArrayList<>();
			prefixes[i] = new ArrayList<>();
			suffixes[i] = new ArrayList<>();
		}
		for (int i=0; i<n-1; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			g[a].add(b);
			g[b].add(a);
		}
		
			// # of ways w/ this node black with only this subtree
		down = new long[n];
		
			// # of ways w/ this node black without this subtree
		up = new long[n];
		
		Arrays.fill(down, 1);
		Arrays.fill(up, 1);
		dfs1(0, -1);
		dfs2(0, -1, 1, 1);
		
		StringBuilder s = new StringBuilder();
		for (int i=0; i<n; i++) {
			s.append((down[i] * up[i])%mod + "\n");
		}
		System.out.print(s);
	}
	
	public static void dfs1(int node, int p) {
		for (Integer a : g[node]) {
			if (a != p) {
				dfs1(a, node);
				down[node] *= (down[a]+1);
				down[node] %= mod;
			}
			prefixes[node].add(down[node]);
		}
		long cur = 1;
		for (int i=g[node].size()-1; i>=0; i--) {
			if (g[node].get(i) != p) cur *= (down[g[node].get(i)]+1);
			cur %= mod;
			suffixes[node].add(cur);
		}
	}
	
	public static void dfs2(int node, int p, long pref, long suff) {
		if (p != -1) {
			up[node] = 1 + (up[p] * pref)%mod * suff % mod;

//			up[node] = 1 + (up[p] * down[p])%mod * modInverse1(down[node]+1, mod) % mod;
				// this doesn't work bc mod not prime. Instead need to use pref and suff
					// to store stuff around down[node]
		}
		for (int i=0; i<g[node].size(); i++) {
			if (g[node].get(i) != p) {
				if (i == 0 && i == g[node].size()-1) {
					dfs2(g[node].get(i), node, 1, 1);					
				}
				else if (i == 0) {
					dfs2(g[node].get(i), node, 1, suffixes[node].get(g[node].size()-1-i-1));					
				}
				else if (i == g[node].size()-1) {
					dfs2(g[node].get(i), node, prefixes[node].get(i-1), 1);					
				}
				else {
					dfs2(g[node].get(i), node, prefixes[node].get(i-1), suffixes[node].get(g[node].size()-1-i-1));					
				}
			}
		}
	}
}