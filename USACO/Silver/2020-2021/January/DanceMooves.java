
import java.util.*;
import java.io.*;

public class DanceMooves {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1086
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//      BufferedReader in = new BufferedReader(new FileReader("b.in"));
		//		PrintWriter out = new PrintWriter(new FileWriter("b.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		int[][] moves = new int[k][2];
		for (int i=0; i<k; i++) {
			st = new StringTokenizer(in.readLine());
			moves[i][0] = Integer.parseInt(st.nextToken())-1;
			moves[i][1] = Integer.parseInt(st.nextToken())-1;
		}
		
		HashSet<Integer>[] passed = new HashSet[n];
			// for each cow, what positions she passes for one run through K
			// max sum of all passed[i].size() = 2K+N
		
		for (int i=0; i<n; i++) {
			passed[i] = new HashSet<>();
			passed[i].add(i);
		}
		
		// simulate
		int[] arr = new int[n];	
		for (int i=0; i<n; i++) arr[i] = i;
		for (int i=0; i<k; i++) {
			s(arr, moves[i]);
			passed[arr[moves[i][0]]].add(moves[i][0]);
			passed[arr[moves[i][1]]].add(moves[i][1]);
		}
		
		
		int[] ans = new int[n];
		
		// find cycles
		boolean[] visited = new boolean[n];
		for (int i=0; i<n; i++) {
			if (visited[i]) continue;
			ArrayList<Integer> cycle = new ArrayList<>();
			HashSet<Integer> all = new HashSet<>();
			int cur = i;
			while (!visited[cur]) {
				visited[cur] = true;
				cycle.add(arr[cur]);
				all.addAll(passed[arr[cur]]);
				cur = arr[cur];
			}
			for (int j=0; j<cycle.size(); j++) {
				ans[cycle.get(j)] = all.size();
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<n; i++) {
			sb.append(ans[i]);
			if (i != n-1) sb.append("\n");
		}
		System.out.println(sb);
		//		out.println();
		//		out.close();

	}
	
	public static void s(int[] arr, int[] move) {
		int i = move[0], j = move[1];
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

}
