
import java.util.*;
import java.io.*;

public class ShortestCycle {

	// https://codeforces.com/contest/1205/problem/B
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ShortestCycle"));

		int n = Integer.parseInt(in.readLine());
		long[] arr = new long[n];
		HashSet<Integer>[] g = new HashSet[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++ ) {
			arr[i] = Long.parseLong(st.nextToken());
			g[i] = new HashSet<>();
		}
		
		ArrayList<Integer>[] pows = new ArrayList[65];
		for (int i=0; i<pows.length; i++) {
			pows[i] = new ArrayList<>();
			for (int j=0; j<n; j++) {
				if (((arr[j] >> i) & 1) >= 1) {
					pows[i].add(j);
				}
			}
			if (pows[i].size() > 2) {
				System.out.println(3);
				return;
			}
		}
		
		for (int i=0; i<pows.length; i++) {
			for (int j=0; j<pows[i].size(); j++) {
				for (int k=j+1; k<pows[i].size(); k++) {
					g[pows[i].get(j)].add(pows[i].get(k));
					g[pows[i].get(k)].add(pows[i].get(j));
				}
			}
		}
		
		int min = n+1;
		int[] dist = new int[n];
		Arrays.fill(dist, (int)(1e9));
		int[] parent = new int[n];
		Arrays.fill(parent, -1);
		for (int i=0; i<n; i++) {
			HashSet<Integer> visited = new HashSet<>();
			ArrayDeque<Integer> d = new ArrayDeque<>();
			d.add(i);
			dist[i] = 0;
			while (!d.isEmpty()) {
				int cur = d.poll();
				visited.add(cur);
				
				for (Integer a : g[cur]) {
					if (dist[a] == (int)(1e9)) {
						dist[a] = dist[cur]+1;
						parent[a] = cur;
						d.add(a);
					}
					else {
						if (parent[a] != cur && parent[cur] != a) {
							min = Math.min(min, dist[cur] + dist[a]+1);
						}
					}					
				}
			}
			// reset
			for (Integer j : visited) {
				dist[j] = (int)(1e9);
				parent[j] = -1;
			}
		}
		
		if (min > n) {
			System.out.println(-1);
		}
		else {
			System.out.println(min);
		}
	}

	public static void sort(int[] a) {
		shuffle(a);
		Arrays.sort(a);
	}

	public static void shuffle(int[] a) {
		Random get = new Random();
		for (int i = 0; i < a.length; i++) {
			int r = get.nextInt(a.length);
			int temp = a[i];
			a[i] = a[r];
			a[r] = temp;
		}
	}
}